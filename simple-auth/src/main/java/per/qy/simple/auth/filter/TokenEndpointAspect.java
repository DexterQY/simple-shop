package per.qy.simple.auth.filter;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import per.qy.simple.common.base.exception.BusinessException;
import per.qy.simple.common.base.exception.ExceptionCode;
import per.qy.simple.common.base.model.ResponseVo;

/**
 * 统一 /oauth/token 接口的响应体结构
 *
 * @author : QY
 * @date : 2022/5/29
 */
@Slf4j
@Component
@Aspect
public class TokenEndpointAspect {

    @Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
    public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object proceed = joinPoint.proceed();
            return ResponseEntity.ok(ResponseVo.success(((ResponseEntity<?>) proceed).getBody()));
        } catch (Exception e) {
            log.error("auth fail", e);
            if (e instanceof BusinessException) {
                BusinessException be = (BusinessException) e;
                return ResponseEntity.ok(ResponseVo.fail(be.getCode(), be.getMessage()));
            }
            return ResponseEntity.ok(ResponseVo.fail(ExceptionCode.UNAUTHORIZED, e.getMessage()));
        }
    }
}
