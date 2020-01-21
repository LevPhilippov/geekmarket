package lev.philippov.geekmarket.Aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution (* lev.philippov.geekmarket.*.*.*(..))")
    public void simpleLog(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        logger.info("Вызван метод: "+ methodSignature);
    }
}
