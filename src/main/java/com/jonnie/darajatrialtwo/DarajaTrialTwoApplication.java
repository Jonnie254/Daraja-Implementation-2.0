package com.jonnie.darajatrialtwo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonnie.darajatrialtwo.mpesa.responses.AcknowledgeResponse;
import okhttp3.OkHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DarajaTrialTwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DarajaTrialTwoApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

    @Bean
    public AcknowledgeResponse acknowledgeResponse() {
        return new AcknowledgeResponse(0, "Accepted");
    }

}
