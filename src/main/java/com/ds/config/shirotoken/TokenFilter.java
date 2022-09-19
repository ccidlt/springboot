package com.ds.config.shirotoken;

import com.alibaba.fastjson.JSONObject;
import com.ds.entity.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenFilter extends BasicHttpAuthenticationFilter {
    //源码注释
    // Check whether the current request's method requires authentication.
    // If no methods have been configured, then all of them require auth,
    // otherwise only the declared ones need authentication.
    /**
     * 作用：个人感觉判断是否需要认证，需要认证的话就进入下面的逻辑
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return super.isAccessAllowed(request, response, mappedValue) ||(!isLoginRequest(request, response) && isPermissive(mappedValue));
    }

    /**
     * 认证未通过执行该方法
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        //完成token登入
        //1.检查请求头中是否含有token
        HttpServletRequest httpServletRequest= (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        String token = httpServletRequest.getHeader("token");
        JwtToken jwtToken = new JwtToken(token);
        try {
            SecurityUtils.getSubject().login(jwtToken);
            /**
             * 这里抛异常处理
             * Result(code=400, msg=Realm [mlt.boot.blog.config.UserRealm@2e2bff56] does not support authentication
             * token [mlt.boot.blog.config.JwtToken@1db51365].
             * Please ensure that the appropriate Realm implementation is configured correctly or that the realm accepts
             * AuthenticationTokens of this type., data=null)
             */
        } catch (AuthenticationException e) {
            //这里在全局异常里面捕获不到，所以把信息封装成json返回
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            Result r = Result.fail(throwable.getMessage());
            String json = JSONObject.toJSONString(r);
            httpServletResponse.setHeader("content-type", "text/html;charset=UTF-8");//防止乱码
            httpServletResponse.getWriter().print(json);
            return false;
        }
        return true;
    }



    /**
     * 方法执行前
     * 对跨域提供支持
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
