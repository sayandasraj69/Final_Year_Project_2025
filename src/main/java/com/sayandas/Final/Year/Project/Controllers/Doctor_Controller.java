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
@RequestMapping("/doctor")
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
}
