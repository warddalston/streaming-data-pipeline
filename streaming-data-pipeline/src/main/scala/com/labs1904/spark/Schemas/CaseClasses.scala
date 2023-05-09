package com.labs1904.spark.Schemas

import org.apache.hadoop.hbase.client.{Result}
import org.apache.hadoop.hbase.util.Bytes

case class rawReview(
  marketplace: String,
  customer_id: Int, // NOTE: May need to be Long
  review_id: String,
  product_id: String,
  product_parent: Int,  // NOTE: May need to be Long
  product_title: String,
  product_category: String,
  star_rating: Int,  // NOTE: Could be Byte
  helpful_votes: Int,
  total_votes: Int,
  vine: String,  // TODO: Make this a Boolean
  verified_purchase: String, // TODO: Make this a Boolean
  review_headline: String,
  review_body: String,
  review_date: String
)

case class userInfo(
  username: String,
  name: String,
  sex: String,
  birthdate: String,
  mail: String
)

object Constructors {
  def rawReviewFromCSV(input: String, sep: String = "\t"): rawReview = {
    val entries = input.split(sep)
    rawReview(
      entries(0), entries(1).toInt, entries(2), entries(3), entries(4).toInt,
      entries(5), entries(6), entries(7).toInt, entries(8).toInt, entries(9).toInt,
      entries(10), entries(11), entries(12), entries(13), entries(14)
    )
  }

  def userInfoFromHBaseResult(r: Result, columnFamily: String = "f1"): userInfo = {
    userInfo(
      Bytes.toString(r.getValue(Bytes.toBytes(columnFamily), Bytes.toBytes("username"))),
      Bytes.toString(r.getValue(Bytes.toBytes(columnFamily), Bytes.toBytes("name"))),
      Bytes.toString(r.getValue(Bytes.toBytes(columnFamily), Bytes.toBytes("sex"))),
      Bytes.toString(r.getValue(Bytes.toBytes(columnFamily), Bytes.toBytes("birthdate"))),
      Bytes.toString(r.getValue(Bytes.toBytes(columnFamily), Bytes.toBytes("mail")))
    )
  }

  )
}
