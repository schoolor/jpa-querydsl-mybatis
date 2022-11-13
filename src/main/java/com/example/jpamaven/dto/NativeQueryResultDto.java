package com.example.jpamaven.dto;

import lombok.Data;

@Data
public class NativeQueryResultDto {
    private String name;
    private int orderCount;

    public NativeQueryResultDto(String name, int orderCount) {
        this.name = name;
        this.orderCount = orderCount;
    }
}
