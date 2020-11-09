# Spring Cloud Gateway
Testing spring cloud with integration of, without Netflix dependencies
- Gateway
- Load-balancer
- Webflux (client)

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
