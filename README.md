# screen-shot-service
This is the coding challenge for Detectify.

For simplicity of demo, It includes embedded MongoDB and Kafka.

### Tag: latest

## Up and Running: 

```shell
docker pull alexbelov91/screen-shot-service:latest
```

```shell
docker run -d -p 8080:8080 alexbelov91/screen-shot-service
```

## API:
### Create screenshots for provided URLs: 
`POST localhost:8080/api/v1/screenshot`

`Body : {"urls":["str"]}`
### Get screenshot of URL:
`GET localhost:8080/api/v1/screenshot`

Query Params: 
* url=URL_OF_WEB_PAGE

If there is no such screenshot in DB will return status code 404 with the message "Screenshot not found".

## How it works
As was mentioned above service uses embedded DB and MQ. 
For messaging was chosen Kafka because of its good scalability.
As DB was chosen MongoDB. The main reason it's trying something new :)

How to screenshot creation works. Service receives a request with a list of URLs and then produces messages for each URL in the topic "create-screenshot". Then the service consumes messages one by one, takes a screenshot uses chromium and chrome-driver and saves it in DB.

Why need MQ and Kafka? It helps to scale the process of taking a screenshot by increasing partitions in the topic and increasing instances of service. 

## Next steps

The above solution is simple and includes embedded DB and MQ so there is a need for something more production-ready.

Next step could be done:
* split requests of creating and getting of the screenshot in separate services. Thus decoupling of these processes can help with scaling of any of the requests.
* service which responsible for taking screenshots just consume messages with URL from the topic "create-screenshot" and produce a message with image and URL to the topic "screenshot-taken". Since it's independent, decoupled, stateless service it can be easy to scale. 
* Kafka sink connector could be used for putting screenshot into DB. The connector also can be scaled.
* another one service responsible for getting a screenshot just retrieves it from DB by URL.

You can find screen-shot-service without webdriver at branch "move_webdriver_to_another_service"
https://github.com/aleksandrbelov/screen-shot-service/tree/move_webdriver_to_another_service

You can find web-driver-service here:
https://github.com/aleksandrbelov/web-driver-service

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

