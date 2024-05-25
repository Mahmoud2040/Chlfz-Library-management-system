package com.maids.chelfz.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionLoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(ExceptionLoggingAspect.class);

    @AfterThrowing(pointcut = "execution(* com.maids.chelfz..*(..))", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception occurred in method: " + joinPoint.getSignature().toShortString());
        logger.error("Exception message: " + exception.getMessage());
        logger.error("Exception stack trace:", exception);
    }
}
