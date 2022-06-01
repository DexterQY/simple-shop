package per.qy.simple.auth.filter;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import per.qy.simple.common.base.constant.SimpleConstant;
import per.qy.simple.common.base.exception.BusinessException;
import per.qy.simple.common.base.exception.ExceptionCode;
import per.qy.simple.common.base.model.ResponseVo;
import per.qy.simple.common.core.util.RequestUtil;

import javax.servlet.http.HttpServletRequest;

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
        ResponseVo vo;
        try {
            Object proceed = joinPoint.proceed();
            vo = ResponseVo.success(((ResponseEntity<?>) proceed).getBody());
        } catch (Exception e) {
            log.error("auth fail", e);
            if (e instanceof BusinessException) {
                BusinessException be = (BusinessException) e;
                vo = ResponseVo.fail(be.getCode(), be.getMessage());
            } else {
                vo = ResponseVo.fail(ExceptionCode.UNAUTHORIZED, e.getMessage());
            }
        }
        HttpServletRequest request = RequestUtil.getRequest();
        if (request != null) {
            vo.setRequestId(request.getHeader(SimpleConstant.HEADER_REQUEST_ID_KEY));
        }
        return ResponseEntity.ok(vo);
    }
}
