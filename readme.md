# Quarkus - Send and Receive Kafka Messages

A java application with a rest interface. The incoming rest call is forwarded as Kafka message.
A kafka processor receives the incoming messages and answers with an outgoing message.

## Prerequisites
- [Install KYMA, Kafka, Postgres, redis, BLOG](../readme.md) 
- [Quarkus](./quarkus_kafka/README.md)

## A look a the quarkus extensions in pom.xml
[pom.xml](/quarkus_kafka/pom.xml)

- RESTEasy Reactive (guide): A JAX-RS implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- SmallRye Reactive Messaging (guide): Produce and consume messages and implement event driven and data streaming applications
- SmallRye Reactive Messaging - Kafka Connector (guide): Connect to Kafka with Reactive Messaging
- Hibernate Validator (guide): Validate object properties (field, getter) and method parameters for your beans (REST, CDI, JPA)
- Reactive PostgreSQL client (guide): Connect to the PostgreSQL database using the reactive pattern
- SmallRye Health (guide): Monitor service health

## Components of quarkus_kafka application

- *Postgres, DB entities*

    - At startup, the initial script is executed: /src/main/ressource/import.sql

    - Hibernate Object: com.heureso.entities.MeterReading.java

- **REST Interface and outgoing Kafka Message**
com.heureso.boundaries.MeterReadingResource.java

@POST - POST a MeterReading
MeterReading is a Hibernate Object, which is persisted. Message is mapped to MeterReadingEvent and forwarded as Kafka Message.

@GET - GET all readings in Database
Returning all meterreading objects in the database.

- *Kafka Event*
PoJo: com.heureso.events.MeterReadingEvent.java

Deserializer JSON -> Quarkus
com.heureso.event.MeterReadingEventDeserializer.java


*Configure Incoming and Outgoing Message in application.properties*



