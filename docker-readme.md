$ docker-machine start
Add host entries in C:\Windows\System32\drivers\etc\hosts

# To allow the same kube context to work on the host and the container:
127.0.0.1 kubernetes.docker.internal

# End of section
192.168.99.105	dockerhost
192.168.99.105	spark-master
192.168.99.105	spark-node01
192.168.99.105	spark-node02
192.168.99.105	namenode
192.168.99.105	edgenode
192.168.99.105	history-server


#
#
#
export DOCKER_HOST=tcp://192.168.0.104:2376
export DOCKER_CERT_PATH=/home/brijeshdhaker/.docker
export DOCKER_TLS_VERIFY=1

docker context create remote-docker --description "remote-docker" --docker "host=tcp://192.168.0.104:2375"

docker context use remote-docker
docker-compose -f docker/dc-kafka-mini-cluster.yaml up -d

#
docker context ls

docker context create remote-docker --description "remote-docker" --docker "host=tcp://192.168.0.102:2375"

docker context rm remote-docker -f
docker context use default

docker context use remote-docker

docker-compose exec mysqlserver bash


## mysql-server 
docker-compose -f docker/mysql/docker-compose.yaml up -d
docker-compose -f docker/mysql/docker-compose.yaml down

docker-compose -f docker/mysql/docker-compose.yaml start mysql
docker-compose -f docker/mysql/docker-compose.yaml stop adminer

## mariadb
cd  /cygdrive/e/git-repos/sb-demo-app/application/docker/mariadb
docker-compose up -d

docker exec -it mariadb /bin/bash 


# Kafka - Start 3 Node Cluster
docker-compose -f docker/dc-kafka-multibroker-cluster.yaml up -d
docker-compose -f docker/dc-kafka-multibroker-cluster.yaml down


# Topic - Creation
docker-compose -f docker/dc-kafka-multibroker-cluster.yaml exec broker-0 kafka-topics \
  --create \
  --bootstrap-server broker-0:19092 \
  --partitions 3 \
  --replication-factor 2 \
  --topic test-topic

# Topic - List 
docker-compose -f docker/dc-kafka-multibroker-cluster.yaml exec broker-0 kafka-topics --list --bootstrap-server broker-0:19092

# Topic - Describe
docker-compose -f docker/dc-kafka-multibroker-cluster.yaml exec broker-0 kafka-topics --describe --topic test-topic --bootstrap-server broker-0:19092

# Topic - Alter
kafka-topics --alter --topic test-topic --partitions 9 --bootstrap-server broker-0:19092

# Topic - Delete
kafka-topics --delete --topic warm-topic --bootstrap-server broker-0:19092

# Kafka - Broker Console
docker-compose -f docker/dc-kafka-multibroker-cluster.yaml exec broker-0 bash

# Producer : 
docker-compose -f docker/dc-kafka-multibroker-cluster.yaml exec broker-0 bash

kafka-console-producer --topic test-topic --broker-list broker-0:19092

docker-compose exec broker kafka-console-producer \
  --topic test-topic 
  --broker-list broker-0:19092 \
  --property parse.key=true \
  --property key.separator=":" \
  
# Consumer : 
docker-compose -f docker/dc-kafka-multibroker-cluster.yaml exec broker-0 bash 
kafka-console-consumer \
 --topic test-topic \
 --group test-cg \
 --bootstrap-server broker-0:19092,broker-1:19093,broker-2:19094 
 
OR
docker exec broker-0 kafka-console-consumer \
 --topic test-topic \
 --group test-cg \
 --bootstrap-server broker-0:19092,broker-1:19093,broker-2:19094
 
docker-compose exec broker-0 kafka-console-consumer \
 --topic test-topic \
 --group test-cg \
 --bootstrap-server broker-0:19092,broker-1:19093,broker-2:19094 \
 --from-beginning \
 --property print.key=true \
 --property key.separator="-"

kafka-consumer-groups --bootstrap-server broker-0:19092 --list
kafka-consumer-groups --bootstrap-server broker-0:19092 --describe --group foo

docker system prune -a --volumes --filter "label=io.confluent.docker"

# Application Setup

docker-compose up -d
docker-compose exec broker kafka-topics --create \
    --topic user-topic \
    --bootstrap-server broker:9092 \
    --partitions 1 \
	--replication-factor 1

docker-compose exec schema-registry bash

kafka-avro-console-producer --topic users-avro \
    --bootstrap-server broker:9092 \
    --property value.schema="$(< /opt/app/schema/user.avsc)"

# Register a new version of a schema under the subject "Kafka-key"
$ curl -X POST -i -H "Content-Type: application/vnd.schemaregistry.v1+json" \
    --data '{"schema": "{\"type\": \"string\"}"}' \
    http://localhost:8081/subjects/Kafka-key/versions

# Register a new version of a schema under the subject "Kafka-value"
$ curl -X POST -i -H "Content-Type: application/vnd.schemaregistry.v1+json" \
    --data '{"schema": "{\"type\": \"string\"}"}' \
     http://localhost:8081/subjects/Kafka-value/versions

# List all subjects
$ curl -X GET -i -H "Content-Type: application/vnd.schemaregistry.v1+json" \
    http://localhost:8081/subjects

# List all schema versions registered under the subject "Kafka-value"
$ curl -X GET -i -H "Content-Type: application/vnd.schemaregistry.v1+json" \
    http://localhost:8081/subjects/Kafka-value/versions

# Fetch a schema by globally unique id 1
$ curl -X GET -i -H "Content-Type: application/vnd.schemaregistry.v1+json" \
    http://localhost:8081/schemas/ids/1

# Fetch version 1 of the schema registered under subject "Kafka-value"
$ curl -X GET -i -H "Content-Type: application/vnd.schemaregistry.v1+json" \
    http://localhost:8081/subjects/Kafka-value/versions/1

# Fetch the most recently registered schema under subject "Kafka-value"
$ curl -X GET -i -H "Content-Type: application/vnd.schemaregistry.v1+json" \
    http://localhost:8081/subjects/Kafka-value/versions/latest

# Check whether a schema has been registered under subject "Kafka-key"
$ curl -X POST -i -H "Content-Type: application/vnd.schemaregistry.v1+json" \
    --data '{"schema": "{\"type\": \"string\"}"}' \
    http://localhost:8081/subjects/Kafka-key

# Test compatibility of a schema with the latest schema under subject "Kafka-value"
$ curl -X POST -i -H "Content-Type: application/vnd.schemaregistry.v1+json" \
    --data '{"schema": "{\"type\": \"string\"}"}' \
    http://localhost:8081/compatibility/subjects/Kafka-value/versions/latest

# Get top level config
$ curl -X GET -i -H "Content-Type: application/vnd.schemaregistry.v1+json" \
    http://localhost:8081/config

# Update compatibility requirements globally
$ curl -X PUT -i -H "Content-Type: application/vnd.schemaregistry.v1+json" \
    --data '{"compatibility": "NONE"}' \
    http://localhost:8081/config

# Update compatibility requirements under the subject "Kafka-value"
$ curl -X PUT -i -H "Content-Type: application/vnd.schemaregistry.v1+json" \
    --data '{"compatibility": "BACKWARD"}' \
    http://localhost:8081/config

kafka-avro-console-consumer --topic users \
--bootstrap-server broker:9092 

kafka-avro-console-consumer --topic users \
--bootstrap-server broker:9092 \
--property schema.registry.url=http://schema-registry:8081 \
--from-beginning

http://dockerhost:8081/subjects
["users-value","order-updated-value","order-created-value"]

http://dockerhost:8081/schemas/types

http://dockerhost:8081/subjects/order-updated-value/versions
http://dockerhost:8081/schemas/ids/1

http://dockerhost:8081/subjects?deleted=true



docker-compose exec connect sh -c "curl -L -O -H 'Accept: application/vnd.github.v3.raw' https://api.github.com/repos/confluentinc/kafka-connect-datagen/contents/config/connector_pageviews_cos.config"
docker-compose exec connect sh -c "curl -X POST -H 'Content-Type: application/json' --data @connector_pageviews_cos.config http://localhost:8083/connectors"

docker-compose exec connect sh -c "curl -L -O -H 'Accept: application/vnd.github.v3.raw' https://api.github.com/repos/confluentinc/kafka-connect-datagen/contents/config/connector_users_cos.config"
docker-compose exec connect sh -c "curl -X POST -H 'Content-Type: application/json' --data @connector_users_cos.config http://localhost:8083/connectors"

docker container stop $(docker container ls -a -q -f "label=io.confluent.docker")
docker container stop $(docker container ls -a -q -f "label=io.confluent.docker") && docker system prune -a -f --volumes

## Start App Components

cd  /cygdrive/d/git-repos/sb-demo-app
cd  /d/git-repos/sb-demo-app


docker-compose -f docker/docker-compose.yaml start mariadb
docker-compose -f docker/docker-compose.yaml stop mariadb

## Start Kafka Cluster
docker-compose -f docker/docker-compose.yaml up -d zookeeper broker schema-registry 

## Create Kafka topic 
docker-compose exec broker kafka-topics --create \
    --topic users \
    --bootstrap-server broker:9092 \
    --replication-factor 1 \
    --partitions 1

docker-compose -f docker/docker-compose.yaml up -d schema-registry
docker-compose -f docker/docker-compose.yaml start schema-registry
docker-compose -f docker/docker-compose.yaml stop schema-registry


docker-compose -f docker/docker-compose.yaml up -d order-service
http://dockerhost:8090/swagger-ui/

docker-compose -f docker/docker-compose.yaml up -d inventory-service
http://dockerhost:8091/swagger-ui/

docker-compose -f docker/docker-compose.yaml up -d payment-service
http://dockerhost:8092/swagger-ui/

docker-compose -f docker/docker-compose.yaml up -d orchestrator-service
http://dockerhost:8093/swagger-ui/

docker-compose -f docker/docker-compose.yaml up -d auditlog-service
http://dockerhost:8093/swagger-ui/

docker-compose -f docker/docker-compose.yaml up -d user-service
http://dockerhost:8093/swagger-ui/

#
# Start Application
#
docker-compose -f docker/docker-compose.yaml up -d
docker-compose -f docker/docker-compose.yaml stop
docker-compose -f docker/docker-compose.yaml down

http://dockerhost:8080/swagger-ui/

# Saga Pattern - Orchestration

This is a sample project to demo saga pattern.

## Prerequisites:

* Kafka cluster

# High Level Architecture

![](images/saga-orchestration.png)

http://localhost:8100/actuator/gateway/routes

http://localhost:8100/api/user/4


SCS Client Starters	Spring Boot	Spring Cloud
3.2 (experimental; uses java-cfenv)	2.4	2020.0.x
3.1 (experimental; uses java-cfenv)	2.2/2.3	Hoxton.x
3.0 (experimental; uses java-cfenv)	2.1	Greenwich.x
2.4 (uses Spring Cloud Connectors)	2.4	2020.0.x
2.3 (uses Spring Cloud Connectors)	2.3	Hoxton.x
2.2 (uses Spring Cloud Connectors)	2.2/2.3	Hoxton.x
2.1 (uses Spring Cloud Connectors)	2.1	Greenwich.x
2.0 (uses Spring Cloud Connectors)	2.0 (not 2.1)	Finchley.x