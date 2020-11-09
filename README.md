# Spring Cloud Gateway
Testing spring cloud with integration of, without Netflix dependencies
- Spring Gateway
- Spring Load-balancer
- Spring Webflux (client)

## Gateway configuration
Gateway configuration takes these:

| API Url | Method | Description | Goal |
| ------- | ------ | ----------- | ---- |
| /api/v1/{lang} | GET | Greetings response | To test gateway load-balancing and data failure. Include chain filters. |
| /api/v1/{lang} | POST | Greetings response, pass parameter "name" | To test gateway load-balancing and data failure. Include chain filters. |
| /api/v2/{lang} | GET | Greetings response | Enabling routing via codes. |
| /upload | POST | File uploading | File limitation control |
| /socket-io/event-emitter | Websocket | Test websocket | Test websocket, use this site "https://www.websocket.org/echo.html" and enter ws://{hostname}:{port} |
| /images/* | GET | Routing images to another location | Routing image, redirect |

*NOTES:* Filter enables for ip address

*NOTES:* Load balancing is using health-check configuration and it's checked every 5seconds. Cached need to be disabled to allow Load balancing.

*NOTES:* To change path for health-check modify spring.cloud.loadbalancer.health-check.path.default (or remove it)

## Test proved
1. Load-balancing via health-check, if "spring.cloud.loadbalancer.configurations: health-check" and are based on 5 seconds interval with program path /status as check. If reverting to round-robing, remove "spring.cloud.loadbalancer.configurations" and "spring.cloud.loadbalancer.cache" in application.yml. No Zuul or Ribbon is used in this setting.
2. Simple Cloud Discovery instead of using Eureka.
3. Web Filtering and ordering in Gateway.
4. Traffic-control based on IP headers to deny access.
5. Websocket and load-balancing on websocket.
6. Resource routing.
7. File upload and controlling file limitation access.
8. Enabling CORS.
9. Disable Dynamic resource control by adding spring.cloud.config.enabled = false in bootstrap.yml


## Webclient
Web client is using Webflux and are Docker Compose enabled.
1) To run manually, do ./gradlew bootRun
2) To run via docker do
 
 ```
./build-docker.sh
docker run -p 8081:8080 demo-gw-web:1.0
docker ps
```

3) If to run via docker -compose, do

```
#docker-compose build
docker-compose up -d
#docker-compose down
```

*NOTES:* Used actuator, where the url http://{hostname}:{port}/actuator/health returns the server status.

## Load Test and Test Suite

1. Download SOAP UI.
2. Import project from gw-demo\soapui-test\SOAPProject.xml

## Getting Started or Running Program
1. Change directory to /gw-demo/src/main/resources and open application.yml, modify 'spring.cloud.discovery.client.simple.instance.greeting-service(s).uri', and change "raspberrypi" to "localhost" or "127.0.0.1".

2. Start gateway by executing:

```
cd gw-demo
./gradlew bootRun
```

3. Try and do a curl GET on http://localhost:9000/api/v1/greetings/pt. *It should FAIL*

4. Start webservices by either

```
## Method 1
cd web-demo
./gradlew bootRun
#change web-demo/src/main/resources/application.yml, and change PORT to 8081.
./gradlew bootRun

## Method 2
Run docker, see method above.

## Method 3
Run docker-compose
```
