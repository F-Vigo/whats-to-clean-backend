package com.example.whatstocleanbackend.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggerAspect {

    @Before("@annotation(com.example.whatstocleanbackend.aspects.annotation.LogDebug)")
    public void logDebug(JoinPoint joinPoint) {
        log.debug(String.format("Executing %s.", getMethodFullName(joinPoint)));
    }

    @Before("@annotation(com.example.whatstocleanbackend.aspects.annotation.LogInfo)")
    public void logInfo(JoinPoint joinPoint) {
        log.info(String.format("Executing %s.", getMethodFullName(joinPoint)));
    }

    @AfterThrowing(
            value = "@annotation(com.example.whatstocleanbackend.aspects.annotation.LogDebug) || " +
                    "@annotation(com.example.whatstocleanbackend.aspects.annotation.LogInfo)",
            throwing = "exception"
    )
    public void logException(JoinPoint joinPoint, Exception exception) {
        String exceptionName = exception.getClass().toString();
        exceptionName = exceptionName.substring(exceptionName.lastIndexOf("."));
        log.error(String.format(
                "%s thrown by method %s: %s.",
                exceptionName,
                getMethodFullName(joinPoint),
                exception.getMessage()));
    }



    private String getMethodFullName(JoinPoint joinPoint) {
        String signature = joinPoint.getSignature().toString();
        signature = signature.substring(signature.indexOf(" ")+1);
        return signature;
    }
}
