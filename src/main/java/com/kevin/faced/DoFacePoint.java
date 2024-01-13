package com.kevin.faced;

import com.alibaba.fastjson.JSON;
import com.kevin.faced.annotation.Faced;
import com.kevin.faced.config.FacedService;
import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Aspect
@Component
public class DoFacePoint {

    @Autowired
    private FacedService facedService;

//    @Resource
//    private FacedServiceAutoConfiguration facedService;

    @Pointcut("@annotation(com.kevin.faced.annotation.Faced)")
    public void aopPoint(){}

    @Around("aopPoint()")
    public Object doFace(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = getMethod(joinPoint);
        Faced faced = method.getAnnotation(Faced.class);
        String fieldValue = getField(faced.key(), joinPoint.getArgs());
        // 默认什么都不写就放行
        if(null == fieldValue || "".equals(fieldValue)){
            return joinPoint.proceed();
        }
        // 进行白名单过滤，如果在白名单中就放行
        String[] witheList = facedService.split(",");
        for(String str : witheList){
            if(fieldValue.equals(str)){
                return joinPoint.proceed();
            }
        }
        return returnObject(faced,method);
    }

    private Object returnObject(Faced faced, Method method) throws InstantiationException, IllegalAccessException {
        Class<?> returnType = method.getReturnType();
        if(null == faced.returnJson() || "".equals(faced.returnJson())){
            return returnType.newInstance();
        }
        return JSON.parseObject(faced.returnJson(),returnType);
    }

    private String getField(String key, Object[] args) {
        String fieldValue = null;
        for(Object arg :args){
            try {
                if(null == fieldValue || "".equals(fieldValue)){
                    fieldValue = BeanUtils.getProperty(args,key);
                }else{
                    break;
                }
            } catch (Exception e) {
                if(args.length == 1){
                    return args[0].toString();
                }
            }
        }
        return fieldValue;
    }

    private Method getMethod(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        return getClass(joinPoint).getMethod(methodSignature.getName(),methodSignature.getParameterTypes());
    }

    public Class getClass(ProceedingJoinPoint joinPoint){
        return joinPoint.getTarget().getClass();
    }
}


