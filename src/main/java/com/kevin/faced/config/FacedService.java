package com.kevin.faced.config;

/**
 * @author wang
 * @create 2024-01-13-13:37
 */
public class FacedService {

    private String userRoll;

    public FacedService(String userRoll) {
        this.userRoll = userRoll;
    }

    public String[] split(String separator){
        return userRoll == null ?null : userRoll.split(separator);
    }
}
