package com.example.elasticsearch.controller;

import com.example.elasticsearch.dto.CreatePersonRequestDto;
import com.example.elasticsearch.dto.PersonDto;
import com.example.elasticsearch.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/")
    public ResponseEntity<PersonDto> createPerson(@RequestBody CreatePersonRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.createPerson(request));
    }

    @GetMapping("/search-by-name/{name}")
    public ResponseEntity<List<PersonDto>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(personService.getByName(name));
    }

    @GetMapping("/score-search")
    public ResponseEntity<List<PersonDto>> search(@RequestParam(value = "fieldName") String fieldName,
                                                          @RequestParam(value = "searchKey") String searchKey,
                                                          @RequestParam(required = false, value = "matchRate")Optional<String> matchRate) {
        return ResponseEntity.ok(personService.search(fieldName, searchKey, matchRate));
    }

    @GetMapping("/fuzzy-search")
    public ResponseEntity<List<PersonDto>> fuzzySearch(@RequestParam(value = "fieldName") String fieldName,
                                                       @RequestParam(value = "searchKey") String searchKey) {
        return ResponseEntity.ok(personService.fuzzySearch(fieldName, searchKey));
    }


}
