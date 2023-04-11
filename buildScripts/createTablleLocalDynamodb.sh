#!/usr/bin/env sh

# docker run -d -p 8000:8000 amazon/dynamodb-local

## list tables on the local dynamodb passing the endpoint-url
aws dynamodb list-tables --endpoint-url http://localhost:8000

## delete any previous tables from local dynamodb passing the endpoint-url
aws dynamodb delete-table --table-name User --endpoint-url http://localhost:8000

## Create a table in the docker container by passing in the endpoint-url [--endpoint-url http://localhost:8000]
aws dynamodb create-table \
   --table-name User \
   --attribute-definitions AttributeName=UserEmail,AttributeType=S AttributeName=UserPassword,AttributeType=S \
   --key-schema AttributeName=UserEmail,KeyType=HASH AttributeName=UserPassword,KeyType=RANGE \
   --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \
   --endpoint-url http://localhost:8000

## list tables on the local dynamodb passing the endpoint-url
aws dynamodb list-tables --endpoint-url http://localhost:8000

## put-item into a local tables on the local dynamodb passing the endpoint-url
aws dynamodb put-item --table-name User  --item '{ "UserEmail": {"S": "joe"}, "UserPassword": {"S": "ABcde5"} }'

exit 0