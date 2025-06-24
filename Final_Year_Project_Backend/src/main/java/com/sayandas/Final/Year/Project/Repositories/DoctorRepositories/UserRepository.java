package com.sayandas.Final.Year.Project.Repositories.DoctorRepositories;

import com.sayandas.Final.Year.Project.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Users findByUserPhn(String userPhn);
}
