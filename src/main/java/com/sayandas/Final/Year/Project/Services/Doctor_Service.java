package com.sayandas.Final.Year.Project.Services;

import com.sayandas.Final.Year.Project.Entities.Doctors;
import com.sayandas.Final.Year.Project.Repositories.Doctor_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Doctor_Service {
    @Autowired
    Doctor_Repository doctorRepository;
    public Doctors addDoctor(Doctors doctor) {
        return doctorRepository.save(doctor);
    }
    public Doctors showDoctor(int id) {
        return doctorRepository.findById(id).get();
    }
}
