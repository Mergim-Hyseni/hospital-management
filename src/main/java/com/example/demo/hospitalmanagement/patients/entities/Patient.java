package com.example.demo.hospitalmanagement.patients.entities;

import com.example.demo.hospitalmanagement.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(Patient.COLLECTION)
public class Patient extends BaseEntity {

  public static final String COLLECTION = "Patient";
  public static final String PATIENT_NAME = "name";
  public static final String AGE = "age";


  private String name;
  private String surname;
  private String diagnose;
  private int age;

}
