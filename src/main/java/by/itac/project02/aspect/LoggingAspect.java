package by.itac.project02.aspect;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
		
	private Logger log = LogManager.getRootLogger();
	
	@Pointcut("execution(* by.itac.project02.controller.*.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* by.itac.project02.service.*.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* by.itac.project02.dao.*.*.*(..))")
	private void forDaoPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {}
	
	@Before("forAppFlow()")
	private void before(JoinPoint joinPoint) {
		
		String method = joinPoint.getSignature().toShortString();
		log.log(Level.INFO, "=======>> in @Before: calling method: " + method);
		
		Object[] args = joinPoint.getArgs();
		for (Object tempArg : args) {
			log.log(Level.INFO, "=======>> argument: " + tempArg);

		}
		
	}

}
