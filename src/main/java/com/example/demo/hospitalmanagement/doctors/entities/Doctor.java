package com.example.demo.hospitalmanagement.doctors.entities;

import com.example.demo.hospitalmanagement.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
@Document("Doctor")
public class Doctor extends BaseEntity {

  public static final String DOCTORID = "doctorId";

  @Indexed(name = "doctorId",unique = true)
  @NotBlank private String doctorId;
  @NotBlank private String name;
  @NotBlank private String surname;
  private String specialization;

}
