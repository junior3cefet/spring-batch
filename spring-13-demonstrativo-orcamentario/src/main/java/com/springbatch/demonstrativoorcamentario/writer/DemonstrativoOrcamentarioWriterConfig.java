package com.springbatch.demonstrativoorcamentario.writer;

import com.springbatch.demonstrativoorcamentario.dominio.GrupoLancamento;
import com.springbatch.demonstrativoorcamentario.dominio.Lancamento;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.batch.item.file.ResourceSuffixCreator;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class DemonstrativoOrcamentarioWriterConfig {

    @Primary
    @StepScope
    @Bean
    public MultiResourceItemWriter<GrupoLancamento> grupoLancamentoMultiResourceItemWriter(
            @Value("#{jobParameters['demonstrativoOrcamentarios']}") Resource demonstrativoOrcamentarios,
            FlatFileItemWriter demonstrativoFinanceiroItemWriter
    ) {
        return new MultiResourceItemWriterBuilder<GrupoLancamento>()
                .name("grupoLancamentoMultiResourceItemWriter")
                .resource(demonstrativoOrcamentarios)
                .delegate(demonstrativoFinanceiroItemWriter)
                .resourceSuffixCreator(getSuffix())
                .itemCountLimitPerResource(1)
                .build();


    }

    private ResourceSuffixCreator getSuffix() {
        return index -> index + ".txt";
    }

    @StepScope
    @Bean
    public FlatFileItemWriter<GrupoLancamento> demonstrativoFinanceiroItemWriter(
            @Value("#{jobParameters['arquivoClientesSaida']}") Resource arquivoClientesSaida,
            DemonstrativoFooterCallback demonstrativoFooterCallback
    ) {
        return new FlatFileItemWriterBuilder<GrupoLancamento>()
                .name("demonstrativoFinanceiroItemWriter")
                .resource(arquivoClientesSaida)
                .lineAggregator(lineAggregator())
                .headerCallback(demonstrativoHeaderCallback())
                .footerCallback(demonstrativoFooterCallback)
                .build();
    }

    private FlatFileHeaderCallback demonstrativoHeaderCallback() {
        return writer -> {
            writer.append("\n");
            writer.append(String.format("SISTEMA INTEGRADO: XPTO \t\t\t\t DATA: %s", new SimpleDateFormat("dd/MM/yyyy").format(new Date())) + "\n");
            writer.append(String.format("MÓDULO: ORÇAMENTO \t\t\t\t\t\t HORA: %s", new SimpleDateFormat("HH:MM").format(new Date())) + "\n");
            writer.append(String.format("\t\t\tDEMONSTRATIVO ORCAMENTARIO\n"));
            writer.append(String.format("----------------------------------------------------------------------------\n"));
            writer.append(String.format("CODIGO NOME VALOR\n"));
            writer.append(String.format("\t Data Descricao Valor\n"));
            writer.append(String.format("----------------------------------------------------------------------------\n"));
        };
    }

    private LineAggregator<GrupoLancamento> lineAggregator() {
        return grupo -> {
            String formatGrupoLancamento = String.format("[%d] %s - %s\n", grupo.getCodigoNaturezaDespesa(),
                    grupo.getDescricaoNaturezaDespesa(),
                    NumberFormat.getCurrencyInstance().format(grupo.getTotal()));
            StringBuilder stringBuilder = new StringBuilder();
            for (Lancamento lancamento : grupo.getLancamentos()) {
                stringBuilder.append(String.format("\t [%s] %s - %s\n", new SimpleDateFormat("dd/MM/yyyy").format(lancamento.getData()), lancamento.getDescricao(),
                        NumberFormat.getCurrencyInstance().format(lancamento.getValor())));
            }
            String formatLancamento = stringBuilder.toString();
            return formatGrupoLancamento + formatLancamento;
        };
    }

}
