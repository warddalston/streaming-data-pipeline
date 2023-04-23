package com.labs1904.hwe.homework

import org.apache.log4j.Logger
import org.apache.spark.sql.{Dataset, SparkSession}

object HelloWorldBatchApp {
  lazy val logger: Logger = Logger.getLogger(this.getClass)
  val jobName = "HelloWorldBatchApp"

  def main(args: Array[String]): Unit = {
    try {
      logger.info(s"$jobName starting...")
      //TODO: What is a spark session - Why do we need this line?
//    A spark session sets up the cluster manager and associated background tools. We need this line to provision the
//    resources that we will use to complete our jobs.
      val spark = SparkSession.builder()
        .appName(jobName)
        .config("spark.sql.shuffle.partitions", "3")
        //TODO- What is local[*] doing here?
//      The locals indicates the resources that will be available. This says that we want all the locally available cores
//      and that we will use all available cores. You can change this and if you want to use some sort of distributed
//      system, this is where you tell it the system that handles that resource management. This is actually the master
//      URL, so it's the address where resource management occurs.
        .master("local[*]")
        //TODO- What does Get or Create do?
//      GET: check if there is a valid spark session already running. This probably won't occur in the simple case for
//      in this class, but it may occur in production. If no valid session is found, a new one is spun up and made to be
//      the global default. In either case, the config options in this builder are applied to the running session.
        .getOrCreate()

      import spark.implicits._
      val sentences: Dataset[String] = spark.read.csv("src/main/resources/sentences.txt").as[String]
      // print out the names and types of each column in the dataset
      sentences.printSchema
      // display some data in the console, useful for debugging
      //TODO- Make sure this runs successfully
      sentences.show(truncate = false)
    } catch {
      case e: Exception => logger.error(s"$jobName error in main", e)
    }
  }
}
