package org.econsult.healthcare;

import java.time.LocalDateTime;
import java.util.List;

import org.econsult.healthcare.entity.Appointment;
import org.econsult.healthcare.entity.AppointmentStatus;
import org.econsult.healthcare.entity.Doctor;
import org.econsult.healthcare.entity.Patient;
import org.econsult.healthcare.repository.AppointmentRepository;
import org.econsult.healthcare.repository.DoctorRepository;
import org.econsult.healthcare.repository.PatientRepository;
import org.econsult.healthcare.service.AppointmentService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@ActiveProfiles("test")
class HealthcareApplicationTests {
    @Autowired
    private DoctorRepository doctorRepo;
    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentRepository appointmentRepo;

    private Doctor doctor;
    private Patient patient;


    @BeforeEach
    void setup() {
        doctor = new Doctor();
        doctor.setName("Dr. Smith");
        // Set other required fields
        doctor = doctorRepo.save(doctor);

        patient = new Patient();
        patient.setName("John Doe");
        // Set other required fields
        patient = patientRepo.save(patient);
    }

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
