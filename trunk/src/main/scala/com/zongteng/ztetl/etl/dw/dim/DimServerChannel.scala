package com.zongteng.ztetl.etl.dw.dim

import com.zongteng.ztetl.common.{Dw_dim_common, Dw_par_val_list_cache, SystemCodeUtil}
import com.zongteng.ztetl.util.DateUtil

object DimServerChannel {
  //任务名称(一般同类名)
  private val task = "DimServerChannel"

  //dw层类名
  private val tableName = "dim_server_channel"

  //获取当天的时间
  private val nowDate: String = DateUtil.getNowTime()
  //要执行的sql语句
  private val gc_wms ="SELECT ssc.row_wid as row_wid" +
    " ,from_unixtime(unix_timestamp(),'yyyyMMdd') as etl_proc_wid" +
    " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_insert_dt" +
    " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_update_dt" +
    " ,ssc.datasource_num_id as datasource_num_id" +
    " ,ssc.data_flag as data_flag" +
    " ,ssc.integration_id as integration_id" +
    " ,ssc.created_on_dt as created_on_dt" +
    " ,ssc.changed_on_dt as changed_on_dt" +
    " ,cast(concat(ssc.datasource_num_id,ssc.sp_id) as bigint) as server_key" +
    " ,ssc.sc_id as server_channel_id" +
    " ,ssc.sp_id as server_channel_server_id" +
    " ,ssc.sc_code as server_channel_code" +
    " ,ssc.sc_short_name as server_channel_short_name" +
    " ,ssc.sc_name as server_channel_name" +
    " ,ssc.sc_name_en as server_channel_name_en" +
    " ,ssc.sc_note as server_channel_note" +
    " ,ssc.sc_status as server_channel_status" +
    " ,nvl(pvl1.vl_bi_name,ssc.sc_status) as server_channel_status_val" +
    " ,ssc.sc_is_weighing as server_channel_is_weighing" +
    " ,nvl(pvl2.vl_bi_name,ssc.sc_is_weighing) as server_channel_is_weighing_val" +
    " ,ssc.sc_is_tracking as server_channel_is_tracking" +
    " ,nvl(pvl3.vl_bi_name,ssc.sc_is_tracking) as server_channel_is_tracking_val" +
    " ,ssc.sc_is_volume as server_channel_is_volume" +
    " ,nvl(pvl4.vl_bi_name,ssc.sc_is_volume) as server_channel_is_volume_val" +
    " ,ssc.st_id as server_channel_st_id" +
    " ,ssc.st_auto_delivery as server_channel_st_auto_delivery" +
    " ,ssc.st_split_start as server_channel_st_split_start" +
    " ,ssc.st_split_value as server_channel_st_split_value" +
    " ,ssc.st_split_type as server_channel_st_split_type" +
    " ,ssc.sc_add_time as server_channel_add_time" +
    " ,ssc.sc_update_time as server_channel_update_time" +
    " ,ssc.asp_id as server_channel_asp_id" +
    " ,ssc.asp_code as server_channel_asp_code" +
    " ,ssc.apm_id as server_channel_apm_id" +
    " ,ssc.api_pk_type as server_channel_api_pk_type" +
    " ,ssc.wut_code as server_channel_wut_code" +
    " ,ssc.sc_min_weight as server_channel_min_weight" +
    " ,ssc.sc_max_weight as server_channel_max_weight" +
    " ,ssc.validate_address as server_channel_is_validate_address" +
    " ,nvl(pvl5.vl_bi_name,ssc.validate_address) as server_channel_is_validate_address_val" +
    " ,ssc.sc_is_pack_weighing as server_channel_is_pack_weighing" +
    " ,nvl(pvl6.vl_bi_name,ssc.sc_is_pack_weighing) as server_channel_is_pack_weighing_val" +
    " ,ssc.sign_type as server_channel_sign_type" +
    " ,ssc.right_trim_length as server_channel_right_trim_length" +
    " ,ssc.left_trim_length as server_channel_left_trim_length" +
    " ,ssc.is_mark_weight as server_channel_is_mark_weight" +
    " ,nvl(pvl7.vl_bi_name,ssc.is_mark_weight) as server_channel_is_mark_weight_val" +
    " ,ssc.push_tms_status as server_channel_push_tms_status" +
    " FROM (select * from ods.gc_wms_sp_service_channel where day="+ nowDate +" ) as ssc" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9004' and pvl.vl_type='sc_status' and pvl.vl_datasource_table='gc_wms_sp_service_channel') as pvl1 on pvl1.vl_value=ssc.sc_status" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9004' and pvl.vl_type='sc_is_weighing' and pvl.vl_datasource_table='gc_wms_sp_service_channel') as pvl2 on pvl2.vl_value=ssc.sc_is_weighing" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9004' and pvl.vl_type='sc_is_tracking' and pvl.vl_datasource_table='gc_wms_sp_service_channel') as pvl3 on pvl3.vl_value=ssc.sc_is_tracking" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9004' and pvl.vl_type='sc_is_volume' and pvl.vl_datasource_table='gc_wms_sp_service_channel') as pvl4 on pvl4.vl_value=ssc.sc_is_volume" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9004' and pvl.vl_type='validate_address' and pvl.vl_datasource_table='gc_wms_sp_service_channel') as pvl5 on pvl5.vl_value=ssc.validate_address" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9004' and pvl.vl_type='sc_is_pack_weighing' and pvl.vl_datasource_table='gc_wms_sp_service_channel') as pvl6 on pvl6.vl_value=ssc.sc_is_pack_weighing" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9004' and pvl.vl_type='is_mark_weight' and pvl.vl_datasource_table='gc_wms_sp_service_channel') as pvl7 on pvl7.vl_value=ssc.is_mark_weight"
  private val zy_wms ="SELECT ssc.row_wid as row_wid" +
    " ,from_unixtime(unix_timestamp(),'yyyyMMdd') as etl_proc_wid" +
    " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_insert_dt" +
    " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_update_dt" +
    " ,ssc.datasource_num_id as datasource_num_id" +
    " ,ssc.data_flag as data_flag" +
    " ,ssc.integration_id as integration_id" +
    " ,ssc.created_on_dt as created_on_dt" +
    " ,ssc.changed_on_dt as changed_on_dt" +
    " ,cast(concat(ssc.datasource_num_id,ssc.sp_id) as bigint) as server_key" +
    " ,ssc.sc_id as server_channel_id" +
    " ,ssc.sp_id as server_channel_server_id" +
    " ,ssc.sc_code as server_channel_code" +
    " ,ssc.sc_short_name as server_channel_short_name" +
    " ,ssc.sc_name as server_channel_name" +
    " ,ssc.sc_name_en as server_channel_name_en" +
    " ,ssc.sc_note as server_channel_note" +
    " ,ssc.sc_status as server_channel_status" +
    " ,nvl(pvl1.vl_bi_name,ssc.sc_status) as server_channel_status_val" +
    " ,ssc.sc_is_weighing as server_channel_is_weighing" +
    " ,nvl(pvl2.vl_bi_name,ssc.sc_is_weighing) as server_channel_is_weighing_val" +
    " ,ssc.sc_is_tracking as server_channel_is_tracking" +
    " ,nvl(pvl3.vl_bi_name,ssc.sc_is_tracking) as server_channel_is_tracking_val" +
    " ,ssc.sc_is_volume as server_channel_is_volume" +
    " ,nvl(pvl4.vl_bi_name,ssc.sc_is_volume) as server_channel_is_volume_val" +
    " ,ssc.st_id as server_channel_st_id" +
    " ,ssc.st_auto_delivery as server_channel_st_auto_delivery" +
    " ,ssc.st_split_start as server_channel_st_split_start" +
    " ,ssc.st_split_value as server_channel_st_split_value" +
    " ,ssc.st_split_type as server_channel_st_split_type" +
    " ,ssc.sc_add_time as server_channel_add_time" +
    " ,ssc.sc_update_time as server_channel_update_time" +
    " ,ssc.asp_id as server_channel_asp_id" +
    " ,ssc.asp_code as server_channel_asp_code" +
    " ,ssc.apm_id as server_channel_apm_id" +
    " ,ssc.api_pk_type as server_channel_api_pk_type" +
    " ,ssc.wut_code as server_channel_wut_code" +
    " ,ssc.sc_min_weight as server_channel_min_weight" +
    " ,ssc.sc_max_weight as server_channel_max_weight" +
    " ,ssc.validate_address as server_channel_is_validate_address" +
    " ,nvl(pvl5.vl_bi_name,ssc.validate_address) as server_channel_is_validate_address_val" +
    " ,ssc.sc_is_pack_weighing as server_channel_is_pack_weighing" +
    " ,nvl(pvl6.vl_bi_name,ssc.sc_is_pack_weighing) as server_channel_is_pack_weighing_val" +
    " ,ssc.sign_type as server_channel_sign_type" +
    " ,ssc.right_trim_length as server_channel_right_trim_length" +
    " ,ssc.left_trim_length as server_channel_left_trim_length" +
    " ,null as server_channel_is_mark_weight" +
    " ,null as server_channel_is_mark_weight_val" +
    " ,null as server_channel_push_tms_status" +
    " FROM (select * from ods.zy_wms_sp_service_channel where day="+ nowDate +" ) as ssc" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9022' and pvl.vl_type='sc_status' and pvl.vl_datasource_table='zy_wms_sp_service_channel') as pvl1 on pvl1.vl_value=ssc.sc_status" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9022' and pvl.vl_type='sc_is_weighing' and pvl.vl_datasource_table='zy_wms_sp_service_channel') as pvl2 on pvl2.vl_value=ssc.sc_is_weighing" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9022' and pvl.vl_type='sc_is_tracking' and pvl.vl_datasource_table='zy_wms_sp_service_channel') as pvl3 on pvl3.vl_value=ssc.sc_is_tracking" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9022' and pvl.vl_type='sc_is_volume' and pvl.vl_datasource_table='zy_wms_sp_service_channel') as pvl4 on pvl4.vl_value=ssc.sc_is_volume" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9022' and pvl.vl_type='validate_address' and pvl.vl_datasource_table='zy_wms_sp_service_channel') as pvl5 on pvl5.vl_value=ssc.validate_address" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9022' and pvl.vl_type='sc_is_pack_weighing' and pvl.vl_datasource_table='zy_wms_sp_service_channel') as pvl6 on pvl6.vl_value=ssc.sc_is_pack_weighing"

  def main(args: Array[String]): Unit = {
    val sqlArray: Array[String] = Array(gc_wms,zy_wms).map(_.replaceAll("dw.par_val_list", Dw_par_val_list_cache.TEMP_PAR_VAL_LIST_NAME))
    Dw_dim_common.getRunCode_full(task,tableName,sqlArray,Array(SystemCodeUtil.GC_WMS,SystemCodeUtil.ZY_WMS))
  }
}
