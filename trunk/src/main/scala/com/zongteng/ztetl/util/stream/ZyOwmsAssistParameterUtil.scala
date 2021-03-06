package com.zongteng.ztetl.util.stream

import com.zongteng.ztetl.common.SystemCodeUtil
import com.zongteng.ztetl.entity.common.AssistParameter
import org.apache.commons.lang.StringUtils
/**
  * zy_owms库的表通过数据名和表名获取对应的辅助实体类（样例类）
  */
object ZyOwmsAssistParameterUtil {
  /**
    * @param database 数据库
    * @param table    表名
    */
  def getEntityObject(database: String, table: String) = {

    assert(StringUtils.isNotBlank(database), "数据库不能为空")
    assert(StringUtils.isNotBlank(table), "表名不能为空")

    val dt: String = database + "." + table

    dt match {
      //订单(orders)
      case "ems_owms_uk.order_address_book" => ems_owms_uk$order_address_book(database, table)
      case "ems_owms_us_east.order_address_book" => ems_owms_us_east$order_address_book(database, table)
      case "ems_owms_ussc.order_address_book" => ems_owms_ussc$order_address_book(database, table)
      case "ems_owms_us_west.order_address_book" => ems_owms_us_west$order_address_book(database, table)
      case "ems_owms_au.order_address_book" => ems_owms_au$order_address_book(database, table)
      case "ems_owms_cz.order_address_book" => ems_owms_cz$order_address_book(database, table)
      case "ems_owms_de.order_address_book" => ems_owms_de$order_address_book(database, table)
      case "ems_owms_ru.order_address_book" => ems_owms_ru$order_address_book(database, table)
      case "ems_owms_uk.orders" => ems_owms_uk$orders(database, table)
      case "ems_owms_us_east.orders" => ems_owms_us_east$orders(database, table)
      case "ems_owms_ussc.orders" => ems_owms_ussc$orders(database, table)
      case "ems_owms_us_west.orders" => ems_owms_us_west$orders(database, table)
      case "ems_owms_au.orders" => ems_owms_au$orders(database, table)
      case "ems_owms_cz.orders" => ems_owms_cz$orders(database, table)
      case "ems_owms_de.orders" => ems_owms_de$orders(database, table)
      case "ems_owms_ru.orders" => ems_owms_ru$orders(database, table)
      //出库(picking)
      case "ems_owms_uk.wellen_area" => ems_owms_uk$wellen_area(database, table)
      case "ems_owms_us_east.wellen_area" => ems_owms_us_east$wellen_area(database, table)
      case "ems_owms_ussc.wellen_area" => ems_owms_ussc$wellen_area(database, table)
      case "ems_owms_us_west.wellen_area" => ems_owms_us_west$wellen_area(database, table)
      case "ems_owms_au.wellen_area" => ems_owms_au$wellen_area(database, table)
      case "ems_owms_cz.wellen_area" => ems_owms_cz$wellen_area(database, table)
      case "ems_owms_de.wellen_area" => ems_owms_de$wellen_area(database, table)
      case "ems_owms_ru.wellen_area" => ems_owms_ru$wellen_area(database, table)

      case _ => throw new Exception("数据库 {" + database + "} 中表{" + table + "} 对应的方法不存在,请添加")
    }
  }

  def ems_owms_uk$order_address_book(database: String, table: String): AssistParameter = {
    val clazz: Class[_] = classOf[com.zongteng.ztetl.entity.zy_owms.zy_owms_uk.OrderAddressBook]
    val primaryKey: String = "oab_id"
    val cf: String = "order_address_book"
    val datasource_num_id: String = SystemCodeUtil.ZY_OWMS_UK
    val add_time_field = ""
    val update_time_field_1 = "oab_update_time"
    val update_time_field_2: String = ""
    check(AssistParameter(clazz, primaryKey, cf, datasource_num_id, add_time_field, update_time_field_1, update_time_field_2, database, table))
  }

  def ems_owms_us_east$order_address_book(database: String, table: String): AssistParameter = {
    val clazz: Class[_] = classOf[com.zongteng.ztetl.entity.zy_owms.zy_owms_usea.OrderAddressBook]
    val primaryKey: String = "oab_id"
    val cf: String = "order_address_book"
    val datasource_num_id: String = SystemCodeUtil.ZY_OWMS_USEA
    val add_time_field = ""
    val update_time_field_1 = "oab_update_time"
    val update_time_field_2: String = ""
    check(AssistParameter(clazz, primaryKey, cf, datasource_num_id, add_time_field, update_time_field_1, update_time_field_2, database, table))
  }

  def ems_owms_ussc$order_address_book(database: String, table: String): AssistParameter = {
    val clazz: Class[_] = classOf[com.zongteng.ztetl.entity.zy_owms.zy_owms_ussc.OrderAddressBook]
    val primaryKey: String = "oab_id"
    val cf: String = "order_address_book"
    val datasource_num_id: String = SystemCodeUtil.ZY_OWMS_USSC
    val add_time_field = ""
    val update_time_field_1 = "oab_update_time"
    val update_time_field_2: String = ""
    check(AssistParameter(clazz, primaryKey, cf, datasource_num_id, add_time_field, update_time_field_1, update_time_field_2, database, table))
  }

  def ems_owms_us_west$order_address_book(database: String, table: String): AssistParameter = {
    val clazz: Class[_] = classOf[com.zongteng.ztetl.entity.zy_owms.zy_owms_uswe.OrderAddressBook]
    val primaryKey: String = "oab_id"
    val cf: String = "order_address_book"
    val datasource_num_id: String = SystemCodeUtil.ZY_OWMS_USWE
    val add_time_field = ""
    val update_time_field_1 = "oab_update_time"
    val update_time_field_2: String = ""
    check(AssistParameter(clazz, primaryKey, cf, datasource_num_id, add_time_field, update_time_field_1, update_time_field_2, database, table))
  }

  def ems_owms_au$order_address_book(database: String, table: String): AssistParameter = {
    val clazz: Class[_] = classOf[com.zongteng.ztetl.entity.zy_owms.zy_owms_au.OrderAddressBook]
    val primaryKey: String = "oab_id"
    val cf: String = "order_address_book"
    val datasource_num_id: String = SystemCodeUtil.ZY_OWMS_AU
    val add_time_field = ""
    val update_time_field_1 = "oab_update_time"
    val update_time_field_2: String = ""
    check(AssistParameter(clazz, primaryKey, cf, datasource_num_id, add_time_field, update_time_field_1, update_time_field_2, database, table))
  }

  def ems_owms_cz$order_address_book(database: String, table: String): AssistParameter = {
    val clazz: Class[_] = classOf[com.zongteng.ztetl.entity.zy_owms.zy_owms_cz.OrderAddressBook]
    val primaryKey: String = "oab_id"
    val cf: String = "order_address_book"
    val datasource_num_id: String = SystemCodeUtil.ZY_OWMS_CZ
    val add_time_field = ""
    val update_time_field_1 = "oab_update_time"
    val update_time_field_2: String = ""
    check(AssistParameter(clazz, primaryKey, cf, datasource_num_id, add_time_field, update_time_field_1, update_time_field_2, database, table))
  }

  def ems_owms_de$order_address_book(database: String, table: String): AssistParameter = {
    val clazz: Class[_] = classOf[com.zongteng.ztetl.entity.zy_owms.zy_owms_de.OrderAddressBook]
    val primaryKey: String = "oab_id"
    val cf: String = "order_address_book"
    val datasource_num_id: String = SystemCodeUtil.ZY_OWMS_DE
    val add_time_field = ""
    val update_time_field_1 = "oab_update_time"
    val update_time_field_2: String = ""
    check(AssistParameter(clazz, primaryKey, cf, datasource_num_id, add_time_field, update_time_field_1, update_time_field_2, database, table))
  }

  def ems_owms_ru$order_address_book(database: String, table: String): AssistParameter = {
    val clazz: Class[_] = classOf[com.zongteng.ztetl.entity.zy_owms.zy_owms_ru.OrderAddressBook]
    val primaryKey: String = "oab_id"
    val cf: String = "order_address_book"
    val datasource_num_id: String = SystemCodeUtil.ZY_OWMS_RU
    val add_time_field = ""
    val update_time_field_1 = "oab_update_time"
    val update_time_field_2: String = ""
    check(AssistParameter(clazz, primaryKey, cf, datasource_num_id, add_time_field, update_time_field_1, update_time_field_2, database, table))
  }
  def ems_owms_uk$orders(database: String, table: String): AssistParameter = {
    val clazz: Class[_] = classOf[com.zongteng.ztetl.entity.zy_owms.zy_owms_uk.Orders]
    val primaryKey: String = "order_id"
    val cf: String = "orders"
    val datasource_num_id: String = SystemCodeUtil.ZY_OWMS_UK
    val add_time_field = "add_time"
    val update_time_field_1 = ""
    val update_time_field_2: String = "update_time"
    check(AssistParameter(clazz, primaryKey, cf, datasource_num_id, add_time_field, update_time_field_1, update_time_field_2, database, table))
  }

  def ems_owms_us_east$orders(database: String, table: String): AssistParameter = {
    val clazz: Class[_] = classOf[com.zongteng.ztetl.entity.zy_owms.zy_owms_usea.Orders]
    val primaryKey: String = "order_id"
    val cf: String = "orders"
    val datasource_num_id: String = SystemCodeUtil.ZY_OWMS_USEA
    val add_time_field = "add_time"
    val update_time_field_1 = ""
    val update_time_field_2: String = "update_time"
    check(AssistParameter(clazz, primaryKey, cf, datasource_num_id, add_time_field, update_time_field_1, update_time_field_2, database, table))
  }

  def ems_owms_ussc$orders(database: String, table: String): AssistParameter = {
    val clazz: Class[_] = classOf[com.zongteng.ztetl.entity.zy_owms.zy_owms_ussc.Orders]
    val primaryKey: String = "order_id"
    val cf: String = "orders"
    val datasource_num_id: String = SystemCodeUtil.ZY_OWMS_USSC
    val add_time_field = "add_time"
    val update_time_field_1 = ""
    val update_time_field_2: String = "update_time"
    check(AssistParameter(clazz, primaryKey, cf, datasource_num_id, add_time_field, update_time_field_1, update_time_field_2, database, table))
  }

  def ems_owms_us_west$orders(database: String, table: String): AssistParameter = {
    val clazz: Class[_] = classOf[com.zongteng.ztetl.entity.zy_owms.zy_owms_uswe.Orders]
    val primaryKey: String = "order_id"
    val cf: String = "orders"
    val datasource_num_id: String = SystemCodeUtil.ZY_OWMS_USWE
    val add_time_field = "add_time"
    val update_time_field_1 = ""
    val update_time_field_2: String = "update_time"
    check(AssistParameter(clazz, primaryKey, cf, datasource_num_id, add_time_field, update_time_field_1, update_time_field_2, database, table))
  }

  def ems_owms_au$orders(database: String, table: String): AssistParameter = {
    val clazz: Class[_] = classOf[com.zongteng.ztetl.entity.zy_owms.zy_owms_au.Orders]
    val primaryKey: String = "order_id"
    val cf: String = "orders"
    val datasource_num_id: String = SystemCodeUtil.ZY_OWMS_AU
    val add_time_field = "add_time"
    val update_time_field_1 = ""
    val update_time_field_2: String = "update_time"
    check(AssistParameter(clazz, primaryKey, cf, datasource_num_id, add_time_field, update_time_field_1, update_time_field_2, database, table))
  }

  def ems_owms_cz$orders(database: String, table: String): AssistParameter = {
    val clazz: Class[_] = classOf[com.zongteng.ztetl.entity.zy_owms.zy_owms_cz.Orders]
    val primaryKey: String = "order_id"
    val cf: String = "orders"
    val datasource_num_id: String = SystemCodeUtil.ZY_OWMS_CZ
    val add_time_field = "add_time"
    val update_time_field_1 = ""
    val update_time_field_2: String = "update_time"
    check(AssistParameter(clazz, primaryKey, cf, datasource_num_id, add_time_field, update_time_field_1, update_time_field_2, database, table))
  }

  def ems_owms_de$orders(database: String, table: String): AssistParameter = {
    val clazz: Class[_] = classOf[com.zongteng.ztetl.entity.zy_owms.zy_owms_de.Orders]
    val primaryKey: String = "order_id"
    val cf: String = "orders"
    val datasource_num_id: String = SystemCodeUtil.ZY_OWMS_DE
    val add_time_field = "add_time"
    val update_time_field_1 = ""
    val update_time_field_2: String = "update_time"
    check(AssistParameter(clazz, primaryKey, cf, datasource_num_id, add_time_field, update_time_field_1, update_time_field_2, database, table))
  }

  def ems_owms_ru$orders(database: String, table: String): AssistParameter = {
    val clazz: Class[_] = classOf[com.zongteng.ztetl.entity.zy_owms.zy_owms_ru.Orders]
    val primaryKey: String = "order_id"
    val cf: String = "orders"
    val datasource_num_id: String = SystemCodeUtil.ZY_OWMS_RU
    val add_time_field = "add_time"
    val update_time_field_1 = ""
    val update_time_field_2: String = "update_time"
    check(AssistParameter(clazz, primaryKey, cf, datasource_num_id, add_time_field, update_time_field_1, update_time_field_2, database, table))
  }

  //出库
  def ems_owms_uk$wellen_area(database: String, table: String): AssistParameter = {
    val clazz: Class[_] = classOf[com.zongteng.ztetl.entity.zy_owms.zy_owms_uk.WellenArea]
    val primaryKey: String = "wellen_area_id"
    val cf: String = "wellen_area"
    val datasource_num_id: String = SystemCodeUtil.ZY_OWMS_UK
    val add_time_field = ""
    val update_time_field_1 = ""
    val update_time_field_2: String = ""
    check(AssistParameter(clazz, primaryKey, cf, datasource_num_id, add_time_field, update_time_field_1, update_time_field_2, database, table))
  }

  def ems_owms_us_east$wellen_area(database: String, table: String): AssistParameter = {
    val clazz: Class[_] = classOf[com.zongteng.ztetl.entity.zy_owms.zy_owms_usea.WellenArea]
    val primaryKey: String = "wellen_area_id"
    val cf: String = "wellen_area"
    val datasource_num_id: String = SystemCodeUtil.ZY_OWMS_USEA
    val add_time_field = ""
    val update_time_field_1 = ""
    val update_time_field_2: String = ""
    check(AssistParameter(clazz, primaryKey, cf, datasource_num_id, add_time_field, update_time_field_1, update_time_field_2, database, table))
  }

  def ems_owms_ussc$wellen_area(database: String, table: String): AssistParameter = {
    val clazz: Class[_] = classOf[com.zongteng.ztetl.entity.zy_owms.zy_owms_ussc.WellenArea]
    val primaryKey: String = "wellen_area_id"
    val cf: String = "wellen_area"
    val datasource_num_id: String = SystemCodeUtil.ZY_OWMS_USSC
    val add_time_field = ""
    val update_time_field_1 = ""
    val update_time_field_2: String = ""
    check(AssistParameter(clazz, primaryKey, cf, datasource_num_id, add_time_field, update_time_field_1, update_time_field_2, database, table))
  }

  def ems_owms_us_west$wellen_area(database: String, table: String): AssistParameter = {
    val clazz: Class[_] = classOf[com.zongteng.ztetl.entity.zy_owms.zy_owms_uswe.WellenArea]
    val primaryKey: String = "wellen_area_id"
    val cf: String = "wellen_area"
    val datasource_num_id: String = SystemCodeUtil.ZY_OWMS_USWE
    val add_time_field = ""
    val update_time_field_1 = ""
    val update_time_field_2: String = ""
    check(AssistParameter(clazz, primaryKey, cf, datasource_num_id, add_time_field, update_time_field_1, update_time_field_2, database, table))
  }

  def ems_owms_au$wellen_area(database: String, table: String): AssistParameter = {
    val clazz: Class[_] = classOf[com.zongteng.ztetl.entity.zy_owms.zy_owms_au.WellenArea]
    val primaryKey: String = "wellen_area_id"
    val cf: String = "wellen_area"
    val datasource_num_id: String = SystemCodeUtil.ZY_OWMS_AU
    val add_time_field = ""
    val update_time_field_1 = ""
    val update_time_field_2: String = ""
    check(AssistParameter(clazz, primaryKey, cf, datasource_num_id, add_time_field, update_time_field_1, update_time_field_2, database, table))
  }

  def ems_owms_cz$wellen_area(database: String, table: String): AssistParameter = {
    val clazz: Class[_] = classOf[com.zongteng.ztetl.entity.zy_owms.zy_owms_cz.WellenArea]
    val primaryKey: String = "wellen_area_id"
    val cf: String = "wellen_area"
    val datasource_num_id: String = SystemCodeUtil.ZY_OWMS_CZ
    val add_time_field = ""
    val update_time_field_1 = ""
    val update_time_field_2: String = ""
    check(AssistParameter(clazz, primaryKey, cf, datasource_num_id, add_time_field, update_time_field_1, update_time_field_2, database, table))
  }

  def ems_owms_de$wellen_area(database: String, table: String): AssistParameter = {
    val clazz: Class[_] = classOf[com.zongteng.ztetl.entity.zy_owms.zy_owms_de.WellenArea]
    val primaryKey: String = "wellen_area_id"
    val cf: String = "wellen_area"
    val datasource_num_id: String = SystemCodeUtil.ZY_OWMS_DE
    val add_time_field = ""
    val update_time_field_1 = ""
    val update_time_field_2: String = ""
    check(AssistParameter(clazz, primaryKey, cf, datasource_num_id, add_time_field, update_time_field_1, update_time_field_2, database, table))
  }

  def ems_owms_ru$wellen_area(database: String, table: String): AssistParameter = {
    val clazz: Class[_] = classOf[com.zongteng.ztetl.entity.zy_owms.zy_owms_ru.WellenArea]
    val primaryKey: String = "wellen_area_id"
    val cf: String = "wellen_area"
    val datasource_num_id: String = SystemCodeUtil.ZY_OWMS_RU
    val add_time_field = ""
    val update_time_field_1 = ""
    val update_time_field_2: String = ""
    check(AssistParameter(clazz, primaryKey, cf, datasource_num_id, add_time_field, update_time_field_1, update_time_field_2, database, table))
  }
  def check(assistParameter: AssistParameter) = {
    assert(assistParameter.className != null, "数据库 {" + assistParameter.database + "} 中表{" + assistParameter.table + "} 对应的样例类不存在,请添加")
    assert(assistParameter.primaryKey != null, "数据库 {" + assistParameter.database + "} 中表{" + assistParameter.table + "} 对应的主键不存在,请添加")
    assert(assistParameter.cf != null, "数据库 {" + assistParameter.database + "} 中表{" + assistParameter.table + "} 对应的列族不存在,请添加")
    assert(assistParameter.datasource_num_id != null, "数据库 {" + assistParameter.database + "} 中表{" + assistParameter.table + "} 对应的数据来源不存在,请添加")
    assert(assistParameter.add_time_field != null, "数据库 {" + assistParameter.database + "} 中表{" + assistParameter.table + "} 对应的新增时间不存在,请添加")
    assert(assistParameter.update_time_field_1 != null, "数据库 {" + assistParameter.database + "} 中表{" + assistParameter.table + "} 对应的修改时间1_时间戳不存在,请添加")
    assert(assistParameter.update_time_field_2 != null, "数据库 {" + assistParameter.database + "} 中表{" + assistParameter.table + "} 对应的修改时间2_修改时间不存在,请添加")
    assistParameter
  }
}
