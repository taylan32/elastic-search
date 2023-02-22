package com.example.elasticsearch.dto;

import com.example.elasticsearch.model.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonDtoConverter {
    public PersonDto convert(Person from) {
        return new PersonDto(
                from.getId(),
                from.getName(),
                from.getLastName(),
                from.getAddress(),
                from.getBithOfDate()
        );
    }

}
