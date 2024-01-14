#!/usr/bin/env bash

mvn clean package -DskipTests

docker build -f src/main/docker/Dockerfile.jvm -t mizmauzdocker/quarkus_kafka:0.0.1 .

docker login -u mizmauzdocker -p dckr_pat_t7yz2HSO1K-4NUiBJRBldYybai8
docker push mizmauzdocker/quarkus_kafka:0.0.1

export KUBECONFIG="$HOME/.kube/config"
kubectl delete deployment quarkuskafka| true
kubectl apply -f ./k8s/quarkus_kafka_kyma.yaml

