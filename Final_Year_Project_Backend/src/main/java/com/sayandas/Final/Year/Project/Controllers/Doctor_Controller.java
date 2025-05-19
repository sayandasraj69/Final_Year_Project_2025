package com.sayandas.Final.Year.Project.Controllers;

import com.sayandas.Final.Year.Project.Entities.DoctorEntities.Specializations;
import com.sayandas.Final.Year.Project.Services.Doctor_Service;
import com.sayandas.Final.Year.Project.Entities.DoctorEntities.Doctors;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.expression.Strings;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://127.0.0.1:5500") // Allow requests from your frontend
@RestController
public class Doctor_Controller {
    @Autowired
    Doctor_Service doctorService;
    @PostMapping("/add-doctor")
    private String addDoctor(@Valid @ModelAttribute("doctor") Doctors doctor, Model model) {
        System.out.println(doctor);
        Doctors savedDoctor = doctorService.addDoctor(doctor);
        if (savedDoctor != null){
            model.addAttribute("SuccessMessage", "Doctor registered successfully");
            return "redirect:/LoginViews/DoctorLogin";
        }
        else {
            model.addAttribute("ErrorMessage", "Doctor registration failed");
            return "redirect:/RegisterViews/DoctorRegister";
        }
    }

//    @GetMapping("/")
//    private String showHomePage() {
//        return "index";
//    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("doctor", new Doctors());
        return "/RegisterViews/DoctorRegister";
    }

    @PostMapping("/register-submit")
    public String submitDoctorRegistration(@ModelAttribute("doctor") Doctors doctor, Model model) {
        if(doctorService.addDoctor(doctor) != null) {
            model.addAttribute("SuccessMessage", "Doctor registered successfully");
            return "redirect:/doctor/login?success=true";
        }
        else{
            model.addAttribute("ErrorMessage", "Doctor registration failed");
            return "redirect:/doctor/register?success=false";
        }
    }

    @GetMapping("/login")
    public String showLoginPage (Model model){
        model.addAttribute("doctor", new Doctors());
        return "/LoginViews/DoctorLogin";
    }

//    @PostMapping("/login-submit")
//    private String submitDoctorLogin(Model model){
//        return "redirect:/doctor/profile";
//    }

    @GetMapping("/profile")
    public String showProfilePage() {
        return "/ProfileViews/DoctorProfile";
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
    public ResponseEntity<List<Object>> getAllDoctors() {
        try {
            List<Object> doctors = doctorService.getAllDoctors();
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

    @GetMapping("/{id}/details")
    public ResponseEntity<Map<String, Object>> getDoctorDetailsById(@PathVariable Integer id) {
        Map<String, Object> doctorDetails = doctorService.getDoctorDetailsById(id);
        return ResponseEntity.ok(doctorDetails);
    }
}
