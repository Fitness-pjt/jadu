package com.ssafy.ssafit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {
    
    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(
            "AKIAX5ZI6BHGHBQABL5I", 
            "I/8JHfIg02DDWBZ4N2nPRAmWrsKj5RKjgcFtcBV3"
        );
        
        return S3Client.builder()
            .region(Region.AP_NORTHEAST_2) // 서울 리전
            .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
            .build();
    }
}