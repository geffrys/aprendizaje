package com.geff.str_producer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geff.str_producer.services.StringProducerService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;


@Slf4j
@RestController
@RequestMapping("/api/v1/producer")
public class StringProducerController {

    @Autowired
    private StringProducerService stringProducerService;

    @PostMapping()    
    public ResponseEntity<?> sendMessage(@RequestBody String message) {
        try {
            log.info("Received message to send: {}", message);
            if (message == null || message.isEmpty()) {
                log.error("Message cannot be null or empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message cannot be null or empty");
            }
            stringProducerService.sendMessage(message);
            log.info("Message sent successfully: {}", message);
            return ResponseEntity.ok("Message sent successfully");
        } catch (Exception e) {
            log.error("Error sending message: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error sending message: " + e.getMessage());
        }

    }
}
