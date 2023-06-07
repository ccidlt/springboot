package com.ds.aop;

import com.ds.annotation.AspectService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@Slf4j
@Order(2)
public class AspectImpl {

    //@Pointcut("execution(public * com.ds.controller.*.*(..)) || execution(public * com.ds.controller.*.*(..))")
    @Pointcut("@annotation(com.ds.annotation.AspectService)")
    private void cut() {}

    //环绕增强，是在before前就会触发,目标方法执行前后分别执行一些代码
//    @Around("cut()")
//    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    @Around("@annotation(aspectService)")
    public Object around(ProceedingJoinPoint joinPoint, AspectService aspectService) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String remoteAddr = request.getRemoteAddr();
        String queryString = request.getQueryString();
        log.info("Request, url: {" + url + "}, method: {" + method + "}, uri: {" + uri + "}, remoteAddr: {" + remoteAddr + "}, params: {" + queryString + "}");

        //获取controller名
        String controllerFullName = joinPoint.getTarget().getClass().getName();
        String[] controllerNames = controllerFullName.split("\\.");
        String controllerName = controllerNames[controllerNames.length - 1];

        //方法上注解
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method methodObject = methodSignature.getMethod();
        String operationType = methodObject.getAnnotation(AspectService.class).operationType();
        String operationName = methodObject.getAnnotation(AspectService.class).operationName();
        log.info("Method Type:" + operationType);
        log.info("Method Desc:" + operationName);

        // 方法名
        //String methodName = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        String methodName = joinPoint.getSignature().getName();
        //请求方式
        String methodType = request.getMethod();

        Map<String, Object> paramsMap = new HashMap<>();
        Object paramValues[] = joinPoint.getArgs();
        log.info("请求方法：{}, 请求参数: {}", methodName, Arrays.toString(paramValues));
        // 参数名称
        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        // 格式化参数信息
        StringBuffer params = new StringBuffer("{");
        for (int i = 0; i < paramValues.length; i++) {
            String paramName = paramNames[i];
            Object paramValue = paramValues[i];
            // 过滤请求、响应、校验结果对象
            if (paramValue instanceof BindingResult || paramValue instanceof HttpServletRequest ||
                    paramValue instanceof HttpServletResponse) {
                continue;
            }
            // 上传文件信息特殊处理
            if (paramValue instanceof MultipartFile) {
                MultipartFile file = (MultipartFile) paramValue;
                paramsMap.put("fileName", file.getOriginalFilename());
                params.append("fileName:").append(file.getOriginalFilename());
                continue;
            }

            String paramValueStr = String.valueOf(paramValue);
            paramsMap.put(paramName, paramValueStr);
            params.append(paramName).append(":").append(paramValueStr);
        }
        params.append("}");

        String startDateStr = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        log.info("【{}】:接口【{}】请求方式【{}】方法【{}】参数【{}】,IP为【{}】进行了【{}】类型的【{}】操作;"
                , startDateStr, controllerName, methodType, methodName, params.toString().replace(" ", ""), remoteAddr, operationType, operationName);

        // result的值就是被拦截方法的返回值
        Object result = joinPoint.proceed();
        log.info("请求返回值：{}", result);
        return result;
    }

    //进来切点世界，先经过的第一个站
    @Before("cut()")
    public void before(JoinPoint joinPoint) {

    }

    //进来切点这，最后经过的一个站，也是方法正常运行结束后,不管是抛出异常或者正常退出都会执行
    @After("cut()")
    public void after(JoinPoint joinPoint) {

    }

    //统一异常处理
    @AfterThrowing(pointcut = "cut()", throwing = "e")
    public void afterThrowable(JoinPoint joinPoint, Throwable e) {
        //ExceptionConfig类统一异常处理
        //方法名获取
//        String methodName = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
//        log.error("请求异常：请求方法：{}, 异常信息：{}", methodName, e.getMessage());
    }
}
