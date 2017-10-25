package com.smps.sip.batch.job1.handler;

import com.smps.sip.batch.job1.model.Person;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Beom on 2017-10-25.
 */
@Component("com.smps.sip.batch.job1.handler.CustomNotificationListener")
public class CustomNotificationListener extends JobExecutionListenerSupport {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            System.out.println(jobExecution.getJobInstance().getJobName() + ")\t!!! JOB FINISHED! Time to verify the results");

            List<Person> results = jdbcTemplate.query("SELECT first_name, last_name FROM people", (rs, row) -> {
                return new Person(rs.getString(1), rs.getString(2));
            });

            for (Person person : results) {
                System.out.println(jobExecution.getJobInstance().getJobName() +jobExecution.getJobInstance().getJobName() +jobExecution.getJobInstance().getJobName() +jobExecution.getJobInstance().getJobName() +jobExecution.getJobInstance().getJobName() + ")\tFound <" + person + "> in the database.");
            }
        }
    }

}
