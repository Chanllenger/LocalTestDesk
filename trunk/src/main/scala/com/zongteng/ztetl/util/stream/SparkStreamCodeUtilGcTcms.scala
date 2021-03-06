package com.zongteng.ztetl.util.stream

import java.util

import com.zongteng.ztetl.api.{HBaseConfig, SparkKafkaCon}
import com.zongteng.ztetl.common.kafka.offset.KafkaOffsetManager
import com.zongteng.ztetl.entity.common.InsertModel
import org.apache.commons.lang3.StringUtils
import org.apache.hadoop.hbase.TableName
import org.apache.hadoop.hbase.client.{Connection, Put, Table}
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.rdd.RDD

object SparkStreamCodeUtilGcTcms {

  /**
    * 生成实时导入ods层代码的工具类
    *
    * @param appName        作业名称
    * @param interval       消费时间间隔
    * @param topics         kafka中topic名称
    * @param groupId        消费组
    * @param mysql_database mysql数据库名称
    * @param datasource_name 数据来源简称（例如：gc_oms）
    */
  def getRunCode(appName: String, interval: Int, topics: Array[String], groupId: String, mysql_database: String, datasource_name: String) = {

    val skzzz  = SparkKafkaCon.getSparkStream$KafkaStrem$ZkClient$ZkUtilsByOffset(appName, interval, groupId, topics, true)

    val sparkStream = skzzz._1
    val kafkaStream = skzzz._2
    val zkClient = skzzz._3
    val zkUtils = skzzz._4
    val zkOffsetPath = skzzz._5

    try {

      kafkaStream.foreachRDD((rdd: RDD[ConsumerRecord[String, String]]) => {

        println("============" + rdd.isEmpty())

        if (!rdd.isEmpty()) {

          rdd.filter((e: ConsumerRecord[String, String]) => checkJsonStr(mysql_database, e.value(), datasource_name)).foreach((e: ConsumerRecord[String, String]) => {

            // hbase连接（必须在这里定义，否则会遇到序列化的问题）
            val connection: Connection = HBaseConfig.getConnection()

           // println(e.value())

            // 获取对应的row数据
            val model: InsertModel = SparkJsonUtil.getInsertObject(e.value())
            val puts: util.ArrayList[Put] = SparkJsonUtil.getHBasePut(model)

            // 数据添加到hbase中
            // val table: Table = connection.getTable(TableName.valueOf(ods_table))

            val ods_table = datasource_name + "_" + model.table
            val table: Table = connection.getTable(TableName.valueOf(ods_table))

            for (i <- 0 until puts.size) {
              val put: Put = puts.get(i)
              table.put(put)
              println( ods_table + " 添加成功 " + put)
            }
            table.close()
          })

          // 提交偏移量
          KafkaOffsetManager.savaOffsets(zkUtils, zkClient, zkOffsetPath, rdd)
        }
      })

      //Log.end(task)
    } catch {
      case e: Exception => e.getMessage
        kafkaStream.stop()
      //Log.end(task)
    }

    sparkStream.start()
    sparkStream.awaitTermination()
    kafkaStream.stop()
  }

  def checkJsonStr(mysql_database: String, jsonStr: String, datasource_name: String) = {

    val allow_mysql_table: Array[String] = AllowMysqlTable.table
    var result = false

    // println(mysql_database + "  ===  " + jsonStr)
    if (StringUtils.isNotBlank(jsonStr)) {
      val model: InsertModel = SparkJsonUtil.getInsertObject(jsonStr)
      result = mysql_database.equalsIgnoreCase(model.database) &&
        allow_mysql_table.contains(datasource_name + "_" + model.table)
    }

    result
  }


}
