package com.sayandas.Final.Year.Project.Controllers;

import com.sayandas.Final.Year.Project.Services.Doctor_Service;
import com.sayandas.Final.Year.Project.Entities.Doctors;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
//@RestController
@RequestMapping("doctor")
public class Doctor_Controller {
    @Autowired
    Doctor_Service doctorService;
    @PostMapping("/add-doctor")
    private ResponseEntity<Doctors> addDoctor(@Valid @ModelAttribute("doctor") Doctors doctor) {
        Doctors savedDoctor = doctorService.addDoctor(doctor);
        if (savedDoctor != null)
            return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        return "Registered successfully";
    }

//    @GetMapping("/")
//    private String showHomePage() {
//        return "index";
//    }

    @GetMapping("/register")
    public String register(@RequestParam(required = false) String success, Model model) {
        model.addAttribute("doctor", new Doctors());
        return "RegistrationViews/doctor";
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

        return "Login_Views/doctor_Login";
    }

    @PostMapping("/login-submit")
    private String submitDoctorLogin(){
        return "redirect:/doctor/profile";
    }

    @PostMapping("/profile")
    public String showProfilePage() {
        return "Profile_Views/Doctor_Profile";
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
