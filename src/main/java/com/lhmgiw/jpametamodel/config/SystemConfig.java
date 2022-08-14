package com.lhmgiw.jpametamodel.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
