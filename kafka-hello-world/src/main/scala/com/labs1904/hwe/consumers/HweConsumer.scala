package com.labs1904.hwe.consumers

import com.labs1904.hwe.util.Constants._
import com.labs1904.hwe.util.Util
import net.liftweb.json.DefaultFormats
import org.apache.kafka.clients.consumer.{ConsumerRecord, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.slf4j.LoggerFactory

import java.time.Duration
import java.util.Arrays

object HweConsumer {
  private val logger = LoggerFactory.getLogger(getClass)

  val consumerTopic: String = "question-1"
  val producerTopic: String = "question-1-output"

  implicit val formats: DefaultFormats.type = DefaultFormats

  def main(args: Array[String]): Unit = {

    // Create the KafkaConsumer
    val consumerProperties = Util.getConsumerProperties(BOOTSTRAP_SERVER)
    val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](consumerProperties)

    // Create the KafkaProducer
    val producerProperties = Util.getProperties(BOOTSTRAP_SERVER)
    val producer = new KafkaProducer[String, String](producerProperties)

    // Subscribe to the topic
    consumer.subscribe(Arrays.asList(consumerTopic))

    while ( {
      true
    }) {
      // poll for new data
      val duration: Duration = Duration.ofMillis(100)
      val records: ConsumerRecords[String, String] = consumer.poll(duration)

      records.forEach((record: ConsumerRecord[String, String]) => {
        // Retrieve the message from each record
        val Array(id, uname, name, email, dob) = record.value().split("\t")
        val new_user = Util.EnrichedUser(id.toInt, uname, name, email, dob, Util.numberToWordMap(id.toInt))
        logger.info(s"Message Received: $new_user")

        val send_record = new ProducerRecord[String, String](producerTopic, Util.enrichedUserToString(new_user))
        producer.send(send_record)
      })
    }
  }
}