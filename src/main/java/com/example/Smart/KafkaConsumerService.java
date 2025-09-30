package com.example.Smart;

import com.example.Smart.services.SubscriberService;
import com.example.Smart.services.WeatherBot;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class KafkaConsumerService {

    private final WeatherBot weatherBot;
    private final KafkaProducerService kafkaProducerService;
    private final SubscriberService subscriberService;

    public KafkaConsumerService(WeatherBot weatherBot, KafkaProducerService kafkaProducerService, SubscriberService subscriberService) {
        this.weatherBot = weatherBot;
        this.kafkaProducerService = kafkaProducerService;
        this.subscriberService = subscriberService;
    }

    @KafkaListener(topics = "my_topic", groupId = "group_id")
    public void consume(String message) {
        System.out.println("Message received from weather service: " + message);


        int temperature = parseTemperature(message);

        // Формируем рекомендацию и отправляем подписчикам
        sendWeatherRecommendation(temperature); // тут проблема
    }

    private int parseTemperature(String message) {
        try {
            // Предполагаем, что сообщение содержит температуру
            // Например: "temperature:17" или просто "17"
            return Integer.parseInt(message.replaceAll("[^0-9-]", ""));
        } catch (NumberFormatException e) {
            System.err.println("Error parsing temperature from message: " + message);
            return 20; // значение по умолчанию
        }
    }

    private void sendWeatherRecommendation(int temperature) {
        String recommendation = getRecommendation(temperature);

        // отправляем всем подписчикам
        subscriberService.getAllSubscribers().forEach(chatId -> {
            sendToTelegram(chatId, recommendation);
        });

        System.out.println("Weather recommendation sent to " +
                subscriberService.getAllSubscribers().size() + " subscribers");
    }

    private String getRecommendation(int temperature) {
        if (temperature < 16) {
            return "🌡 Температура: " + temperature + "°C\n❄️ Оденьтесь потеплее!";
        } else {
            return "🌡 Температура: " + temperature + "°C\n☀️ Можно и в футболке погонять!";
        }
    }

    private void sendToTelegram(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        try {
            weatherBot.execute(message);
        } catch (TelegramApiException e) {
            System.err.println("Error sending to Telegram chat " + chatId + ": " + e.getMessage());
        }
    }


}
