package com.asms.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.asms.entity")
@EnableJpaRepositories(basePackages = "com.asms.repository")
public class JpaConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
