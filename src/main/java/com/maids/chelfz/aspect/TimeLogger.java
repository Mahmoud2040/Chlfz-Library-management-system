package com.maids.chelfz.aspect;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeLogger {

    Logger log = LoggerFactory.getLogger(TimeLogger.class);

    @Around(value = "execution(* com.maids.chelfz.service.impl..*(..))")
    public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder("TimeLogger: ");
        sb.append("{").append(joinPoint.getKind()).append("}\tfor: ").append(joinPoint.getSignature())
                .append("\twith arguments: ").append("(").append(StringUtils.join(joinPoint.getArgs(),",")).append(")");

        Object returnValue = joinPoint.proceed();
        log.info(sb.append(System.currentTimeMillis()-startTime).append("ms.").toString());

        return returnValue;

    }

}
