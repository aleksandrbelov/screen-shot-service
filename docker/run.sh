#!/bin/bash

set -e
(
if lsof -Pi :27017 -sTCP:LISTEN -t >/dev/null ; then
    echo "Please terminate the local mongodb on 27017"
    exit 1
fi
)

echo "Starting docker ."
docker-compose up -d --build

function clean_up {
    echo -e "\n\nSHUTTING DOWN\n\n"
    curl --output /dev/null -X DELETE http://localhost:8083/connectors/mongodb-sink || true
    docker-compose exec mongo-db /usr/bin/mongo --eval "db.dropDatabase()"
    docker-compose down
    if [ -z "$1" ]
    then
      echo -e "Bye!\n"
    else
      echo -e $1
    fi
}

sleep 5
echo -ne "\n\nWaiting for the systems to be ready.."
function test_systems_available {
  COUNTER=0
  until $(curl --output /dev/null --silent --head --fail http://localhost:$1); do
      printf '.'
      sleep 2
      let COUNTER+=1
      if [[ $COUNTER -gt 30 ]]; then
        MSG="\nWARNING: Could not reach configured kafka system on http://localhost:$1 \nNote: This script requires curl.\n"

          if [[ "$OSTYPE" == "darwin"* ]]; then
            MSG+="\nIf using OSX please try reconfiguring Docker and increasing RAM and CPU. Then restart and try again.\n\n"
          fi

        echo -e $MSG
        clean_up "$MSG"
        exit 1
      fi
  done
}

test_systems_available 8082
test_systems_available 8083

trap clean_up EXIT

# echo -e "\nKafka Topics:"
# curl -X GET "http://localhost:8082/topics" -w "\n"

echo -e "\nKafka Connectors:"
curl -X GET "http://localhost:8083/connectors/" -w "\n"

sleep 5

echo -e "\nAdding MongoDB Kafka Sink Connector for the 'screenshot-taken' topic into the 'test.screenshot' collection:"
curl -X POST -H "Content-Type: application/json" --data '{
	"name": "mongodb-sink",
    "config": {
    	"document.id.strategy": "com.mongodb.kafka.connect.sink.processor.id.strategy.PartialValueStrategy",
    	"value.projection.list": "url",
    	"value.projection.type": "whitelist",
        "connector.class": "MongoSinkConnector",
        "tasks.max": 1,
        "topics": "screenshot-taken",
        "connection.uri": "mongodb://mongo-db:27017",
        "database": "test",
        "collection": "screenshot",
        "key.converter": "org.apache.kafka.connect.json.JsonConverter",
        "key.converter.schemas.enable": false,
        "value.converter": "org.apache.kafka.connect.json.JsonConverter",
        "value.converter.schemas.enable": false,
        "writemodel.strategy": "com.mongodb.kafka.connect.sink.writemodel.strategy.ReplaceOneBusinessKeyStrategy"
    }
}' http://localhost:8083/connectors -w "\n"

sleep 2
echo -e "\nKafka Connectors: \n"
curl -X GET "http://localhost:8083/connectors/" -w "\n"

echo "Looking at data in 'test.screenshot':"
docker-compose exec mongo-db /usr/bin/mongo --eval 'db.screenshot.find()'


echo -e '''
==============================================================================================================
Examine the topics in the Kafka UI: http://localhost:9021 or http://localhost:8000/
  - The `screenshot-taken` topic should have the generated screenshots.
  - The `test.screenshot` collection in MongoDB should contain the sinked screenshot.
Examine the collections:
  - In your shell run: docker-compose exec mongo-db /usr/bin/mongo
==============================================================================================================
Use <ctrl>-c to quit'''

read -r -d '' _ </dev/tty
