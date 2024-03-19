package com.facenet.bt2.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthorDto {
    private int id;
    private String name;
    private String dob;
}
