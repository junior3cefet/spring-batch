package br.com.batch.poc.step;

import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class NumberProcessor {
    private static final String message = "The number %d is %s";

    public FunctionItemProcessor<Integer, String> processorNumbers() {
        return new FunctionItemProcessor<>(item -> item % 2 == 0 ? String.format(message, item, "EVEN")
                : String.format(message, item, "ODD"));
    }


}
