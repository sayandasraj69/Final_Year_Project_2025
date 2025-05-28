package com.sayandas.Final.Year.Project.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDetailsDTO {
    private String doctorName;
    private List<String> degree;
    private List<String> specialization;
    private List<ScheduleDTO> schedule;
}
