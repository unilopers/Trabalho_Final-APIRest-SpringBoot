package com.example.demo.config;

import com.example.demo.security.ApiKey;
import com.example.demo.security.ApiKeyRepository;
import com.example.demo.security.util.HashUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.UUID;

@Configuration
public class DataLoader {

    @Value("${security.api-key.salt}")
    private String salt;

    @Bean
    @Profile("!prod")
    public CommandLineRunner loadData(ApiKeyRepository apiKeyRepository) {
        return args -> {

            if(apiKeyRepository.count() == 0) {

                for (int i = 1; i <= 2; i++) {

                    String plainKey = "sk-" + UUID.randomUUID();

                    String hash = HashUtils.sha256Hex(plainKey + salt);

                    ApiKey apiKey = new ApiKey(hash, "Test API Key" + i);

                    apiKeyRepository.save(apiKey);

                    System.out.println("API Key " + i + ": " + plainKey);
                }
            } else {
                System.out.println("API Keys já existem no banco.");
            }
        };
    }
}
