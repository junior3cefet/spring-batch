package br.com.batch.poc.step.config;

import br.com.batch.poc.step.NumberProcessor;
import br.com.batch.poc.step.NumberReader;
import br.com.batch.poc.step.NumberWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TypeOfNumberStep {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private NumberReader numberReader;

    @Autowired
    private NumberProcessor numberProcessor;

    @Autowired
    private NumberWriter numberWriter;

    public Step stepBuilderFactory() {
        return stepBuilderFactory
                .get("stepBuilderFactory")
                .<Integer, String>chunk(10)
                .reader(numberReader.numberIterator())
                .processor(numberProcessor.processorNumbers())
                .writer(numberWriter::write)
                .build();
    }



}
