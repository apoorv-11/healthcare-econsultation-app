package org.econsult.healthcare;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.econsult.healthcare.entity.Appointment;
import org.econsult.healthcare.entity.AppointmentStatus;
import org.econsult.healthcare.entity.Doctor;
import org.econsult.healthcare.entity.Patient;
import org.econsult.healthcare.repository.AppointmentRepository;
import org.econsult.healthcare.repository.DoctorRepository;
import org.econsult.healthcare.repository.PatientRepository;
import org.econsult.healthcare.service.AppointmentService;
import org.econsult.healthcare.service.DoctorService;
import org.econsult.healthcare.service.PatientService;
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
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentRepository appointmentRepo;

    private Doctor doctor;
    private Patient patient;

    @BeforeEach
    void setup() {
        doctor = new Doctor("Dr. Smith", "Cardiology");
        doctor = doctorRepo.save(doctor);

        patient = new Patient("John Doe", "john@example.com");
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

    @Test
    void testFetchDoctorAppointments() {
        appointmentService.bookAppointment(doctor.getId(), patient.getId(), LocalDateTime.now().plusHours(2), "Check-up");
        appointmentService.bookAppointment(doctor.getId(), patient.getId(), LocalDateTime.now().plusHours(3), "Routine");

        List<Appointment> appointments = doctorService.getDoctorAppointments(doctor.getId());
        assertEquals(2, appointments.size());
    }

    @Test
    void testFetchDoctorAppointmentsOnDate() {
        LocalDate today = LocalDate.now();
        LocalDateTime appointmentTime = LocalDateTime.of(today, LocalTime.of(10, 0));
        appointmentService.bookAppointment(doctor.getId(), patient.getId(), appointmentTime, "Daily Check");

        List<Appointment> appointments = doctorService.getAppointmentsByDoctorAndDate(doctor.getId(), today);
        assertEquals(1, appointments.size());
    }

    @Test
    void testFetchPatientAppointments() {
        appointmentService.bookAppointment(doctor.getId(), patient.getId(), LocalDateTime.now().plusDays(2), "Nutrition consult");
        appointmentService.bookAppointment(doctor.getId(), patient.getId(), LocalDateTime.now().plusDays(3), "Eye exam");

        List<Appointment> appointments = patientService.getPatientAppointments(patient.getId());
        assertEquals(2, appointments.size());
    }
}