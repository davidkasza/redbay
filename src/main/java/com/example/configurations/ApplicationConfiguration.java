package com.example.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    @Value("${application.domain}")
    private String domain;

    public String getDomain() {
        return domain;
    }
}
