package com.example.jpamaven.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;

@MappedSuperclass
@Data
@EqualsAndHashCode
public class Auditable {
    @CreatedDate
    ZonedDateTime createdAt;
    @LastModifiedDate
    ZonedDateTime updatedAt;
}
