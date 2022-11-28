package com.example.tutorial2;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

//@Component
public class KafkaRoute extends EndpointRouteBuilder {

    // http://localhost:8080?name=aaa&password=123

    @Override
    public void configure() throws Exception {

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", "우태진");
        map.put("group", "연구소");

        from(timer("test-003").period("100"))
        .setBody().constant(map)
        .to(kafka("topic-001").lingerMs(5000).maxBlockMs(300))
        .log("보냈다. ${body}, Type: ${body.getClass}");

        from(kafka("topic-001"))
        .log("unmarshal 후. ${body}, Type: ${body.getClass}");
    }

}
