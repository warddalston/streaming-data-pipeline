package com.labs1904.spark.Schemas

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
