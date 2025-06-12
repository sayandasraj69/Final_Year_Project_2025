package com.sayandas.Final.Year.Project.Entities.DoctorEntities;

import com.sayandas.Final.Year.Project.Entities.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer appId;

    @ManyToOne
    @JoinColumn(name = "docId")
    private Doctors doctor;

    @ManyToOne
    @JoinColumn(name = "schId")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "timingId")
    private Timings timing;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users users;

}
