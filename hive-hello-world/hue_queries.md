# Hive Lab

The queries below were written and executed on `Hue`.

## Task 1

Create a database for yourself using your handle: first initial + last name

```
CREATE DATABASE dward
```

## Task 2

Create an external table named “reviews_ext” in your database on top of the data stored in the reviews_external directory. Verify you can query it with “select * from reviews”

```
CREATE EXTERNAL TABLE IF NOT EXISTS dward.reviews_ext(
  marketplace STRING,
  customer_id INTEGER,
  review_id STRING,
  product_id STRING,
  product_parent STRING,
  product_title STRING,
  product_category STRING,
  star_rating INTEGER,
  helpful_votes INTEGER,
  total_votes INTEGER,
  vine STRING,
  verified_purchase STRING,
  review_headline STRING,
  review_body STRING,
  review_date STRING
  )
  ROW FORMAT
    DELIMITED FIELDS TERMINATED BY "\t"
    ESCAPED BY "\\"
    LINES TERMINATED BY "\n"
  STORED AS TEXTFILE
  LOCATION "/user/dward/reviews/reviews.tsv"
  TBLPROPERTIES("skip.header.line.count"="1")
```

How many reviews gave 2 stars? 5 stars?

```
SELECT
  COUNT(*) as two_start
FROM
  reviews_ext
WHERE
  star_rating=2
```

```
SELECT
  COUNT(*) as num_ratings
FROM
  reviews_ext
GROUP BY
  star_rating
```

How many reviews were verified reviews?

```
SELECT
  COUNT(*) as num_verified
FROM
  reviews_ext
WHERE
  verified_review = "Y"
```

## Task 3

Drop your reviews_ext table.

```
DROP TABLE dward.reviews_ext
```

Was your data deleted? How do you know?

```
It wasn't. I can check the file browser. I can also make a new external table.
```

## Task 4

Create an internal table named reviews_int in your database. LOAD the data from the reviews folder into this table.

```
CREATE TABLE IF NOT EXISTS dward.reviews_int(
  marketplace STRING,
  customer_id INTEGER,
  review_id STRING,
  product_id STRING,
  product_parent STRING,
  product_title STRING,
  product_category STRING,
  star_rating INTEGER,
  helpful_votes INTEGER,
  total_votes INTEGER,
  vine STRING,
  verified_purchase STRING,
  review_headline STRING,
  review_body STRING,
  review_date STRING
  )
  ROW FORMAT
    DELIMITED FIELDS TERMINATED BY "\t"
    ESCAPED BY "\\"
    LINES TERMINATED BY "\n"
  STORED AS TEXTFILE
  TBLPROPERTIES("skip.header.line.count"="1")

LOAD DATA INPATH "/user/dward/reviews/reviews.tsv" INTO TABLE dward.reviews_int
```

## Task 5

Drop your reviews_int table.

```
DROP TABLE dward.reviews_int
```

Was your data deleted? How do you know?

```
Yes. It's gone in the file browser and I can't create a new table.
```