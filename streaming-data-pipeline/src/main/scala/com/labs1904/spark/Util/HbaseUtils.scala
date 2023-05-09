package com.labs1904.spark.Util

import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{ConnectionFactory, Table}
object HbaseUtils {
  def tableFromConnectionString(table: String, connection: String): Table = {
    val conf = HBaseConfiguration.create()
    conf.set("hbase.zookeeper.quorum", connection)
    ConnectionFactory.createConnection(conf).getTable(TableName.valueOf(table))
  }
}
