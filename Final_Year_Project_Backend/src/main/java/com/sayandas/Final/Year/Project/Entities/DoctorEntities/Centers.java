package com.sayandas.Final.Year.Project.Entities.DoctorEntities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class Centers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cenId;
    private String cenName;

    @ManyToOne
    @JoinColumn(name = "cityId")
    private City city;

//    @ManyToMany(mappedBy = "center")
//    private List<Doctors> doctors;
}
