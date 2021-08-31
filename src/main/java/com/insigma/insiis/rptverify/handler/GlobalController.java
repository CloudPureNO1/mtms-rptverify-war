package com.insigma.insiis.rptverify.handler;

import com.insigma.insiis.rptverify.Exception.BaseException;
import com.insigma.insiis.rptverify.comm.DataRtn;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;
/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/3 16:45
 * @since 1.0.0
 */
@Slf4j
@ControllerAdvice
public class GlobalController {
    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public DataRtn baseEx(BaseException e){
        return  getDataRtn(e);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public DataRtn dealRuntime(RuntimeException re){
        return new DataRtn(re.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public DataRtn dealException(Exception e){
        DataRtn DataRtn=null;
        if(e instanceof BaseException){
            BaseException be=(BaseException)e;
            DataRtn =getDataRtn(be);
        }else{
            DataRtn=new DataRtn(e.getMessage());
        }
        return DataRtn;
    }
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public DataRtn dealException(Throwable e){
        DataRtn DataRtn=null;
        if(e instanceof BaseException){
            BaseException be=(BaseException)e;
            DataRtn =getDataRtn(be);
        }else{
            DataRtn=new DataRtn(e.getMessage());
        }
        return  DataRtn;
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public DataRtn handle(ValidationException exception) {
        if(exception instanceof ConstraintViolationException){
            ConstraintViolationException exs = (ConstraintViolationException) exception;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                log.info("校验信息：{}",item.getMessage());
                return new DataRtn(item.getMessage());
            }
        }
        return new DataRtn("bad request") ;
    }
    private DataRtn getDataRtn(BaseException e) {
        return new DataRtn(StringUtils.isBlank(e.getCode())?"-1":e.getCode(),e.getMessage());
    }
}
