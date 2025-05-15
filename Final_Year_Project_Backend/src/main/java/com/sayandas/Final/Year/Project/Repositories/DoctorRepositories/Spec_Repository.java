package com.sayandas.Final.Year.Project.Repositories.DoctorRepositories;

import com.sayandas.Final.Year.Project.Entities.DoctorEntities.Specializations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Spec_Repository extends JpaRepository<Specializations,Integer> {
    @Query("SELECT s.specName, d.docName FROM Specializations s JOIN s.doctors d where s.specName = :specName")
    List<Object> findDocBySpecName(@Param("specName") String specName);
//    @Query("SELECT s.specName FROM Specializations s where s.specName = :specName")
    Specializations findBySpecName(String specName);
}
