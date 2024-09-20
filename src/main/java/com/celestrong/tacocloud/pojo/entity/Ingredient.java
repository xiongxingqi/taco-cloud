package com.celestrong.tacocloud.pojo.entity;

import lombok.Data;

@Data
public class Ingredient {
    private final String id;
    private final String name;
    private final Type type;
    // todo 看一看枚举类
    public enum Type {
        WRAP,PROTEIN,VEGGIES,CHEESE,SAUCE
    }
}
