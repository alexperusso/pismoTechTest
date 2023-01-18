package com.pismo.util.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("local")
@Aspect
@Component
public class LocalPerformanceMonitorAspect {

    @Pointcut("@annotation(com.pismo.util.annotation.LocalPerformanceTrace)")
    public void monitorMethodExecTimeAt() {
    }

}
