package br.com.batch.poc.job.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class PropertiesConfiguration {

    @Bean
    public PropertySourcesPlaceholderConfigurer getApplicationProperties() {
        var configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new FileSystemResource("/opt/config/application.properties"));
        return configurer;
    }

}
