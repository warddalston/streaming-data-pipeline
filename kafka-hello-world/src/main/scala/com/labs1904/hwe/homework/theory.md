# Overview

Kafka has many moving pieces, but also has a ton of helpful resources to learn available online. In this homework, your
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
#### What problem does Kafka help solve? Use a specific use case in your answer 
* Helpful resource: [Confluent Motivations and Use Cases](https://youtu.be/BsojaA1XnpM)

* Kafka helps us move from a "static" version of data to an "event" version. A static 
  version is like a filing cabinet that holds single truths. The event version is more 
  like a shelf that can hold all sorts of things and that needs to be easily accessed 
  by lots of people at once, and where there are always more things coming in.
* Old systems with queries are designed to operate on a lot of data at once, but not 
  until the requester asks for the data. This makes it slow when requesters need the 
  data immediately and one event at a time.
  
Kakfa supports the development of real-time streaming data applications, where events 
occur, are recorded, and can be used by other systems in almost real time and where 
the amount of events can become very large without overwhelming the system. In 
contrast, batch processing systems are slower, more rigid, and unable to handle events 
one-at-aa time.

One example use case is online advertising; clicks and conversions can be recorded and 
sent to models that then dynamically adjust the costs for impressions based on the performance 
of that piece of digital real estate. With a batch system, this is not possible: the 
performance of various assets must be assessed once an hour or once a day and only 
then can pricing be updated. This means that the firm will be under or over charging 
for impressions.

#### What is Kafka?
* Helpful resource: [Kafka in 6 minutes](https://youtu.be/Ch5VhJzaoaI) 

- "At its foundation, it is a distributed log."
- An answer to the distribution and scaling of messaging systems
- Scaling: it can handle a lot because of the partitioning
- Distribution: We have to specify some schema for which messages enter which log
- Consumers can be set up to read from the partition in whatever order they want

Kafka is a system for processing event streams in real time that allows for efficient 
scaling, flexible organizing of messages across topics and partitions, and that 
provides reliability in the case of node failures. 

#### Describe each of the following with an example of how they all fit together: 
 * Topic: A stream of related data.
 * Producer: The entities that send data to Kafka.
 * Consumer: The entities that read data from partitions 
 * Broker: An instance that performs the reads and writes in kafka. Partitions exist 
   on brokers, with a maximum of one copy of a partition per broker.
 * Partition: A subset of the related data in a topic. Which messages go to which 
   partition may be random, or it can be controlled by the engineer.

Brokers are the center of the Kafka cluster. An engineer sets up the cluster by 
deciding how many topics, or sets of related data, it will handle and then by breaking 
each topic into a set of partitions. Partitions allow it to handle more data and give 
more fine grained control about how data is stored and how it can be accessed. The 
produces then send messages to the brokers and consumers read them.

#### Describe Kafka Producers and Consumers

#### How are consumers and consumer groups different in Kafka? 
* Helpful resource: [Consumers](https://youtu.be/lAdG16KaHLs)
* Helpful resource: [Confluent Consumer Overview](https://youtu.be/Z9g4jMQwog0)

A consumer is a single instance that is responsible for reading messages from the 
partitions. Message order _within_ a partition is guaranteed, but if a single consumer 
reads from multiple paritions, it can't guarantee the order across partitions 
(messages from partition A may come in, then some from B, etc). Also, when a single 
consumer must read all the partitions, it can become a bottleneck: that instance 
might not be able to keep up. Consumer groups are a team of consumers that work 
together to read a topic. You can have $N_{Consumers} <= N_{Partitions}$. When the 
number of consumers and partitions are equal, each consumer reads just a single 
partition. That makes it faster and ensures that you can always have the right order 
of messages. All the scaling up (and down) is handled automatically by the system. 

#### How are Kafka offsets different from partitions? 

A partition is basically a dictionary or an array; the offest is like a key or an 
index. It identifies where a particular piece of data can be found.

#### How is data assigned to a specific partition in Kafka?

If the user sends a key along with the message, the key determines the partition that 
receives the data. This is helpful for cases like recording information about specific 
sporting information; we want all the STL vs CHI information in one partition and the 
KC vs MIN in aother one. If there are no keys, then Kafka sends the messages to 
partitions effectively at random, balancing the load.

#### Describe immutability - Is data on a Kafka topic immutable? 

Yes, Kafka partitions are append-only logs. Messages cannot be deleted once written 
(until the designated clean-up period when old data is removed) and they cannot be 
changed.

#### How is data replicated across brokers in kafka? If you have a replication factor of 3 and 3 brokers, explain how data is spread across brokers
* Helpful resource [Brokers and Replication factors](https://youtu.be/ZOU7PJWZU9w)

There are two types of partitions: the leader and the in-sync replicas (ISR). Each 
broker can only contain a single replica of a partition. That means that a replication 
factor of three leads to one leader and two ISR. Each broker has a copy of all the 
data.

The leader is the only one that can do reads and writes with producers and consumers. 
The replicas just get copies of the data as it is read in. When a broker goes down, if 
the leader goes down, then one of the ISR is promoted.

*Q*: What happens when an ISR goes down? Are the data from the down-period copied 
before new data arrives or does that ISR just have a gap in its record?

#### What was the most fascinating aspect of Kafka to you while learning?

Thinking about how using an append-only log makes it much easier for Kafka to handle 
lots of data for both reads and writes and how that leads to easier 
memory and storage management. 
