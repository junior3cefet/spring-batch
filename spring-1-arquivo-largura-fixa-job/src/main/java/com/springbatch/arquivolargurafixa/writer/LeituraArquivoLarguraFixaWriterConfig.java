package com.springbatch.arquivolargurafixa.writer;

import com.springbatch.arquivolargurafixa.dominio.Cliente;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LeituraArquivoLarguraFixaWriterConfig {
    @Bean
    public ItemWriter<Cliente> leituraArquivoLarguraFixaWriter() {
		return items -> items.forEach(System.out::println);

//        return items -> {
//            items.forEach(item -> {
//                if (item.getNome().equals("Maria")) {
//                    throw new RuntimeException("Deu merda");
//                } else {
//                    System.out.println(item);
//                }
//            });
//        };
    }
}
