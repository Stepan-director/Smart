package com.example.Smart.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void checkAndNotifyWeather() {
        int temperature = 17;
        String message = "Температура: " + temperature + "°C";

        // ПРОСТОЙ ТЕСТ - отправляем сообщение
        try {
            kafkaTemplate.send("weather-topic", message);
            System.out.println("✅ Сообщение отправлено в Kafka: " + message);
        } catch (Exception e) {
            System.out.println("❌ Ошибка отправки в Kafka: " + e.getMessage());
        }
    }

    public int getCurrentTemperature() {
        return 17;
    }
}