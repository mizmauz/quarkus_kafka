#!/usr/bin/env bash

mvn clean package -DskipTests

docker build -f src/main/docker/Dockerfile.jvm -t mizmauzdocker/quarkus_kafka:0.0.1 .

docker login -u <docker_username> -p <docker_password> # !! Enter your own user here
docker push mizmauzdocker/quarkus_kafka:0.0.1

export KUBECONFIG="$HOME/.kube/config"
kubectl delete deployment quarkuskafka | true
kubectl apply -f ./k8s/quarkus_kafka_kyma.yaml

