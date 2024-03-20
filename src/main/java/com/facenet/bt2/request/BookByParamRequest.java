package com.facenet.bt2.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookByParamRequest {
    private int pageNumber;
    private int pageSize;
}
