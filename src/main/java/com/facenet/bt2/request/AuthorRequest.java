package com.facenet.bt2.request;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthorRequest {
    private String name;
    private String dob;
}
