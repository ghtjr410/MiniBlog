package com.miniblog.back.member.aspect;

import com.miniblog.back.common.exception.DuplicateException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class MemberServiceAspect {

    @Pointcut("execution(* com.miniblog.back.member.service.MemberService.*(..))")
    public void memberServiceMethods() {}

    @AfterReturning("memberServiceMethods()")
    public void logServiceSuccess(JoinPoint joinPoint) {
        log.info("성공: {}", joinPoint.getSignature());
    }

    @Around("memberServiceMethods()")
    public Object logServiceExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (DuplicateException | DataAccessException ex) {
            // 비즈니스 예외 및 데이터 접근 예외는 그대로 던짐 (글로벌 예외 처리기에서 처리)
            throw ex;
        } catch (Exception ex) {
            // 예측하지 못한 예외를 로깅하고 RuntimeException으로 래핑하여 던짐 (글로벌 예외 처리기에서 처리)
            log.error("Unexpected exception in {}.{}() with cause = {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    ex.getCause() != null ? ex.getCause() : "NULL",
                    ex);
            throw new RuntimeException("예상치 못한 예외", ex);
        }
    }
}
