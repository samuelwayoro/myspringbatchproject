package com.samydevup.myspringbatchproject.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class FirstItemReader implements ItemReader<Integer> {

    private static Logger logger = LoggerFactory.getLogger(FirstItemReader.class);
    int i = 0;


    List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    /**
     * Boucle sur la liste des integer pour retourner chaque entier à tour de rôle, en utilisant la variable "i"
     * comme indice de boucle
     *
     * @return
     * @throws Exception
     * @throws UnexpectedInputException
     * @throws ParseException
     * @throws NonTransientResourceException
     */
    @Override
    public Integer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        logger.info("🔁 itemReader en cours ... 🔁 ");
        Integer item;
        if (i < integerList.size()) {
            item = integerList.get(i);
            i++;
            return item;
        }
        return null;
    }
}
