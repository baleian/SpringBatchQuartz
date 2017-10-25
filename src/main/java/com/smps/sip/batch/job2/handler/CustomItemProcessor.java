package com.smps.sip.batch.job2.handler;

import com.smps.sip.batch.job2.model.Person;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by Beom on 2017-10-25.
 */
public class CustomItemProcessor implements ItemProcessor<Person, Person> {

    @Override
    public Person process(Person person) throws Exception {
        System.out.println("process... convert to uppercase");
        person.setFirstName(person.getFirstName().toUpperCase());
        person.setLastName(person.getLastName().toUpperCase());
        return person;
    }

}
