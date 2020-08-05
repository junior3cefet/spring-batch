package br.com.batch.poc.job.config;

import br.com.batch.poc.step.config.TypeOfNumberStep;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class JobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private TypeOfNumberStep typeOfNumberStep;

    @Bean
    public Job typeOfNumberJob() {
        return jobBuilderFactory
                .get("typeOfNumberJob")
                .start(typeOfNumberStep.stepBuilderFactory())
                .incrementer(new RunIdIncrementer())
                .build();
    }


}
