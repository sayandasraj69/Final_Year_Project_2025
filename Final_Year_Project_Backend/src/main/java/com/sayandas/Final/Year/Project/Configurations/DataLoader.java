package com.sayandas.Final.Year.Project.Configurations;

import com.sayandas.Final.Year.Project.Services.Doctor_Service;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class DataLoader implements CommandLineRunner {

    private final Doctor_Service doctorService;

    public DataLoader(Doctor_Service doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    public void run(String... args) throws Exception {
//        doctorService.addDoctorsWithSpecializations();
    }
}
