package com.sayandas.Final.Year.Project.Services;

import com.sayandas.Final.Year.Project.Entities.DoctorEntities.Doctors;
import com.sayandas.Final.Year.Project.Entities.DoctorEntities.Specializations;
import com.sayandas.Final.Year.Project.Repositories.DoctorRepositories.Doctor_Repository;
import com.sayandas.Final.Year.Project.Repositories.DoctorRepositories.Spec_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Doctor_Service {
    @Autowired
    Doctor_Repository doctorRepository;
    @Autowired
    Spec_Repository specRepository;
    @Autowired
    Spec_Repository specializationRepository;
    public Doctors addDoctor(Doctors doctor) {
        return doctorRepository.save(doctor);
    }
    public Doctors showDoctor(Integer id) {
        return doctorRepository.findById(id).get();
    }
    public List<Object> getAllDoctors() {
        return doctorRepository.getDetails();
    }
    public List<Object> getMinorDetails(String specName) {
        return specRepository.findDocBySpecName(specName);
    }

//    public List<String> getAllSpecializationNames() {
//        return specializationRepository.findAllSpecializationNames();
//    }
}
