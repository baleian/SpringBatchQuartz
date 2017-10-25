package com.smps.sip.batch.backup.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Beom on 2017-10-25.
 */
@SpringBootApplication
@EnableAutoConfiguration
public class SpringBatchQuartzApplication {

    public static void main(String[] args) {
        SpringApplication.run(com.smps.sip.batch.SpringBatchQuartzApplication.class, args);
    }

}
