package com.example.elasticsearch.service;

import com.example.elasticsearch.dto.CreatePersonRequestDto;
import com.example.elasticsearch.dto.PersonDto;
import com.example.elasticsearch.dto.PersonDtoConverter;
import com.example.elasticsearch.model.Person;
import com.example.elasticsearch.repository.PersonRepository;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonDtoConverter converter;
    private final ElasticsearchOperations elasticsearchOperations;

    public PersonService(PersonRepository personRepository, PersonDtoConverter converter, ElasticsearchOperations elasticsearchOperations) {
        this.personRepository = personRepository;
        this.converter = converter;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public List<PersonDto> getByName(String name) {
        return this.personRepository.getByName(name)
                .stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    public PersonDto createPerson(CreatePersonRequestDto request) {
        Person person = personRepository.save(new Person(
                UUID.randomUUID().toString(),
                request.getName(),
                request.getLastName(),
                request.getAddress(),
                request.getBirthOfDate()
        ));

        return converter.convert(person);
    }

    public List<PersonDto> search(String fieldName, String searchKey, Optional<String> matchRate) {

        String match = "75%";
        if(matchRate.isPresent()) {
            if(Integer.parseInt(matchRate.get()) > 100) {
                throw new IllegalArgumentException("Invalid parameter value");
            }
            match = matchRate.get() + "%";
        }

        final NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery(fieldName, searchKey).minimumShouldMatch(match))
                .build();
        return elasticsearchOperations.search(
                searchQuery,
                Person.class,
                IndexCoordinates.of("person_index")
        ).stream().map(item -> converter.convert(item.getContent()))
                .collect(Collectors.toList());
    }

    public List<PersonDto> fuzzySearch(String fieldName, String searchKey) {
        final NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery(fieldName, searchKey)
                        .operator(Operator.AND)
                        .fuzziness(Fuzziness.TWO)
                        .prefixLength(2)
                )
                .build();

        return elasticsearchOperations.search(
                searchQuery,
                Person.class,
                IndexCoordinates.of("person_index")
        ).stream()
                .map(item -> converter.convert(item.getContent()))
                .collect(Collectors.toList());

    }

}
