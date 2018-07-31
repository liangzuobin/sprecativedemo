package com.mc.sp5.demo;

import com.mc.sp5.demo.DemoApplication.Model;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

        private static final WebTestClient client =
                        WebTestClient.bindToRouterFunction(DemoApplication.getRouter()).build();

        @Test
        public void testIndex() {
                client.get().uri("/").exchange().expectStatus().is2xxSuccessful()
                                .expectBody(String.class).isEqualTo("hello");
        }

        @Test
        public void testJson() {
                client.get().uri("/json").exchange().expectStatus().is2xxSuccessful().expectHeader()
                                .contentType(MediaType.APPLICATION_JSON).expectBody(Model.class)
                                .isEqualTo(new Model("Josyee", 20));
        }

}
