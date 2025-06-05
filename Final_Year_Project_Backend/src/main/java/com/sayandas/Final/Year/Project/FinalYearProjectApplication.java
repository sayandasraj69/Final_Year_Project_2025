package com.sayandas.Final.Year.Project;

import com.sayandas.Final.Year.Project.Entities.DoctorEntities.*;
import com.sayandas.Final.Year.Project.Repositories.DoctorRepositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class FinalYearProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalYearProjectApplication.class, args);
    }

//    @Bean
    public CommandLineRunner commandLineRunner(DoctorRepository doctorRepository, SpecRepository specRepository,
                                               CityRepository cityRepository, CenterRepository centerRepository, QualRepository qualRepository) {
        return args -> {
            List<Qualifications> degrees = List.of(
                    qualRepository.findById(1).get(),
                    qualRepository.findById(2).get()
            );
            List<Specializations> specializations = List.of(
                    specRepository.findById(1).get(),
                    specRepository.findById(2).get()
            );
            City city = cityRepository.findById(1).get();
            Centers center = centerRepository.findById(1).get();
            City city1 = cityRepository.findById(1).get();
            Centers center1 = centerRepository.findById(2).get();
            List<Timings> timings = List.of(
                    Timings.builder()
                            .timeRange("10:00 - 12:00")
                            .noOfPatients(20)
                            .city(city)
                            .center(center)
                            .build(),
                    Timings.builder()
                            .timeRange("12:00 - 14:00")
                            .noOfPatients(20)
                            .city(city1)
                            .center(center1)
                            .build()
            );


            List<Schedule> schedule = List.of(
                    Schedule.builder()
                            .weekDay("Monday")
                            .timings(timings)
                            .build()
                    );
            Doctors doctor = Doctors.builder()
                    .docName("Sayan Das")
                    .docEmail("sayan@gmail.com")
                    .docPhn("123456")
                    .about("Hi I am Sayan ")
                    .qualifications(degrees)
                    .specializations(specializations)
                    .schedule(schedule)
                .build();

            schedule.forEach(sch -> sch.setDoctor(doctor));
            timings.forEach(t -> t.setSchedule(schedule.get(0))); // assuming one schedule

            doctorRepository.save(doctor);
        };


    }

//    @Bean
    public CommandLineRunner commandLineRunner1(AppointmentsRepository appointmentsRepository) {
        return args -> {
            Appointments appointment = Appointments.builder()
                    .appDate("21-05-2015")
                    .appWeekDay("Wednesday")
                    .appTimeRange("10:30 - 12:30")
                    .appPatName("Sayan Das")
                    .build();

            List<Appointments> appointmentsList = new ArrayList<>();
            appointmentsList.add(appointment);
            appointmentsRepository.saveAll(appointmentsList);
        };
    }

//    @Bean
    public CommandLineRunner saveSpecs(SpecRepository specRepository) {
        return args -> {
            List<Specializations> specializations = List.of(
                    Specializations.builder().specName("General Physician").build(),
                    Specializations.builder().specName("Neurology").build(),
                    Specializations.builder().specName("Cardiology").build(),
                    Specializations.builder().specName("Orthopedics").build(),
                    Specializations.builder().specName("Pediatrics").build(),
                    Specializations.builder().specName("Dermatology").build(),
                    Specializations.builder().specName("Oncology").build(),
                    Specializations.builder().specName("Psychiatry").build(),
                    Specializations.builder().specName("Radiology").build(),
                    Specializations.builder().specName("Gastroenterology").build(),
                    Specializations.builder().specName("Endocrinology").build()
            );
            specRepository.saveAll(specializations);
        };
    }

//    @Bean
    public CommandLineRunner saveDegrees(QualRepository degRepository) {
        return args -> {
            List<Qualifications> degrees = List.of(
                    Qualifications.builder().qualName("MS").build(),
                    Qualifications.builder().qualName("MBBS").build(),
                    Qualifications.builder().qualName("MD").build(),
                    Qualifications.builder().qualName("DM").build()
            );
            degRepository.saveAll(degrees);
        };
    }

//    @Bean
    public CommandLineRunner saveCities(CityRepository cityRepository) {
        return args -> {
            List<City> cities = List.of(
                    City.builder().cityName("Kolkata").build(),
                    City.builder().cityName("Siliguri").build(),
                    City.builder().cityName("Asansol").build(),
                    City.builder().cityName("Durgapur").build(),
                    City.builder().cityName("Bardhaman").build(),
                    City.builder().cityName("Howrah").build(),
                    City.builder().cityName("Kalyani").build(),
                    City.builder().cityName("Malda").build(),
                    City.builder().cityName("Murshidabad").build(),
                    City.builder().cityName("Kharagpur").build()
            );
            cityRepository.saveAll(cities);
        };
    }

//    @Bean
    public CommandLineRunner saveCenters(CenterRepository centerRepository, CityRepository cityRepository) {
        return args -> {
            City Kolkata = cityRepository.findById(1).get();
            City Siliguri = cityRepository.findById(2).get();
            City Asansol = cityRepository.findById(3).get();
            City Durgapur = cityRepository.findById(4).get();
            City Bardhaman = cityRepository.findById(5).get();
            City Howrah = cityRepository.findById(6).get();
            City Kalyani = cityRepository.findById(7).get();
            City Malda = cityRepository.findById(8).get();
            City Murshidabad = cityRepository.findById(9).get();
            City Kharagpur = cityRepository.findById(10).get();
            List<Centers> centers = List.of(
                    Centers.builder().cenName("Apollo Gleneagles Hospitals").city(Kolkata).build(),
                    Centers.builder().cenName("Medica Superspecialty Hospital").city(Kolkata).build(),
                    Centers.builder().cenName("Fortis Medical Centre").city(Kolkata).build(),
                    Centers.builder().cenName("Ruby General Hospital").city(Kolkata).build(),
                    Centers.builder().cenName("Peerless Hospital").city(Kolkata).build(),

                    Centers.builder().cenName("Desun Hospital").city(Siliguri).build(),
                    Centers.builder().cenName("Medica North Bengal Clinic").city(Siliguri).build(),
                    Centers.builder().cenName("Neotia Getwel Healthcare Centre").city(Siliguri).build(),
                    Centers.builder().cenName("Anandloke Hospital & Neurosciences Centre Pvt Ltd.").city(Siliguri).build(),
                    Centers.builder().cenName("Mukherjee Hospital").city(Siliguri).build(),

                    Centers.builder().cenName("The Mission Hospital").city(Durgapur).build(),
                    Centers.builder().cenName("HealthWorld Hospitals").city(Durgapur).build(),
                    Centers.builder().cenName("Vivekananda Hospital").city(Durgapur).build(),
                    Centers.builder().cenName("IQ City Medical College Hospital").city(Durgapur).build(),
                    Centers.builder().cenName("Anandaloke Hospital").city(Durgapur).build(),

                    Centers.builder().cenName("Sonoscan Healthcare").city(Malda).build(),
                    Centers.builder().cenName("Dishari Health Point Pvt. Ltd.").city(Malda).build(),
                    Centers.builder().cenName("Norbe Hospital").city(Malda).build(),
                    Centers.builder().cenName("Mango City Health Point and Diagnostic Centre").city(Malda).build(),
                    Centers.builder().cenName("Malda Medical College and Hospital").city(Malda).build(),

                    Centers.builder().cenName("B C Roy Technology Hospital (IIT Campus)").city(Kharagpur).build(),
                    Centers.builder().cenName("Lifeline Nursing Home").city(Kharagpur).build(),
                    Centers.builder().cenName("Kharagpur Sub-divisional Hospital").city(Kharagpur).build(),
                    Centers.builder().cenName("Sanjeevani Hospital").city(Kharagpur).build(),
                    Centers.builder().cenName("Sarada Diagnostic & Polyclinic").city(Kharagpur).build(),

                    Centers.builder().cenName("Berhampore New General Hospital").city(Murshidabad).build(),
                    Centers.builder().cenName("Kothari Medical Centre (Branch)").city(Murshidabad).build(),
                    Centers.builder().cenName("Murshidabad Medical College and Hospital").city(Murshidabad).build(),
                    Centers.builder().cenName("BMRC Hospital").city(Murshidabad).build(),
                    Centers.builder().cenName("Disha Eye Hospital (Branch)").city(Murshidabad).build(),

                    Centers.builder().cenName("AIIMS Kalyani").city(Kalyani).build(),
                    Centers.builder().cenName("Kalyani General Hospital (KGH)").city(Kalyani).build(),
                    Centers.builder().cenName("Rainbow Specialty Hospital").city(Kalyani).build(),
                    Centers.builder().cenName("Gandhi Memorial Hospital").city(Kalyani).build(),
                    Centers.builder().cenName("Majumder Millenium Nursing Home & RDC Pvt Ltd").city(Kalyani).build(),

                    Centers.builder().cenName("Narayana Superspeciality Hospital").city(Howrah).build(),
                    Centers.builder().cenName("ILS Hospitals").city(Howrah).build(),
                    Centers.builder().cenName("ADS Hospital").city(Howrah).build(),
                    Centers.builder().cenName("Sanjiban Hospital").city(Howrah).build(),
                    Centers.builder().cenName("Ambika Eyecare Hospital").city(Howrah).build(),

                    Centers.builder().cenName("KIMS Hospital").city(Bardhaman).build(),
                    Centers.builder().cenName("BIMS Hospital").city(Bardhaman).build(),
                    Centers.builder().cenName("Swasthya Niketan Hospital").city(Bardhaman).build(),
                    Centers.builder().cenName("Disha Eye Hospitals").city(Bardhaman).build(),
                    Centers.builder().cenName("The Mission Hospital").city(Bardhaman).build(),

                    Centers.builder().cenName("HLG Memorial Hospital").city(Asansol).build(),
                    Centers.builder().cenName("HealthWorld Hospitals").city(Asansol).build(),
                    Centers.builder().cenName("Burnpur Hospital").city(Asansol).build(),
                    Centers.builder().cenName("Central Hospital Kalla").city(Asansol).build(),
                    Centers.builder().cenName("Asansol District Hospital").city(Asansol).build()

            );
            centerRepository.saveAll(centers);
        };
    }
}

//"doctorName":"Sayan Das",
//"deegree": [MBBS, MS],
//"specialization":[General Physician, Neurology],
//"schedule":[
//        "scheduleDay":"Monday",
//        "timings":[
//                "timingRange":"10:00 - 12:00",
//                "noOfPatients":"20",
//                "city":"Kolkata",
//                "center":"Apollo Gleneagles Hospitals"
//                ],
//                [
//                "timingRange":"10:00 - 12:00",
//                "noOfPatients":"20",
//                "city":"Kolkata",
//                "center":"Apollo Gleneagles Hospitals"
//                ]










