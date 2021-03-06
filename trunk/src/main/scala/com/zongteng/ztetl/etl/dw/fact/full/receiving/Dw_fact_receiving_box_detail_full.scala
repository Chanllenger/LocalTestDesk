package com.zongteng.ztetl.etl.dw.fact.full.receiving

import com.zongteng.ztetl.common.{Dw_dim_common, Dw_fact_common, Dw_par_val_list_cache, SystemCodeUtil}
import com.zongteng.ztetl.etl.dw.fact.full.receiving.Dw_fact_receiving_detail_full._
import com.zongteng.ztetl.util.DateUtil

object Dw_fact_receiving_box_detail_full {
  //任务名称(一般同类名)
  private val task = "Dw_fact_receiving_box_detail_full"

  //dw层类名
  private val tableName = "fact_receiving_box_detail"

  //获取当天的时间
  private val nowDate: String = DateUtil.getNowTime()

  //要执行的sql语句
  private val zy_wms = "SELECT rbd.row_wid as row_wid" +
    " ,from_unixtime(unix_timestamp(),'yyyyMMdd') as etl_proc_wid" +
    " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_insert_dt" +
    " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_update_dt" +
    " ,rbd.datasource_num_id as datasource_num_id" +
    " ,rbd.data_flag as data_flag" +
    " ,rbd.integration_id as integration_id" +
    " ,rbd.created_on_dt as created_on_dt" +
    " ,rbd.changed_on_dt as changed_on_dt" +
    " ,null as timezone" +
    " ,null as exchange_rate" +
    " ,cast(concat(rbd.datasource_num_id,rbd.product_id) as bigint) as product_key" +
    " ,cast(concat(rbd.datasource_num_id,rb.warehouse_id) as bigint) as warehouse_key" +
    " ,rbd.rbd_id as rbd_id" +
    " ,rbd.rb_id as rbd_rb_id" +
    " ,rbd.product_id as rbd_product_id" +
    " ,rbd.product_barcode as rbd_product_barcode" +
    " ,rb.receiving_id as rbd_receiving_id" +
    " ,rb.receiving_code as rbd_receiving_code" +
    " ,wh.warehouse_code as rbd_warehouse_code" +
    " ,rbd.quantity as rbd_quantity" +
    " ,rbd.received_qty as rbd_received_qty" +
    " ,null as rbd_putaway_qty" +
    " ,null as rbd_status" +
    " ,null as rbd_status_val" +
    " ,rbd.package_type as rbd_package_type" +
    " ,rbd.product_package_type as rbd_product_package_type" +
    " ,rbd.fba_product_code as rbd_fba_product_code" +
    " ,rbd.is_new_add as rbd_is_new_add" +
    " ,nvl(pvl1.vl_bi_name,rbd.is_new_add) as rbd_is_new_add_val" +
    " ,rbd.add_new_box_quantity as rbd_add_new_box_quantity" +
    " ,rbd.receiving_width as rbd_receiving_width" +
    " ,rbd.receiving_height as rbd_receiving_height" +
    " ,rbd.receiving_length as rbd_receiving_length" +
    " ,rbd.receiving_weight as rbd_receiving_weight" +
    " ,rbd.received_weight as rbd_received_weight" +
    " ,rbd.received_length as rbd_received_length" +
    " ,rbd.received_width as rbd_received_width" +
    " ,rbd.received_height as rbd_received_height" +
    " ,rbd.valid_date as rbd_valid_date" +
    " ,rbd.rbx_timestamp as rbd_rbx_timestamp" +
    " FROM (select * from ods.zy_wms_receiving_box_detail where data_flag<>'delete' ) as rbd" +
    " left join (select * from ods.zy_wms_receiving_box where data_flag<>'delete' ) as rb on rb.rb_id=rbd.rb_id" +
    s" left join ${Dw_dim_common.getDimSql("zy_wms_warehouse","wh")} on wh.warehouse_id=rb.warehouse_id" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9022' and pvl.vl_type='is_new_add' and pvl.vl_datasource_table='zy_wms_receiving_box_detail') as pvl1 on pvl1.vl_value=rbd.is_new_add"
  private val gc_wms_gc="SELECT rbd.row_wid as row_wid" +
    " ,from_unixtime(unix_timestamp(),'yyyyMMdd') as etl_proc_wid" +
    " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_insert_dt" +
    " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_update_dt" +
    " ,rbd.datasource_num_id as datasource_num_id" +
    " ,rbd.data_flag as data_flag" +
    " ,rbd.integration_id as integration_id" +
    " ,rbd.created_on_dt as created_on_dt" +
    " ,rbd.changed_on_dt as changed_on_dt" +
    " ,null as timezone" +
    " ,null as exchange_rate" +
    " ,cast(concat(rbd.datasource_num_id,pro.product_id) as bigint) as product_key" +
    " ,cast(concat(rbd.datasource_num_id,wh.warehouse_id) as bigint) as warehouse_key" +
    " ,rbd.rbd_id as rbd_id" +
    " ,rbd.rb_id as rbd_rb_id" +
    " ,pro.product_id as rbd_product_id" +
    " ,rbd.product_barcode as rbd_product_barcode" +
    " ,rb.receiving_id as rbd_receiving_id" +
    " ,rb.receiving_code as rbd_receiving_code" +
    " ,wh.warehouse_code as rbd_warehouse_code" +
    " ,rbd.quantity as rbd_quantity" +
    " ,rbd.received_qty as rbd_received_qty" +
    " ,rbd.rbd_putaway_qty as rbd_putaway_qty" +
    " ,rbd.rbd_status as rbd_status" +
    " ,nvl(pvl2.vl_bi_name,rbd.rbd_status) as rbd_status_val" +
    " ,null as rbd_package_type" +
    " ,null as rbd_product_package_type" +
    " ,rbd.fba_product_code as rbd_fba_product_code" +
    " ,rbd.is_new_add as rbd_is_new_add" +
    " ,nvl(pvl1.vl_bi_name,rbd.is_new_add) as rbd_is_new_add_val" +
    " ,rbd.add_new_box_quantity as rbd_add_new_box_quantity" +
    " ,null as rbd_receiving_width" +
    " ,null as rbd_receiving_height" +
    " ,null as rbd_receiving_length" +
    " ,null as rbd_receiving_weight" +
    " ,null as rbd_received_weight" +
    " ,null as rbd_received_length" +
    " ,null as rbd_received_width" +
    " ,null as rbd_received_height" +
    " ,null as rbd_valid_date" +
    " ,rbd.r_timestamp as rbd_rbx_timestamp" +
    " FROM (select * from ods.gc_wms_gc_receiving_box_detail  where data_flag<>'delete' ) as rbd" +
    s" left join ${Dw_dim_common.getDimSql("gc_wms_product","pro")} on pro.product_barcode=rbd.product_barcode" +
    " left join (select * from ods.gc_wms_gc_receiving_box  where data_flag<>'delete' ) as rb on rb.rb_id=rbd.rb_id" +
    " left join (select * from ods.gc_wms_gc_receiving  where data_flag<>'delete' ) as rv on rv.receiving_id=rb.receiving_id" +
    s" left join ${Dw_dim_common.getDimSql("gc_wms_warehouse","wh")} on wh.warehouse_code=rv.warehouse_code" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9004' and pvl.vl_type='is_new_add' and pvl.vl_datasource_table='gc_wms_gc_receiving_box_detail') as pvl1 on pvl1.vl_value=rbd.is_new_add" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9004' and pvl.vl_type='rbd_status' and pvl.vl_datasource_table='gc_wms_gc_receiving_box_detail') as pvl2 on pvl2.vl_value=rbd.rbd_status"

  def main(args: Array[String]): Unit = {
    val sqlArray: Array[String] = Array(zy_wms,gc_wms_gc).map(_.replaceAll("dw.par_val_list", Dw_par_val_list_cache.TEMP_PAR_VAL_LIST_NAME))

    Dw_fact_common.getRunCode_hive_nopartition_full_Into(task, tableName, sqlArray, Array(SystemCodeUtil.ZY_WMS,SystemCodeUtil.GC_WMS))
  }
}
