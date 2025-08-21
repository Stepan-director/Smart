//package com.example.Smart.services;
//
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.time.LocalDateTime;
//
//@Service
//public class WeatherService {
//    private final RestTemplate restTemplate;
//
//    public WeatherService(RestTemplateBuilder restTemplateBuilder) {
//        this.restTemplate = restTemplateBuilder.build();
//    }
//
//    public double getCurrentTemperature() {
//        try {
//            String url = "https://api.openweathermap.org/data/2.5/weather?q=Moscow&appid=&units=metric";
//            WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);
//            return response.getMain().getTemp();
//        } catch (Exception e) {
//            // api не доступно,   значение по умолчанию
//            return 20.0;
//        }
//    }
//
//    private static class WeatherResponse {
//        private Main main;
//
//        public Main getMain() {
//            return main;
//        }
//
//        public void setMain(Main main) {
//            this.main = main;
//        }
//    }
//
//    private static class Main {
//        private double temp;
//
//        public double getTemp() {
//            return temp;
//        }
//
//        public void setTemp(double temp) {
//            this.temp = temp;
//        }
//    }
//
//
//}