package com.example.tutorial2;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.TemplatedRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyRouteTemplates extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // create a route template with the given name
        routeTemplate("myTemplate")
                // here we define the required input parameters (can have default values)
                .templateParameter("name")
                .templateParameter("greeting")
                .templateParameter("myPeriod", "3s")
                // here comes the route in the template
                // notice how we use {{name}} to refer to the template parameters
                // we can also use {{propertyName}} to refer to property placeholders
                .from("timer:{{name}}?period={{myPeriod}}")
                .setBody(simple("{{greeting}} ${body}"))
                .log("${body}");

        templatedRoute("myTemplate")
                .parameter("name", "one")
                .parameter("greeting", "Hello");

        templatedRoute("myTemplate")
                .parameter("name", "two")
                .parameter("greeting", "Bonjour")
                .parameter("myPeriod", "5s");
    }
}