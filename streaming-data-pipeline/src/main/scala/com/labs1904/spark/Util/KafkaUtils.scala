package com.labs1904.spark.Util

object KafkaUtils {
  def getScramAuthString(username: String, password: String) = {
    s"""org.apache.kafka.common.security.scram.ScramLoginModule required
 username=\"$username\"
 password=\"$password\";"""
  }
}
