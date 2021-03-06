package com.zongteng.ztetl.etl.dw.dim

import com.zongteng.ztetl.common.{Dw_dim_common, Dw_par_val_list_cache, SystemCodeUtil}
import com.zongteng.ztetl.util.DateUtil

object DimZoneScheme {
  //任务名称(一般同类名)
  private val task = "DimZoneScheme"

  //dw层类名
  private val tableName = "dim_zone_scheme"

  //获取当天的时间
  private val nowDate: String = DateUtil.getNowTime()
  //要执行的sql语句
  private val gc_wms ="SELECT zs.row_wid as row_wid" +
    " ,from_unixtime(unix_timestamp(),'yyyyMMdd') as etl_proc_wid" +
    " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_insert_dt" +
    " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_update_dt" +
    " ,zs.datasource_num_id as datasource_num_id" +
    " ,zs.data_flag as data_flag" +
    " ,zs.integration_id as integration_id" +
    " ,zs.created_on_dt as created_on_dt" +
    " ,zs.changed_on_dt as changed_on_dt" +
    " ,zs.zs_id as zs_id" +
    " ,zs.zs_status as zs_status" +
    " ,zs.zs_name as zs_name" +
    " ,zs.zs_name_en as zs_name_en" +
    " ,zs.pg_code as zs_pg_code" +
    " ,zs.sm_code as zs_sm_code" +
    " ,zs.sc_id as zs_sc_id" +
    " ,zs.zs_creator_id as zs_creator_id" +
    " ,zs.zs_modify_id as zs_modify_id" +
    " ,zs.zs_note as zs_note" +
    " ,zs.zs_add_time as zs_add_time" +
    " ,zs.zs_update_time as zs_update_time" +
    " ,zs.zs_is_oda as zs_is_oda" +
    " ,nvl(pvl1.vl_bi_name,zs.zs_is_oda) as zs_is_oda_val" +
    " FROM (select * from ods.gc_wms_sp_zone_scheme where day="+ nowDate +" ) as zs" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9004' and pvl.vl_type='zs_is_oda' and pvl.vl_datasource_table='gc_wms_sp_zone_scheme') as pvl1 on pvl1.vl_value=zs.zs_is_oda"
  private val zy_wms ="SELECT zs.row_wid as row_wid" +
    " ,from_unixtime(unix_timestamp(),'yyyyMMdd') as etl_proc_wid" +
    " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_insert_dt" +
    " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_update_dt" +
    " ,zs.datasource_num_id as datasource_num_id" +
    " ,zs.data_flag as data_flag" +
    " ,zs.integration_id as integration_id" +
    " ,zs.created_on_dt as created_on_dt" +
    " ,zs.changed_on_dt as changed_on_dt" +
    " ,zs.zs_id as zs_id" +
    " ,zs.zs_status as zs_status" +
    " ,zs.zs_name as zs_name" +
    " ,zs.zs_name_en as zs_name_en" +
    " ,zs.pg_code as zs_pg_code" +
    " ,zs.sm_code as zs_sm_code" +
    " ,zs.sc_id as zs_sc_id" +
    " ,zs.zs_creator_id as zs_creator_id" +
    " ,zs.zs_modify_id as zs_modify_id" +
    " ,zs.zs_note as zs_note" +
    " ,zs.zs_add_time as zs_add_time" +
    " ,zs.zs_update_time as zs_update_time" +
    " ,null as zs_is_oda" +
    " ,null as zs_is_oda_val" +
    " FROM (select * from ods.zy_wms_sp_zone_scheme where day="+ nowDate +" ) as zs"

  def main(args: Array[String]): Unit = {
    val sqlArray: Array[String] = Array(gc_wms,zy_wms).map(_.replaceAll("dw.par_val_list", Dw_par_val_list_cache.TEMP_PAR_VAL_LIST_NAME))
    Dw_dim_common.getRunCode_full(task,tableName,sqlArray,Array(SystemCodeUtil.GC_WMS,SystemCodeUtil.ZY_WMS))
  }
}
