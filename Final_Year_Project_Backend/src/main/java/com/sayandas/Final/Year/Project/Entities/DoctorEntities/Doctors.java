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
public class Doctors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer docId;
    private String docName;

    private String docImageName;
    private String docImageType;
    @Lob
    private byte[] docImageData;

    private String docEmail;
    private String docPhn;
    private String about;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "DoctorDegrees",
            joinColumns = @JoinColumn(name = "docId"),
            inverseJoinColumns = @JoinColumn(name = "degId")
    )
    private List<Degrees> degrees;

    @ManyToMany(cascade = {
            CascadeType.MERGE
        }
    )
    @JoinTable(
            name = "DoctorSpecs",
            joinColumns = @JoinColumn(name = "docId"),
            inverseJoinColumns = @JoinColumn(name = "specId")
    )
    private List<Specializations> specializations;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "DoctorCities",
            joinColumns = @JoinColumn(name = "docId"),
            inverseJoinColumns = @JoinColumn(name = "cityId")
    )
    private List<City> cities;

    @ManyToMany
    @JoinTable(
            name = "DoctorCenter",
            joinColumns = @JoinColumn(name = "cenId"),
            inverseJoinColumns = @JoinColumn(name = "docId")
    )
    private List<Centers> center;


    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedule;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointments> appointments;



}
