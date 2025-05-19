package com.sayandas.Final.Year.Project.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sayandas.Final.Year.Project.Entities.DoctorEntities.Doctors;
import com.sayandas.Final.Year.Project.Entities.DoctorEntities.Specializations;
import com.sayandas.Final.Year.Project.Repositories.DoctorRepositories.Doctor_Repository;
import com.sayandas.Final.Year.Project.Repositories.DoctorRepositories.Spec_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Doctor_Service {
    @Autowired
    Doctor_Repository doctorRepository;
    @Autowired
    Spec_Repository specRepository;
//    @Autowired
//    Spec_Repository specializationRepository;
//    ObjectMapper objectMapper = new ObjectMapper();
    public Doctors addDoctor(Doctors doctor) {
        return doctorRepository.save(doctor);
    }
    public Doctors showDoctor(Integer id) {
        return doctorRepository.findById(id).get();
    }
    public List<Object> getAllDoctors() {
        return doctorRepository.getDetails();
    }
    public List<Map<String, Object>> getMinorDetailsBySpecName(String specName) {
        List<Map<String, Object>> queryResult = doctorRepository.findBySpecName(specName);

        Map<String, Set<String>> doctorSpecializationsMap = new LinkedHashMap<>();

        // Group specializations by doctor name
        queryResult.forEach(row -> {
            String doctorName = (String) row.get("doctorName");
            String specialization = (String) row.get("specialization");

            doctorSpecializationsMap
                    .computeIfAbsent(doctorName, k -> new LinkedHashSet<>())
                    .add(specialization);
        });

        // Transform the grouped data into the desired JSON structure
        List<Map<String, Object>> result = new ArrayList<>();
        doctorSpecializationsMap.forEach((doctorName, specializations) -> {
            List<String> orderedSpecializations = new ArrayList<>(specializations);

            // Ensure the query parameter specialization is first
            if (orderedSpecializations.remove(specName)) {
                orderedSpecializations.add(0, specName);
            }

            Map<String, Object> doctorData = new HashMap<>();
            doctorData.put("doctorName", doctorName);
            doctorData.put("specializations", orderedSpecializations);
            result.add(doctorData);
        });

        return result;
    }

    public List<String> getAllSpecializationNames() {
        return specRepository.findAllSpecializationNames();
    }

    public Map<String, Object> getDoctorDetailsById(Integer doctorId) {
        List<Map<String, Object>> queryResult = doctorRepository.findDoctorDetailsById(doctorId);

        Map<String, Object> result = new HashMap<>();
        result.put("docName", queryResult.get(0).get("doctorName"));

        // Collect specializations
        Set<String> specializations = new HashSet<>();
        queryResult.forEach(row -> specializations.add((String) row.get("specialization")));
        result.put("specializations", specializations);

        // Collect schedules
        Map<String, Map<String, Object>> scheduleMap = new LinkedHashMap<>();
        queryResult.forEach(row -> {
            String weekDay = (String) row.get("scheduleDay");
            String timeRange = (String) row.get("timeRange");
            Integer noOfPatients = (Integer) row.get("noOfPatients");

            // Create timing object
            Map<String, Object> timing = new HashMap<>();
            timing.put("timeRange", timeRange);
            timing.put("noOfPatients", noOfPatients);

            // Add timings to the corresponding weekday
            scheduleMap.computeIfAbsent(weekDay, k -> {
                Map<String, Object> schedule = new HashMap<>();
                schedule.put("weekday", weekDay);
                schedule.put("timings", new ArrayList<Map<String, Object>>());
                return schedule;
            });

            List<Map<String, Object>> timings = (List<Map<String, Object>>) scheduleMap.get(weekDay).get("timings");
            if (!timings.contains(timing)) { // Avoid duplicates
                timings.add(timing);
            }
        });

        result.put("schedule", new ArrayList<>(scheduleMap.values()));
        return result;
    }
}
