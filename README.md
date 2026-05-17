Use these command to start the rabbitMQ and kafka before running services
Kafka + Docker + Spring Boot Complete Commands
1. Pull Kafka Image (Only First Time)
docker pull apache/kafka:4.2.0
You only do this once.

2. Run Kafka Container
docker run -d --name mykafka -p 9092:9092 apache/kafka:4.2.0

3. Check Running Containers
docker ps

4. Check All Containers (Running + Stopped)
docker ps -a

5. Enter Kafka Container
docker exec -it kafka bash

6. Create Topic
(inside container)
/opt/kafka/bin/kafka-topics.sh --create --topic orders --bootstrap-server localhost:9092

7. List Topics
/opt/kafka/bin/kafka-topics.sh --list --bootstrap-server localhost:9092

8. Start Producer
/opt/kafka/bin/kafka-console-producer.sh --topic orders --bootstrap-server localhost:9092
Type messages:
hello
order1

9. Start Consumer
Open another PowerShell:
docker exec -it kafka bash
Then:
/opt/kafka/bin/kafka-console-consumer.sh --topic orders --from-beginning --bootstrap-server localhost:9092

10. Exit Container
exit

11. Stop Kafka Container
(outside container / PowerShell)
docker stop kafka

12. Restart Kafka Container
If image already pulled and container already created:
docker start kafka
This is the main restart command you asked for.

13. Remove Container
docker rm kafka

14. Remove Docker Image
docker rmi apache/kafka:4.2.0

Spring Boot Configuration
application.properties
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=my-group

Producer
kafkaTemplate.send("orders", message);

Consumer
@KafkaListener(topics = "orders")

Most Important Commands For Daily Use
First Time Setup
docker pull apache/kafka:4.2.0
docker run -d --name kafka -p 9092:9092 apache/kafka:4.2.0

Every Next Time
Just restart:
docker start kafka

Stop When Done
docker stop kafka
This is the normal developer workflow.


RUN RabbitMq image on Docker:
 docker run --rm -it -p 15672:15672 -p 5672:5672 rabbitmq:4-management


# Kafka Broker
spring.kafka.bootstrap-servers=localhost:9092

# Consumer Group
spring.kafka.consumer.group-id=my-group

# Auto create topic
spring.kafka.admin.auto-create=true

# Producer Serialization
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer

# Consumer Deserialization
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer

# Read messages from beginning if no offset exists
spring.kafka.consumer.auto-offset-reset=earliest

