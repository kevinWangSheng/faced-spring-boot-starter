package com.kevin.faced.config;

import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author wang
 * @create 2024-01-13-13:35
 */
@AutoConfiguration
@ConditionalOnClass(value = FacedService.class)
@EnableConfigurationProperties(value = FaceConfigurationProperties.class)
public class FacedServiceAutoConfiguration {
    @Resource
    private FaceConfigurationProperties faceConfigurationProperties;

    @Bean
    public FacedService facedService(){
        return new FacedService(faceConfigurationProperties.getUserRoll());
    }
}
