package org.econsult.healthcare.repository;
import java.time.LocalDateTime;
import java.util.List;

import org.econsult.healthcare.entity.Appointment;
import org.econsult.healthcare.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctorId(Long doctorId);

    List<Appointment> findByDoctorIdAndDateTimeBetween(Long doctorId, LocalDateTime start, LocalDateTime end);

    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByPatientIdAndStatus(Long patientId, AppointmentStatus status);

    boolean existsByDoctorIdAndDateTime(Long doctorId, LocalDateTime dateTime);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND a.dateTime = :date AND a.status IN :statuses")
    List<Appointment> findByDoctorIdAndDateAndStatuses(
        @Param("doctorId") Long doctorId,
        @Param("date") LocalDateTime date,
        @Param("statuses") List<AppointmentStatus> statuses
    );

}
