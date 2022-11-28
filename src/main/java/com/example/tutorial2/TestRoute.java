package com.example.tutorial2;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TestRoute extends EndpointRouteBuilder {

    // http://localhost:8080?name=aaa&password=123

//    @Value("${custom.root-dir}")
//    private String rootDir;

    @Autowired
    private CustomProperties customProperties;

    @Override
    public void configure() throws Exception {
        from(timer("영화예매").period(1000))
                .setHeader("Number").simple("${random(1,100)}")
                .setBody(constant(Map.of("userId", "taejinwoo", "screenId", "Screen12345", "seatNumber", "J09")))
                .log("Random Number: ${body}")
                .to(direct("사용자인증"))
                .to(direct("좌석확인"))
                .to(direct("금액결제"))
                .log("영화예매 완료");

        from(direct("사용자인증"))
                .choice()
                .when(simple("${header.Number} contains 1"))
                .throwException(new RuntimeException("[DNA-00381] 인증되지 않은 사용자입니다."))
                .otherwise()
                .log("사용자인증 완료");


        from(direct("좌석확인"))
                .choice()
                .when(simple("${header.Number} < 10"))
                .throwException(new RuntimeException("[DNA-00382] 좌석 번호가 유효하지 않습니다."))
                .otherwise()
                .log("좌석확인 완료");

        from(direct("금액결제"))
                .choice()
                .when(simple("${header.Number} > 90"))
                .throwException(new RuntimeException("[DNA-00383] 잔고가 부족합니다."))
                .otherwise()
                .log("금액결제 완료");
    }

}
