package com.example.elasticsearch.repository;

import com.example.elasticsearch.model.Person;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PersonRepository extends ElasticsearchRepository<Person, String> {

    @Query("{\"bool\": {\"must\": [{\"match\":{\"name\":\"?0\"}}]}}")
    List<Person> getByName(String name);
}
