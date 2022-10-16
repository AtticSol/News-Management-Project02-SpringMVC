package by.itac.project02.aspect;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import by.itac.project02.controller.JSPPageName;
import by.itac.project02.dao.NewsDAOException;
import by.itac.project02.service.ServiceException;

@Aspect
@Component
public class AfterThrowingAspect {
	private Logger log = LogManager.getRootLogger();

	@AfterThrowing(pointcut = "execution(* by.itac.project02.dao.impl.NewsDAOImpl.*(..))", throwing = "theExc")
	private void afterThrowingException(JoinPoint joinPoint, Throwable theExc) throws NewsDAOException {
		
		String method = joinPoint.getSignature().toShortString();
		log.log(Level.WARN, "=======>> in @AfterThrowing: calling method: " + method);
		log.log(Level.INFO, "=======>> The exception is: " + theExc);
		
		throw new NewsDAOException(theExc.getMessage());
	}

	@AfterThrowing(pointcut = "execution(* by.itac.project02.service.impl.NewsServiceImpl.*(..))", throwing = "theExc")
	private void afterThrowingNewsDaoException(JoinPoint joinPoint, Throwable theExc) throws ServiceException {
		
		String method = joinPoint.getSignature().toShortString();
		log.log(Level.WARN, "=======>> in @AfterThrowing: calling method: " + method);
		log.log(Level.INFO, "=======>> The exception is: " + theExc);
		log.log(Level.INFO, "=======>> The exception is: " + theExc.getCause());
		throw new ServiceException(theExc.getMessage());
	}
	
	
//	@AfterThrowing(pointcut = "execution(* by.itac.project02.controller.impl.BasePage.*(..))", throwing = "theExc")
//	private void afterThrowingNewsServiceException(JoinPoint joinPoint, Throwable theExc) {
//		
//		String method = joinPoint.getSignature().toShortString();
//		log.log(Level.WARN, "=======>> in @AfterThrowing: calling method: " + method);
//		log.log(Level.INFO, "=======>> The exception is: " + theExc);
//		log.log(Level.INFO, "=======>> The exception is: " + theExc.getCause());
//		
////		result = JSPPageName.ERROR_PAGE;
////		return result;
//	}
	
	
	@Around("execution(* by.itac.project02.controller.impl.BasePage.*(..))")
	private String aroundControllerBasePage(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			joinPoint.proceed();
		} catch (Exception theExc) {
			String method = joinPoint.getSignature().toShortString();
			log.log(Level.WARN, "=======>> in @Around: calling method: " + method);
			log.log(Level.INFO, "=======>> The exception is: " + theExc);
			return JSPPageName.ERROR_PAGE;

		}
		return JSPPageName.BASE_PAGE;
	}
}
