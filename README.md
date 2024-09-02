
# Froneus challenge - User Status Microservice
## Descripción

Este microservicio, desarrollado con Spring Boot, implementa una arquitectura event-driven utilizando Kafka. Su propósito es recibir el estado de un usuario (activo o inactivo) y generar eventos que serán enviados a un topic de Kafka. Otros microservicios podrán suscribirse a estos eventos y reaccionar en tiempo real.

## Características

- **Arquitectura Event-Driven**: Emite eventos cuando el estado de un usuario cambia.
- **Apache Kafka**: Utiliza Kafka como broker de mensajes para publicar y consumir eventos.
- **Microservicios**: Desarrollado siguiendo principios de microservicios y Hexagonal Architecture.

## Tecnologías utilizadas

- **Java 17**
- **Spring Boot 2.2.0**
- **Apache Kafka**
- **Docker y Docker Compose**

## Requisitos previos

Para ejecutar este microservicio, asegúrate de tener instaladas las siguientes herramientas:

- Java 17
- Maven (para gestionar las dependencias y construir el proyecto)
- Docker y Docker Compose (para ejecutar Kafka, Zookeeper y otros servicios necesarios)

## Configuración

### 1. Variables de entorno

Este microservicio requiere algunas configuraciones para conectarse a Kafka. Estas configuraciones pueden ser pasadas como variables de entorno o establecidas en el archivo `application.properties`:

```properties
spring.application.name=challenge
kafka.bootstrap-servers=kafka1:29092
kafka.topic.name=user-status-topic
```
### 2. Docker Compose
Para ejecutar Kafka y Zookeeper, puedes utilizar el siguiente docker-compose.yml:

### Docker Compose Configuration

Para ejecutar Kafka y Zookeeper, puedes utilizar el siguiente archivo `docker-compose.yml`:

```yaml
version: '3.8'

services:
  zookeeper:
    image: bitnami/zookeeper:3.9.1
    container_name: zookeeper
    tmpfs: "/zktmp"
    environment:
      ALLOW_ANONYMOUS_LOGIN: 'yes'
    ports:
      - "2181:2181"
    networks:
      - kafka-network

  kafka1:
    image: bitnami/kafka:3.7.0
    container_name: kafka1
    depends_on:
      - zookeeper
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_CFG_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_CFG_LISTENERS: INTERNAL://:9092,EXTERNAL://0.0.0.0:29092
      KAFKA_CFG_ADVERTISED_LISTENERS: INTERNAL://kafka1:9092,EXTERNAL://localhost:29092
      KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP: INTERNAL:PLAINTEXT,EXTERNAL:PLAINTEXT
      KAFKA_CFG_INTER_BROKER_LISTENER_NAME: INTERNAL
      KAFKA_CFG_AUTO_CREATE_TOPICS_ENABLE: 'true'
      ALLOW_PLAINTEXT_LISTENER: 'yes'
    ports:
      - "9092:9092"
      - "29092:29092"
    volumes:
      - kafka_data1:/bitnami/kafka
    networks:
      - kafka-network

  kafka-ui:
    image: provectuslabs/kafka-ui:latest
    container_name: kafka-ui
    environment:
      KAFKA_CLUSTERS_0_NAME: local
      KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS: kafka1:9092
      KAFKA_CLUSTERS_0_ZOOKEEPER: zookeeper:2181
    ports:
      - "8085:8080"
    depends_on:
      - kafka1
    networks:
      - kafka-network

networks:
  kafka-network:
    driver: bridge

volumes:
  kafka_data1:
    driver: local
```
### 3. Construcción del proyecto
   Compila y empaqueta la aplicación con Maven:

```
mvn clean install
```
###  Productor Kafka
Este microservicio actúa como productor de eventos en Kafka. Cuando el estado de un usuario cambia, se emite un evento al topic de Kafka configurado (user-status-topic).

Ejemplo de Payload del evento:

```json
{
"userId": "1234",
"status": "ACTIVE",
"timestamp": "2024-09-02T04:20:30Z"
}
```
Este payload será publicado en el topic user-status-topic para que otros servicios puedan suscribirse a los eventos y reaccionar en tiempo real.

### Endpoints
Este microservicio expone los siguientes endpoints:

POST /user-status: Recibe el cambio de estado de un usuario y publica un evento en Kafka.

Ejemplo de petición:

```bash
curl -X 'POST' \
  'http://localhost:8080/api/users/status' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "1",
  "name": "Martin",
  "active": false
}'
```

### Swagger
Disponible en http://localhost:8080/swagger-ui/index.html#/

### Monitoreo
Puedes monitorear los topics de Kafka utilizando Kafka UI, disponible en http://localhost:8085.