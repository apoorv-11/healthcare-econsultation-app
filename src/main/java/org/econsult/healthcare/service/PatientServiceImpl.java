package org.econsult.healthcare.service;

import java.util.List;

import org.econsult.healthcare.entity.Appointment;
import org.econsult.healthcare.entity.AppointmentStatus;
import org.econsult.healthcare.entity.Patient;
import org.econsult.healthcare.exception.ResourceNotFoundException;
import org.econsult.healthcare.repository.AppointmentRepository;
import org.econsult.healthcare.repository.PatientRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepo;
    private final AppointmentRepository appointmentRepo;

    @Override
    public Patient createPatient(Patient patient) {
        return patientRepo.save(patient);
    }

    @Override
    public Patient updatePatient(Long patientId, Patient patientDetails) {
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        patient.setName(patientDetails.getName());
        patient.setContactInfo(patientDetails.getContactInfo());
        return patientRepo.save(patient);
    }

    @Override
    public void deletePatient(Long patientId) {
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        patientRepo.delete(patient);
    }
    
    @Override
    public Patient getPatientById(Long patientId) {
        return patientRepo.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }

    @Override
    public List<Appointment> getPatientAppointments(Long patientId) {
        return appointmentRepo.findByPatientId(patientId);
    }

    @Override
    public List<Appointment> getCompletedAppointmentsWithNotes(Long patientId) {
        return appointmentRepo.findByPatientIdAndStatus(patientId, AppointmentStatus.COMPLETED);
    }
}

