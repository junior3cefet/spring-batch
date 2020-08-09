package com.springbatch.contasbancarias.writer;

import com.springbatch.contasbancarias.dominio.Conta;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Configuration
public class ImpressaoContaWriterConfig {

    @StepScope
    @Bean
    public JdbcBatchItemWriter<Conta> impressaoContaWriter(
            @Qualifier("appDataSource") DataSource dataSource
    ) {
        return new JdbcBatchItemWriterBuilder<Conta>()
                .dataSource(dataSource)
                .sql("INSERT INTO conta (tipo, limite, cliente_id) values (?, ?, ?)")
                .itemPreparedStatementSetter(getItemPreparedStatement())
                .build();
    }

    private ItemPreparedStatementSetter<Conta> getItemPreparedStatement() {
        return (conta, ps) -> {
            ps.setString(1, conta.getTipo().name());
            ps.setDouble(2, conta.getLimite());
            ps.setString(3, conta.getClienteId());
        };
    }

}
