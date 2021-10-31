package com.example.demo.hospitalmanagement.schedules.entities;

import com.example.demo.hospitalmanagement.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@Document("Schedule")
public class Schedule extends BaseEntity {
  public static final String DOCTOR_ID = "doctorId";
  public static final String TIME_INTERVAL_FROM = "timeInterval.from";
  public static final String TIME_INTERVAL_TO = "timeInterval.to";

  @NotNull
  @Field(targetType = FieldType.OBJECT_ID)
  private String patientId;

  @NotNull
  @Field(targetType = FieldType.OBJECT_ID)
  private String doctorId;

  @Valid private Interval timeInterval;

  @Data
  public static class Interval {
    @NotNull private Instant from;
    @NotNull private Instant to;
  }
}
