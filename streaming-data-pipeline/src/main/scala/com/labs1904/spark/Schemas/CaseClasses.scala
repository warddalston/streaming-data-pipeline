package com.labs1904.spark.Schemas

import org.apache.hadoop.hbase.client.{Get, Result, Table}
import org.apache.hadoop.hbase.util.Bytes

case class rawReview(
  marketplace: String,
  customer_id: Int, // NOTE: May need to be Long
  review_id: String,
  product_id: String,
  product_parent: Int,  // NOTE: May need to be Long
  product_title: String,
  product_category: String,
  star_rating: Byte,
  helpful_votes: Int,
  total_votes: Int,
  vine: Boolean,
  verified_purchase: Boolean,
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

case class enrichedReview(
  marketplace: String,
  customer_id: Int, // NOTE: May need to be Long
  review_id: String,
  product_id: String,
  product_parent: Int, // NOTE: May need to be Long
  product_title: String,
  product_category: String,
  star_rating: Byte,
  helpful_votes: Int,
  total_votes: Int,
  vine: Boolean,
  verified_purchase: Boolean,
  review_headline: String,
  review_body: String,
  review_date: String,
  username: String,
  name: String,
  sex: String,
  birthdate: String,
  mail: String
 )

object Constructors {
  def yesNoToBoolean(s: String): Boolean = {
    if(s == "Y") {
      return true
    }
    false
  }
  def rawReviewFromCSV(input: String, sep: String = "\t"): rawReview = {
    val entries = input.split(sep)
    rawReview(
      entries(0), entries(1).toInt, entries(2), entries(3), entries(4).toInt,
      entries(5), entries(6), entries(7).toByte, entries(8).toInt, entries(9).toInt,
      yesNoToBoolean(entries(10)), yesNoToBoolean(entries(11)),
      entries(12), entries(13), entries(14)
    )
  }

  private def getFromRawReview(rr: rawReview, columnFamily: String = "f1"): Get = {
    new Get(Bytes.toBytes(rr.customer_id.toString)).addFamily(Bytes.toBytes(columnFamily))
  }

  private def userInfoFromHBaseResult(r: Result, columnFamily: String = "f1"): userInfo = {
    userInfo(
      Bytes.toString(r.getValue(Bytes.toBytes(columnFamily), Bytes.toBytes("username"))),
      Bytes.toString(r.getValue(Bytes.toBytes(columnFamily), Bytes.toBytes("name"))),
      Bytes.toString(r.getValue(Bytes.toBytes(columnFamily), Bytes.toBytes("sex"))),
      Bytes.toString(r.getValue(Bytes.toBytes(columnFamily), Bytes.toBytes("birthdate"))),
      Bytes.toString(r.getValue(Bytes.toBytes(columnFamily), Bytes.toBytes("mail")))
    )
  }

  def userInfoFromComponents(rr: rawReview, table: Table): userInfo = {
    val get = getFromRawReview((rr))
    val result = table.get(get)
    userInfoFromHBaseResult(result)
  }

  def enrichedReviewFromComponents(rr: rawReview, ui: userInfo): enrichedReview = {
    enrichedReview(
      rr.marketplace,
      rr.customer_id,
      rr.review_id,
      rr.product_id,
      rr.product_parent,
      rr.product_title,
      rr.product_category,
      rr.star_rating,
      rr.helpful_votes,
      rr.total_votes,
      rr.vine,
      rr.verified_purchase,
      rr.review_headline,
      rr.review_body,
      rr.review_date,
      ui.username,
      ui.name,
      ui.sex,
      ui.birthdate,
      ui.mail
    )
  }
}
