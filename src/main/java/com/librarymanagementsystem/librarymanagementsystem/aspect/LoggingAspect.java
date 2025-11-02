package com.librarymanagementsystem.librarymanagementsystem.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String POINTCUT = "within(com.librarymanagementsystem.librarymanagementsystem.controller..*) || " +
                                           "within(com.librarymanagementsystem.librarymanagementsystem.service..*) || " +
                                           "within(com.librarymanagementsystem.librarymanagementsystem.repository..*)";

    @Before(POINTCUT)
    public void logBefore(JoinPoint joinPoint) {
        log.info("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = POINTCUT, returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = POINTCUT, throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with cause = '{}' and exception = '{}'", joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL", e.getMessage(), e);
    }
}
