package com.example.tutorial2;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.http.common.HttpMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class RestRoute extends EndpointRouteBuilder {

    // http://localhost:8080?name=aaa&password=123

//    @Value("${custom.root-dir}")
//    private String rootDir;

    @Autowired
    private CustomProperties customProperties;

    @Override
    public void configure() throws Exception {

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", "Mike");
        map.put("group", "vtw");

        from(jetty("http://localhost:9998/tutorial/svc1").httpMethodRestrict("GET"))
        .log("${body}")
        .process(exchange -> {
            HttpMessage message = (HttpMessage) exchange.getMessage();
            Map<String, String[]> parameterMap = message.getRequest().getParameterMap();
            exchange.getMessage().setBody(parameterMap);
        })
        .marshal().json();




        rest("tutorial")
            .get("svc1").to(direct("svc1").getUri())
            .get("svc2").to(direct("svc2").getUri());

        from(direct("svc1"))
        .setBody().constant(Map.of("id", "11111", "name", "Hong"));

        from(direct("svc2"))
        .setBody().constant(Map.of("id", "2222", "name", "Kim"));




        from("rest:post:/tutorial/svc3")
        .unmarshal().json()
        .log("${body}")
        .process(exchange -> {
            Map body = exchange.getMessage().getBody(Map.class);
            body.put("address", "seoul");
        })
        .marshal().json();


    }

}
