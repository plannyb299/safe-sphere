package com.pada.safe_sphere.config;

import jakarta.annotation.PostConstruct;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WhatsappConfig {

    @Autowired
    private CamelContext camelContext;

    @Value("${whatsapp.api_url}")
    private String apiUrl;

    @Value("${whatsapp.access_token}")
    private String apiToken;

    @PostConstruct
    public void init() throws Exception {
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
                jacksonDataFormat.setPrettyPrint(true);

                from("direct:sendWhatsAppMessage")
                        .setHeader("Authorization", constant("Bearer " + apiToken))
                        .setHeader("Content-Type", constant("application/json"))
                        .marshal(jacksonDataFormat)
                        .process(exchange -> {
                            log.debug("Sending JSON: {}", exchange.getIn().getBody(String.class));
                        }).to(apiUrl).process(exchange -> {
                            log.debug("Response: {}", exchange.getIn().getBody(String.class));
                        });
            }
        });
    }
}
