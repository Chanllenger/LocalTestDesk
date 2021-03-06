package com.zongteng.ztetl.entity.common

/**
  * ContainerDetails的公共字段
  */
case class GcLmsContainerDetails (container_details_id: String,
                             container_id: String,
                             container_code: String,
                             order_number: String,
                             tracking_number: String,
                             channel_code: String,
                             loader_time: String,
                             shipper_time: String,
                             update_user_id: String,
                             update_name: String,
                             is_push_wms: String,
                             is_order_shipper_time: String,
                             push_fail_num: String
                            )