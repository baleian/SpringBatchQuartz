package com.smps.sip.batch.job2;

import com.smps.sip.batch.job2.handler.CustomItemProcessor;
import com.smps.sip.batch.job2.handler.CustomItemReader;
import com.smps.sip.batch.job2.model.Person;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by Beom on 2017-10-24.
 */
@Configuration("com.smps.sip.batch.job2.BatchConfiguration")
public class BatchConfiguration {
    public static final String JOB_NAME = "testJob222";

    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    public DataSource dataSource;

    @Bean("com.smps.sip.batch.job2.BatchConfiguration.reader")
    public CustomItemReader reader() {
        return new CustomItemReader();
    }

    @Bean("com.smps.sip.batch.job2.BatchConfiguration.processor")
    public CustomItemProcessor processor() {
        return new CustomItemProcessor();
    }

    /**
     * Insert new data from resource
     */
    @Bean("com.smps.sip.batch.job2.BatchConfiguration.step2")
    public Step step2() {
        return stepBuilderFactory.get("com.smps.sip.batch.job2.BatchConfiguration.step2")
                .<Person, Person> chunk(10)
                .<Person> reader(reader())
                .<Person, Person> processor(processor())
                .<Person> writer(items -> {
                    for (Person item : items) {
                        System.out.println(JOB_NAME + JOB_NAME +JOB_NAME +JOB_NAME + ")\t" + item.getFirstName());
                    }
                })
                .build();
    }

    @Bean
    public RunIdIncrementer runIdIncrementer() {
        return new RunIdIncrementer();
    }

    @Bean("com.smps.sip.batch.job2.BatchConfiguration.testJob")
    public Job testJob() {
        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(step2())
                .end()
                .build();
    }

}
