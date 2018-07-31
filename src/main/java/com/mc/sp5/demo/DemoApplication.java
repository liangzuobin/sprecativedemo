package com.mc.sp5.demo;

import java.beans.ConstructorProperties;
import java.util.Objects;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.ipc.netty.http.server.HttpServer;

@SpringBootApplication
public class DemoApplication {

        static class Model {
                private String name;
                private int age;

                @ConstructorProperties({"name", "age"})
                Model(String name, int age) {
                        this.name = name;
                        this.age = age;
                }

                public String getName() {
                        return this.name;
                }

                public void setName(String name) {
                        this.name = name;
                }

                public int getAge() {
                        return this.age;
                }

                public void setAge(int age) {
                        this.age = age;
                }

                @Override
                public boolean equals(Object other) {
                        Model that;
                        return this == other || (
					other instanceof Model
					&& Objects.equals(this.getName(), (that = (Model) other).getName())
                                        && this.getAge() == that.getAge());
                }
        }

        public static void main(String[] args) throws Exception {
                RouterFunction<ServerResponse> router = getRouter();
                HttpHandler handler = RouterFunctions.toHttpHandler(router);
                HttpServer.create("localhost", 8080)
                                .newHandler(new ReactorHttpHandlerAdapter(handler)).block();
                Thread.currentThread().join();
        }

        static RouterFunction<ServerResponse> getRouter() {
                HandlerFunction<ServerResponse> index = request -> ServerResponse.ok()
                                .body(BodyInserters.fromObject("hello"));
                HandlerFunction<ServerResponse> json = request -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromObject(new Model("Josyee", 20)));
                return RouterFunctions.route(RequestPredicates.GET("/"), index)
                                .andRoute(RequestPredicates.GET("/json"), json);
        }
}
