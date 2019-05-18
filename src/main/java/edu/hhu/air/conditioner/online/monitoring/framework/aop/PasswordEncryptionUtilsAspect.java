package edu.hhu.air.conditioner.online.monitoring.framework.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 *
 *@author 覃国强
 *@date 2019-03-05
 */
@Aspect
@Component
public class PasswordEncryptionUtilsAspect {


    @Pointcut("execution(* generate*(..) throws Exception+)")
    public void passwordGenerate() {}

    @Before("passwordGenerate()")
    public void before() {
        System.out.println("I am before");
    }


}
