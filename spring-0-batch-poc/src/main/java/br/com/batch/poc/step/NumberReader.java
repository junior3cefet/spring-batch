package br.com.batch.poc.step;

import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NumberReader {

    public IteratorItemReader<Integer> numberIterator() {
        var listOfNumbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        return new IteratorItemReader<>(listOfNumbers);
    }

}
