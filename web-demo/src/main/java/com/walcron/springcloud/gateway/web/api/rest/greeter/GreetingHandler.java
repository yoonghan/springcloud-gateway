package com.walcron.springcloud.gateway.web.api.rest.greeter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@Component("GreetingHandler")
public class GreetingHandler {

  private int serverPort;

  @Autowired
  public GreetingHandler(Environment environment) {
    this.serverPort = environment.getProperty("server.port", Integer.class);
  }

  Logger log = LoggerFactory.getLogger(this.getClass());

  public Mono<ServerResponse> hello(ServerRequest request) {
    return responseHello(Optional.empty(), Optional.empty());
  }

  public Mono<ServerResponse> helloByLanguage(ServerRequest request) {
    String language = request.pathVariable("language");
    return responseHello(Optional.of(language), Optional.empty());
  }

  public Mono<ServerResponse> sayHello(ServerRequest request) {
    Mono<Greeter> greeter = request.bodyToMono(Greeter.class);
    return greeter
            .flatMap(response ->
              responseHello(Optional.empty(), Optional.of(response.getName()))
            )
            .doOnError(error -> {
              error.printStackTrace();
            })
            .onErrorReturn(
                    ServerResponse.badRequest().body(BodyInserters.fromValue("provide a name")).block()
            );
  }

  public Mono<ServerResponse> sayHelloByLanguage(ServerRequest request) {
    String language = request.pathVariable("language");
    Mono<Greeter> greeter = request.bodyToMono(Greeter.class);
    return greeter
            .flatMap(response ->
              responseHello(Optional.of(language), Optional.of(response.getName()))
            )
            .doOnError(error -> {
              error.printStackTrace();
            })
            .onErrorReturn(
              ServerResponse.badRequest().body(BodyInserters.fromValue("provide a name")).block()
            );
  }

  private Mono<ServerResponse> responseHello(Optional<String> language, Optional<String> name) {


    try {
      log.info("Trigger greetings on {}:{}",
              InetAddress.getLocalHost().getHostAddress(), serverPort);
    } catch (UnknownHostException e) {
      log.warn("Got triggered without host");
    }
    String ehlo = getReplies(language.orElse("Hello"));
    String greetings = String.format("%s, %s!", ehlo, name.orElse("There"));
    GreeterResponse greeterResponse = new GreeterResponse(greetings);

    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .bodyValue(greeterResponse);
            //.body(BodyInserters.fromValue(greetings));

  }

  private String getReplies(String language) {
    switch(language) {
      case "zh":
        return "Ni Hao";
      case "pt":
        return "Bom Dia";
      case "en":
        return "Hello";
      case "cv":
        return "чӗлхи";
      case "au":
        return "Hello Mate";
      default:
        return "Hi";
    }
  }
}
