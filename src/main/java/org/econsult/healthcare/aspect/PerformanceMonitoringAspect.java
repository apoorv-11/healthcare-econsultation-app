package org.econsult.healthcare.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class PerformanceMonitoringAspect {

    @Around("execution(* org.econsult.healthcare.service.AppointmentService.bookAppointment(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;
        log.info("Execution time of {}: {} ms", joinPoint.getSignature(), duration);
        return result;
    }

    @AfterThrowing(pointcut = "execution(* org.econsult.healthcare.service.AppointmentService.*(..))", throwing = "ex")
    public void logException(Exception ex) {
        log.error("Exception occurred in AppointmentService: {}", ex.getMessage());
    }
}
