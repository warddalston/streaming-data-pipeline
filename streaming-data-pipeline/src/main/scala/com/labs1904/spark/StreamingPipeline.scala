package com.labs1904.spark

import org.apache.log4j.Logger
import org.apache.hadoop.hbase.TableName
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.{OutputMode, Trigger}
import com.labs1904.spark.Schemas.Constructors
import com.labs1904.spark.Util.HbaseUtils.newHBaseConnection
import com.labs1904.spark.Util.KafkaUtils.getScramAuthString

/**
 * Spark Structured Streaming app
 *
 */
object StreamingPipeline {
  lazy val logger: Logger = Logger.getLogger(this.getClass)

  // Spark
  val jobName = "StreamingPipeline"

  // Kafka
  val bootstrapServers: String = "CHANGEME"
  val username: String = "CHANGEME"
  val password: String = "CHANGEME"

  // HBase
  val hbaseUrl: String = "CHANGEME"
  val hbaseUsername: String = "CHANGEME"

  // HDFS
  val hdfsUrl: String = "CHANGEME"
  val hdfsUsername: String = "CHANGEME"

  val trustStore: String = "src/main/resources/kafka.client.truststore.jks"

  def main(args: Array[String]): Unit = {
    try {
      val spark = SparkSession.builder()
        .config("spark.sql.shuffle.partitions", "3")
        .config("spark.hadoop.dfs.client.use.datanode.hostname", "true")
        .config("spark.hadoop.fs.defaultFS", hdfsUrl)
        .appName(jobName)
        .master("local[*]")
        .getOrCreate()

      import spark.implicits._

      val ds = spark
        .readStream
        .format("kafka")
        .option("kafka.bootstrap.servers", bootstrapServers)
        .option("subscribe", "reviews")  // This is the Kafka TOPIC
        .option("startingOffsets", "earliest")
        .option("maxOffsetsPerTrigger", "20")
        .option("startingOffsets","earliest")
        .option("kafka.security.protocol", "SASL_SSL")
        .option("kafka.sasl.mechanism", "SCRAM-SHA-512")
        .option("kafka.ssl.truststore.location", trustStore)
        .option("kafka.sasl.jaas.config", getScramAuthString(username, password))
        .load()
        .selectExpr("CAST(value AS STRING)")
        .as[String]

      val enriched_reviews = ds.mapPartitions(p => {
        val connection = newHBaseConnection(hbaseUrl)
        val table = connection.getTable(TableName.valueOf(hbaseUsername))

        val enriched_review = p.map(row => {
          val raw_review = Constructors.rawReviewFromCSV(row)
          val user_info = Constructors.userInfoFromComponents(raw_review, table)
          Constructors.enrichedReviewFromComponents(raw_review, user_info)
        }).toList.iterator

        connection.close()
        enriched_review
      })

      // Write output to console
//      val query = enriched_reviews.writeStream
//        .outputMode(OutputMode.Append())
//        .format("console")
//        .option("truncate", false)
//        .trigger(Trigger.ProcessingTime("5 seconds"))
//        .start()

      // Write output to HDFS as csv (from Kit on Discord)
      val query = enriched_reviews.writeStream
        .outputMode(OutputMode.Append())
        .format("csv")
        .option("delimiter", "\t")
        .option("path", s"/user/${hdfsUsername}/reviews_csv")
        .option("checkpointLocation", s"/user/${hdfsUsername}/reviews_checkpoint")
        .partitionBy("star_rating")
        .trigger(Trigger.ProcessingTime("5 seconds"))
        .start()

      // Write output to HDFS as JSON
//      val query = result.writeStream
//        .outputMode(OutputMode.Append())
//        .format("json")
//        .option("path", s"/user/${hdfsUsername}/reviews_json")
//        .option("checkpointLocation", s"/user/${hdfsUsername}/reviews_checkpoint")
//        .trigger(Trigger.ProcessingTime("5 seconds"))
//        .start()
      query.awaitTermination()
    } catch {
      case e: Exception => logger.error(s"$jobName error in main", e)
    }
  }
}
