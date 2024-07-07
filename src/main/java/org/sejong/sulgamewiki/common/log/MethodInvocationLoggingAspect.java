package org.sejong.sulgamewiki.common.log;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.sejong.sulgamewiki.common.exception.GlobalErrorCode;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class MethodInvocationLoggingAspect {
  @Around("@annotation(LogMethodInvocation) || @annotation(LogMonitoringInvocation)")
  public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();

//    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//    HttpServletRequest request = attributes.getRequest();
//    String requestId = (String) request.getAttribute("RequestID");

    log.info("[{}], Parameter: {}", signature.getMethod().getName(),  Arrays.toString(joinPoint.getArgs()));

    Object result = GlobalErrorCode.INTERNAL_SERVER_ERROR; // 문제가 생기면 ERROR 반환
    try {
      result = joinPoint.proceed();
    } finally {
      log.info("[{}], Result: {}", signature.getMethod().getName() , result);
    }

    return result;
  }

}
