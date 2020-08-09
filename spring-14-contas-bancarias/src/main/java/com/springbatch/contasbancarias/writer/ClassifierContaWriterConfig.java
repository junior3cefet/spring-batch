package com.springbatch.contasbancarias.writer;

import com.springbatch.contasbancarias.dominio.Conta;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.springbatch.contasbancarias.dominio.TipoConta.INVALIDO;

@Configuration
public class ClassifierContaWriterConfig {

    @Bean
    public ClassifierCompositeItemWriter<Conta> classifierCompositeItemWriter(
            @Qualifier("clienteInvalidoFlatFileItemWriter") FlatFileItemWriter<Conta> clienteInvalidoItemWriter,
            CompositeItemWriter<Conta> clienteValidoWriter
    ) {
        return new ClassifierCompositeItemWriterBuilder<Conta>()
                .classifier(classifier(clienteInvalidoItemWriter, clienteValidoWriter))
                .build();
    }

    private Classifier<Conta, ItemWriter<? super Conta>> classifier(FlatFileItemWriter<Conta> clienteInvalidoItemWriter,
                                                                    CompositeItemWriter<Conta> clienteValidoWriter) {
        return (Classifier<Conta, ItemWriter<? super Conta>>) conta -> {
            if (conta.getTipo() == INVALIDO) {
                return clienteInvalidoItemWriter;
            }
            return clienteValidoWriter;
        };
    }


}
