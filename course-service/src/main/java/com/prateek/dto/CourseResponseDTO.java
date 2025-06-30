package com.prateek.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseResponseDTO {
    private int courseId;
    private String name;
    private String trainerName;
    private String duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date startDate;
    private String courseType; // Live or Recording
    private double fees;

    @JsonProperty("isCertificateAvailable")
    private boolean isCertificateAvailable;
    private String description;
    private String courseUniqueCode;
    private String emailId;
    private String contact;
}
