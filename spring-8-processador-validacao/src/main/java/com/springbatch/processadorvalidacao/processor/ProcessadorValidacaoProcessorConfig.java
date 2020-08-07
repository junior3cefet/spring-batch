package com.springbatch.processadorvalidacao.processor;

import com.springbatch.processadorvalidacao.dominio.Cliente;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ProcessadorValidacaoProcessorConfig {

    public Set<String> emails = new HashSet<>();

    @Bean
    public ItemProcessor<Cliente, Cliente> procesadorValidacaoProcessor() throws Exception {
        return new CompositeItemProcessorBuilder<Cliente, Cliente>()
                .delegates(getBeanValidationItemProcessor(), getValidationItemProcessor())
                .build();
    }

    private BeanValidatingItemProcessor getBeanValidationItemProcessor() throws Exception {
        BeanValidatingItemProcessor<Cliente> beanValidatingItemProcessor = new BeanValidatingItemProcessor<>();
        beanValidatingItemProcessor.setFilter(true);
        beanValidatingItemProcessor.afterPropertiesSet();
        return beanValidatingItemProcessor;
    }

    private ValidatingItemProcessor getValidationItemProcessor() {
        ValidatingItemProcessor<Cliente> validatingItemProcessor = new ValidatingItemProcessor<>();
        validatingItemProcessor.setFilter(true);
        validatingItemProcessor.setValidator(customValidator());
        return validatingItemProcessor;
    }

    private Validator<Cliente> customValidator() {
        return new Validator<Cliente>() {
            @Override
            public void validate(Cliente cliente) throws ValidationException {
                if (emails.contains(cliente.getEmail())) {
                    throw new ValidationException(String.format("O cliente com o email %s já foi encontrado na lista", cliente.getEmail()));
                }
                emails.add(cliente.getEmail());

            }

        };

    }

}

