package com.st.dtit.cam.authentication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;

@Configuration
public class ApplicationConfig {

      @Value("${spring.application.gateway}")
      private String apiBaseUrl;

      @Value("${database.connection.source-directory}")
      private String dbConnectionSource;

      @PostConstruct
      public void setConnectionProperty(){
          System.setProperty("database.connection.source-directory", dbConnectionSource);
      }

      @Bean
      public WebClient getWebClient()
      {
            final int size = 16 * 1024 * 1024;
            final ExchangeStrategies strategies = ExchangeStrategies.builder()
                    .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                    .build();

            return WebClient.builder()
                    .baseUrl(apiBaseUrl)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .exchangeStrategies(strategies)
                    .build();
      }
}
