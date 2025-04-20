package com.sayandas.Final.Year.Project.Controllers;

import com.sayandas.Final.Year.Project.Services.Doctor_Service;
import com.sayandas.Final.Year.Project.Entities.Doctors;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/doctor")
public class Doctor_Controller {
    @Autowired
    Doctor_Service doctorService;
    @PostMapping("/add-doctor")
    private ResponseEntity<Doctors> addDoctor(@Valid @RequestBody Doctors doctor) {
        Doctors savedDoctor = doctorService.addDoctor(doctor);
        if (savedDoctor != null)
            return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        return "Registered successfully";
    }

    @GetMapping("/show-doctor/{id}")
    private ResponseEntity<Doctors> showDoctor(@PathVariable Integer id) {
        try {
            Doctors doctor = doctorService.showDoctor(id);
            if (doctor != null) {
                return new ResponseEntity<>(doctor, HttpStatus.FOUND);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
