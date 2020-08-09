package com.springbatch.contasbancarias.processor;

import com.springbatch.contasbancarias.dominio.Cliente;
import com.springbatch.contasbancarias.dominio.Conta;
import org.springframework.batch.item.ItemProcessor;

import static com.springbatch.contasbancarias.dominio.TipoConta.INVALIDO;

public class ContaInvalidaItemProcessor implements ItemProcessor<Cliente, Conta> {

    @Override
    public Conta process(Cliente cliente) throws Exception {
        Conta conta = new Conta();
        conta.setTipo(INVALIDO);
        conta.setClienteId(cliente.getEmail());
        return conta;
    }
}
