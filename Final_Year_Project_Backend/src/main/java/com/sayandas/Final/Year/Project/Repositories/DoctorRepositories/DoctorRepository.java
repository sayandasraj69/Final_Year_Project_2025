package com.sayandas.Final.Year.Project.Repositories.DoctorRepositories;

import com.sayandas.Final.Year.Project.Entities.DoctorEntities.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DoctorRepository extends JpaRepository<Doctors,Integer> {
//    Doctors findByUsername(String username);
//    @Query(value = "select d.Doc_name, s.spec_name from Doctors d, Specializations s," +
//            " Doctor_specs ds where ds.doc_id = d.doc_id and ds.spec_id = s.spec_Id " +
//            "and s.spec_Name = :specName", nativeQuery = true)
//    List<Object> findBySpecName(String specName);

    @Query("SELECT d.docId as docId, d.docName AS doctorName, sAll.specName AS specialization " +
            "FROM Doctors d " +
            "JOIN d.specializations sAll " +
            "WHERE EXISTS (" +
            "  SELECT 1 " +
            "  FROM d.specializations sFilter " +
            ")")
    List<Map<String, Object>> findMinorDetails();

    @Query("SELECT d.docName AS doctorName, s.specName AS specialization, sch.weekDay AS scheduleDay, t.timeRange AS timeRange, t.noOfPatients AS noOfPatients " +
            "FROM Doctors d " +
            "JOIN d.specializations s " +
            "JOIN d.schedule sch " +
            "JOIN sch.timings t " +
            "WHERE d.docId = :doctorId")
    List<Map<String, Object>> getDoctorDetailsById(@Param("doctorId") Integer doctorId);

    @Query("SELECT d.docName AS doctorName, " +
            "q.qualName AS degree, " +
            "spec.specName AS specialization, " +
            "sch.weekDay AS scheduleDay, " +
            "t.timingId AS timingId, t.timeRange AS timeRange, " +
            "t.noOfPatients AS noOfPatients, " +
            "t.city.cityName AS city, t.center.cenName AS center " +
            "FROM Doctors d " +
            "LEFT JOIN d.qualifications q " +
            "LEFT JOIN d.specializations spec " +
            "LEFT JOIN d.schedule sch " +
            "LEFT JOIN sch.timings t " +
            "WHERE d.docId = :doctorId")
    List<Map<String, Object>> findDoctorDetailsById(@Param("doctorId") Integer id);

    @Query("SELECT d.docId AS docId, d.docName AS doctorName, sAll.specName AS specialization " +
            "FROM Doctors d " +
            "JOIN d.specializations sAll " +
            "WHERE EXISTS (" +
            "  SELECT 1 " +
            "  FROM d.specializations sFilter " +
            "  WHERE (sFilter.specName) = (:specName)" +
            ")")
    List<Map<String, Object>> findBySpecName(@Param("specName") String specName);

    @Query("SELECT d.docId AS docId, d.docName AS docName, d.docImageName AS docImageName, d.docImageType AS docImageType, d.docImageData AS docImageData, " +
            "d.experience AS experience, s.specName AS specialization, q.qualName AS degree " +
            "FROM Doctors d " +
            "LEFT JOIN d.specializations s " +
            "LEFT JOIN d.qualifications q " +
            "WHERE d.docId <> :excludeDoctorId AND s.specName IN :specializations")
    List<Map<String, Object>> findMinorDetails(@Param("specializations") List<String> specializations, @Param("excludeDoctorId") Integer excludeDoctorId);
}
