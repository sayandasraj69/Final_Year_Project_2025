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
    private String docEmail;
    private String docPhn;
    @ManyToMany(cascade = {
            CascadeType.MERGE
        }
    )
    @JoinTable(
            name = "docSpecs",
            joinColumns = {
                    @JoinColumn(name = "docId")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "specId")
            }
    )
    private List<Specializations> specializations;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedule;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointments> appointments;



}
