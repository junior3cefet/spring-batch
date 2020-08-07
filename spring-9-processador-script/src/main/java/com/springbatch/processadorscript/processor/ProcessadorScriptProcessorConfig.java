package com.springbatch.processadorscript.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.ScriptItemProcessor;
import org.springframework.batch.item.support.builder.ScriptItemProcessorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.processadorscript.dominio.Cliente;

@Configuration
public class ProcessadorScriptProcessorConfig {
	@Bean
	public ItemProcessor<Cliente, Cliente> processadorScriptProcessor() {
		return new ScriptItemProcessorBuilder<Cliente, Cliente>()
				.language("nashorn")
				.scriptSource(
						"var email = item.getEmail();" +
						"var fileExists = `ls | grep study/${email}.txt`;" +
						"if (!fileExists) item; else null;"
				)
				.build();
	}

}
