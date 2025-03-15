package com.pada.safe_sphere.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class WhatsappService {

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private final CounsellingServices counsellingServices;

    public void sendWhatsAppMessage(String toNumber, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("messaging_product", "whatsapp");
        body.put("to", toNumber);
        body.put("type", "text");

        Map<String, String> text = new HashMap<>();
        text.put("body", message);
        body.put("text", text);

        producerTemplate.sendBody("direct:sendWhatsAppMessage", body);

    }

    public void processIncomingMessage(String payload) {
        try {
            JsonNode jsonNode = objectMapper.readTree(payload);
            JsonNode messages = jsonNode.at("/entry/0/changes/0/value/messages");

            if (messages.isArray() && messages.size() > 0) {

                String receivedText = messages.get(0).at("/text/body").asText();
                String fromNumber = messages.get(0).at("/from").asText();
                log.debug(fromNumber + " sent the message: " + receivedText);

                this.sendWhatsAppMessage(fromNumber, counsellingServices.processChat(fromNumber, receivedText));
            }
        } catch (Exception e) {
            log.error("Error processing incoming payload: {} ", payload, e);
        }
    }
}
