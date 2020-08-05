package com.springbatch.arquivolargurafixa.reader;

import com.springbatch.arquivolargurafixa.dominio.Cliente;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class LeituraArquivoLarguraFixaReaderConfig {

    private static Range NOME_RANGE = new Range(1, 10);
    private static Range SOBRENOME_RANGE = new Range(11, 20);
    private static Range IDADE_RANGE = new Range(21, 23);
    private static Range EMAIL_RANGE = new Range(24, 43);
    private static String[] NAMES_BINDING = new String[]{"nome", "sobrenome", "idade", "email"};

    @Bean
    public FlatFileItemReader<Cliente> leituraArquivoLarguraFixaReader() {
        return new FlatFileItemReaderBuilder<Cliente>()
                .name("leituraArquivoLarguraFixaReader")
                .resource(new FileSystemResource("/opt/config/files/clientes.txt"))
                .fixedLength()
                .columns(NOME_RANGE, SOBRENOME_RANGE, IDADE_RANGE, EMAIL_RANGE)
                .names(NAMES_BINDING)
                .addComment("Feito")
                .targetType(Cliente.class)
                .build();
    }

}
