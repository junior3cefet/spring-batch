package com.springbatch.contasbancarias.writer;

import com.springbatch.contasbancarias.dominio.Conta;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class ClienteInvalidaItemWriter {

    @StepScope
    @Bean
    public FlatFileItemWriter<Conta> clienteInvalidoFlatFileItemWriter(
            @Value("#{jobParameters['arquivoContaFalha']}") Resource arquivoContaFalha
    ) {
        return new FlatFileItemWriterBuilder<Conta>()
                .name("contaInvalidaFlatFileItemWriter")
                .resource(arquivoContaFalha)
                .delimited()
                .names("clienteId")
                .build();
    }

}
