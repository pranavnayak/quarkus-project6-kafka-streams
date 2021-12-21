# quarkus-project6-kafka-streams
 A sample Java Microservice application , that demonstrates how your Quarkus application can utilize the Apache Kafka Streams API to implement stream processing applications based on Apache Kafka.

# NOTE: PARTIALLY DONE !!
Just Producer of the stream data is done .. yet to complete the aggregator 

# Application Details !!
TBD

# Flow Diagram
TBD


docker-compose -f docker-compose-local.yaml up
docker exec -it kafka_kafka_1 /bin/bash
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic temperature-values --from-beginning
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic weather-stations --from-beginning
