package org.econsult.healthcare.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.econsult.healthcare.entity.Appointment;
import org.econsult.healthcare.entity.AppointmentStatus;
import org.econsult.healthcare.entity.Doctor;
import org.econsult.healthcare.entity.Patient;
import org.econsult.healthcare.repository.AppointmentRepository;
import org.econsult.healthcare.repository.DoctorRepository;
import org.econsult.healthcare.repository.PatientRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepo;
    private final DoctorRepository doctorRepo;
    private final PatientRepository patientRepo;

    @Override
    public Appointment bookAppointment(Long doctorId, Long patientId, LocalDateTime dateTime) {
        if (appointmentRepo.existsByDoctorIdAndDateTime(doctorId, dateTime)) {
            throw new RuntimeException("Slot already booked.");
        }

        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDateTime(dateTime);
        appointment.setStatus(AppointmentStatus.BOOKED);

        return appointmentRepo.save(appointment);
    }

    @Override
    public Appointment rescheduleAppointment(Long appointmentId, LocalDateTime newDateTime) {
        Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (appointmentRepo.existsByDoctorIdAndDateTime(appointment.getDoctor().getId(), newDateTime)) {
            throw new RuntimeException("Slot already booked.");
        }

        appointment.setDateTime(newDateTime);
        return appointmentRepo.save(appointment);
    }

    @Override
    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepo.save(appointment);
    }

    @Override
    public List<Appointment> getAppointmentsForDoctorOnDate(Long doctorId, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay().minusSeconds(1);
        return appointmentRepo.findByDoctorIdAndDateTimeBetween(doctorId, start, end);
    }

    @Override
    public List<Appointment> getPatientConsultationHistory(Long patientId) {
        return appointmentRepo.findByPatientIdAndStatus(patientId, AppointmentStatus.COMPLETED);
    }
}
