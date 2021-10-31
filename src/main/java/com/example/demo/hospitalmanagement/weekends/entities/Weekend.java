package com.example.demo.hospitalmanagement.weekends.entities;

import com.example.demo.hospitalmanagement.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Document("Weekend")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@CompoundIndex(name = "doctorId_weekendDay_1", def = "{'doctorId' : 1, 'weekendDay' : 1}", unique = true)
public class Weekend extends BaseEntity {

    public static final String WEEKENDDAY = "weekendDay";

    @NotBlank
    private String doctorId;
    @NotBlank
    @Pattern(regexp = "MONDAY|TUESDAY|WEDNESDAY|THURSDAY|FRIDAY|SATURDAY|SUNDAY",flags = Pattern.Flag.CASE_INSENSITIVE)
    private String weekendDay;

}
