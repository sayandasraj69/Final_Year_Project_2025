package com.sayandas.Final.Year.Project.Repositories.DoctorRepositories;

import com.sayandas.Final.Year.Project.Entities.DoctorEntities.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Doctor_Repository extends JpaRepository<Doctors,Integer> {
//    Doctors findByUsername(String username);
//    @Query(value = "select d.Doc_name, s.spec_name from Doctors d, Specializations s," +
//            " Doctor_specs ds where ds.doc_id = d.doc_id and ds.spec_id = s.spec_Id " +
//            "and s.spec_Name = :specName", nativeQuery = true)
//    List<Object> findBySpecName(String specName);

    @Query("SELECT d.docId, d.docName, d.docEmail, d.docPhn, s.specName " +
            "FROM Doctors d JOIN d.specializations s")
    List<Object> getDetails();
}
