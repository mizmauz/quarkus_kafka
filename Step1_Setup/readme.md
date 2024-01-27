# Provisioning a KYMA instance on SAP BTP

This blog shows, how to install postgres, kafka and redis on a Kubernetes cluster here SAP® KYMA. 

**Enable SAP BTP, Kyma Runtime | SAP Tutorials**

Got to the SAP BTP site: https://account.hanatrial.ondemand.com/

[Enable KYMA in BTP](https://developers.sap.com/tutorials/cp-kyma-getting-started.html)

[Sizing of the Trial Account](https://help.sap.com/docs/btp/sap-business-technology-platform/about-trial-account)

**Update the config in .kube environment**
1. Copy the kubeconfig URL from the BTP Site, download the file
   ![img.png](kubeconfig.png)

2. Rename the file to *config* and put it in the  .kube directory (in your home directory)


**Install local tools**

[Install Ubuntu on WSL2 and get started with graphical applications ](https://ubuntu.com/tutorials/install-ubuntu-on-wsl2-on-windows-11-with-gui-support#1-overview)
[Install kubectl](https://kubernetes.io/docs/tasks/tools/)

**Installing prerequisite tools**

- Postgres DB

``` kubectl
kubectl apply -f ./Step1_Setup/k8s/postgres
```

- Test Postgres
``` kubectl 
- kubectl exec -it <postgres_pod> -- psql -U postgres
```

[Find useful commands for postgres here]( https://docs.vmware.com/en/VMware-SQL-with-Postgres-for-Kubernetes/1.9/tanzu-postgres-k8s/GUID-accessing.html)

- Kafka, Zookeeper, AKQH

  <span style="color: red;">Before deployment, update the HOSTNAME in the /k8s/kafka/akhq-api-rule.yaml file, Line 21</span>.

Get the hostname as in the screenshot:

![Hostname Kyma](hostname_kyma.png) or use this command:

``` kubectl
kubectl get gateway kyma-gateway -n kyma-system -o=jsonpath='{.spec.servers[0].hosts[0]}'
```

Then apply the .yaml files.

``` kubectl
kubectl apply -f ./Step1_Setup/k8s/kafka 
```

- Redis

``` kubectl
kubectl apply -f ./Step1_Setup/k8s/redis
```

**Test the Kafka Connection**

-Deploy testclient
``` kubectl
kubectl apply -f testclient.yaml
```

-Create Topic
``` kubectl
kubectl exec -ti testclient -- ./bin/kafka-topics.sh --zookeeper zookeeper:32181 --topic messages --create --partitions 1 --replication-factor 1
```

-Check Topic
``` kubectl
kubectl exec -ti testclient -- ./bin/kafka-topics.sh --zookeeper zookeeper:32181 --list
```

-Create a listener for the topic ( open a new terminal session for this)
``` kubectl
kubectl exec -ti testclient -- ./bin/kafka-console-consumer.sh --bootstrap-server kafka:29092 --topic messages --from-beginning
```

-Create a producer for the topic ( open a new terminal session for this)
``` kubectl
kubectl exec -ti testclient -- ./bin/kafka-console-producer.sh --broker-list kafka:29092 --topic messages
```
Enter a message in the producer terminal session, this should be shown in the listener session.


**Test the AKHQ Website**

The AKQH website shows the topics created. You have to forward the local port, here to 9090 local

``` kubectl
kubectl port-forward <akhq-pod> localport:8080
```

Open your browser and enter the URL
``` url
localhost:9090/akhq
```

![Kafka Topics in AKHQ](img.png)

## Github
Code is available from Github Repository: [Github](https://github.com/mizmauz/heureso_kyma)
