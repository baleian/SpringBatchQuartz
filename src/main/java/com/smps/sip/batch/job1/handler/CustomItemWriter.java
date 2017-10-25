package com.smps.sip.batch.job1.handler;

import com.smps.sip.batch.job1.model.Person;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Beom on 2017-10-25.
 */
public class CustomItemWriter extends JdbcBatchItemWriter<Person> {

    {
        setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
        setSql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)");
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Override
    public void write(List<? extends Person> list) throws Exception {
        System.out.println("write...");
        for (Person p : list) {
            System.out.println(p.toString());
        }
        super.write(list);
    }
}
