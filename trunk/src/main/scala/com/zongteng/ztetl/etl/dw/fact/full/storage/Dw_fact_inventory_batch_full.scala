package com.zongteng.ztetl.etl.dw.fact.full.storage

import com.zongteng.ztetl.common.{Dw_dim_common, Dw_fact_common, Dw_par_val_list_cache, SystemCodeUtil}
import com.zongteng.ztetl.util.DateUtil

object Dw_fact_inventory_batch_full {
    //任务名称(一般同类名)
    private val task = "Dw_fact_inventory_batch_full"

    //dw层类名
    private val tableName = "fact_inventory_batch"

    // 获取当天的时间
    private val nowDate: String = DateUtil.getNowTime()

    //要执行的sql语句
    private val gc_wms = "select ib.row_wid " +
      " ,from_unixtime(unix_timestamp(),'yyyyMMdd') as etl_proc_wid" +
      " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_insert_dt" +
      " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_update_dt" +
      " ,ib.datasource_num_id as datasource_num_id" +
      " ,ib.data_flag as  data_flag" +
      " ,ib.integration_id as  integration_id" +
      " ,ib.created_on_dt as created_on_dt" +
      " ,ib.changed_on_dt as changed_on_dt" +
      " ,case " +
      " when tz.timezone_season_type='winner_time'  then  " +
      "     case when ib.ib_add_time between tz.timezone_season_start and tz.timezone_season_end then tz.timezone_winner_time_dif_val else tz.timezone_summer_time_dif_val end " +
      " when   tz.timezone_season_type='summer_time' then " +
      "     case when ib.ib_add_time between tz.timezone_season_start and tz.timezone_season_end then tz.timezone_summer_time_dif_val else tz.timezone_winner_time_dif_val end " +
      " else null end as timezone" +
      " ,null as exchange_rate" +
      " ,concat(w.datasource_num_id,w.warehouse_id) warehouse_key" +
      " ,concat(ib.datasource_num_id,ib.product_id) product_key" +
      " ,c.row_wid as customer_key" +
      " ,wp.row_wid as wp_key" +
      " ,ib.ib_id ib_id" +
      " ,ib.lc_code ib_lc_code" +
      " ,ib.product_id ib_product_id" +
      " ,ib.box_code ib_box_code" +
      " ,ib.product_barcode ib_product_barcode" +
      " ,c.customer_id ib_customer_id " +
      " ,ib.customer_code_own  ib_customer_code_own" +
      " ,ib.reference_no ib_reference_no" +
      " ,ib.application_code ib_application_code" +
      " ,ib.supplier_id ib_supplier_id" +
      " ,ib.warehouse_id ib_warehouse_id" +
      " ,w.warehouse_code ib_warehouse_code" +
      " ,ib.receiving_code ib_receiving_code" +
      " ,ib.receiving_id ib_receiving_id" +
      " ,ib.po_code ib_po_code" +
      " ,ib.lot_number ib_lot_number" +
      " ,ib.ib_type ib_type" +
      " ,nvl(pvl1.vl_bi_name,ib.ib_type) as ib_type_val" +
      " ,ib.ib_status" +
      " ,nvl(pvl2.vl_bi_name,ib.ib_status) as ib_status_val" +
      " ,ib.ib_hold_status" +
      " ,nvl(pvl3.vl_bi_name,ib.ib_hold_status) as ib_hold_status_val" +
      " ,ib.ib_quantity" +
      " ,ib.ib_fifo_time" +
      " ,ib.ib_note" +
      " ,ib.ib_add_time" +
      " ,ib.ib_update_time" +
      " ,ib.transit_receiving_code as ib_transit_receiving_code" +
      " ,ib.ib_timestamp" +
      " ,ib.out_quantity ib_out_quantity" +
      " ,wp.wp_id ib_wp_id" +
      " ,ib.wp_code  ib_wp_code" +
      " ,ib.owms_ib_id ib_owms_ib_id" +
      " ,date_format(ib.created_on_dt,'yyyyMM') as month" +
      " from  (select * from  ods.gc_wms_inventory_batch where data_flag<>'delete') as ib " +
      s" left join  ${Dw_dim_common.getDimSql("gc_wms_warehouse","w")} on w.warehouse_id=ib.warehouse_id" +
      s" left join ${Dw_dim_common.getDimSql("gc_wms_warehouse_physical","wp")} on wp.wp_code=ib.wp_code" +
      s" left join  ${Dw_dim_common.getDimSql("gc_wms_customer","c")} on c.customer_code=ib.customer_code_own" +
      " left join dw.par_timezone as tz on tz.warehouse_code=w.warehouse_code and  tz.timezone_year=year(ib.ib_add_time)" +
      " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9004' and pvl.vl_type='ib_type' and pvl.vl_datasource_table='gc_wms_inventory_batch') " +
      "            as pvl1 on pvl1.vl_value=ib.ib_type" +
      " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9004' and pvl.vl_type='ib_status' and pvl.vl_datasource_table='gc_wms_inventory_batch') " +
      "            as pvl2 on pvl2.vl_value=ib.ib_status" +
      " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9004' and pvl.vl_type='ib_hold_status' and pvl.vl_datasource_table='gc_wms_inventory_batch') " +
      "            as pvl3 on pvl3.vl_value=ib.ib_hold_status"
  private val zy_wms="select ib.row_wid " +
    " ,from_unixtime(unix_timestamp(),'yyyyMMdd') as etl_proc_wid" +
    " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_insert_dt" +
    " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_update_dt" +
    " ,ib.datasource_num_id as datasource_num_id" +
    " ,ib.data_flag as  data_flag" +
    " ,ib.integration_id as  integration_id" +
    " ,ib.created_on_dt as created_on_dt" +
    " ,ib.changed_on_dt as changed_on_dt" +
    " ,case " +
    " when tz.timezone_season_type='winner_time'  then  " +
    "     case when ib.ib_add_time between tz.timezone_season_start and tz.timezone_season_end then tz.timezone_winner_time_dif_val else tz.timezone_summer_time_dif_val end " +
    " when   tz.timezone_season_type='summer_time' then " +
    "     case when ib.ib_add_time between tz.timezone_season_start and tz.timezone_season_end then tz.timezone_summer_time_dif_val else tz.timezone_winner_time_dif_val end " +
    " else null end as timezone" +
    " ,null as exchange_rate" +
    " ,concat(w.datasource_num_id,w.warehouse_id) warehouse_key" +
    " ,concat(ib.datasource_num_id,ib.product_id) product_key" +
    " ,c.row_wid customer_key" +
    " ,null wp_key" +
    " ,ib.ib_id ib_id" +
    " ,ib.lc_code ib_lc_code" +
    " ,ib.product_id ib_product_id" +
    " ,ib.box_code ib_box_code" +
    " ,ib.product_barcode ib_product_barcode" +
    " ,c.customer_id ib_customer_id " +
    " ,ib.customer_code_own  ib_customer_code_own" +
    " ,ib.reference_no ib_reference_no" +
    " ,ib.application_code ib_application_code" +
    " ,ib.supplier_id ib_supplier_id" +
    " ,ib.warehouse_id ib_warehouse_id" +
    " ,w.warehouse_code ib_warehouse_code" +
    " ,ib.receiving_code ib_receiving_code" +
    " ,ib.receiving_id ib_receiving_id" +
    " ,ib.po_code ib_po_code" +
    " ,ib.lot_number ib_lot_number" +
    " ,ib.ib_type ib_type" +
    " ,nvl(pvl1.vl_bi_name,ib.ib_type) as ib_type_val" +
    " ,ib.ib_status" +
    " ,nvl(pvl2.vl_bi_name,ib.ib_status) as ib_status_val" +
    " ,ib.ib_hold_status" +
    " ,nvl(pvl3.vl_bi_name,ib.ib_hold_status) as ib_hold_status_val" +
    " ,ib.ib_quantity" +
    " ,ib.ib_fifo_time" +
    " ,ib.ib_note" +
    " ,ib.ib_add_time" +
    " ,ib.ib_update_time" +
    " ,ib.transit_receiving_code as ib_transit_receiving_code" +
    " ,ib.ib_timestamp" +
    " ,null ib_out_quantity" +
    " ,null ib_wp_id" +
    " ,null ib_wp_code" +
    " ,null ib_owms_ib_id" +
    " ,date_format(ib.created_on_dt,'yyyyMM') as month" +
    " from (select * from  ods.zy_wms_inventory_batch where data_flag<>'delete') as ib " +
    s" left join ${Dw_dim_common.getDimSql("zy_wms_warehouse","w")} on w.warehouse_id=ib.warehouse_id" +
    s" left join  ${Dw_dim_common.getDimSql("zy_wms_customer","c")} on c.customer_code=ib.customer_code_own" +
    " left join dw.par_timezone as tz on tz.warehouse_code=w.warehouse_code and  tz.timezone_year=year(ib.ib_add_time)" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9022' and pvl.vl_type='ib_type' and pvl.vl_datasource_table='zy_wms_inventory_batch') " +
    "            as pvl1 on pvl1.vl_value=ib.ib_type" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9022' and pvl.vl_type='ib_status' and pvl.vl_datasource_table='zy_wms_inventory_batch') " +
    "            as pvl2 on pvl2.vl_value=ib.ib_status" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9022' and pvl.vl_type='ib_hold_status' and pvl.vl_datasource_table='zy_wms_inventory_batch') " +
    "            as pvl3 on pvl3.vl_value=ib.ib_hold_status"

    def main(args: Array[String]): Unit = {
      val sqlArray: Array[String] = Array(gc_wms,zy_wms).map(_.replaceAll("dw.par_val_list",Dw_par_val_list_cache.TEMP_PAR_VAL_LIST_NAME))
      Dw_fact_common.getRunCode_hive_full_Into(task,tableName,sqlArray,Array(SystemCodeUtil.GC_WMS,SystemCodeUtil.ZY_WMS))
    }
}
