package org.econsult.healthcare.service;
import java.time.LocalDate;
import java.util.List;

import org.econsult.healthcare.entity.Appointment;
import org.econsult.healthcare.entity.Doctor;

public interface DoctorService {

    Doctor createDoctor(Doctor doctor);

    Doctor updateDoctor(Long doctorId, Doctor doctorDetails);

    void deleteDoctor(Long doctorId);

    Doctor getDoctorById(Long doctorId);

    List<Doctor> getAllDoctors();

    List<Appointment> getDoctorAppointments(Long doctorId);

    List<Appointment> getAppointmentsByDoctorAndDate(Long doctorId, LocalDate dateTime);
}
