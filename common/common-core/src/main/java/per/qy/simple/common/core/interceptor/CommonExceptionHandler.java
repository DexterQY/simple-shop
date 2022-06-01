package per.qy.simple.common.core.interceptor;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import per.qy.simple.common.base.constant.SimpleConstant;
import per.qy.simple.common.base.exception.BusinessException;
import per.qy.simple.common.base.exception.ExceptionCode;
import per.qy.simple.common.base.model.ResponseVo;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 统一异常处理
 *
 * @author : QY
 * @date : 2021/12/10
 */
@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    /**
     * 默认异常处理
     *
     * @param request request
     * @param e       异常
     * @return ResponseVo
     */
    @ExceptionHandler(Exception.class)
    public ResponseVo commonErrorHandler(HttpServletRequest request, Exception e) {
        ResponseVo vo = ResponseVo.fail(ExceptionCode.SERVER_ERROR);
        vo.setRequestId(request.getHeader(SimpleConstant.HEADER_REQUEST_ID_KEY));
        logError(request, vo, e);
        return vo;
    }

    /**
     * 必填参数为空异常处理
     *
     * @param request request
     * @param e       异常
     * @return ResponseVo
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseVo handleMissingServletRequestParameterException(
            HttpServletRequest request, MissingServletRequestParameterException e) {
        ResponseVo vo = ResponseVo.fail(ExceptionCode.ILLEGAL_PARAM,
                "'" + e.getParameterName() + "'不能为空");
        vo.setRequestId(request.getHeader(SimpleConstant.HEADER_REQUEST_ID_KEY));
        logError(request, vo, e);
        return vo;
    }

    /**
     * 参数类型错误异常处理
     *
     * @param request request
     * @param e       异常
     * @return ResponseVo
     */
    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseVo handleMethodArgumentTypeMismatchException(
            HttpServletRequest request, Exception e) {
        ResponseVo vo = ResponseVo.fail(ExceptionCode.ILLEGAL_PARAM, e.getMessage());
        vo.setRequestId(request.getHeader(SimpleConstant.HEADER_REQUEST_ID_KEY));
        logError(request, vo, e);
        return vo;
    }

    /**
     * 参数校验异常处理
     *
     * @param request request
     * @param e       异常
     * @return ResponseVo
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseVo handleMethodArgumentNotValidException(
            HttpServletRequest request, MethodArgumentNotValidException e) {
        StringBuilder sb = new StringBuilder();
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        for (ObjectError error : errors) {
            appendObjectErrorData(error, sb);
        }
        ResponseVo vo = ResponseVo.fail(ExceptionCode.ILLEGAL_PARAM, sb.toString());
        vo.setRequestId(request.getHeader(SimpleConstant.HEADER_REQUEST_ID_KEY));
        logError(request, vo, e);
        return vo;
    }

    /**
     * 参数绑定异常处理
     *
     * @param request request
     * @param e       异常
     * @return ResponseVo
     */
    @ExceptionHandler(BindException.class)
    public ResponseVo handleBindException(HttpServletRequest request, BindException e) {
        StringBuilder sb = new StringBuilder();
        List<ObjectError> errors = e.getAllErrors();
        for (ObjectError error : errors) {
            appendObjectErrorData(error, sb);
        }
        ResponseVo vo = ResponseVo.fail(ExceptionCode.ILLEGAL_PARAM, sb.toString());
        vo.setRequestId(request.getHeader(SimpleConstant.HEADER_REQUEST_ID_KEY));
        logError(request, vo, e);
        return vo;
    }

    /**
     * 请求方法异常处理
     *
     * @param request request
     * @param e       异常
     * @return ResponseVo
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseVo handleRequestMethodException(
            HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        ResponseVo vo = ResponseVo.fail(ExceptionCode.ILLEGAL_PARAM, "请求方法不支持");
        vo.setRequestId(request.getHeader(SimpleConstant.HEADER_REQUEST_ID_KEY));
        logError(request, vo, e);
        return vo;
    }

    /**
     * 业务异常处理
     *
     * @param request request
     * @param e       异常
     * @return ResponseVo
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseVo businessExceptionHandler(HttpServletRequest request, BusinessException e) {
        ResponseVo vo = ResponseVo.fail(e.getCode(), e.getMessage());
        vo.setRequestId(request.getHeader(SimpleConstant.HEADER_REQUEST_ID_KEY));
        logError(request, vo, e);
        return vo;
    }

    private void logError(HttpServletRequest request, ResponseVo vo, Exception e) {
        Map<String, Object> requestMap = new LinkedHashMap<>();
        requestMap.put("remoteAddr", request.getRemoteAddr());
        requestMap.put("url", request.getRequestURL().toString());
        requestMap.put("httpMethod", request.getMethod());
        requestMap.put("requestParam", request.getParameterMap());
        requestMap.put("responseData", vo);
        String info = JSONUtil.toJsonStr(requestMap);
        log.error(info, e);
    }

    private void appendObjectErrorData(ObjectError error, StringBuilder sb) {
        if (error instanceof FieldError) {
            FieldError fieldError = (FieldError) error;
            sb.append("[parameter：")
                    .append(fieldError.getField())
                    .append(" | value：")
                    .append(fieldError.getRejectedValue())
                    .append(" | message：")
                    .append(fieldError.getDefaultMessage())
                    .append("]");
        } else {
            sb.append("[message：").append(error.getDefaultMessage()).append("]");
        }
    }
}
