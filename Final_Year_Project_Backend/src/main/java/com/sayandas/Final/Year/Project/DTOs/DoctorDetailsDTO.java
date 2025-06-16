package com.sayandas.Final.Year.Project.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDetailsDTO {
    private String docName;
    private List<String> degrees;
    private List<String> specializations;
    private List<ScheduleDTO> schedules;
    private String docImageName;
    private String docImageType;
    private byte[] docImageData;
    private String docEmail;
    private String docPhn;
    private String about;
    private Integer experience;
    private Integer fee;
}
