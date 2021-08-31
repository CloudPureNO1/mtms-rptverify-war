package com.insigma.insiis.rptverify.service.imp;

import com.alibaba.fastjson.JSON;
import com.insigma.insiis.rptverify.Exception.BaseException;
import com.insigma.insiis.rptverify.comm.ApiInfoEnum;
import com.insigma.insiis.rptverify.service.BizService;
import com.insigma.insiis.rptverify.service.GatewayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <p></p>
 * <p></p>
 * @author 王森明
 * @date 2021/3/4 13:20
 * @since 1.0.0
 */
@Slf4j
@Service
public class GatewayServiceImpl implements GatewayService {
    private static final String CLASS_PREFIX_IN="com.insigma.insiis.rptverify.dto.in.Tran";
    private static final String CLASS_SUFFIX_IN="In";

    @Autowired
    private BizServiceImpl bizServiceImpl;

    @Override
    public Object server(String tranNo, String jsonStr) throws BaseException {
        try{
            return execute(tranNo,jsonStr,getClassIn(tranNo));
        }catch (ClassNotFoundException e){
            log.info("【"+ ApiInfoEnum.CLASS_NOTFOUND.getCode()+"】【"+ApiInfoEnum.CLASS_NOTFOUND.getMsg()+"】：{}",e.getMessage(),e);
            throw new BaseException(ApiInfoEnum.CLASS_NOTFOUND.getCode()+"",ApiInfoEnum.CLASS_NOTFOUND.getMsg());
        }catch (NoSuchMethodException e){
            log.info("【"+ ApiInfoEnum.NO_SUCH_METHOD.getCode()+"】【"+ApiInfoEnum.NO_SUCH_METHOD.getMsg()+"】：{}",e.getMessage(),e);
            throw new BaseException(ApiInfoEnum.NO_SUCH_METHOD.getCode()+"",ApiInfoEnum.NO_SUCH_METHOD.getMsg());
        }catch (InvocationTargetException e){
            if(e.getTargetException() instanceof javax.validation.ConstraintViolationException){
                throw (ConstraintViolationException)e.getTargetException();
            }
            if(e.getTargetException() instanceof BaseException ){
                throw (BaseException) e.getTargetException();
            }
            log.info("【"+ ApiInfoEnum.INVOCATION_TARGET.getCode()+"】【"+ApiInfoEnum.INVOCATION_TARGET.getMsg()+"】：{}",e.getMessage(),e);
            throw new BaseException(ApiInfoEnum.INVOCATION_TARGET.getCode()+"",ApiInfoEnum.INVOCATION_TARGET.getMsg());
        }catch (IllegalAccessException e){
            log.info("【"+ ApiInfoEnum.ILLEGAL_ACCESS.getCode()+"】【"+ApiInfoEnum.ILLEGAL_ACCESS.getMsg()+"】：{}",e.getMessage(),e);
            throw new BaseException(ApiInfoEnum.ILLEGAL_ACCESS.getCode()+"",ApiInfoEnum.ILLEGAL_ACCESS.getMsg());
        }catch(Exception e){
            log.info("【"+ ApiInfoEnum.SERVICE_EXC.getCode()+"】【"+ApiInfoEnum.SERVICE_EXC.getMsg()+"】：{}",e.getMessage(),e);
            throw new BaseException(ApiInfoEnum.SERVICE_EXC.getCode()+"",ApiInfoEnum.SERVICE_EXC.getMsg());
        }
    }

    public <T,E> E  execute(String tranNo,String jsonStr,Class<T> clazzIn) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        T t =JSON.parseObject(jsonStr,clazzIn);
        BizService service =bizServiceImpl;
        Method method = service.getClass().getMethod(getMethodName(tranNo),clazzIn);
        E e = (E) method.invoke(service,clazzIn.cast(t));
        return e;
    }

    private Class<?> getClassIn(String tranNo) throws ClassNotFoundException {
        return Class.forName(CLASS_PREFIX_IN+tranNo+CLASS_SUFFIX_IN);
    }
    private String getMethodName(String tranNo){
        return "serve"+tranNo;
    }
}
