package com.zongteng.ztetl.etl.dw.dim

import com.zongteng.ztetl.common.Dw_dim_common
import com.zongteng.ztetl.util.DateUtil

object DimTcmsOrganization {
  //任务名称(一般同类名)
  private val task = "DimTcmsOrganization"

  //dw层类名
  private val tableName = "dim_tcms_organization"

  //获取当天的时间
  private val nowDate: String = DateUtil.getNowTime()

  //要执行的sql语句
  private val gc_tcms="SELECT org.row_wid as row_wid" +
    " ,from_unixtime(unix_timestamp(),'yyyyMMdd') as etl_proc_wid" +
    " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_insert_dt" +
    " ,from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as w_update_dt" +
    " ,org.datasource_num_id as datasource_num_id" +
    " ,org.data_flag as data_flag" +
    " ,org.integration_id as integration_id" +
    " ,org.created_on_dt as created_on_dt" +
    " ,org.changed_on_dt as changed_on_dt" +
    " ,org.og_id as organization_id" +
    " ,org.tms_id as organization_tms_id" +
    " ,org.og_rightcode as organization_rightcode" +
    " ,org.og_shortcode as organization_shortcode" +
    " ,org.og_type as organization_type" +
    " ,nvl(pvl1.vl_bi_name,org.og_type) as organization_type_cnname" +
    " ,org.og_name as organization_name" +
    " ,org.og_ename as organization_ename" +
    " ,org.og_enable as organization_enable" +
    " ,org.og_id_farther as organization_id_farther" +
    " ,org.og_citycnname as organization_citycnname" +
    " ,org.og_cityenname as organization_cityenname" +
    " ,org.og_chargingpoint as organization_chargingpoint" +
    " ,org.firstcharge_og_id as organization_firstcharge_og_id" +
    " ,org.chargesecond_og_id as organization_chargesecond_og_id" +
    " ,org.estimatecost_og_id as organization_estimatecost_og_id" +
    " ,org.og_address as organization_address" +
    " ,org.og_contact as organization_contact" +
    " ,org.og_phone as organization_phone" +
    " ,org.og_telephone as organization_telephone" +
    " ,org.og_email as organization_email" +
    " ,org.og_faxno as organization_faxno" +
    " ,org.last_update_time as organization_last_update_time" +
    " ,org.country_code as organization_country_code" +
    " ,org.ck_code as organization_ck_code" +
    " ,org.charge_address_id as organization_charge_address_id" +
    " FROM (select * from ods.gc_tcms_sys_organization where day="+ nowDate +" ) as org" +
    " left join  (select * from dw.par_val_list as pvl where pvl.datasource_num_id='9020' and pvl.vl_type='og_type' and pvl.vl_datasource_table='gc_tcms_sys_organization') as pvl1 on pvl1.vl_value=org.og_type"

  def main(args: Array[String]): Unit = {
    Dw_dim_common.getRunCode_full(task, tableName, Array(gc_tcms))
  }
}
