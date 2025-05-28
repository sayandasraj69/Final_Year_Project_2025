package com.sayandas.Final.Year.Project;

import com.sayandas.Final.Year.Project.Entities.DoctorEntities.*;
import com.sayandas.Final.Year.Project.Repositories.DoctorRepositories.AppointmentsRepository;
import com.sayandas.Final.Year.Project.Repositories.DoctorRepositories.Doctor_Repository;
import com.sayandas.Final.Year.Project.Repositories.DoctorRepositories.Spec_Repository;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Bean
	public CommandLineRunner commandLineRunner(Doctor_Repository doctorRepository, Spec_Repository specRepository){
		return args -> {
			Specializations specs = Specializations.builder()
					.specName("General Physician")
					.build();
			specRepository.save(specs);
			Specializations specs1 = Specializations.builder()
					.specName("Cardiology")
					.build();
			specRepository.save(specs1);
			Specializations specs2 = Specializations.builder()
					.specName("Neurology")
					.build();
			specRepository.save(specs2);

			Specializations specialization = specRepository.findBySpecName("Cardiology");
			Specializations specialization1 = specRepository.findBySpecName("General Physician");
			Specializations specialization2 = specRepository.findBySpecName("Neurology");

			List<Specializations> specsList = new ArrayList<>();
			specsList.add(specialization);
			specsList.add(specialization1);

			List<Specializations> specsList1 = new ArrayList<>();
			specsList1.add(specialization1);
			specsList1.add(specialization2);

			List<Specializations> specsList2 = new ArrayList<>();
			specsList2.add(specialization2);
			specsList2.add(specialization);

			Timings montime = new Timings();
			montime.setTimeRange("10:30 - 12:30");
			montime.setNoOfPatients(20);

			Timings montime1 = new Timings();
			montime1.setTimeRange("18:30 - 20:30");
			montime1.setNoOfPatients(20);

			Timings tuestime = new Timings();
			tuestime.setTimeRange("10:30 - 12:30");
			tuestime.setNoOfPatients(20);

			Timings tuestime1 = new Timings();
			tuestime1.setTimeRange("18:30 - 20:30");
			tuestime1.setNoOfPatients(20);

			List<Timings> monTimings = List.of(montime, montime1);
			List<Timings> tuesTimings = List.of(tuestime, tuestime1);

			Schedule monSchedule = new Schedule();
			monSchedule.setWeekDay("Monday");
			monSchedule.setTimings(monTimings);

			Schedule tuesSchedule = new Schedule();
			tuesSchedule.setWeekDay("Tuesday");
			tuesSchedule.setTimings(tuesTimings);

			monTimings.forEach(timing -> timing.setSchedule(monSchedule));
			tuesTimings.forEach(timing -> timing.setSchedule(tuesSchedule));

			List<Schedule> schedules = List.of(monSchedule, tuesSchedule);
			Doctors doctor = Doctors.builder()
					.docName("Sayan Das")
					.docEmail("sayandas@gmail.com")
					.docPhn("1234567890")
					.specializations(specsList)
					.schedule(schedules)
					.build();

			schedules.forEach(schedule -> schedule.setDoctor(doctor));
			doctorRepository.save(doctor);


			Timings montime10 = new Timings();
			montime10.setTimeRange("10:30 - 12:30");
			montime10.setNoOfPatients(20);

			Timings montime11 = new Timings();
			montime11.setTimeRange("18:30 - 20:30");
			montime11.setNoOfPatients(20);

			Timings tuestime10 = new Timings();
			tuestime10.setTimeRange("10:30 - 12:30");
			tuestime10.setNoOfPatients(20);

			Timings tuestime11 = new Timings();
			tuestime11.setTimeRange("18:30 - 20:30");
			tuestime11.setNoOfPatients(20);

			List<Timings> monTimings1 = List.of(montime10, montime11);
			List<Timings> tuesTimings1 = List.of(tuestime10, tuestime11);

			Schedule monSchedule1 = new Schedule();
			monSchedule1.setWeekDay("Monday");
			monSchedule1.setTimings(monTimings1);

			Schedule tuesSchedule1 = new Schedule();
			tuesSchedule1.setWeekDay("Tuesday");
			tuesSchedule1.setTimings(tuesTimings1);

			monTimings1.forEach(timing -> timing.setSchedule(monSchedule1));
			tuesTimings1.forEach(timing -> timing.setSchedule(tuesSchedule1));

			List<Schedule> schedules1 = List.of(monSchedule1, tuesSchedule1);
			Doctors doctor1 = Doctors.builder()
					.docName("Garga Saha")
					.docEmail("garga@gmail.com")
					.docPhn("1234567890")
					.specializations(specsList1)
					.schedule(schedules1)
					.build();

			schedules1.forEach(schedule -> schedule.setDoctor(doctor1));
			doctorRepository.save(doctor1);

			Timings montime20 = new Timings();
			montime20.setTimeRange("10:30 - 12:30");
			montime20.setNoOfPatients(20);

			Timings montime21 = new Timings();
			montime21.setTimeRange("18:30 - 20:30");
			montime21.setNoOfPatients(20);

			Timings tuestime20 = new Timings();
			tuestime20.setTimeRange("10:30 - 12:30");
			tuestime20.setNoOfPatients(20);

			Timings tuestime21 = new Timings();
			tuestime21.setTimeRange("18:30 - 20:30");
			tuestime21.setNoOfPatients(20);

			List<Timings> monTimings2 = List.of(montime20, montime21);
			List<Timings> tuesTimings2 = List.of(tuestime20, tuestime21);

			Schedule monSchedule2 = new Schedule();
			monSchedule2.setWeekDay("Monday");
			monSchedule2.setTimings(monTimings2);

			Schedule tuesSchedule2 = new Schedule();
			tuesSchedule2.setWeekDay("Tuesday");
			tuesSchedule2.setTimings(tuesTimings2);

			List<Schedule> schedules2 = List.of(monSchedule2, tuesSchedule2);

			monTimings2.forEach(timing -> timing.setSchedule(monSchedule2));
			tuesTimings2.forEach(timing -> timing.setSchedule(tuesSchedule2));
			Doctors doctor2 = Doctors.builder()
					.docName("Soumashree Haldar")
					.docEmail("soumo@gmail.com")
					.docPhn("1234567890")
					.specializations(specsList2)
					.schedule(schedules2)
					.build();

			schedules2.forEach(schedule -> schedule.setDoctor(doctor2));
			doctorRepository.save(doctor2);
		};


	}

//	@Bean
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





}


