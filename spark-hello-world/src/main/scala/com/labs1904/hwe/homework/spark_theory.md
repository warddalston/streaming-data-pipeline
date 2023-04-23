# Overview

Similar to the work you did for Kafka, this is your crash course into Spark through different questions. In this homework, your
challenge is to write answers that make sense to you, and most importantly, **in your own words!**
Two of the best skills you can get from this class are to find answers to your questions using any means possible, and to
reword confusing descriptions in a way that makes sense to you. 

### Tips
* You don't need to write novels, just write enough that you feel like you've fully answered the question
* Use the helpful resources that we post next to the questions as a starting point, but carve your own path by searching on Google, YouTube, books in a library, etc to get answers!
* We're here if you need us. Reach out anytime if you want to ask deeper questions about a topic 
* This file is a markdown file. We don't expect you to do any fancy markdown, but you're welcome to format however you like
* Spark By Examples is a great resources to start with - [Spark By Examples](https://sparkbyexamples.com/)

### Your Challenge
1. Create a new branch for your answers 
2. Complete all of the questions below by writing your answers under each question
3. Commit your changes and push to your forked repository

## Questions
#### What problem does Spark help solve? Use a specific use case in your answer 
* Helpful resource: [Apache Spark Use Cases](https://www.toptal.com/spark/introduction-to-apache-spark)
* [Overivew of Apache Spark](https://www.youtube.com/watch?v=znBa13Earms&t=42s)

Spark solves the problem of how to quickly, easily, and reliably process large amounts of data. It is flexible, with 
the ability to handle data processing, query look-ups, graph executions, etc, really anything. It is fault-tolerant, 
with systems that make it so that everything is guaranteed to ony run once and ony once. It is scalable, allowing 
from very large clusters that can divide work without having to do a lot of work on the front-end to manage machines 
and utilization. And it is fast, with resources able to shift purpose as demand requires and a compiler that trims 
away unneeded computations and lazily executes them.

A nice use-case for spark is real-time training of machine learning models. Instead of training a model on whole 
datasets, one could use spark and a pub-sub system like kafka to do streaming learning. In ad-tech, this would mean 
that we could just subscribe to information about impressions and clicks from the pub-sub, then whenever something 
arrives, we run it through our gradient descent algorithm to update the weights of our model. We could either have a 
system where the model in production is swapped out with the most recent one running on the spark cluster or (if 
this is possible), the spark cluster could simultaneously be responsible for serving impressions and training.

Spark can be used in batch mode for faster processing from databases. Spark can separate the load of running SQL 
like queries from a single machine to many machines. We could have each node handle a single partition of the 
data, with the driver combining the various results into a table that users can use to perform some sort of analysis 
in python or r.

Spark can also be used for data engineers that need tools to clean-up data and store it as it arrives. For instance, 
an e-commerce site with a lot of sales could use spark to merge order information with user information before 
saving them to a database. Or order information could be merged with supply chain data to monitor whether the 
company needs to update prices.

#### What is Apache Spark?
* Helpful resource: [Spark Overview](https://www.youtube.com/watch?v=ymtq8yjmD9I) 

Spark is a distributed computing engine. Machines in a spark cluster do one of three things: they are the driver, 
the cluster manager, or the workers. The driver is where the developer operates, providing instructions on how many 
resources to put into the cluster, where to get the data, how to transform it, and what to do with the transformed 
data. The cluster mangager operates in the background, making sure that everything works: ensuring workers are 
properly utilized, that jobs are completed, and that failures in the workers do not disrupt the jobs. The workers do 
the actual computation.

The key data type used in spark is the RDD aka the "Resilient distributed dataset". These are _immutable_ and 
fault-tolerant (if one worker dies, its work on the RDD can be shifted to another worker) collections (they can be 
iterated on) of data. The actual data can be of any type.

Spark is a framework that lets developers provide instructions on how to process an RDD over potentially many 
machines. The developer focuses on the _what_, spark handles the _how_.

#### What is distributed data processing? How does it relate to Apache Spark?  
[Apache Spark for Beginners](https://medium.com/@aristo_alex/apache-spark-for-beginners-d3b3791e259e)

Distributed data processing is when some series of operations are performed on a dataset where the execution of 
these operations is split across several machines. Spark is an example of distributed data processing, although 
there are others such as MapReduce. Spark's key features are that it does all processing in-memory, which makes it 
very fast (disk IO is slow), and that it is resilient: because all operations and the data to be done are treated as 
graphs, when a failure occurs, spark can go back to the last point in the graph where the data is found and re-do 
execution from there.

#### On the physical side of a spark cluster, you have a driver and executors. Define each and give an example of how they work together to process data

The driver is where instructions are given by the developer and it kicks off the entire computation process by 
telling the cluster manager to allocate resources and build the computation graph as specified by the instructions. 
The executors do the actual work. Which nodes do which work and for how long is handled by the cluster manager. This 
is all out of sight of the developer; they just provision resources and define a job.

#### Define each and explain how they are different from each other 
* RDD (Resilient Distributed Dataset): This is the base class. It is an immutable, resiliant, collection of data.
* DataFrame: A special case of a dataset. Less strongly typed. It has named columns, like a table in a RDB.
* DataSet: Datasets are strongly typed dataframes. They aren't available in python because it lacks static typing. 
  Also named.

An RDD is the most low-level. Think of this as an array: the elements in each array could be anything. A Dataset 
provides some structure: each element in the array is a collection of pieces of data. But the Dataset is STRONGLY 
TYPED. There are pieces of data and each piece must be a specific thing. A dataframe just takes away the strong 
typing. The data can be treated however you want and if you ask the code to do a string operation but it gets an 
integer, you just get an error at runtime.

#### What is a spark transformation?
[Spark By Examples-Transformations](https://sparkbyexamples.com/apache-spark-rdd/spark-rdd-transformations/)

A spark transformation is an operation that returns another RDD/DataFrame/Dataset. Classic examples are map and filter.

#### What is a spark action? How do actions differ from transformations? 

Spark actions do something other than return a new RDD. Printing, reducing, writing.

#### What is a partition in spark? Why would you ever need to repartition? 
[Spark Partitioning](https://sparkbyexamples.com/spark/spark-repartition-vs-coalesce/)

A partition is a subset of the data being processed. You may want to repartition if the original partition is done 
based on some feature and this leads to skew. Or if you do a `flatMap` operation that increases the number of rows, 
you may end up with far more data per core than you originally had.

#### What was the most fascinating aspect of Spark to you while learning? 

It's flexibility. For almost any big-data problem---processing, querying, batch and streaming, ML, monitoring, 
etc---Spark is a reasonable answer.