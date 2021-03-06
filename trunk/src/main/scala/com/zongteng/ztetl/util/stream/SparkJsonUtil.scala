package com.zongteng.ztetl.util.stream

import java.lang.reflect.Field
import java.{io, util}

import com.google.gson.Gson
import com.zongteng.ztetl.common.MysqlDatabase
import com.zongteng.ztetl.entity.common.{AssistParameter, InsertModel}
import com.zongteng.ztetl.util.{DataNullUtil, DateUtil}
import org.apache.commons.lang.StringUtils
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.util.Bytes

object SparkJsonUtil {

  private val gson: Gson = new Gson()

  def getAssistParameter(database: String, table: String) = {

    var parameter: AssistParameter = null
    if (parameter == null) {
      //parameter = PickingAssistParameterUtil.getEntityObject(database, table)
    } else if (parameter == null) {

    }

    if (parameter == null) {
      throw new Exception("数据库 {" + database + "} 中表{" + table + "} 对应的方法不存在,请添加")
    }

    parameter
  }

  /**
    *
    * @param insertModel 数据信息
    * @return
    */

  def getHBasePut(insertModel: InsertModel): util.ArrayList[Put] = {

    // 最终返回的记录
    val putList: util.ArrayList[Put] = new java.util.ArrayList[Put]

    // 获取数据,可能里面有多条
    val maps: Array[util.HashMap[String, String]] = insertModel.data

    // mysql数据名
    val database: String = insertModel.database

    // mysql表名
    val table: String = insertModel.table

    // mysql记录修改方式(update、insert、delete)
    val action_type: String = insertModel.`type`

    var parameter: AssistParameter = null

    // 相关参数的样例类
    if (database.startsWith("wms", 0) || database.startsWith("oms", 0)) {

      parameter = AssistParameterUtil.getEntityObject(database, table)

    } else if (database.startsWith("owms", 0)) {

      parameter = GcOwmsAssistParameterUtil.getEntityObject(database, table)

    } else if (database.startsWith("ems", 0)) {

      parameter = ZyOwmsAssistParameterUtil.getEntityObject(database, table)

    } else if ("gc_inventory_batch_log".equalsIgnoreCase(database)) {

      parameter = BatchLogAssistParameterUtil.getEntityObject(database, table)

    } else {

      parameter = getAssistParameter(database, table)

    }

    // 样例类的Class
    val entityClass: Class[_] = parameter.className

    // mysql中主键
    val primaryKey: String = parameter.primaryKey


    // 系统编号
    val datasource_num_id: String = parameter.datasource_num_id

    // hbase列族
    val column_family: String = parameter.cf

    // mysql新增时间
    val add_time: String = parameter.add_time_field

    // mysql时间戳
    val update_time_1: String = parameter.update_time_field_1

    // mysql修改时间
    val update_time_2: String = parameter.update_time_field_2

    var put: Put = null

    // 如果data为null，那么返回一个空集合
    if (maps == null) {
      return putList
    }

    for (i <- 0 until maps.size) {

      // 赋值
      val map: util.HashMap[String, String] = maps(i)

      // 转换成样例类
      val entityObject: io.Serializable = gson.fromJson(gson.toJson(map), entityClass)

      // 通过实体类的字段个数、字段名字、字段值，构造一行数据
      val fields: Array[Field] = entityObject.getClass.getDeclaredFields

      // 设置Hbase中的rowKey
      val fieldValue: String = map.get(primaryKey)
      //gc_inventory_batch_log库中的表的数据都汇总到ods层的一个表中,row_key为系统标号+主键标号+下划线+表名
      if ("gc_inventory_batch_log".equalsIgnoreCase(database)) {
        put = new Put(Bytes.toBytes(datasource_num_id.concat(fieldValue).concat("_").concat(table)))
      } else {
        put = new Put(Bytes.toBytes(datasource_num_id + fieldValue))
      }

      // 将integration_id添加进去
      put.addColumn(Bytes.toBytes(column_family), Bytes.toBytes("integration_id"), Bytes.toBytes(fieldValue))

      // 添加系统编号
      put.addColumn(Bytes.toBytes(column_family), Bytes.toBytes("datasource_num_id"), Bytes.toBytes(datasource_num_id))

      // 设置ETL编号
      put.addColumn(Bytes.toBytes(column_family), Bytes.toBytes("etl_proc_wid"), Bytes.toBytes(DateUtil.getNowTime()))

      // 如果是新增,就添加新增时间,否则不添加
      if ("insert".equalsIgnoreCase(action_type)) {
        put.addColumn(Bytes.toBytes(column_family), Bytes.toBytes("w_insert_dt"), Bytes.toBytes(DateUtil.getNow()))
      }

      // 设置修改时间
      put.addColumn(Bytes.toBytes(column_family), Bytes.toBytes("w_update_dt"), Bytes.toBytes(DateUtil.getNow()))

      // 设置操作类型
      put.addColumn(Bytes.toBytes(column_family), Bytes.toBytes("data_flag"), Bytes.toBytes(action_type))

      // 设置外键中的新增和修改时间
      var created_on_dt = DataNullUtil.nvlDateTime(map.get(add_time))
      put.addColumn(Bytes.toBytes(column_family), Bytes.toBytes("created_on_dt"), Bytes.toBytes(created_on_dt))


      var changed_on_dt = getChangedOnDt(map.get(update_time_1), map.get(update_time_2))
      put.addColumn(Bytes.toBytes(column_family), Bytes.toBytes("changed_on_dt"), Bytes.toBytes(changed_on_dt))


      // 获取其它要插入的列值row
      fields.foreach((field: Field) => {

        val fieldName: String = field.getName
        var fieldValue: String = DataNullUtil.nvlNull(map.get(fieldName))

        //如果是需要替换的表字段,则重新赋值
        if(filterFiledAndTable(database,table,fieldName)){
          fieldValue=fieldValue.replaceAll("\r","").replaceAll("\n"," ")
        }


        //判断值是否在时间列表中
        //        if (timeList.contains(fieldName)) {
        //          fieldValue = DataNullUtil.nvlDateTime(fieldValue)
        //        } else if (default_null_ints.contains(fieldName)) {
        //          fieldValue = DataNullUtil.nvlInt(fieldValue)
        //        } else if (default_null_strings.contains(fieldName)) {
        //          fieldValue = DataNullUtil.nvlString(fieldValue)
        //        } else {
        //          fieldValue = DataNullUtil.nvlString(fieldValue)
        //        }

        //列族，列，值
        put.addColumn(Bytes.toBytes(column_family), Bytes.toBytes(fieldName), Bytes.toBytes(fieldValue))
      })

      putList.add(put)
    }

    putList
  }

  def getHBaseTable(jsonStr: String) = {
    getInsertObject(jsonStr).table
  }

  def getInsertObject(jsonStr: String) = {

    var insertModel: InsertModel = null

    if (StringUtils.isNotBlank(jsonStr)) {
      insertModel = gson.fromJson(jsonStr, classOf[InsertModel])
    }

    insertModel
  }

  /**
    * 获取源系统修改时间
    *
    * @param update_time_1
    * @param update_time_2
    * @return
    */
  def getChangedOnDt(update_time_1: String, update_time_2: String) = {

    val changed_on_dt: String = if (!DataNullUtil.dtIsNull(update_time_1) && !DataNullUtil.dtIsNull(update_time_2)) {
      update_time_1
    } else if (!DataNullUtil.dtIsNull(update_time_1)) {
      update_time_1
    } else if (!DataNullUtil.dtIsNull(update_time_2)) {
      update_time_2
    } else {
      null
    }

    DataNullUtil.nvlDateTime(changed_on_dt)
  }

  //需要判断mysql值是否有换行的表和字段,ods层表名+"."+字段名
  private val tableFiled = Array[String]("gc_wms_gc_receiving.receiving_description",
    "gc_oms_receiving.receiving_description",
    "gc_wms_receiving.receiving_description",
    "zy_wms_receiving.receiving_description")

  /**
    * 判断是否符合mysql值要替换的表和字段
    * @param database
    * @param table
    * @param filed
    * @return
    */
  def filterFiledAndTable(database: String, table: String, filed: String) = {
    //获取简称
    val datasource_name: String = MysqlDatabase.shortName(database)
    var result = false

    result = tableFiled.contains(datasource_name.concat("_").concat(table).concat(".").concat(filed))
    result
  }

  def main(args: Array[String]): Unit = {
    /*val jsonStr: String = "{\"data\":[{\"bi_id\":\"25381267\",\"bcb_id\":\"0\",\"customer_code\":\"611\",\"bi_withdraw_sign\":\"2\",\"bb_id\":\"6967946\",\"ft_code\":\"shipping\",\"ut_code\":\"KG\",\"sm_code\":\"FEDEX-LP\",\"bi_creator_id\":\"0\",\"bi_verifier_id\":\"0\",\"bi_amount\":\"35.850\",\"bi_org_amount\":\"35.850\",\"currency_code\":\"RMB\",\"currency_rate\":\"1.0\",\"bi_note\":\"\",\"bi_create_type\":\"0\",\"bi_rp_type\":\"0\",\"bi_status\":\"2\",\"bi_balance_sign\":\"2\",\"bi_return_sign\":\"1\",\"bi_chargeable_time\":\"2019-10-12 11:57:45\",\"bi_add_time\":\"2019-10-12 11:57:45\",\"bi_verify_time\":\"2019-10-12 11:57:45\",\"bi_update_time\":\"0000-00-00 00:00:00\",\"allow_create_bill\":\"0\",\"allow_create_bill_time\":\"2019-10-13 12:50:08\",\"parent_ft_code\":null,\"remark_code\":null,\"create_currency_code\":\"RMB\"}],\"database\":\"wms\",\"es\":1570940408000,\"id\":107844,\"isDdl\":false,\"mysqlType\":{\"bi_id\":\"int(11)\",\"bcb_id\":\"int(11)\",\"customer_code\":\"varchar(8)\",\"bi_withdraw_sign\":\"enum('y','n')\",\"bb_id\":\"int(11)\",\"ft_code\":\"varchar(32)\",\"ut_code\":\"varchar(6)\",\"sm_code\":\"varchar(32)\",\"bi_creator_id\":\"int(11)\",\"bi_verifier_id\":\"int(11)\",\"bi_amount\":\"decimal(10,3)\",\"bi_org_amount\":\"decimal(10,3)\",\"currency_code\":\"char(3)\",\"currency_rate\":\"decimal(10,4)\",\"bi_note\":\"varchar(300)\",\"bi_create_type\":\"tinyint(1)\",\"bi_rp_type\":\"tinyint(1)\",\"bi_status\":\"tinyint(1)\",\"bi_balance_sign\":\"enum('n','y')\",\"bi_return_sign\":\"enum('n','y')\",\"bi_chargeable_time\":\"datetime\",\"bi_add_time\":\"datetime\",\"bi_verify_time\":\"datetime\",\"bi_update_time\":\"datetime\",\"allow_create_bill\":\"tinyint(1)\",\"allow_create_bill_time\":\"timestamp\",\"parent_ft_code\":\"varchar(10)\",\"remark_code\":\"varchar(20)\",\"create_currency_code\":\"char(6)\"},\"old\":[{\"allow_create_bill_time\":\"2019-10-13 12:15:08\"}],\"pkNames\":[\"bi_id\"],\"sql\":\"\",\"sqlType\":{\"bi_id\":4,\"bcb_id\":4,\"customer_code\":12,\"bi_withdraw_sign\":4,\"bb_id\":4,\"ft_code\":12,\"ut_code\":12,\"sm_code\":12,\"bi_creator_id\":4,\"bi_verifier_id\":4,\"bi_amount\":3,\"bi_org_amount\":3,\"currency_code\":1,\"currency_rate\":3,\"bi_note\":12,\"bi_create_type\":-7,\"bi_rp_type\":-7,\"bi_status\":-7,\"bi_balance_sign\":4,\"bi_return_sign\":4,\"bi_chargeable_time\":93,\"bi_add_time\":93,\"bi_verify_time\":93,\"bi_update_time\":93,\"allow_create_bill\":-7,\"allow_create_bill_time\":93,\"parent_ft_code\":12,\"remark_code\":12,\"create_currency_code\":1},\"table\":\"bil_income\",\"ts\":1571045462730,\"type\":\"UPDATE\"}"

    val insertModel: InsertModel = getInsertObject(jsonStr)

    val puts: util.ArrayList[Put] = getHBasePut(insertModel)
    println(puts)*/

    val bool: Boolean = filterFiledAndTable("wms_goodcang_com","gc_receiving","receiving_descriptio")
    println(bool)

    val str="A-1-HG3726K:预报200箱 未出\nA-1-HG2824AK:预报100箱 实出25箱\nA-1-HG4801A:预报100箱 未出\nB-1-HG4802A:预报100箱 未出"
    val str2="骆贞伟_106_SEA_YKDWEST\r\n骆贞伟_108_SEA_YKDWEST\r\n圳钰_144_SEA_YKDWEST采购\r\n圳钰_144_SEA_YKDWEST提库"


  }

}

