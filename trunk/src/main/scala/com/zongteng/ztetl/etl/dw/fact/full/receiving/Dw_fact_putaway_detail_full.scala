package com.zongteng.ztetl.etl.dw.fact.full.receiving

import com.zongteng.ztetl.common.{Dw_dim_common, Dw_fact_common, Dw_par_val_list_cache, SystemCodeUtil}
import com.zongteng.ztetl.etl.dw.fact.full.receiving.Dw_fact_receiving_full._
import com.zongteng.ztetl.util.DateUtil

object Dw_fact_putaway_detail_full {
  //任务名称(一般同类名)
  private val task = "Dw_fact_putaway_detail_full"

  //dw层类名
  private val tableName = "fact_putaway_detail"

  //获取当天的时间
  private val nowDate: String = DateUtil.getNowTime()

  //要执行的sql语句
  private val zy_wms = "SELECT pd.row_wid as row_wid" +
    " ,from_unixtime(unix_timestamp(),'yyyyMMdd') as etl_proc_wid" +
    " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_insert_dt" +
    " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_update_dt" +
    " ,pd.datasource_num_id as datasource_num_id" +
    " ,pd.data_flag as data_flag" +
    " ,pd.integration_id as integration_id" +
    " ,pd.created_on_dt as  created_on_dt" +
    " ,pd.changed_on_dt as changed_on_dt" +
    " ,case when (ptz.timezone_season_type='summer_time' and pd.pd_putaway_time between ptz.timezone_season_start and ptz.timezone_season_end)" +
    " or" +
    " (ptz.timezone_season_type='winner_time' and pd.pd_putaway_time not between ptz.timezone_season_start and ptz.timezone_season_end) " +
    " then ptz.timezone_summer_number" +
    " when  (ptz.timezone_season_type='summer_time' and pd.pd_putaway_time not between ptz.timezone_season_start and ptz.timezone_season_end)" +
    " or" +
    " (ptz.timezone_season_type='winner_time' and pd.pd_putaway_time  between ptz.timezone_season_start and ptz.timezone_season_end) " +
    " then ptz.timezone_winner_number" +
    " else null end " +
    " as timezone" +
    " ,null as exchange_rate" +
    " ,cast(concat(pd.datasource_num_id,pd.product_id) as bigint) as product_key" +
    " ,cast(concat(pd.datasource_num_id,pd.warehouse_id) as bigint) as warehouse_key" +
    " ,cast(concat(pd.datasource_num_id,rv.customer_id) as bigint) as customer_key" +
    " ,pd.pd_id as pd_id" +
    " ,pd.putaway_id as pd_putaway_id" +
    " ,pd.qc_code as pd_qc_code" +
    " ,pd.box_code as pd_box_code" +
    " ,pd.putaway_code as pd_putaway_code" +
    " ,pd.receiving_code as pd_receiving_code" +
    " ,pd.lc_code as pd_lc_code" +
    " ,pd.pd_type as pd_type" +
    " ,nvl(pvl3.vl_bi_name,pd.pd_type) as pd_type_val" +
    " ,pd.product_id as pd_product_id" +
    " ,pd.product_barcode as pd_product_barcode" +
    " ,rv.customer_code as pd_customer_code" +
    " ,null as pd_rd_id" +
    " ,pd.pd_quantity as pd_quantity" +
    " ,pd.pd_lot_number as pd_lot_number" +
    " ,pd.warehouse_id as pd_warehouse_id" +
    " ,wa.warehouse_code as pd_warehouse_code" +
    " ,pd.pd_note as pd_note" +
    " ,pd.pd_status as pd_status" +
    " ,nvl(pvl2.vl_bi_name,pd.pd_status) as pd_status_val" +
    " ,pd.putaway_user_id as pd_putaway_user_id" +
    " ,pd.transit_receiving_code as pd_transit_receiving_code" +
    " ,pd.pd_inventory_type as pd_inventory_type" +
    " ,nvl(pvl1.vl_bi_name,pd.pd_inventory_type) as pd_inventory_type_val" +
    " ,pd.pd_putaway_time as pd_putaway_time" +
    " ,pd.pd_timestamp as pd_timestamp" +
    " ,pd.pd_add_time as pd_add_time" +
    " ,pd.pd_update_time as pd_update_time" +
    " ,date_format(pd.created_on_dt,'yyyyMM') as month" +
    " FROM (select * from ods.zy_wms_putaway_detail where data_flag<>'delete' ) as pd" +
    s" left join ${Dw_dim_common.getDimSql("zy_wms_warehouse","wa")} on wa.warehouse_id=pd.warehouse_id" +
    " left join dw.par_timezone as ptz on ptz.warehouse_code=wa.warehouse_code and date_format(pd.pd_putaway_time,'yyyy')=ptz.timezone_year" +
    " left join (select * from ods.zy_wms_receiving where data_flag<>'delete' ) as rv on rv.receiving_code=pd.receiving_code" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9022' and pvl.vl_type='pd_inventory_type' and pvl.vl_datasource_table='zy_wms_putaway_detail') as pvl1 on pvl1.vl_value=pd.pd_inventory_type" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9022' and pvl.vl_type='pd_status' and pvl.vl_datasource_table='zy_wms_putaway_detail') as pvl2 on pvl2.vl_value=pd.pd_status" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9022' and pvl.vl_type='pd_type' and pvl.vl_datasource_table='zy_wms_putaway_detail') as pvl3 on pvl3.vl_value=pd.pd_type"
  private val gc_wms_gc = "SELECT pd.row_wid as row_wid" +
    " ,from_unixtime(unix_timestamp(),'yyyyMMdd') as etl_proc_wid" +
    " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_insert_dt" +
    " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_update_dt" +
    " ,pd.datasource_num_id as datasource_num_id" +
    " ,pd.data_flag as data_flag" +
    " ,pd.integration_id as integration_id" +
    " ,pd.created_on_dt as  created_on_dt" +
    " ,pd.changed_on_dt as changed_on_dt" +
    " ,case when (ptz.timezone_season_type='summer_time' and pd.pd_putaway_time between ptz.timezone_season_start and ptz.timezone_season_end)" +
    " or" +
    " (ptz.timezone_season_type='winner_time' and pd.pd_putaway_time not between ptz.timezone_season_start and ptz.timezone_season_end) " +
    " then ptz.timezone_summer_number" +
    " when  (ptz.timezone_season_type='summer_time' and pd.pd_putaway_time not between ptz.timezone_season_start and ptz.timezone_season_end)" +
    " or" +
    " (ptz.timezone_season_type='winner_time' and pd.pd_putaway_time  between ptz.timezone_season_start and ptz.timezone_season_end) " +
    " then ptz.timezone_winner_number" +
    " else null end " +
    " as timezone" +
    " ,null as exchange_rate" +
    " ,pro.row_wid as product_key" +
    " ,cast(concat(pd.datasource_num_id,pd.warehouse_id) as bigint) as warehouse_key" +
    " ,cus.row_wid as customer_key" +
    " ,pd.pd_id as pd_id" +
    " ,null as pd_putaway_id" +
    " ,null as pd_qc_code" +
    " ,null as pd_box_code" +
    " ,null as pd_putaway_code" +
    " ,pd.receiving_code as pd_receiving_code" +
    " ,pd.lc_code as pd_lc_code" +
    " ,pd.pd_type as pd_type" +
    " ,nvl(pvl3.vl_bi_name,pd.pd_type) as pd_type_val" +
    " ,pro.product_id as pd_product_id" +
    " ,pd.product_barcode as pd_product_barcode" +
    " ,pd.customer_code as pd_customer_code" +
    " ,pd.rd_id as pd_rd_id" +
    " ,pd.pd_quantity as pd_quantity" +
    " ,null as pd_lot_number" +
    " ,pd.warehouse_id as pd_warehouse_id" +
    " ,wa.warehouse_code as pd_warehouse_code" +
    " ,pd.pd_note as pd_note" +
    " ,pd.pd_status as pd_status" +
    " ,nvl(pvl2.vl_bi_name,pd.pd_status) as pd_status_val" +
    " ,pd.putaway_user_id as pd_putaway_user_id" +
    " ,pd.transit_receiving_code as pd_transit_receiving_code" +
    " ,pd.pd_inventory_type as pd_inventory_type" +
    " ,nvl(pvl1.vl_bi_name,pd.pd_inventory_type) as pd_inventory_type_val" +
    " ,pd.pd_putaway_time as pd_putaway_time" +
    " ,null as pd_timestamp" +
    " ,pd.pd_add_time as pd_add_time" +
    " ,pd.pd_update_time as pd_update_time" +
    " ,date_format(pd.created_on_dt,'yyyyMM') as month" +
    " FROM (select * from ods.gc_wms_gc_putaway_detail where data_flag<>'delete' ) as pd" +
    s" LEFT JOIN ${Dw_dim_common.getDimSql("gc_wms_product","pro")} ON pro.product_barcode=pd.product_barcode" +
    s" left join ${Dw_dim_common.getDimSql("gc_wms_customer","cus")} on cus.customer_code=pd.customer_code" +
    s" left join ${Dw_dim_common.getDimSql("gc_wms_warehouse","wa")} on wa.warehouse_id=pd.warehouse_id" +
    " left join dw.par_timezone as ptz on ptz.warehouse_code=wa.warehouse_code and date_format(pd.pd_putaway_time,'yyyy')=ptz.timezone_year" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9004' and pvl.vl_type='pd_inventory_type' and pvl.vl_datasource_table='gc_wms_gc_putaway_detail') as pvl1 on pvl1.vl_value=pd.pd_inventory_type" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9004' and pvl.vl_type='pd_status' and pvl.vl_datasource_table='gc_wms_gc_putaway_detail') as pvl2 on pvl2.vl_value=pd.pd_status" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9004' and pvl.vl_type='pd_type' and pvl.vl_datasource_table='gc_wms_gc_putaway_detail') as pvl3 on pvl3.vl_value=pd.pd_type"


  def main(args: Array[String]): Unit = {
    val sqlArray: Array[String] = Array(zy_wms,gc_wms_gc).map(_.replaceAll("dw.par_val_list", Dw_par_val_list_cache.TEMP_PAR_VAL_LIST_NAME))

    Dw_fact_common.getRunCode_hive_full_Into(task, tableName, sqlArray, Array(SystemCodeUtil.ZY_WMS,SystemCodeUtil.GC_WMS))

  }

}
