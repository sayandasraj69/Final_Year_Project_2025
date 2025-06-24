package com.sayandas.Final.Year.Project.Controllers;

import com.sayandas.Final.Year.Project.DTOs.DoctorDetailsDTO;
import com.sayandas.Final.Year.Project.DTOs.ScheduleWithTimingsDTO;
import com.sayandas.Final.Year.Project.Entities.DoctorEntities.Appointments;
import com.sayandas.Final.Year.Project.Entities.DoctorEntities.Doctors;
import com.sayandas.Final.Year.Project.Entities.DoctorEntities.Schedule;
import com.sayandas.Final.Year.Project.Entities.DoctorEntities.Specializations;
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
    private ResponseEntity<?> addDoctor(@RequestPart Doctors doctor, @RequestPart MultipartFile image) {
        try {
            Doctors savedDoctor = doctorService.addDoctor(doctor, image);
            return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
//            return new ResponseEntity<>(HttpStatus.CREATED);
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
    @GetMapping("/specializations")
    public ResponseEntity<List<Object>> getAllSpecializationNames() {
         return new ResponseEntity<>(doctorService.getAllSpecializationNames(),HttpStatus.FOUND);
    }

    @GetMapping("/qualifications")
    public ResponseEntity<List<Object>> getAllQualificationNames() {
        return new ResponseEntity<>(doctorService.getAllQualificationNames(),HttpStatus.FOUND);
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

        @GetMapping("/schedule/{doctorId}")
    public ResponseEntity<List<ScheduleWithTimingsDTO>> getDoctorScheduleByDoctorId(@PathVariable Integer doctorId) {
        List<ScheduleWithTimingsDTO> schedule = doctorService.getSchedulesByDoctorId(doctorId);
        if (schedule != null && !schedule.isEmpty()) {
            return new ResponseEntity<>(schedule, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/similar")
    public ResponseEntity<List<Map<String, Object>>> getSimilarDoctors(@RequestBody Map<String, Object> request) {
        List<String> specializations = (List<String>) request.get("specializations");
        Object excludeDoctorIdObj = request.get("excludeDoctorId");
        Integer excludeDoctorId = null;
        if (excludeDoctorIdObj != null) {
            if (excludeDoctorIdObj instanceof Number) {
                excludeDoctorId = ((Number) excludeDoctorIdObj).intValue();
            } else {
                excludeDoctorId = Integer.parseInt(excludeDoctorIdObj.toString());
            }
        }
        List<Map<String, Object>> doctors = doctorService.findMinorDetailsBySpecializations(specializations, excludeDoctorId);
        if (!doctors.isEmpty()) {
            return new ResponseEntity<>(doctors, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/book-appointment")
    public ResponseEntity<Appointments> bookAppointment(@RequestBody Appointments appointment) {
        try {
            Appointments savedAppointment = doctorService.bookAppointment(appointment);
            return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
