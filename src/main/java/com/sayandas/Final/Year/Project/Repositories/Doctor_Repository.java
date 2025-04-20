package com.sayandas.Final.Year.Project.Repositories;

import com.sayandas.Final.Year.Project.Entities.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Doctor_Repository extends JpaRepository<Doctors,Integer> {
    Doctors findByName(String name);
}
