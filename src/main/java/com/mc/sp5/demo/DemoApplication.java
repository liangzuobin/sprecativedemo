package com.mc.sp5.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.server.HttpServer;

@SpringBootApplication
public class DemoApplication {

        public static void main(String[] args) throws Exception {
		RouterFunction<ServerResponse> router = getRouter();
		HttpHandler handler = RouterFunctions.toHttpHandler(router);
		HttpServer.create("localhost", 8080).newHandler(new ReactorHttpHandlerAdapter(handler)).block();
		Thread.currentThread().join();
        }

        private static RouterFunction<ServerResponse> getRouter() {
                HandlerFunction<ServerResponse> index = request -> ServerResponse.ok()
                                .body(Mono.just("hello"), String.class);
                return RouterFunctions.route(RequestPredicates.GET("/"), index);
        }
}
