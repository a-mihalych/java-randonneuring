package ru.mihalych.randonneuring.bot.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "token")
@PropertySource("classpath:config.properties")
@PropertySource("classpath:application.properties")
public class BotConfig {

    @Value("${bot.name}")
    String botName;
    @Value("${bot.token}")
    String token;
    @Value("${bot.chatId}")
    String chatId;
}