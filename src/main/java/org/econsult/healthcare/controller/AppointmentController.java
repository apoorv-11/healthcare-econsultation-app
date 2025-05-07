package org.econsult.healthcare.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.econsult.healthcare.entity.Appointment;
import org.econsult.healthcare.service.AppointmentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    
    @PostMapping("/book")
    public ResponseEntity<Appointment> bookAppointment(
            @RequestParam Long doctorId,
            @RequestParam Long patientId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
            @RequestParam String consultationNotes) {
        Appointment appointment = appointmentService.bookAppointment(doctorId, patientId, dateTime, consultationNotes);
        return ResponseEntity.status(201).body(appointment);
    }

    @PutMapping("/{id}/reschedule") 
    public ResponseEntity<Appointment> rescheduleAppointment(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime newDateTime) {
        Appointment appointment = appointmentService.rescheduleAppointment(id, newDateTime);
        return ResponseEntity.ok(appointment);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctorOnDate(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Appointment> appointments = appointmentService.getAppointmentsForDoctorOnDate(id, date);
        return ResponseEntity.ok(appointments);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Appointment> completeAppointment(
        @PathVariable Long id,
        @RequestBody Map<String, String> body) {
    String notes = body.get("consultationNotes");
    return ResponseEntity.ok(appointmentService.completeAppointment(id, notes));
    }

    @GetMapping("/patient/{id}/history")
    public ResponseEntity<List<Appointment>> getPatientConsultationHistory(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getPatientConsultationHistory(id));
    }
}
