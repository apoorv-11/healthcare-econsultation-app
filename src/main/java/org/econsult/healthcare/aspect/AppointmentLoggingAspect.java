package org.econsult.healthcare.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class AppointmentLoggingAspect {

    @Before("execution(* org.econsult.healthcare.service.AppointmentService.bookAppointment(..))")
    public void logBeforeBooking(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long doctorId = (Long) args[0];
        Long patientId = (Long) args[1];
        log.info("Booking appointment - Doctor ID: {}, Patient ID: {}", doctorId, patientId);
    }

    @Before("execution(* org.econsult.healthcare.service.AppointmentService.cancelAppointment(..))")
    public void logBeforeCancellation(JoinPoint joinPoint) {
        Long appointmentId = (Long) joinPoint.getArgs()[0];
        log.info("Cancelling appointment - Appointment ID: {}", appointmentId);
    }
}
