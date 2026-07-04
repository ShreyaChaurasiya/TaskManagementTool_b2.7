package com.TaskManagementTool_b72.Cloud;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class ClodinaryConfig {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String key;

    @Value("${cloudinary.api-secret}")
    private String secret;

    @Bean
    public Cloudinary cloudinary() {

        Map<String, Object> config = new HashMap<>();

        config.put("cloud_name", cloudName);
        config.put("api_key", key);
        config.put("api_secret", secret);

        return new Cloudinary(config);
    }
}