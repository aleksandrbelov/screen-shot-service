# screen-shot-service
So following schema could be used:
![Schema](https://github.com/aleksandrbelov/screen-shot-service/blob/master/screenshot-schema.png)

## Up and Runnig
Use https://github.com/aleksandrbelov/screen-shot-service/tree/master/docker

To run the example: `./run.sh` which will:
  
  - Run `docker-compose up` 
  - Wait for MongoDB, Kafka, Kafka Connect to be ready
  - Register the MongoDB Kafka Sink Connector

Examine the collections in MongoDB:
  - In your shell run: docker-compose exec mongo-db /usr/bin/mongo

## docker-compose.yml

The following systems will be created:

  - Zookeeper
  - Kafka
  - Confluent Kafka Connect
  - Kafka Rest Proxy
  - Kafka Topics UI
  - MongoDB
  - Mongo Client
