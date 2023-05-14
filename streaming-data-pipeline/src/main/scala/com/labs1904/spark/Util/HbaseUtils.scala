package com.labs1904.spark.Util

import org.apache.hadoop.hbase.{HBaseConfiguration}
import org.apache.hadoop.hbase.client.{ConnectionFactory, Connection}
object HbaseUtils {
  def newHBaseConnection(connection: String): Connection = {
    val conf = HBaseConfiguration.create()
    conf.set("hbase.zookeeper.quorum", connection)
    ConnectionFactory.createConnection(conf)
  }
}
