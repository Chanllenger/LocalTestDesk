package com.zongteng.ztetl.etl.dw.dim.all

import com.zongteng.ztetl.common.{Dw_dim_common, Dw_par_val_list_cache, SystemCodeUtil}
import com.zongteng.ztetl.util.DateUtil

object DimEmployee {
  //任务名称(一般同类名)
  private val task = "Dw_dim_employee"

  //dw层类名
  private val tableName = "dim_employee"

  //获取当天的时间
  private val nowDate: String = DateUtil.getNowTime()
  //private val nowDate: String = "20191108"

  // tsms的表hmr_staff加入导致字段新增18个
//  private val addFields =
//    ",\n" +
//    "NULL AS employee_competence_og_id,\n" +
//    "NULL AS employee_st_id_ctreate,\n" +
//    "NULL AS employee_tms_id,\n" +
//    "NULL AS employee_error_count,\n" +
//    "NULL AS employee_last_obtain_time,\n" +
//    "NULL AS employee_last_update_time,\n" +
//    "NULL AS employee_sp_cnname,\n" +
//    "NULL AS employee_sp_enname,\n" +
//    "NULL AS employee_st_gender,\n" +
//    "NULL AS employee_st_birthdate,\n" +
//    "NULL AS employee_st_qq,\n" +
//    "NULL AS employee_st_msn,\n" +
//    "NULL AS employee_st_wechat,\n" +
//    "NULL AS employee_st_skype,\n" +
//    "NULL AS employee_st_entrydate,\n" +
//    "NULL AS employee_st_regularydate,\n" +
//    "NULL AS employee_st_departuredate,\n" +
//    "NULL AS employee_st_departurenote"

  private val addFields = ""


  //要执行的sql语句
  private val gc_oms="SELECT" +
    " emp.row_wid AS row_wid," +
    " from_unixtime(unix_timestamp(current_date()),'yyyyMMdd') AS etl_proc_wid," +
    " CURRENT_TIMESTAMP() AS w_insert_dt," +
    " CURRENT_TIMESTAMP() AS w_update_dt," +
    " emp.datasource_num_id AS datasource_num_id," +
    " emp.data_flag AS data_flag," +
    " emp.integration_id AS integration_id," +
    " emp.created_on_dt AS created_on_dt," +
    " emp.changed_on_dt AS changed_on_dt," +
    " cast(concat(emp.datasource_num_id,emp.ud_id) as bigint)  AS ed_key," +
    " cast(concat(emp.datasource_num_id,emp.up_id) as bigint)  AS ep_key," +
    " emp.user_id AS employee_id," +
    " emp.user_code AS employee_code," +
    " emp.company_code AS employee_company_code," +
    " emp.is_admin AS employee_is_admin," +
    " emp.user_password AS employee_password," +
    " emp.user_name AS employee_name," +
    " emp.user_name_en AS employee_name_en," +
    " emp.user_status AS employee_status," +
    " nvl(val.vl_bi_name,emp.user_status) AS employee_status_val," +
    " emp.user_email AS employee_email," +
    " emp.email_verify AS employee_email_verify," +
    " emp.ud_id AS employee_ud_id," +
    " emp.up_id AS employee_up_id," +
    " emp.user_password_update_time AS employee_password_update_time," +
    " emp.user_phone AS employee_phone," +
    " emp.user_mobile_phone AS employee_mobile_phone," +
    " emp.user_note AS employee_note," +
    " emp.user_supervisor_id AS employee_supervisor_id," +
    " emp.user_add_time AS employee_add_time," +
    " emp.user_last_login AS employee_last_login," +
    " emp.user_update_time AS employee_update_time," +
    " emp.user_activate_code AS employee_activate_code," +
    " emp.is_index_show AS employee_is_index_show," +
    " emp.versions AS employee_versions," +
    " null AS employee_p_id," +
    " null AS employee_ez_user_id," +
    " null AS employee_priority_login " +
    addFields +
    " FROM" +
    " (select * from ods.gc_oms_user where day="+ nowDate +") as emp" +
    " LEFT JOIN ( SELECT * FROM dw.par_val_list WHERE datasource_num_id = '9001' " +
    " AND vl_type = 'user_status' " +
    " AND vl_datasource_table = 'gc_oms_user'" +
    " ) as val ON emp.user_status = val.vl_value"


  private val gc_wms="SELECT" +
    " emp.row_wid AS row_wid," +
    " from_unixtime(unix_timestamp(current_date()),'yyyyMMdd') AS etl_proc_wid," +
    " CURRENT_TIMESTAMP() AS w_insert_dt," +
    " CURRENT_TIMESTAMP() AS w_update_dt," +
    " emp.datasource_num_id AS datasource_num_id," +
    " emp.data_flag AS data_flag," +
    " emp.integration_id AS integration_id," +
    " emp.created_on_dt AS created_on_dt," +
    " emp.changed_on_dt AS changed_on_dt," +
    " cast(concat( emp.datasource_num_id,emp.ud_id) as bigint) AS ed_key," +
    " cast(concat( emp.datasource_num_id,emp.up_id) as bigint) AS ep_key," +
    " emp.user_id AS employee_id," +
    " emp.user_code AS employee_code," +
    " null  AS employee_company_code," +
    " null  AS employee_is_admin," +
    " emp.user_password AS employee_password," +
    " emp.user_name AS employee_name," +
    " emp.user_name_en AS employee_name_en," +
    " emp.user_status AS employee_status," +
    " nvl(val.vl_bi_name,emp.user_status) AS employee_status_val," +
    " emp.user_email AS employee_email," +
    " null  AS employee_email_verify," +
    " emp.ud_id AS employee_ud_id," +
    " emp.up_id AS employee_up_id," +
    " emp.user_password_update_time AS employee_password_update_time," +
    " emp.user_phone AS employee_phone," +
    " emp.user_mobile_phone AS employee_mobile_phone," +
    " emp.user_note AS employee_note," +
    " emp.user_supervisor_id AS employee_supervisor_id," +
    " emp.user_add_time AS employee_add_time," +
    " emp.user_last_login AS employee_last_login," +
    " emp.user_update_time AS employee_update_time," +
    " null AS employee_activate_code," +
    " emp.is_index_show AS employee_is_index_show," +
    " null AS employee_versions," +
    " emp.p_id AS employee_p_id," +
    " emp.ez_user_id AS employee_ez_user_id," +
    " emp.priority_login AS employee_priority_login" +
    addFields +
    " FROM" +
    " (select * from ods.gc_wms_user where day="+ nowDate +") AS emp" +
    " LEFT JOIN ( SELECT * FROM dw.par_val_list WHERE datasource_num_id = '9004' " +
    " AND vl_type = 'user_status' " +
    " AND vl_datasource_table = 'gc_wms_user') AS val ON emp.user_status = val.vl_value"

  private val zy_wms="SELECT" +
    " emp.row_wid AS row_wid," +
    " from_unixtime(unix_timestamp(current_date()),'yyyyMMdd') AS etl_proc_wid," +
    " CURRENT_TIMESTAMP() AS w_insert_dt," +
    " CURRENT_TIMESTAMP() AS w_update_dt," +
    " emp.datasource_num_id AS datasource_num_id," +
    " emp.data_flag AS data_flag," +
    " emp.integration_id AS integration_id," +
    " emp.created_on_dt AS created_on_dt," +
    " emp.changed_on_dt AS changed_on_dt," +
    " cast(concat(emp.datasource_num_id,emp.ud_id) as bigint) AS ed_key," +
    " cast(concat(emp.datasource_num_id,emp.up_id) as bigint) AS ep_key," +
    " emp.user_id AS employee_id," +
    " emp.user_code AS employee_code," +
    " null AS employee_company_code," +
    " null AS employee_is_admin," +
    " emp.user_password AS employee_password," +
    " emp.user_name AS employee_name," +
    " emp.user_name_en AS employee_name_en," +
    " emp.user_status AS employee_status," +
    " nvl(val.vl_bi_name,emp.user_status) AS employee_status_val," +
    " emp.user_email AS employee_email," +
    " null AS employee_email_verify," +
    " emp.ud_id AS employee_ud_id," +
    " emp.up_id AS employee_up_id," +
    " emp.user_password_update_time AS employee_password_update_time," +
    " emp.user_phone AS employee_phone," +
    " emp.user_mobile_phone AS employee_mobile_phone," +
    " emp.user_note AS employee_note," +
    " emp.user_supervisor_id AS employee_supervisor_id," +
    " emp.user_add_time AS employee_add_time," +
    " emp.user_last_login AS employee_last_login," +
    " emp.user_update_time AS employee_update_time," +
    " null AS employee_activate_code," +
    " null AS employee_is_index_show," +
    " null AS employee_versions," +
    " emp.p_id AS employee_p_id," +
    " emp.ez_user_id AS employee_ez_user_id," +
    " emp.priority_login AS employee_priority_login " +
    addFields +
    " FROM" +
    " (select * from ods.zy_wms_user where day="+ nowDate +") AS emp" +
    " LEFT JOIN ( SELECT * FROM dw.par_val_list WHERE datasource_num_id = '9022' " +
    " AND vl_type = 'user_status' " +
    " AND vl_datasource_table = 'zy_wms_user' ) AS val ON emp.user_status = val.vl_value"

  private val gc_tcms = "SELECT \n" +
    "  sta.row_wid AS row_wid,\n" +
    "  date_format(current_date(), 'yyyyMMdd') AS etl_proc_wid,\n" +
    "  current_timestamp ( ) AS w_insert_dt,\n" +
    "  current_timestamp ( ) AS w_update_dt,\n" +
    "  sta.datasource_num_id AS datasource_num_id,\n" +
    "  sta.data_flag AS data_flag,\n" +
    "  sta.st_id AS integration_id,\n" +
    "  sta.created_on_dt AS created_on_dt,\n" +
    "  sta.changed_on_dt AS changed_on_dt,\n" +
    "\n" +
    "  concat(sta.datasource_num_id, sta.og_id) AS ed_key,\n" +
    "  concat(sta.datasource_num_id, sta.sp_id) AS ep_key,\n" +
    "\n" +
    "  sta.st_id AS employee_id,\n" +
    "  sta.st_code AS employee_code, \n" +
    "  NULL AS employee_company_code, \n" +
    "  NULL AS employee_is_admin,                              \n" +
    "  sta.ur_loginpassword AS employee_password,                  \n" +
    "  sta.st_name AS employee_name,                      \n" +
    "  sta.st_ename AS employee_name_en,                   \n" +
    "  sta.vs_code AS employee_status,                       \n" +
    "  nvl(v_user_status.vl_name, sta.vs_code) AS employee_status_val,\n" +
    "  stat.st_email AS employee_email,                    \n" +
    "  NULL AS employee_email_verify,\n" +
    "  sta.og_id AS employee_ud_id,                             \n" +
    "  sta.sp_id AS employee_up_id,                               \n" +
    "  NULL AS employee_password_update_time,\n" +
    "  stat.st_phone AS employee_phone,                                       \n" +
    "  stat.st_telephone AS employee_mobile_phone,                              \n" +
    "  NULL AS employee_note,                                            \n" +
    "  NULL AS employee_supervisor_id,                              \n" +
    "  NULL AS employee_add_time,                                  \n" +
    "  NULL AS employee_last_login,                                \n" +
    "  NULL AS employee_update_time,                           \n" +
    "  NULL AS employee_activate_code,\n" +
    "  NULL AS employee_is_index_show,\n" +
    "  NULL AS employee_versions,                                  \n" +
    "  NULL AS employee_p_id,\n" +
    "  NULL AS employee_ez_user_id,\n" +
    "  NULL AS employee_priority_login,\n" +
    "  sta.competence_og_id AS employee_competence_og_id,\n" +
    "  sta.st_id_ctreate AS employee_st_id_ctreate,\n" +
    "  sta.tms_id AS employee_tms_id,\n" +
    "  sta.error_count AS employee_error_count,\n" +
    "  sta.last_obtain_time AS employee_last_obtain_time,\n" +
    "  sta.last_update_time AS employee_last_update_time,\n" +
    "  stp.sp_name AS employee_sp_cnname,\n" +
    "  stp.sp_ename AS employee_sp_enname,\n" +
    "  stg.sg_name AS employee_st_gender,\n" +
    "  stat.st_birthdate AS employee_st_birthdate,\n" +
    "  stat.st_qq AS employee_st_qq,\n" +
    "  stat.st_msn AS employee_st_msn,\n" +
    "  stat.st_wechat AS employee_st_wechat,\n" +
    "  stat.st_skype AS employee_st_skype,\n" +
    "  stat.st_entrydate AS employee_st_entrydate,\n" +
    "  stat.st_regularydate AS employee_st_regularydate,\n" +
    "  stat.st_departuredate AS employee_st_departuredate,\n" +
    "  stat.st_departurenote AS employee_st_departurenote\n" +
    s"FROM (SELECT * FROM ods.gc_tcms_hmr_staff WHERE day = '$nowDate') AS sta \n" +
    s"LEFT JOIN (SELECT * FROM ods.gc_tcms_hmr_staffattach WHERE day = '$nowDate') AS stat ON stat.st_id= sta.st_id\n" +
    s"LEFT JOIN (SELECT * FROM ods.gc_tcms_bd_staffpositions WHERE day = '$nowDate') AS stp ON stp.sp_id= sta.sp_id\n" +
    s"LEFT JOIN (SELECT * FROM ods.gc_tcms_bd_staffgender WHERE day = '$nowDate') AS stg ON stg.sg_code=stat.sg_code\n" +
    "LEFT JOIN (SELECT vl_value, vl_name FROM DW.par_val_list \n" +
    "  WHERE vl_datasource_table = 'gc_tcms_hmr_staff' AND vl_type = 'user_status') v_user_status\n" +
    "ON sta.vs_code = v_user_status.vl_value"

  def main(args: Array[String]): Unit = {
    val sqlArray: Array[String] = Array(gc_oms, gc_wms, zy_wms).map(_.replaceAll("dw.par_val_list", Dw_par_val_list_cache.TEMP_PAR_VAL_LIST_NAME))
    Dw_dim_common.getRunCode_full(task, tableName, sqlArray, Array(SystemCodeUtil.GC_OMS,SystemCodeUtil.GC_WMS,SystemCodeUtil.ZY_WMS))
  }
}
