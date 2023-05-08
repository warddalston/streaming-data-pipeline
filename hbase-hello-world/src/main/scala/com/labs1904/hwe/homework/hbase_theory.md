# Overview

By now you've seen some different Big Data frameworks such as Kafka and Spark. Now we'll be focusing in on HBase. In this homework, your
challenge is to write answers that make sense to you, and most importantly, **in your own words!**
Two of the best skills you can get from this class are to find answers to your questions using any means possible, and to
reword confusing descriptions in a way that makes sense to you. 

### Tips
* You don't need to write novels, just write enough that you feel like you've fully answered the question
* Use the helpful resources that we post next to the questions as a starting point, but carve your own path by searching on Google, YouTube, books in a library, etc to get answers!
* We're here if you need us. Reach out anytime if you want to ask deeper questions about a topic 
* This file is a markdown file. We don't expect you to do any fancy markdown, but you're welcome to format however you like


### Your Challenge
1. Create a new branch for your answers 
2. Complete all of the questions below by writing your answers under each question
3. Commit your changes and push to your forked repository

## Questions
#### What is a NoSQL database? 
A NoSQL database is one that does not have a defined & strictly enforced schema. It also doesn't have foreign & 
primary keys.

#### In your own words, what is Apache HBase? 
HBase is a distributed and scalable NoSQL database that uses a key-value structure to store data and provide fast 
lookups

#### What are some strengths and limitations of HBase? 
* [HBase By Examples](https://sparkbyexamples.com/apache-hbase-tutorial/)

Good: fast lookups, flexible with no requirements to set a schema
Bad: Unpredictable what you'll find in a key without a scehma. Hard to combine records since there is not a 
predefined schema.

#### Explain the following concepts: 
* Rowkey: the unique ID for a cell. 
* Column Qualifier: The index of a value or a column name
* Column Family: Where the data is stored in storage. 


#### What are the differences between Get and Put commands in HBase? 
* [HBase commands](https://www.tutorialspoint.com/hbase/hbase_create_data.htm)

Get retrieves data, put stores it.

#### What is the HBase Scan command for? 
* [HBase Scan](https://www.tutorialspoint.com/hbase/hbase_scan.htm)

Getting data over a range of keys (keys are stored in storage in ascending order based on their binary values)

#### What was the most interesting aspect of HBase when went through all the questions?
Trying to understand how the keys are used to store data across the cluster; is it hashing or something else? How 
does it guarantee no collisions and O(1) lookups?