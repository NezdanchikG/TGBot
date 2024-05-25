package com.movierec;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.movierec.config.AppConfig;

public class MovieRecommendationBotApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    }
}