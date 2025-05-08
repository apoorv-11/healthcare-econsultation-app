package org.econsult.healthcare;

import java.util.Arrays;
import java.util.List;

import org.econsult.healthcare.entity.Doctor;
import org.econsult.healthcare.entity.Patient;
import org.econsult.healthcare.repository.DoctorRepository;
import org.econsult.healthcare.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class HealthcareApplication implements CommandLineRunner {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public static void main(String[] args) {
        SpringApplication.run(HealthcareApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // Seed Doctors
        if (doctorRepository.count() == 0) {
            List<Doctor> doctors = Arrays.asList(
                new Doctor("Dr. Alice Smith", "Cardiology"),
                new Doctor("Dr. Bob Johnson", "Neurology"),
                new Doctor("Dr. Clara Lee", "Pediatrics")
            );
            doctorRepository.saveAll(doctors);
            log.info("Sample doctors seeded.");
        }

        // Seed Patients
        if (patientRepository.count() == 0) {
            List<Patient> patients = Arrays.asList(
                new Patient("John Doe", "john@example.com"),
                new Patient("Jane Roe", "jane@example.com")
            );
            patientRepository.saveAll(patients);
            log.info("Sample patients seeded.");
        }

        log.info("Healthcare service is running...");
    }
}
