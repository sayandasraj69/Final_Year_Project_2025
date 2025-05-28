package com.sayandas.Final.Year.Project.Services;

import com.sayandas.Final.Year.Project.DTOs.DoctorDetailsDTO;
import com.sayandas.Final.Year.Project.DTOs.ScheduleDTO;
import com.sayandas.Final.Year.Project.DTOs.TimingDTO;
import com.sayandas.Final.Year.Project.Entities.DoctorEntities.Degrees;
import com.sayandas.Final.Year.Project.Entities.DoctorEntities.Doctors;
import com.sayandas.Final.Year.Project.Entities.DoctorEntities.Specializations;
import com.sayandas.Final.Year.Project.Repositories.DoctorRepositories.DoctorRepository;
import com.sayandas.Final.Year.Project.Repositories.DoctorRepositories.SpecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class Doctor_Service {
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    SpecRepository specRepository;
//    @Autowired
//    SpecRepository specializationRepository;
//    ObjectMapper objectMapper = new ObjectMapper();
    public Doctors addDoctor(Doctors doctor, MultipartFile file) throws IOException {
        doctor.setDocImageName(file.getOriginalFilename());
        doctor.setDocImageType(file.getContentType());
        doctor.setDocImageData(file.getBytes());
        return doctorRepository.save(doctor);
    }
    public Doctors showDoctor(Integer id) {
        return doctorRepository.findById(id).get();
    }
    public List<Map<String, Object>> findMinorDetails() {
        List<Map<String, Object>> queryResult = doctorRepository.findMinorDetails();

        Map<Integer, Map<String, Object>> doctorDetailsMap = new LinkedHashMap<>();

        // Group specializations by doctor ID
        queryResult.forEach(row -> {
            Integer docId = (Integer) row.get("docId");
            String doctorName = (String) row.get("doctorName");
            String specialization = (String) row.get("specialization");

            doctorDetailsMap.computeIfAbsent(docId, k -> {
                Map<String, Object> doctorData = new HashMap<>();
                doctorData.put("docId", docId);
                doctorData.put("doctorName", doctorName);
                doctorData.put("specializations", new LinkedHashSet<String>());
                return doctorData;
            });

            Set<String> specializations = (Set<String>) doctorDetailsMap.get(docId).get("specializations");
            specializations.add(specialization);
        });

        // Transform the grouped data into the desired JSON structure
        List<Map<String, Object>> result = new ArrayList<>();
        doctorDetailsMap.forEach((docId, doctorData) -> {
            doctorData.put("specializations", new ArrayList<>((Set<String>) doctorData.get("specializations")));
            result.add(doctorData);
        });

        return result;
    }
    public List<Map<String, Object>> getMinorDetailsBySpecName(String specName) {
        List<Map<String, Object>> queryResult = doctorRepository.findBySpecName(specName);

        Map<String, Map<String, Object>> doctorDetailsMap = new LinkedHashMap<>();

        // Group specializations by doctor name
        queryResult.forEach(row -> {
            Integer docId = (Integer) row.get("docId");
            String doctorName = (String) row.get("doctorName");
            String specialization = (String) row.get("specialization");

            doctorDetailsMap.computeIfAbsent(doctorName, k -> {
                Map<String, Object> doctorData = new HashMap<>();
                doctorData.put("docId", docId);
                doctorData.put("doctorName", doctorName);
                doctorData.put("specializations", new LinkedHashSet<String>());
                return doctorData;
            });

            Set<String> specializations = (Set<String>) doctorDetailsMap.get(doctorName).get("specializations");
            specializations.add(specialization);
        });

        // Transform the grouped data into the desired JSON structure
        List<Map<String, Object>> result = new ArrayList<>();
        doctorDetailsMap.forEach((doctorName, doctorData) -> {
            List<String> orderedSpecializations = new ArrayList<>((Set<String>) doctorData.get("specializations"));

            // Ensure the query parameter specialization is first
            if (orderedSpecializations.remove(specName)) {
                orderedSpecializations.add(0, specName);
            }

            doctorData.put("specializations", orderedSpecializations);
            result.add(doctorData);
        });

        return result;
    }

    public List<String> getAllSpecializationNames() {
        return specRepository.findAllSpecializationNames();
    }

    public Map<String, Object> getDoctorDetailsById1(Integer doctorId) {
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

    public List<Map<String, Object>> findDoctorDetailsById(Integer id) {
        return doctorRepository.findDoctorDetailsById(id);
    }

    // In your service class
    public DoctorDetailsDTO getDoctorDetailsById(Integer doctorId) {
        Doctors doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        List<String> degrees = doctor.getDegrees().stream()
                .map(Degrees::getDegName)
                .collect(Collectors.toList());

        List<String> specializations = doctor.getSpecializations().stream()
                .map(Specializations::getSpecName)
                .collect(Collectors.toList());

        List<ScheduleDTO> schedules = doctor.getSchedule().stream()
                .map(sch -> new ScheduleDTO(
                        sch.getWeekDay(),
                        sch.getTimings().stream()
                                .map(t -> new TimingDTO(
                                        t.getTimeRange(),
                                        t.getNoOfPatients(),
                                        t.getCity().getCityName(),
                                        t.getCenter().getCenName()
                                )).collect(Collectors.toList())
                )).collect(Collectors.toList());

        return new DoctorDetailsDTO(
                doctor.getDocName(),
                degrees,
                specializations,
                schedules
        );
    }
}
