package com.smps.sip.batch.job1;

import com.smps.sip.batch.job1.handler.CustomItemProcessor;
import com.smps.sip.batch.job1.handler.CustomItemReader;
import com.smps.sip.batch.job1.handler.CustomItemWriter;
import com.smps.sip.batch.job1.handler.CustomNotificationListener;
import com.smps.sip.batch.job1.model.Person;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by Beom on 2017-10-24.
 */
@Configuration("com.smps.sip.batch.job1.BatchConfiguration")
public class BatchConfiguration {
    public static final String JOB_NAME = "testJob111";

    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    public DataSource dataSource;

    @Bean("com.smps.sip.batch.job1.BatchConfiguration.reader")
    public CustomItemReader reader() {
        return new CustomItemReader();
    }

    @Bean("com.smps.sip.batch.job1.BatchConfiguration.processor")
    public CustomItemProcessor processor() {
        return new CustomItemProcessor();
    }

    @Bean("com.smps.sip.batch.job1.BatchConfiguration.writer")
    public CustomItemWriter writer() {
        return new CustomItemWriter() {{
            setDataSource(dataSource);
        }};
    }

    /**
     * Remove old data
     */
    @Bean("com.smps.sip.batch.job1.BatchConfiguration.step1")
    public Step step1() {
        return stepBuilderFactory.get("com.smps.sip.batch.job2.BatchConfiguration.step1")
                .tasklet((stepContribution, chunkContext) -> {
                    new JdbcTemplate(dataSource).execute("DELETE FROM people");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    /**
     * Insert new data from resource
     */
    @Bean("com.smps.sip.batch.job1.BatchConfiguration.step2")
    public Step step2() {
        return stepBuilderFactory.get("com.smps.sip.batch.job2.BatchConfiguration.step2")
                .<Person, Person> chunk(10)
                .<Person> reader(reader())
//                .<Person, Person> processor(processor())
//                .<Person> writer(writer())
                .writer(a -> {
                    System.out.println(JOB_NAME + JOB_NAME + JOB_NAME + JOB_NAME + JOB_NAME + JOB_NAME + a.size());
                })
                .build();
    }

    @Bean("com.smps.sip.batch.job1.BatchConfiguration.testJob")
    public Job testJob(CustomNotificationListener listener) {
        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .next(step2())
                .end()
                .build();
    }

}
