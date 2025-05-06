package org.econsult.healthcare.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.econsult.healthcare.entity.Appointment;
public interface AppointmentService {

    Appointment bookAppointment(Long doctorId, Long patientId, LocalDateTime dateTime, String consulationNotes);

    Appointment rescheduleAppointment(Long appointmentId, LocalDateTime newDateTime);

    void cancelAppointment(Long appointmentId);

    List<Appointment> getAppointmentsForDoctorOnDate(Long doctorId, LocalDate date);

    List<Appointment> getPatientConsultationHistory(Long patientId);

    Appointment completeAppointment(Long appointmentId, String consultationNotes);

}
