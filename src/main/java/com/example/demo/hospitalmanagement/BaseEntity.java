package com.example.demo.hospitalmanagement;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Data
public class BaseEntity {
  public static final String ID = "_id";
  public static final String CREATED_AT = "createdAt";
  public static final String UPDATED_AT = "updatedAt";

  @Id private String id;
  @CreatedDate private Instant createdAt;
  @LastModifiedDate private Instant updatedAt;

}
