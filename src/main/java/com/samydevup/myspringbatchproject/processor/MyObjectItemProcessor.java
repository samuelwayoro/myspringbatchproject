package com.samydevup.myspringbatchproject.processor;

import com.samydevup.myspringbatchproject.model.StudentCsv;
import com.samydevup.myspringbatchproject.model.StudentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyObjectItemProcessor implements ItemProcessor<StudentCsv, StudentDTO> {

    private final static Logger LOG = LoggerFactory.getLogger(MyObjectItemProcessor.class);

    @Override
    public StudentDTO process(StudentCsv studentCsv) throws Exception {
        LOG.info(" ↪  Processor en cours ....");
        return new StudentDTO(studentCsv.getId(), studentCsv.getFirstName(), studentCsv.getLastName(), studentCsv.getEmail());
    }
}
