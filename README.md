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

Might return status code 404 with message "Screenshot not found" due to there is not such screen shot in DB.

## How it works
As was mentioned above service uses embedded DB and MQ. 
For messaging was chosen Kafka because of its good scalability.
As DB was chosen MongoDB. The main reason it's triying something new :)

How screenshot creation works. Service receives a request wiht list or URLs and then produce messages for each URL in topic "create-screenshot". Then service consumes messages one by one, takes screenshot uses chromium and chrome-driver and saves it in DB.

Why need MQ and Kafka? It helps to scale process of taking screenshot by increasing partions in topic and increasing instances of service. 

## Next steps

Due to above solution is simple and includes embedded DB and MQ so there is need of something more production ready.

Next step could be done:
* split requests of creating and getting of screenshot in separate services. Thus decoupeling of these processes can help with scaling of any of requests.
* service whiche responsible for taking screenshots just consume messages with URL from the topic "create-screenshot" and produce message with image and URL to the topic "screenshot-taken". Since it's independent, decoupled, stateless service it can be easy to scale. 
* kafka sink connector could be used for puting screenshot into DB. Connector also can be scaled.
* another one service responsible for getting screenshot just retrieves it from DB by URL.

So following schema could be used:
![Schema](https://github.com/aleksandrbelov/screen-shot-service/blob/master/screenshot-schema.png)

