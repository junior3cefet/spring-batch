package com.springbatch.skipexception.reader;

import com.springbatch.skipexception.dominio.Cliente;
import com.springbatch.skipexception.exception.NotFoundException;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

@Configuration
public class SkipExceptionReaderConfig {
    @Bean
    public ItemReader<Cliente> skipExceptionReader(@Qualifier("appDataSource") DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Cliente>()
                .name("skipExceptionReader")
                .dataSource(dataSource)
                .sql("select * from cliente")
                .rowMapper(customRowMapper())
                .build();
    }

    @Bean
    public RowMapper<Cliente> customRowMapper() {
        return (resultSet, i) -> {
            if (resultSet.getRow() == 11) {
                throw new NotFoundException(resultSet.getString("nome"));
            }
            Cliente cliente = new Cliente();
            cliente.setNome(resultSet.getString("nome"));
            cliente.setSobrenome(resultSet.getString("sobrenome"));
            cliente.setIdade(resultSet.getString("idade"));
            cliente.setEmail(resultSet.getString("email"));
            return cliente;
        };
    }

}
