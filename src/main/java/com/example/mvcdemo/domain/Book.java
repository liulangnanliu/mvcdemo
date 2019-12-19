package com.example.mvcdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Document
@AllArgsConstructor
public class Book {
    private String id;

    private String name;

    @CreatedDate
    private Date createTime;

    @LastModifiedDate
    private Date updateTime;

    private BigDecimal price;

    private Integer storage;

    private Size size;

}
