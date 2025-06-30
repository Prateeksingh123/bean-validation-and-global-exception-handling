package com.prateek.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.prateek.annotation.CourseTypeValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequestDTO {
    @NotBlank(message = "Course name shouldn't be NULL OR EMPTY")
    private String name;

    @NotEmpty(message = "Trainer name should be defined")
    private String trainerName;

    @NotNull(message = "duration must needs to be specified")
    private String duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
//    @Past(message = "start date can't be before date from current")
    private Date startDate;

    @CourseTypeValidation
    private String courseType; // Live or Recording

    @Min(value = 1500, message = "course price can't be less than 1500")
    @Max(value = 5000, message = "course price can't be more than 5000")
    private double fees;

    @JsonProperty("isCertificateAvailable")
    private boolean isCertificateAvailable;

    @NotEmpty(message = "description must pe present")
    @Size(max = 50, message = "description must be within 50 chars")
    private String description;

    @Email(message = "invalid email id")
    private String emailId;

    @Pattern(regexp = "^[0-9]{10}$")
    private String contact;
}
