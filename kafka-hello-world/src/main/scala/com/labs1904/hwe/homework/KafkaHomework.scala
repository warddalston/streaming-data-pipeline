package com.labs1904.hwe.homework

import java.time.Duration
import java.util.{Arrays, Properties, UUID}
import net.liftweb.json.DefaultFormats
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.serialization.StringDeserializer
import com.labs1904.hwe.util.Constants._
import org.slf4j.LoggerFactory

object KafkaHomework {
  private val logger = LoggerFactory.getLogger(getClass)

  /**
   * Your task is to try to understand this code and run the consumer successfully. Follow each step below for completion.
   * Implement all the todos below
   */

    //TODO: If these are given in class, change them so that you can run a test. If not, don't worry about this step
  val Topic: String = "change-me"

  implicit val formats: DefaultFormats.type = DefaultFormats

  def main(args: Array[String]): Unit = {

    // Create the KafkaConsumer
    //TODO: Write in a comment what these lines are doing. What are the properties necessary to instantiate a consumer?
    val properties = getProperties(BOOTSTRAP_SERVER)
    val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](properties)
// The first line is creating a properties instance, which is type of hashmap. In this, we set the bootstrap server, the
// method for deserializing keys and values (i.e., turning the byte stream into strings), and the method for creating
// ID's for the consumer groups (UUID), and then what to do when the current offset isn't in the server anymore (use the
// earlierst still existing one.)

// The second line then uses these properties to instantiate a Kafka Consumer. If we wanted it to be part of a group, we
// just set the group_id key to have the value of the target group.

    //TODO: What does this line mean? Write your answer in a comment below
    consumer.subscribe(Arrays.asList(Topic))
// Using our new consumer, subscribe (start getting messages) from the Topic(s) in Topic.

    while (true) {
      // TODO: Change this to be every 5 seconds
      val duration: Duration = Duration.ofMillis(5000)

      //TODO: Look up the ConsumerRecords class below, in your own words what is the class designed to do?
      val records: ConsumerRecords[String, String] = consumer.poll(duration)
// The ConsumerRecords class is a container for instances of the ConsumerRecord class. The ConsumerRecordS class allows
// for searching records by topic and partition. The ConsumerRecord class holds the data retrieved from the partition,
// it is basically a dictionary entry with some additional metadata on its origin.

      records.forEach((record: ConsumerRecord[String, String]) => {
        // Retrieve the message from each record
        //TODO: Describe why we need the .value() at the end of record
        val message = record.value()
// We need .value because the record is an instance of the ConsumerRecord class, which is like a mini-Map.
        //TODO: If you were given the values for the bootstrap servers in class, run the app with the green play button and make sure it runs successfully. You should see message(s) printing out to the screen
        logger.info(s"Message Received: $message")
      })
    }
  }

  def getProperties(bootstrapServer: String): Properties = {
    // Set Properties to be used for Kafka Consumer
    val properties = new Properties
    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer)
    properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString)

    properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    properties
  }

}
