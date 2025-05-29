package com.sayandas.Final.Year.Project.Controllers;

import com.sayandas.Final.Year.Project.DTOs.DoctorDetailsDTO;
import com.sayandas.Final.Year.Project.Entities.DoctorEntities.Doctors;
import com.sayandas.Final.Year.Project.Services.Doctor_Service;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"}) // Allow requests from your frontend
@RestController
@RequestMapping("/doctor")
public class Doctor_Controller {
    @Autowired
    Doctor_Service doctorService;
    @PostMapping("/register")
    private ResponseEntity<?> addDoctor(@Valid @RequestBody Doctors doctor) {
        try {
            Doctors savedDoctor = doctorService.addDoctor(doctor);
//            return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
    @GetMapping("/all")
    public ResponseEntity<List<Map<String, Object>>> getAllDoctors() {
        try {
            List<Map<String, Object>> doctors = doctorService.findMinorDetails();
            if (!doctors.isEmpty()) {
                return new ResponseEntity<>(doctors, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/minor/{specName}")
    public ResponseEntity <List<Map<String, Object>>> getMinorDetails(@PathVariable String specName) {
        try {
            List<Map<String, Object>> doctors = doctorService.getMinorDetailsBySpecName(specName);
            if (!doctors.isEmpty()) {
                return new ResponseEntity<>(doctors, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/specialization_names")
    public ResponseEntity<List<String>> getAllSpecializationNames() {
         return new ResponseEntity<>(doctorService.getAllSpecializationNames(),HttpStatus.FOUND);
    }

//    @GetMapping("/{id}/details")
//    public ResponseEntity<Map<String, Object>> getDoctorDetailsById(@PathVariable Integer id) {
//        Map<String, Object> doctorDetails = doctorService.getDoctorDetailsById(id);
//        return ResponseEntity.ok(doctorDetails);
//    }

    @GetMapping("/{id}/details")
    public ResponseEntity<DoctorDetailsDTO> findDoctorDetailsById(@PathVariable Integer id) {
        DoctorDetailsDTO doctorDetails = doctorService.getDoctorDetailsById(id);
        System.out.println(doctorDetails);
        if (doctorDetails != null) {
            return ResponseEntity.ok(doctorDetails);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
