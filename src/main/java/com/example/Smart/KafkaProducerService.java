package com.example.Smart;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final String TOPIC = "my_topic";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public int getTemp(){
        return 17;
    }

    public void sendMessage() {
        String message = String.valueOf(getTemp());
        kafkaTemplate.send(TOPIC, message);
        System.out.println("Message sent: " + message);
    }
}