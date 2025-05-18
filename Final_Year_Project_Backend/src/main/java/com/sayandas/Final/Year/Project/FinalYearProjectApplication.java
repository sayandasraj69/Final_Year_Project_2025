package com.sayandas.Final.Year.Project;

import com.sayandas.Final.Year.Project.Entities.DoctorEntities.Doctors;
import com.sayandas.Final.Year.Project.Entities.DoctorEntities.Specializations;
import com.sayandas.Final.Year.Project.Repositories.DoctorRepositories.Doctor_Repository;
import com.sayandas.Final.Year.Project.Repositories.DoctorRepositories.Spec_Repository;
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

//	@Bean
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

			Doctors doctor = Doctors.builder()
					.docName("Sayan Das")
					.docEmail("sayandas@gmail.com")
					.docPhn("1234567890")
					.specializations(specsList)
					.build();
			doctorRepository.save(doctor);

			Doctors doctor1 = Doctors.builder()
					.docName("Garga Saha")
					.docEmail("garga@gmail.com")
					.docPhn("1234567890")
					.specializations(specsList1)
					.build();
			doctorRepository.save(doctor1);

			Doctors doctor2 = Doctors.builder()
					.docName("Soumashree Haldar")
					.docEmail("soumo@gmail.com")
					.docPhn("1234567890")
					.specializations(specsList2)
					.build();
			doctorRepository.save(doctor2);
		};


	}

//	@Bean
	public CommandLineRunner commandLineRunner1(Doctor_Repository doctorRepository) {
		List<Specializations> specializations = new ArrayList<>();
		Specializations specializations1 = new Specializations();
		specializations1.setSpecName("MBBS");
		Specializations specialization2 = new Specializations();
		specialization2.setSpecName("Cardiology");
		specializations.add(specializations1);
		specializations.add(specialization2);

		return args -> {
			Doctors doctor = Doctors.builder()
					.docName("Soumashree Haldar")
					.docEmail("soumo@gmail.com")
					.docPhn("1234567890")
					.specializations(specializations)
					.build();
			doctorRepository.save(doctor);
		};
	}





}


