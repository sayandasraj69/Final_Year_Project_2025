package com.sayandas.Final.Year.Project.Services;

import com.sayandas.Final.Year.Project.Entities.Doctors;
import com.sayandas.Final.Year.Project.Entities.UserPrincipal;
import com.sayandas.Final.Year.Project.Repositories.Doctor_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private Doctor_Repository doctorRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Doctors doctor = doctorRepository.findByName(username);
        if (doctor == null) {
            throw new UsernameNotFoundException("Doctor not found");
        }
        return new UserPrincipal(doctor);
    }

//    public UserDetails loadUserById(Integer id) throws UsernameNotFoundException {
//        Doctors doctor = doctorRepository.findById(id).get();
////        if (doctor == null){
////            throw new UsernameNotFoundException("Doctor not found");
////        }
//        return new UserPrincipal(doctor);
//    }
}
