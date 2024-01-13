package com.kevin.faced.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;


@ConfigurationProperties(prefix = "com.kevin.faced")
public class FaceConfigurationProperties {

    private String userRoll;

    public String getUserRoll() {
        return userRoll;
    }

    public void setUserRoll(String userRoll) {
        System.out.println("hello");
        this.userRoll = userRoll;
    }

    public String[] split(String separator){
        return userRoll == null ?null : userRoll.split(separator);
    }
}
