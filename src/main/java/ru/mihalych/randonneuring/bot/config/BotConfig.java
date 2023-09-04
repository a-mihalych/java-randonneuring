package ru.mihalych.randonneuring.bot.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Getter
@ToString
@EqualsAndHashCode(of = "token")
@PropertySource("classpath:config.properties")
@PropertySource("classpath:application.properties")
public class BotConfig {

    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String token;
    @Value("${bot.chatId}")
    private String chatId;
    @Value("${bot.chatIdAdmin}")
    private String chatIdAdmin;
}