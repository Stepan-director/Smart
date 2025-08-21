package com.example.Smart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableScheduling
public class SmartApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:3000")
						.allowedMethods("GET", "POST", "PUT", "DELETE");
			}
		};
	}
}




//то есть
//есть users сервер - для пользователей типо что они забронировали со скольки и до скольки
//будет погодный сервис, он берёт информацию о погоде отправляет в кафка
//через кафку персональный ассистент отправляет сообщение для пользователя, который есть в users сервис , то есть можно сделать так
//
//weather services-> kafka(сообщение о погоде) -> personal assiastant services-> kafka(сообщение о том что надо одеться потеплее(допустим) -> users services