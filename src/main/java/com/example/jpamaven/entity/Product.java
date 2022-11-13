package com.example.jpamaven.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class Product extends Auditable {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private int listPrice;
    private String description;

    public Product() {}
    public Product(int productId) {
        this.id= productId;
    }
}
