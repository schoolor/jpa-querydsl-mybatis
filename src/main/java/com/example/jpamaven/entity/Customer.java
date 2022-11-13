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
public class Customer extends Auditable{
    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String email;

    public Customer() {}
    public Customer(int customerId) {
        this.id = customerId;
    }
}
