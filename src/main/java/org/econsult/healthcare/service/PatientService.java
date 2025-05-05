package org.econsult.healthcare.service;

import java.util.List;

import org.econsult.healthcare.entity.Appointment;
import org.econsult.healthcare.entity.Patient;

public interface PatientService {

    Patient getPatientById(Long patientId);

    List<Patient> getAllPatients();

    List<Appointment> getPatientAppointments(Long patientId);

    List<Appointment> getCompletedAppointmentsWithNotes(Long patientId);
}
