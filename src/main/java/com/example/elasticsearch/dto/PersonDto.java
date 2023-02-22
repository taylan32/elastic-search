package com.example.elasticsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {
    private String id;
    private String name;
    private String lastName;
    private String address;
    private Date birthOfDate;
}
