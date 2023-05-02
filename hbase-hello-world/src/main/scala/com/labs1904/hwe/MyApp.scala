package com.labs1904.hwe

import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory, Get, Put, Scan, Delete}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.logging.log4j.{LogManager, Logger}
import scala.collection.JavaConverters._

object MyApp {
  lazy val logger: Logger = LogManager.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {
    logger.info("MyApp starting...")
    var connection: Connection = null
    try {
      val conf = HBaseConfiguration.create()
      conf.set("hbase.zookeeper.quorum", "CHANGE ME")
      connection = ConnectionFactory.createConnection(conf)

      val table = connection.getTable(TableName.valueOf("table-name"))

//    Challenge 1:
//      val get = new Get(Bytes.toBytes("10000001"))
//      get.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("mail"))
//      val result = table.get(get)
//      logger.debug(Bytes.toString(result.getValue(Bytes.toBytes("f1"), Bytes.toBytes("mail"))))

//    If you want all of the columns:
//      val my_map = result.getFamilyMap(Bytes.toBytes("f1"))
//      my_map.forEach((k, v) => {
//        println(Bytes.toString(k))
//        println(Bytes.toString(v))
//      })

//    Challenge 2: Put into a new row
//      val put = new Put(Bytes.toBytes("99"))
//      put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("username"), Bytes.toBytes("DE-HWE"))
//      put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("name"), Bytes.toBytes("The Panther"))
//      put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("sex"), Bytes.toBytes("F"))
//      put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("favorite_color"), Bytes.toBytes("pink"))
//
//      table.put(put)
//      NOTE: Could make a collection that is like a mpa of Cols -> values, then map through and populate the put.addColumn that way.

//    Challenge 3: count entries in a scan
//      Challenge #3: How many user IDs exist between 10000001 and 10006001 ? (Not all user IDs exist, so the answer is * not *6000)
//      val myScan = new Scan().withStartRow(Bytes.toBytes("10000001")).withStopRow(Bytes.toBytes("10006001"))
//      val scanner = table.getScanner(myScan)
//
//      var count: Int = 0
//      scanner.iterator.asScala.foreach(x => {
//        count = count + 1
//      })
//
//      println(count)
//    Challenge #4: Delete the user with ID = 99 that we inserted earlier.
//      val delete = new Delete(Bytes.toBytes("99"))
//      table.delete(delete)
//      val get = new Get(Bytes.toBytes("99"))
//      val result = table.get(get)
//      println(result.isEmpty)

//      Challenge #5: There is also an operation that returns multiple results in a single HBase "Get" operation.
//      Write a single HBase call that returns the email addresses for the following 5 users: 9005729, 500600, 30059640, 6005263 , 800182
//      (Hint: Look at the Javadoc for "Table")
//      val users = List("9005729", "500600", "30059640", "6005263", "800182")
//
//      val getList = users.map((u: String) => {
//        val byteList = List(u, "f1", "mail").map(i => Bytes.toBytes(i))
//        new Get(byteList(0)).addColumn(byteList(1), byteList(2))
//      }).asJava
//
//      val mailArray = table.get(getList).map(result => {
//        Bytes.toString(result.getValue(Bytes.toBytes("f1"), Bytes.toBytes("mail")))
//      })
//
//      println(mailArray.mkString(", "))

    } catch {
      case e: Exception => logger.error("Error in main", e)
    } finally {
      if (connection != null) connection.close()
    }
  }
}
