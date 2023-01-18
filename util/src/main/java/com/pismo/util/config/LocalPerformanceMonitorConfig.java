package com.pismo.util.config;


import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

/**
 * Necessario habilitar log com trace por causa do PerformanceMonitor. Buscar no log por running time
 */
@Profile("local")
@Configuration
@EnableAspectJAutoProxy
public class LocalPerformanceMonitorConfig {

    @Bean
    public PerformanceMonitorInterceptor performanceMonitorInterceptor() {
        return new PerformanceMonitorInterceptor(true);
    }

    @Bean
    public Advisor localPerformanceMonitorAdvisor() {

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("com.pismo.util.aspect.LocalPerformanceMonitorAspect.monitorMethodExecTimeAt()");

        return new DefaultPointcutAdvisor(pointcut, performanceMonitorInterceptor());
    }

}
