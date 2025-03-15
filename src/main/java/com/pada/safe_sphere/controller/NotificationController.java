package com.pada.safe_sphere.controller;

import com.pada.safe_sphere.service.WhatsappService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final WhatsappService whatsappService;

    @PostMapping("/api/whatsapp/send")
    public String sendWhatsAppMessage(@RequestParam String to, @RequestParam String message) {
        log.info("Sending WhatsApp Message to {}: Message: {}", to, message);
        whatsappService.sendWhatsAppMessage(to, message);
        return "Message sent";
    }

    @PostMapping("/webhook")
    public void receiveMessage(@RequestBody String payload) {

        log.info("Webhook Message: {}", payload);
        whatsappService.processIncomingMessage(payload);
    }
}
