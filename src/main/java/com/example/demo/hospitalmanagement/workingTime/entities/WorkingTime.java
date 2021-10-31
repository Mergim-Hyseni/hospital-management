package com.example.demo.hospitalmanagement.workingTime.entities;


import com.example.demo.hospitalmanagement.BaseEntity;
import com.mongodb.lang.Nullable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Document("WorkingTime")
@EqualsAndHashCode(callSuper = true)
@Data
public class WorkingTime extends BaseEntity {

    public static final String DAYOFWORK = "dayOfWork";
    public static final String DOCTORID = "doctorId";
    public static final String STARTMINUTEOFTHEDAY = "startMinuteOfDay";
    public static final String ENDMINUTEOFTHEDAY = "endMinuteOfDay";

    @NotBlank
    private String doctorId;
    @Pattern(regexp = "MONDAY|TUESDAY|WEDNESDAY|THURSDAY|FRIDAY|SATURDAY|SUNDAY",flags = Pattern.Flag.CASE_INSENSITIVE)
    @NotBlank
    private String dayOfWork;
    @NotNull
    @Range(min = 0, max = 23)
    private int startHour;
    @NotNull
    @Range(min = 0, max = 59)
    private int startMinute;
    @NotNull
    @Range(min = 0, max = 23)
    private int endHour;
    @NotNull
    @Range(min = 0, max = 59)
    private int endMinute;
    @Nullable
    private int startMinuteOfDay;
    @Nullable
    private int endMinuteOfDay;

}
