package com.smps.sip.batch.job1.handler;

import com.smps.sip.batch.job1.model.Person;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by Beom on 2017-10-25.
 */
public class CustomItemReader extends FlatFileItemReader<Person> {

    {
        setResource(new ClassPathResource("sample-data.csv"));
        setLineMapper(new DefaultLineMapper<Person>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"firstName", "lastName"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                setTargetType(Person.class);
            }});
        }});
    }

    @Override
    public Person read() throws Exception {
        Person p = super.read();
        System.out.println("read... " + p);
        return p;
    }

}
