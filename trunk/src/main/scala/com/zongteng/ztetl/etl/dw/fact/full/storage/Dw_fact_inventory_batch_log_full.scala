package com.zongteng.ztetl.etl.dw.fact.full.storage

import com.zongteng.ztetl.common.{Dw_dim_common, Dw_fact_common, Dw_par_val_list_cache, SystemCodeUtil}
import com.zongteng.ztetl.util.DateUtil

object Dw_fact_inventory_batch_log_full {
    //任务名称(一般同类名)
    private val task = "Dw_fact_inventory_batch_log_full"

    //dw层类名
    private val tableName = "fact_inventory_batch_log"

    // 获取当天的时间
    private val nowDate: String = DateUtil.getNowTime()

    //要执行的sql语句
    private val gc_wms = "select concat(ibl.row_wid,'_','inventory_batch_log') as row_wid " +
      " ,from_unixtime(unix_timestamp(),'yyyyMMdd') as etl_proc_wid" +
      " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_insert_dt" +
      " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_update_dt" +
      " ,ibl.datasource_num_id as datasource_num_id" +
      " ,ibl.data_flag as  data_flag" +
      " ,ibl.integration_id as  integration_id" +
      " ,ibl.created_on_dt as created_on_dt" +
      " ,ibl.changed_on_dt as changed_on_dt" +
      " ,case " +
      " when tz.timezone_season_type='winner_time'  then  " +
      "     case when ibl.ibl_add_time between tz.timezone_season_start and tz.timezone_season_end then tz.timezone_winner_time_dif_val else tz.timezone_summer_time_dif_val " +
      " end " +
      " when   tz.timezone_season_type='summer_time' then " +
      "     case when ibl.ibl_add_time between tz.timezone_season_start and tz.timezone_season_end then tz.timezone_summer_time_dif_val else tz.timezone_winner_time_dif_val " +
      " end " +
      " else null end as timezone" +
      " ,null as exchange_rate" +
      " ,concat(ibl.datasource_num_id,ibl.warehouse_id) warehouse_key" +
      " ,concat(ibl.datasource_num_id,ibl.product_id) product_key" +
      " ,c.row_wid customer_key" +
      " ,wp.row_wid wp_key" +
      " ,ibl.ibl_id" +
      " ,ibl.ref_no ibl_ref_no" +
      " ,ibl.lc_code ibl_lc_code" +
      " ,ibl.product_id ibl_product_id" +
      " ,ibl.product_barcode ibl_product_barcode" +
      " ,ibl.supplier_id ibl_supplier_id" +
      " ,ibl.warehouse_id ibl_warehouse_id" +
      " ,ibl.ib_id ibl_ib_id" +
      " ,ibl.receiving_code ibl_receiving_code" +
      " ,ibl.po_code ibl_po_code" +
      " ,ibl.box_code ibl_box_code" +
      " ,ibl.reference_no ibl_reference_no" +
      " ,ibl.application_code ibl_application_code" +
      " ,nvl(pvl1.vl_bi_name,ibl.application_code) ibl_application_code_val" +
      " ,ibl.ibl_note" +
      " ,ibl.ibl_quantity_before" +
      " ,ibl.ibl_quantity_after" +
      " ,ibl.user_id ibl_user_id" +
      " ,ibl.ibl_ip" +
      " ,ibl.ibl_add_time" +
      " ,c.customer_id ibl_customer_id" +
      " ,ib.customer_code_own  ibl_customer_code" +
      " ,wp.wp_id ibl_wp_id" +
      " ,ib.wp_code ibl_wp_code" +
      " ,w.warehouse_code ibl_warehouse_code" +
      " ,date_format(ibl.created_on_dt,'yyyyMM') as month" +
      " from  (select * from ods.gc_wms_inventory_batch_log  where ibl_add_time<'2019-09-24' and data_flag<>'delete') as ibl" +
      " left join  (select * from ods.gc_wms_inventory_batch where data_flag<>'delete') as ib on ib.ib_id=ibl.ib_id" +
      s" left join  ${Dw_dim_common.getDimSql("gc_wms_warehouse","w")} on w.warehouse_id=ibl.warehouse_id" +
      s" left join  ${Dw_dim_common.getDimSql("gc_wms_warehouse_physical","wp")} on wp.wp_code=ib.wp_code" +
      s" left join  ${Dw_dim_common.getDimSql("gc_wms_customer","c")} on c.customer_code=ib.customer_code_own" +
      " left join dw.par_timezone as tz on tz.warehouse_code=w.warehouse_code and  tz.timezone_year=year(ibl.ibl_add_time)" +
      " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9004' and pvl.vl_type='application_code' and " +
      " pvl.vl_datasource_table='gc_wms_inventory_batch_log') as pvl1 on pvl1.vl_value=ibl.application_code"
  private val zy_wms = "select concat(ibl.row_wid,'_','inventory_batch_log') as row_wid " +
    " ,from_unixtime(unix_timestamp(),'yyyyMMdd') as etl_proc_wid" +
    " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_insert_dt" +
    " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_update_dt" +
    " ,ibl.datasource_num_id as datasource_num_id" +
    " ,ibl.data_flag as  data_flag" +
    " ,ibl.integration_id as  integration_id" +
    " ,ibl.created_on_dt as created_on_dt" +
    " ,ibl.changed_on_dt as changed_on_dt" +
    " ,case " +
    " when tz.timezone_season_type='winner_time'  then  " +
    "     case when ibl.ibl_add_time between tz.timezone_season_start and tz.timezone_season_end then tz.timezone_winner_time_dif_val else tz.timezone_summer_time_dif_val " +
    " end " +
    " when   tz.timezone_season_type='summer_time' then " +
    "     case when ibl.ibl_add_time between tz.timezone_season_start and tz.timezone_season_end then tz.timezone_summer_time_dif_val else tz.timezone_winner_time_dif_val " +
    " end " +
    " else null end as timezone" +
    " ,null as exchange_rate" +
    " ,concat(ibl.datasource_num_id,ibl.warehouse_id) warehouse_key" +
    " ,concat(ibl.datasource_num_id,ibl.product_id) product_key" +
    " ,c.row_wid customer_key" +
    " ,null wp_key" +
    " ,ibl.ibl_id" +
    " ,ibl.ref_no ibl_ref_no" +
    " ,ibl.lc_code ibl_lc_code" +
    " ,ibl.product_id ibl_product_id" +
    " ,ibl.product_barcode ibl_product_barcode" +
    " ,ibl.supplier_id ibl_supplier_id" +
    " ,ibl.warehouse_id ibl_warehouse_id" +
    " ,ibl.ib_id ibl_ib_id" +
    " ,ibl.receiving_code ibl_receiving_code" +
    " ,ibl.po_code ibl_po_code" +
    " ,ibl.box_code ibl_box_code" +
    " ,ibl.reference_no ibl_reference_no" +
    " ,ibl.application_code ibl_application_code" +
    " ,nvl(pvl1.vl_bi_name,ibl.application_code) ibl_application_code_val" +
    " ,ibl.ibl_note" +
    " ,ibl.ibl_quantity_before" +
    " ,ibl.ibl_quantity_after" +
    " ,ibl.user_id ibl_user_id" +
    " ,ibl.ibl_ip" +
    " ,ibl.ibl_add_time" +
    " ,c.customer_id ibl_customer_id" +
    " ,ib.customer_code_own  ibl_customer_code" +
    " ,null  ibl_wp_id" +
    " ,null ibl_wp_code" +
    " ,w.warehouse_code ibl_warehouse_code" +
    " ,date_format(ibl.created_on_dt,'yyyyMM') as month" +
    " from  (select * from ods.zy_wms_inventory_batch_log  where  data_flag<>'delete') as  ibl" +
    " left join  (select * from ods.zy_wms_inventory_batch  where  data_flag<>'delete') as  ib on ib.ib_id=ibl.ib_id" +
    s" left join  ${Dw_dim_common.getDimSql("zy_wms_warehouse","w")} on w.warehouse_id=ibl.warehouse_id" +
    s" left join  ${Dw_dim_common.getDimSql("zy_wms_customer","c")} on c.customer_code=ib.customer_code_own" +
    " left join dw.par_timezone as  tz on tz.warehouse_code=w.warehouse_code and  tz.timezone_year=year(ibl.ibl_add_time)" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9022' and pvl.vl_type='application_code' and " +
    " pvl.vl_datasource_table='zy_wms_inventory_batch_log') as pvl1 on pvl1.vl_value=ibl.application_code"
  private val gc_wms_ibl = "select ibl.row_wid " +
    " ,from_unixtime(unix_timestamp(),'yyyyMMdd') as etl_proc_wid" +
    " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_insert_dt" +
    " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_update_dt" +
    " ,ibl.datasource_num_id as datasource_num_id" +
    " ,ibl.data_flag as  data_flag" +
    " ,ibl.integration_id as  integration_id" +
    " ,ibl.created_on_dt as created_on_dt" +
    " ,ibl.changed_on_dt as changed_on_dt" +
    " ,case " +
    " when tz.timezone_season_type='winner_time'  then  " +
    "     case when ibl.ibl_add_time between tz.timezone_season_start and tz.timezone_season_end then tz.timezone_winner_time_dif_val else tz.timezone_summer_time_dif_val end " +
    " when   tz.timezone_season_type='summer_time' then " +
    "     case when ibl.ibl_add_time between tz.timezone_season_start and tz.timezone_season_end then tz.timezone_summer_time_dif_val else tz.timezone_winner_time_dif_val end " +
    " else null end as timezone" +
    " ,null as exchange_rate" +
    " ,concat(ibl.datasource_num_id,ibl.warehouse_id) warehouse_key" +
    " ,concat(ibl.datasource_num_id,ibl.product_id) product_key" +
    " ,c.row_wid customer_key" +
    " ,wp.row_wid wp_key" +
    " ,ibl.ibl_id" +
    " ,ibl.ref_no ibl_ref_no" +
    " ,ibl.lc_code ibl_lc_code" +
    " ,ibl.product_id ibl_product_id" +
    " ,ibl.product_barcode ibl_product_barcode" +
    " ,ibl.supplier_id ibl_supplier_id" +
    " ,ibl.warehouse_id ibl_warehouse_id" +
    " ,ibl.ib_id ibl_ib_id" +
    " ,ibl.receiving_code ibl_receiving_code" +
    " ,ibl.po_code ibl_po_code" +
    " ,ibl.box_code ibl_box_code" +
    " ,ibl.reference_no ibl_reference_no" +
    " ,ibl.application_code ibl_application_code" +
    " ,nvl(pvl1.vl_bi_name,ibl.application_code) ibl_application_code_val" +
    " ,ibl.ibl_note" +
    " ,ibl.ibl_quantity_before" +
    " ,ibl.ibl_quantity_after" +
    " ,ibl.user_id ibl_user_id" +
    " ,ibl.ibl_ip" +
    " ,ibl.ibl_add_time" +
    " ,c.customer_id ibl_customer_id" +
    " ,ibl.customer_code  ibl_customer_code" +
    " ,wp.wp_id ibl_wp_id" +
    " ,ib.wp_code ibl_wp_code" +
    " ,w.warehouse_code ibl_warehouse_code" +
    " ,date_format(ibl.created_on_dt,'yyyyMM') as month" +
    " from  (select * from ods.gc_wms_ibl_inventory_batch_log  where ibl_add_time>='2019-09-24' and data_flag<>'delete') as ibl" +
    " left join (select * from ods.gc_wms_inventory_batch  where  data_flag<>'delete') as ib on ib.ib_id=ibl.ib_id" +
    s" left join ${Dw_dim_common.getDimSql("gc_wms_warehouse","w")} on w.warehouse_id=ibl.warehouse_id" +
    s" left join ${Dw_dim_common.getDimSql("gc_wms_warehouse_physical","wp")} on wp.wp_code=ib.wp_code" +
    s" left join ${Dw_dim_common.getDimSql("gc_wms_customer","c")} on c.customer_code=ibl.customer_code" +
    " left join dw.par_timezone as tz on tz.warehouse_code=w.warehouse_code and  tz.timezone_year=year(ibl.ibl_add_time)" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9004' and pvl.vl_type='application_code' and pvl.vl_datasource_table='gc_wms_inventory_batch_log') as pvl1 on pvl1.vl_value=ibl.application_code"

    def main(args: Array[String]): Unit = {
      val sqlArray: Array[String] = Array(gc_wms,zy_wms,gc_wms_ibl).map(_.replaceAll("dw.par_val_list",Dw_par_val_list_cache.TEMP_PAR_VAL_LIST_NAME))
      Dw_fact_common.getRunCode_hive_full_Into(task,tableName,sqlArray,Array(SystemCodeUtil.GC_WMS,SystemCodeUtil.ZY_WMS,SystemCodeUtil.GC_WMS_IBL))
    }
}
