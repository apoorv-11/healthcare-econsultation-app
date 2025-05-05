package org.econsult.healthcare.repository;
import org.econsult.healthcare.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}

