package com.follow.common.exception;

import com.follow.common.exception.enums.CoreExceptionEnum;
import com.follow.common.result.ErrorResponseData;
import com.follow.common.result.ResponseData;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * @description:  全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 * @author: huzhiwen
 * @create: 2019-01-26 17:59
 **/

@RestControllerAdvice
@Slf4j
public class DefaultExceptionHandler {
    /**
     * 拦截各个服务的具体异常
     */
    @ExceptionHandler(ApiServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData apiService(ApiServiceException e) {
        log.error("服务具体异常:", e);
        ErrorResponseData errorResponseData = new ErrorResponseData(e.getCode(), e.getErrorMessage());
        return errorResponseData;
    }

    /**
     * 拦截业务异常
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData notFount(ServiceException e) {
        log.info("业务异常:", e);
        return new ErrorResponseData(e.getCode(), e.getErrorMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData serverError(Exception e) {
        log.error("运行时异常:", e);
        return new ErrorResponseData(CoreExceptionEnum.SERVICE_ERROR.getCode(), CoreExceptionEnum.SERVICE_ERROR.getMessage());
   // return null;
    }


    /**
     * 用来处理bean validation异常
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseData resolveConstraintViolationException(ConstraintViolationException ex){
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        if(!CollectionUtils.isEmpty(constraintViolations)){
            StringBuilder msgBuilder = new StringBuilder();
            for(ConstraintViolation constraintViolation :constraintViolations){
                msgBuilder.append(constraintViolation.getMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if(errorMessage.length()>1){
                errorMessage = errorMessage.substring(0,errorMessage.length()-1);
            }
            return ResponseData.error(errorMessage);
        }
        return ResponseData.error(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseData resolveMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        if(!CollectionUtils.isEmpty(objectErrors)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ObjectError objectError : objectErrors) {
                msgBuilder.append(objectError.getDefaultMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }
            return ResponseData.error(errorMessage);
        }
        return ResponseData.error(ex.getMessage());
    }
}
