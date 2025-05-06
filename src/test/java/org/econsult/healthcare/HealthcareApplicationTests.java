package org.econsult.healthcare;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.econsult.healthcare.entity.Appointment;
import org.econsult.healthcare.entity.AppointmentStatus;
import org.econsult.healthcare.entity.Doctor;
import org.econsult.healthcare.entity.Patient;
import org.econsult.healthcare.repository.AppointmentRepository;
import org.econsult.healthcare.service.AppointmentService;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class AppointmentServiceTest {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentRepository appointmentRepo;

    private Doctor doctor;
    private Patient patient;

    @Test
    void testBookAppointment() {
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        Appointment appt = appointmentService.bookAppointment(
            doctor.getId(), patient.getId(), dateTime, "Initial consultation"
        );
        assertNotNull(appt.getId());
        assertEquals(AppointmentStatus.BOOKED, appt.getStatus());
    }

    @Test
    void testConsultationHistory() {
        Appointment a = appointmentService.bookAppointment(
            doctor.getId(), patient.getId(), LocalDateTime.now(), "Follow-up visit"
        );
        a.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepo.save(a);
        List<Appointment> history = appointmentService.getPatientConsultationHistory(patient.getId());
        assertEquals(1, history.size());
    }
}
