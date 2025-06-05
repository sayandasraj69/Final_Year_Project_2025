package com.sayandas.Final.Year.Project.Repositories.DoctorRepositories;

import com.sayandas.Final.Year.Project.Entities.DoctorEntities.Qualifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QualRepository extends JpaRepository<Qualifications, Integer> {
    @Query("SELECT q.qualId, q.qualName from Qualifications q")
    List<Object> findAllQualifications();
}
