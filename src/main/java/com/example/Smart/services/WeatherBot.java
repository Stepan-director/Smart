package com.example.Smart.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class WeatherBot extends TelegramLongPollingBot {

    private final SubscriberService subscriberService;
    private final String botToken; // храним токен в поле

    private final String botUsername;

    public WeatherBot(@Value("${telegram.bot.token}") String botToken,
                      @Value("${telegram.bot.username}") String botUsername,
                      SubscriberService subscriberService) {
        super(botToken); // передаем в родительский класс
        this.botToken = botToken;
        this.botUsername = botUsername;
        this.subscriberService = subscriberService;
        System.out.println("Bot initialized for: " + botUsername);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            System.out.println("Received message: " + messageText + " from chat: " + chatId);

            try {
                if (messageText.equals("/start")) {
                    subscriberService.addSubscriber(chatId);
                    sendMessage(chatId.toString(), "✅ Вы подписались на погодные рекомендации!");
                    System.out.println("Sent welcome message to: " + chatId);
                } else if (messageText.equals("/stop")) {
                    subscriberService.removeSubscriber(chatId);
                    sendMessage(chatId.toString(), "❌ Вы отписались от погодных рекомендаций.");
                }
            } catch (TelegramApiException e) {
                System.err.println("Failed to send message to " + chatId + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String chatId, String text) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        execute(message);
    }
}
