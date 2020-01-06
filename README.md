# screen-shot-service
This is the coding challenge for Detectify.

For simplicity of demo, It includes embedded MongoDB and Kafka.

### Tag: latest

## Up and Running: 

```shell
docker pull alexbelov91/screen-shot-service:latest
```

```shell
docker run -d -p 8080:8080 screen-shot-service
```

## API:
### Create screenshots for provided URLs: 
`POST localhost:8080/api/v1/screenshot`

`Body : {"urls":["str"]}`
### Get screenshot of URL:
`GET localhost:8080/api/v1/screenshot`

Query Params: 
* url=URL_OF_WEB_PAGE
