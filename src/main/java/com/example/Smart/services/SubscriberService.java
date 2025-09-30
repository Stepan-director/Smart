package com.example.Smart.services;

import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class SubscriberService {
    private final Set<Long> subscribers = new HashSet<>();

    public void addSubscriber(Long chatId) {
        subscribers.add(chatId);
        System.out.println("New subscriber: " + chatId);
    }

    public void removeSubscriber(Long chatId) {
        subscribers.remove(chatId);
        System.out.println("Subscriber removed: " + chatId);
    }

    public Set<Long> getAllSubscribers() {
        return new HashSet<>(subscribers);
    }
}