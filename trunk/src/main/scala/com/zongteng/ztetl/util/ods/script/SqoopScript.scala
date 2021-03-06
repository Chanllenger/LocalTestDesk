package com.zongteng.ztetl.util.ods.script

import java.io.FileInputStream
import java.sql.{Connection, DriverManager, ResultSet, SQLException, Statement}

import com.mchange.v2.c3p0.ComboPooledDataSource
import com.zongteng.ztetl.common.{MysqlDatabase, SystemCodeUtil}
import com.zongteng.ztetl.entity.common.MysqlOdsScript
import com.zongteng.ztetl.util.DataNullUtil
import javax.sql.DataSource
import org.apache.poi.xssf.usermodel.{XSSFRow, XSSFWorkbook}

import scala.collection.mutable.ListBuffer

object SqoopScript {

  val jdbcMessage = "useUnicode=true&characterEncoding=UTF-8&tinyInt1isBit=false&zeroDateTimeBehavior=convertToNull"

  /**
    * 通过系统来源获取对应的jdbc连接
    *
    * @param datasource 系统来源
    *
    *                   目前：
    *                   1、gc_wms
    *                   2、gc_oms
    *                   3、gc_bsc
    *                   4、zy_wms
    *
    **/
  def getJdbcInfo(row: Int, datasource: String) = datasource match {
    case "gc_owms_au" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53308/owms_au?$jdbcMessage",
      "username" -> "zt103_datateam",
      "password" -> "RVHcACC88X6rZwzCvVLN",
      "schema" -> "owms_au",
      "datasource_num_id" -> SystemCodeUtil.GC_OWMS_AU,
      "mysql_datasource_name" -> MysqlDatabase.GC_OWMS_AU
    )

    case "gc_owms_cz" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53308/owms_cz?$jdbcMessage",
      "username" -> "zt103_datateam",
      "password" -> "RVHcACC88X6rZwzCvVLN",
      "schema" -> "owms_cz",
      "datasource_num_id" -> SystemCodeUtil.GC_OWMS_CZ,
      "mysql_datasource_name" -> MysqlDatabase.GC_OWMS_CZ
    )

    case "gc_owms_de" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53308/owms_de?$jdbcMessage",
      "username" -> "zt103_datateam",
      "password" -> "RVHcACC88X6rZwzCvVLN",
      "schema" -> "owms_de",
      "datasource_num_id" -> SystemCodeUtil.GC_OWMS_DE,
      "mysql_datasource_name" -> MysqlDatabase.GC_OWMS_DE
    )

    case "gc_owms_es" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53308/owms_es?$jdbcMessage",
      "username" -> "zt103_datateam",
      "password" -> "RVHcACC88X6rZwzCvVLN",
      "schema" -> "owms_es",
      "datasource_num_id" -> SystemCodeUtil.GC_OWMS_ES,
      "mysql_datasource_name" -> MysqlDatabase.GC_OWMS_ES
    )

    case "gc_owms_frvi" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53308/owms_frvi?$jdbcMessage",
      "username" -> "zt103_datateam",
      "password" -> "RVHcACC88X6rZwzCvVLN",
      "schema" -> "owms_frvi",
      "datasource_num_id" -> SystemCodeUtil.GC_OWMS_FRVI,
      "mysql_datasource_name" -> MysqlDatabase.GC_OWMS_FRVI
    )

    case "gc_owms_it" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53308/owms_it?$jdbcMessage",
      "username" -> "zt103_datateam",
      "password" -> "RVHcACC88X6rZwzCvVLN",
      "schema" -> "owms_it",
      "datasource_num_id" -> SystemCodeUtil.GC_OWMS_IT,
      "mysql_datasource_name" -> MysqlDatabase.GC_OWMS_IT
    )

    case "gc_owms_jp" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53308/owms_jp?$jdbcMessage",
      "username" -> "zt103_datateam",
      "password" -> "RVHcACC88X6rZwzCvVLN",
      "schema" -> "owms_jp",
      "datasource_num_id" -> SystemCodeUtil.GC_OWMS_JP,
      "mysql_datasource_name" -> MysqlDatabase.GC_OWMS_JP
    )

    case "gc_owms_uk" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53308/owms_uk?$jdbcMessage",
      "username" -> "zt103_datateam",
      "password" -> "RVHcACC88X6rZwzCvVLN",
      "schema" -> "owms_uk",
      "datasource_num_id" -> SystemCodeUtil.GC_OWMS_UK,
      "mysql_datasource_name" -> MysqlDatabase.GC_OWMS_UK
    )

    case "gc_owms_ukob" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53308/owms_ukob?$jdbcMessage",
      "username" -> "zt103_datateam",
      "password" -> "RVHcACC88X6rZwzCvVLN",
      "schema" -> "owms_ukob",
      "datasource_num_id" -> SystemCodeUtil.GC_OWMS_UKOB,
      "mysql_datasource_name" -> MysqlDatabase.GC_OWMS_UKOB
    )

    case "gc_owms_usea" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53308/owms_us_east?$jdbcMessage",
      "username" -> "zt103_datateam",
      "password" -> "RVHcACC88X6rZwzCvVLN",
      "schema" -> "owms_us_east",
      "datasource_num_id" -> SystemCodeUtil.GC_OWMS_USEA,
      "mysql_datasource_name" -> MysqlDatabase.GC_OWMS_USEA
    )

    case "gc_owms_uswe" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53308/owms_us_west?$jdbcMessage",
      "username" -> "zt103_datateam",
      "password" -> "RVHcACC88X6rZwzCvVLN",
      "schema" -> "owms_us_west",
      "datasource_num_id" -> SystemCodeUtil.GC_OWMS_USWE,
      "mysql_datasource_name" -> MysqlDatabase.GC_OWMS_USWE
    )

    case "gc_owms_usnb" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53308/owms_usnb?$jdbcMessage",
      "username" -> "zt103_datateam",
      "password" -> "RVHcACC88X6rZwzCvVLN",
      "schema" -> "owms_usnb",
      "datasource_num_id" -> SystemCodeUtil.GC_OWMS_USNB,
      "mysql_datasource_name" -> MysqlDatabase.GC_OWMS_USNB
    )

    case "gc_owms_usot" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53308/owms_usot?$jdbcMessage",
      "username" -> "zt103_datateam",
      "password" -> "RVHcACC88X6rZwzCvVLN",
      "schema" -> "owms_usot",
      "datasource_num_id" -> SystemCodeUtil.GC_OWMS_USOT,
      "mysql_datasource_name" -> MysqlDatabase.GC_OWMS_USOT
    )

    case "gc_owms_ussc" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53308/owms_ussc?$jdbcMessage",
      "username" -> "zt103_datateam",
      "password" -> "RVHcACC88X6rZwzCvVLN",
      "schema" -> "owms_ussc",
      "datasource_num_id" -> SystemCodeUtil.GC_OWMS_USSC,
      "mysql_datasource_name" -> MysqlDatabase.GC_OWMS_USSC
    )

    case "gc_lms_au" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53308/ts_au?$jdbcMessage",
      "username" -> "zt103_datateam",
      "password" -> "RVHcACC88X6rZwzCvVLN",
      "schema" -> "ts_au",
      "datasource_num_id" -> SystemCodeUtil.GC_LMS_AU,
      "mysql_datasource_name" -> MysqlDatabase.GC_LMS_AU
    )

    case "gc_lms_cz" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53308/ts_cz?$jdbcMessage",
      "username" -> "zt103_datateam",
      "password" -> "RVHcACC88X6rZwzCvVLN",
      "schema" -> "ts_cz",
      "datasource_num_id" -> SystemCodeUtil.GC_LMS_CZ,
      "mysql_datasource_name" -> MysqlDatabase.GC_LMS_CZ
    )

    case "gc_lms_es" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53308/ts_es?$jdbcMessage",
      "username" -> "zt103_datateam",
      "password" -> "RVHcACC88X6rZwzCvVLN",
      "schema" -> "ts_es",
      "datasource_num_id" -> SystemCodeUtil.GC_LMS_ES,
      "mysql_datasource_name" -> MysqlDatabase.GC_LMS_ES
    )

    case "gc_lms_frvi" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53308/ts_frvi?$jdbcMessage",
      "username" -> "zt103_datateam",
      "password" -> "RVHcACC88X6rZwzCvVLN",
      "schema" -> "ts_frvi",
      "datasource_num_id" -> SystemCodeUtil.GC_LMS_FRVI,
      "mysql_datasource_name" -> MysqlDatabase.GC_LMS_FRVI
    )

    case "gc_lms_it" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53308/ts_it?$jdbcMessage",
      "username" -> "zt103_datateam",
      "password" -> "RVHcACC88X6rZwzCvVLN",
      "schema" -> "ts_it",
      "datasource_num_id" -> SystemCodeUtil.GC_LMS_IT,
      "mysql_datasource_name" -> MysqlDatabase.GC_LMS_IT
    )

    case "gc_lms_uk" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53308/ts_uk?$jdbcMessage",
      "username" -> "zt103_datateam",
      "password" -> "RVHcACC88X6rZwzCvVLN",
      "schema" -> "ts_uk",
      "datasource_num_id" -> SystemCodeUtil.GC_LMS_UK,
      "mysql_datasource_name" -> MysqlDatabase.GC_LMS_UK
    )

    case "gc_lms_uswe" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53308/ts_us_west?$jdbcMessage",
      "username" -> "zt103_datateam",
      "password" -> "RVHcACC88X6rZwzCvVLN",
      "schema" -> "ts_us_west",
      "datasource_num_id" -> SystemCodeUtil.GC_LMS_USWE,
      "mysql_datasource_name" -> MysqlDatabase.GC_LMS_USWE
    )

    case "gc_lms_usea" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53308/ts_usea?$jdbcMessage",
      "username" -> "zt103_datateam",
      "password" -> "RVHcACC88X6rZwzCvVLN",
      "schema" -> "ts_usea",
      "datasource_num_id" -> SystemCodeUtil.GC_LMS_USEA,
      "mysql_datasource_name" -> MysqlDatabase.GC_LMS_USEA
    )

    case "gc_lms_ussc" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53308/ts_ussc?$jdbcMessage",
      "username" -> "zt103_datateam",
      "password" -> "RVHcACC88X6rZwzCvVLN",
      "schema" -> "ts_ussc",
      "datasource_num_id" -> SystemCodeUtil.GC_LMS_USSC,
      "mysql_datasource_name" -> MysqlDatabase.GC_LMS_USSC
    )

    case "gc_wms" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53309/wms_goodcang_com?$jdbcMessage",
      "username" -> "zt103-datateam",
      "password" -> "VUfE8ivE7BJgWNxeL4g",
      "schema" -> "wms_goodcang_com",
      "datasource_num_id" -> SystemCodeUtil.GC_WMS,
      "mysql_datasource_name" -> MysqlDatabase.GC_WMS
    )

    case "gc_wms_ibl" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53309/gc_inventory_batch_log?$jdbcMessage",
      "username" -> "zt103-datateam",
      "password" -> "VUfE8ivE7BJgWNxeL4g",
      "schema" -> "gc_inventory_batch_log",
      "datasource_num_id" -> SystemCodeUtil.GC_WMS_IBL,
      "mysql_datasource_name" -> MysqlDatabase.GC_WMS_IBL
    )

    case "gc_oms" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53309/oms_goodcang_com?$jdbcMessage",
      "username" -> "zt103-datateam",
      "password" -> "VUfE8ivE7BJgWNxeL4g",
      "schema" -> "oms_goodcang_com",
      "datasource_num_id" -> SystemCodeUtil.GC_OMS,
      "mysql_datasource_name" -> MysqlDatabase.GC_OMS
    )

    case "gc_bsc" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.200:63310/gc_bsc_common?$jdbcMessage",
      "username" -> "zt103-datateam",
      "password" -> "a2yaTXv_hhHy9WqY",
      "schema" -> "gc_bsc_common",
      "datasource_num_id" -> SystemCodeUtil.GC_BSC,
      "mysql_datasource_name" -> MysqlDatabase.GC_BSC
    )

    case "zy_wms" => Map[String, String](
      "connect" -> s"jdbc:mysql://120.78.102.220:3306/wms?$jdbcMessage",
      "username" -> "zt103_datateam",
      "password" -> "JbGGLcpyx!Njs^AD",
      "schema" -> "wms",
      "datasource_num_id" -> SystemCodeUtil.ZY_WMS,
      "mysql_datasource_name" -> MysqlDatabase.ZY_WMS
    )

    /**
      * zy_owms
      */

    case "zy_owms_au" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53334/ems_owms_au?$jdbcMessage",
      "username" -> "zt103-datateam",
      "password" -> "a2yaTXv_hhHy9WqY",
      "schema" -> "ems_owms_au",
      "datasource_num_id" -> SystemCodeUtil.ZY_OWMS_AU,
      "mysql_datasource_name" -> MysqlDatabase.ZY_OWMS_AU
    )

    case "zy_owms_cz" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53334/ems_owms_cz?$jdbcMessage",
      "username" -> "zt103-datateam",
      "password" -> "a2yaTXv_hhHy9WqY",
      "schema" -> "ems_owms_cz",
      "datasource_num_id" -> SystemCodeUtil.ZY_OWMS_CZ,
      "mysql_datasource_name" -> MysqlDatabase.ZY_OWMS_CZ
    )

    case "zy_owms_de" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53334/ems_owms_de?$jdbcMessage",
      "username" -> "zt103-datateam",
      "password" -> "a2yaTXv_hhHy9WqY",
      "schema" -> "ems_owms_de",
      "datasource_num_id" -> SystemCodeUtil.ZY_OWMS_DE,
      "mysql_datasource_name" -> MysqlDatabase.ZY_OWMS_DE
    )

    case "zy_owms_ru" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53334/ems_owms_ru?$jdbcMessage",
      "username" -> "zt103-datateam",
      "password" -> "a2yaTXv_hhHy9WqY",
      "schema" -> "ems_owms_ru",
      "datasource_num_id" -> SystemCodeUtil.ZY_OWMS_RU,
      "mysql_datasource_name" -> MysqlDatabase.ZY_OWMS_RU
    )

    case "zy_owms_uk" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53334/ems_owms_uk?$jdbcMessage",
      "username" -> "zt103-datateam",
      "password" -> "a2yaTXv_hhHy9WqY",
      "schema" -> "ems_owms_uk",
      "datasource_num_id" -> SystemCodeUtil.ZY_OWMS_UK,
      "mysql_datasource_name" -> MysqlDatabase.ZY_OWMS_UK
    )

    case "zy_owms_usea" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53334/ems_owms_us_east?$jdbcMessage",
      "username" -> "zt103-datateam",
      "password" -> "a2yaTXv_hhHy9WqY",
      "schema" -> "ems_owms_us_east",
      "datasource_num_id" -> SystemCodeUtil.ZY_OWMS_USEA,
      "mysql_datasource_name" -> MysqlDatabase.ZY_OWMS_USEA
    )

    case "zy_owms_uswe" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53334/ems_owms_us_west?$jdbcMessage",
      "username" -> "zt103-datateam",
      "password" -> "a2yaTXv_hhHy9WqY",
      "schema" -> "ems_owms_us_west",
      "datasource_num_id" -> SystemCodeUtil.ZY_OWMS_USWE,
      "mysql_datasource_name" -> MysqlDatabase.ZY_OWMS_USWE
    )

    case "zy_owms_ussc" => Map[String, String](
      "connect" -> s"jdbc:mysql://192.168.14.201:53334/ems_owms_ussc?$jdbcMessage",
      "username" -> "zt103-datateam",
      "password" -> "a2yaTXv_hhHy9WqY",
      "schema" -> "ems_owms_ussc",
      "datasource_num_id" -> SystemCodeUtil.ZY_OWMS_USSC,
      "mysql_datasource_name" -> MysqlDatabase.ZY_OWMS_USSC
    )
//转运
    case "gc_tcms" => Map[String, String](
      "connect" -> s"jdbc:mysql://47.88.35.185:63307/goodcang_toms?$jdbcMessage",
      "username" -> "zt103-datateam",
      "password" -> "KDBwO_.@s982.DG__apfEQH0ss29",
      "schema" -> "goodcang_toms",
      "datasource_num_id" -> SystemCodeUtil.GC_TCMS,
      "mysql_datasource_name" -> MysqlDatabase.GC_TCMS
    )
    case "gc_toms" => Map[String, String](
      "connect" -> s"jdbc:mysql://47.88.35.185:63307/goodcang_toms_web?$jdbcMessage",
      "username" -> "zt103-datateam",
      "password" -> "KDBwO_.@s982.DG__apfEQH0ss29",
      "schema" -> "goodcang_toms_web",
      "datasource_num_id" -> SystemCodeUtil.GC_TOMS,
      "mysql_datasource_name" -> MysqlDatabase.GC_TOMS
    )


      
    case _ => throw new Exception(s"第${row}行源系统${datasource}，对应的信息{jdbc信息(connect、username、password)、schema、datasource_num_id、mysql_datasource_name}不存在。"
      + s"请检查Excel第${row}行源系统${datasource}信息填写是否正确；如果正确，那么检查源系统对应的信息是否在程序中填写；如果没有，请填写")
  }


  def getInfoByExel(excel_path: String) = {

    val infos: ListBuffer[MysqlOdsScript] = ListBuffer[MysqlOdsScript]()

    // 维护文档所在的路径
    val filePath = excel_path
    val fs = new FileInputStream(filePath)
    val wb: XSSFWorkbook = new XSSFWorkbook(fs)
    val sheet = wb.getSheetAt(0)
    val maxRow = sheet.getLastRowNum()

    for (i <- 1 to maxRow) {
      val row: XSSFRow = sheet.getRow(i)

      val mysqlDataSource = DataNullUtil.nvlObject(row.getCell(0)).toLowerCase()
      val mysqlTable = DataNullUtil.nvlObject(row.getCell(1)).toLowerCase()
      val primaryKey = DataNullUtil.nvlObject(row.getCell(2)).toLowerCase()
      val createTime = DataNullUtil.nvlObject(row.getCell(3)).toLowerCase()
      val updataTime_1 = DataNullUtil.nvlObject(row.getCell(4)).toLowerCase()
      val updataTime_2 = DataNullUtil.nvlObject(row.getCell(5)).toLowerCase()
      val columnFamily = DataNullUtil.nvlObject(row.getCell(6)).toLowerCase()
      val tableType = DataNullUtil.nvlObject(row.getCell(7)).toLowerCase()

      val sqoopMessage: Map[String, String] = getJdbcInfo(i + 1, mysqlDataSource)
      val connectOp = sqoopMessage.get("connect")
      val usernameOp = sqoopMessage.get("username")
      val passwordOp = sqoopMessage.get("password")
      val mysqlTableSchemaOp = sqoopMessage.get("schema")
      val datasource_num_idOp = sqoopMessage.get("datasource_num_id")
      val mysql_datasource_nameOp = sqoopMessage.get("mysql_datasource_name")

      checkInfoExcel(i + 1, mysqlDataSource, mysqlTable, primaryKey, columnFamily,
        tableType, connectOp, usernameOp, passwordOp, mysqlTableSchemaOp, datasource_num_idOp, mysql_datasource_nameOp)

      val connect = DataNullUtil.nvlString(connectOp.get)
      val username = DataNullUtil.nvlString(usernameOp.get)
      val password = DataNullUtil.nvlString(passwordOp.get)
      val mysqlTableSchema = DataNullUtil.nvlString(mysqlTableSchemaOp.get)
      val datasource_num_id = DataNullUtil.nvlString(datasource_num_idOp.get)
      val mysql_datasource_name = DataNullUtil.nvlString(mysql_datasource_nameOp.get)

      infos += MysqlOdsScript(mysqlDataSource, mysqlTable, primaryKey, createTime, updataTime_1, updataTime_2, columnFamily, tableType,
        connect, username, password, mysqlTableSchema, datasource_num_id, mysql_datasource_name)
    }

    fs.close()
    infos
  }

  /**
    * 检查excel文档输入信息的合法性
    *
    * @param row
    * @param mysqlDataSource
    * @param mysqlTable
    * @param primaryKey
    * @param columnFamily
    * @param tableType
    */
  def checkInfoExcel(row: Int, mysqlDataSource: String, mysqlTable: String, primaryKey: String, columnFamily: String, tableType: String,
                     connect: Option[String], username: Option[String], password: Option[String],
                     mysqlTableSchema: Option[String], datasource_num_id: Option[String], mysql_datasource_name: Option[String]) = {

    val errorMessage = "，请检查并填上对应的信息。如果是空行，请删除"
    val errorMessage2 = "，请检查并填上对应的信息"
    val rowMessage = s"第${row}行"

    assert(mysqlDataSource != "", s"${rowMessage}的源系统信息为空${errorMessage}")
    assert(mysqlTable != "", s"${rowMessage}的表名信息为空${errorMessage}")
    assert(tableType != "", s"${rowMessage}的表的类型（fact/dim）信息为空${errorMessage}")

    if ("fact".equals(tableType)) {
      assert(primaryKey != "", s"${rowMessage}的主键信息为空，事实表必须指定对应的主键${errorMessage}")
      assert(columnFamily != "", s"${rowMessage}的列族信息为空，事实表必须指定对应的列族${errorMessage}")
    } else if ("dim".equals(tableType)) {
      assert(primaryKey != "", s"${rowMessage}的主键信息为空，维度表必须指定对应的主键${errorMessage}")
    } else {
      assert(false, s"${rowMessage}的表类型${tableType}不合法。事实表请输入：fact；维度表请输入：dim${errorMessage}")
    }

    assert(connect.isDefined && connect.get.trim != "", s"${rowMessage}对应的${mysqlDataSource}系统的jdbc连接协议信息（connect）不能为空${errorMessage2}")
    assert(username.isDefined && username.get.trim != "", s"${rowMessage}对应的${mysqlDataSource}系统的jdbc连接用户名（username）不能为空${errorMessage2}")
    assert(password.isDefined && password.get.trim != "", s"${rowMessage}对应的${mysqlDataSource}系统的jdbc连接密码（password）不能为空${errorMessage2}")
    assert(mysqlTableSchema.isDefined && mysqlTableSchema.get.trim != "", s"${rowMessage}对应的${mysqlDataSource}系统的（schema）不能为空${errorMessage2}")
    assert(datasource_num_id.isDefined && datasource_num_id.get.trim != "", s"${rowMessage}对应的${mysqlDataSource}系统的系统代码（datasource_num_id）不能为空${errorMessage2}")
    assert(mysql_datasource_name.isDefined && mysql_datasource_name.get.trim != "", s"${rowMessage}对应的${mysqlDataSource}系统的mysql数据库名字（mysql_datasource_name）不能为空${errorMessage2}")

  }

  def getJDBCConnection(row: Int, mysqlDataSource: String, connect: String, username: String, password: String) = {
    var connection: Connection = null
    val rowMessage = s"第${row}行"

    try {
      classOf[com.mysql.jdbc.Driver]
      connection = DriverManager.getConnection(connect, username, password)
      if (connection == null) {
        assert(false, s"${rowMessage}对应的${mysqlDataSource}系统使用JDBC连接不上，请检查。")
      }
    } catch {
      case e: Exception => assert(false, s"${rowMessage}对应的${mysqlDataSource}系统使用JDBC连接不上，请检查。")
    }

    connection
  }


  // 获取连接
  def getConnection(row: Int, mysqlDataSource: String) = {
    var connection: Connection = null
    val rowMessage = s"第${row}行"

    try {
      val ds: DataSource = new ComboPooledDataSource(mysqlDataSource);
      connection=ds.getConnection()
      if (connection == null) {
        assert(false, s"${rowMessage}对应的${mysqlDataSource}系统使用JDBC连接不上，请检查。")
      }
    } catch {
      case e: Exception => assert(false, s"${rowMessage}对应的${mysqlDataSource}系统使用JDBC连接不上，请检查。")
    }
    connection
  }

  /**
    * 关闭连接
    * @param resultSet
    * @param pst
    * @param connection
    */
  def close(resultSet : ResultSet,  pst: Statement, connection: Connection) {

    if (resultSet != null) {
      try {
        resultSet.close();
      } catch {
        case e: SQLException => e.printStackTrace()
      }
    }

    if (pst != null) {
      try {
        pst.close();
      } catch {
        case e: SQLException => e.printStackTrace()
      }
    }

    if (connection != null) {
      try {
        connection.close();
      } catch {
        case e: SQLException => e.printStackTrace()
      }
    }
  }





  def main(args: Array[String]) = {
    //getInfoByExel().foreach(println(_))
  }

  def getSqoopScriptByTableType(tableType: String, hbase_cf: String, hbase_rk: String, hbase_table: String) = {

    if ("dim".equals(tableType)) {


      var part_hive = "#导入数据之前，使用hadoop命令物理删除分区文件夹\n" +
        "hive -e \"ALTER TABLE ods.${table} DROP IF EXISTS PARTITION (day = '${etl_proc_wid}');\"\n\n" +
        "sqoop import -D mapreduce.task.timeout=1800000 -D mapred.job.name=\"${job_name}\" -D  mapred.job.queue.name='etl' \\\n" +
        "--connect \"$connect\" --username \"$username\" --password \"$password\" \\\n" +
        "--split-by \"$pk\" \\\n" +
        "--query \"$querySql\" \\\n" +
        "--hcatalog-database \"$database\" \\\n" +
        "--hcatalog-table \"$table\" \\\n" +
        "--hcatalog-partition-keys 'day' \\\n" + // 分区
        "--hcatalog-partition-values \"${etl_proc_wid}\" \\\n" + // 分区
        "--null-string '\\\\N" +
        "' \\\n" +
        "--null-non-string '\\\\N" +
        "' \\\n" +
        "--compress \\\n" +
        "--compression-codec 'SNAPPY'"

      Array[String](part_hive)
    } else {

      var hbase = s"hbase_cf='$hbase_cf'\n" +
        s"hbase_rk='$hbase_rk'\n" +
        s"hbase_tb='$hbase_table'\n"

      var hbase2 = "sqoop import -D mapreduce.task.timeout=7200000 -D mapred.job.name=\"${job_name}\" -D  mapred.job.queue.name='etl' \\\n" +
        "--connect \"$connect\" --username \"$username\" --password \"$password\" \\\n" +
        "--split-by \"$pk\" \\\n" +
        "--m \"5\" \\\n" +
        "--query \"$querySql\" \\\n" +
        "--column-family \"$hbase_cf\" \\\n" +
        "--hbase-create-table \\\n" +
        "--hbase-row-key \"$hbase_rk\" \\\n" +
        "--hbase-table \"$hbase_tb\" \\\n" +
        "--compress \\\n" +
        "--null-string '\\\\N' \\\n" +
        "--null-non-string '\\\\N' "

      Array[String](hbase, hbase2)
    }


  }


}
