package com.labs1904.hwe

import org.apache.log4j.Logger
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{StringType, StructType}

object WordCountBatchApp {
  lazy val logger: Logger = Logger.getLogger(this.getClass)
  val jobName = "WordCountBatchApp"

  def main(args: Array[String]): Unit = {
    logger.info(s"$jobName starting...")
    try {
      val spark = SparkSession.builder()
        .appName(jobName)
        .config("spark.sql.shuffle.partitions", "3")
        .master("local[*]")
        .getOrCreate()
      import spark.implicits._
      val schema = new StructType().add("text", StringType, nullable=true)
      val sentences = spark.read.schema(schema).csv("src/main/resources/sentences.txt").as[String]

      val wc = sentences
        .flatMap(_.split(" "))
        .map(s => s.filter(c => c.isLetter))
        //        .rdd
//        .map(row => (row, 1))
//        .reduceByKey(_ + _)
        .groupBy("value")
        .count
        .sort($"count".desc)
      wc.show(truncate=false)
    } catch {
      case e: Exception => logger.error(s"$jobName error in main", e)
    }
  }

  // TODO: implement this function
  // HINT: you may have done this before in Scala practice...
//  def splitSentenceIntoWords(sentence: String): Array[String] = ???

}
