package com.celestrong.tacocloud.pojo.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class Taco {

    private Long id;

    private Date createAt = new Date();

    @NotNull
    @Size(min = 5 , message = "名称不得小于5个字符")
    private String name;
    @NotNull
    @Size(min = 1, message = "至少要有一种原料")
    private List<IngredientRef> ingredients;
}
