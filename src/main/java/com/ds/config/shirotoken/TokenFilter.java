package com.ds.config.shirotoken;

import com.alibaba.fastjson.JSONObject;
import com.ds.entity.Result;
import lombok.SneakyThrows;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenFilter extends BasicHttpAuthenticationFilter {
    /**
     * 是否允许访问。如果带有 token，则对 token 进行检查，否则直接通过。
     * 如果请求头不存在 Token，则可能是执行登陆操作或者是游客状态访问，无需检查 token，直接返回 true
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @SneakyThrows
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //判断请求的请求头是否带上 "Token"
        if (isLoginAttempt(request, response)) {
            //如果存在，则进入 executeLogin 方法执行登入，检查 token 是否正确
            try {
                executeLogin(request, response);
                return true;
            } catch (Exception e) {
                //token 错误
                Throwable throwable = e.getCause() == null ? e : e.getCause();
                Result r = Result.fail(throwable.getMessage());
                String json = JSONObject.toJSONString(r);
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.setHeader("content-type", "text/html;charset=UTF-8");//防止乱码
                httpServletResponse.getWriter().print(json);
                return false;
            }
        }
        //如果请求头不存在 Token，则可能是执行登陆操作或者是游客状态访问，无需检查 token，直接返回 true
        return true;
    }

    /**
     * 判断用户是否想要登入。检测 header 里面是否包含 Token 字段。
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("token");
        return token != null;
    }

    /**
     * executeLogin实际上就是先调用createToken来获取token，这里我们重写了这个方法，就不会自动去调用createToken来获取token
     * 然后调用getSubject方法来获取当前用户再调用login方法来实现登录
     * 这也解释了我们为什么要自定义jwtToken，因为我们不再使用Shiro默认的UsernamePasswordToken了。
     * 然后getSubject方法是调用到了UserRealm的 执行方法  因为上面我是抛错的所有最后做个异常捕获就好了
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("token");
        JwtToken jwt = new JwtToken(token);
        //交给自定义的realm对象去登录，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(jwt);
        return true;
    }

//    /**
//     * 判断是否需要认证，需要认证的话就进入下面的逻辑
//     * @param request
//     * @param response
//     * @param mappedValue
//     * @return
//     */
//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//        return super.isAccessAllowed(request, response, mappedValue) ||(!isLoginRequest(request, response) && isPermissive(mappedValue));
//    }
//
//    /**
//     * 认证未通过执行该方法
//     * @param request
//     * @param response
//     * @return
//     */
//    @SneakyThrows
//    @Override
//    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
//        //完成token登入
//        //1.检查请求头中是否含有token
//        HttpServletRequest httpServletRequest= (HttpServletRequest) request;
//        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
//        String token = httpServletRequest.getHeader("token");
//        JwtToken jwtToken = new JwtToken(token);
//        try {
//            getSubject(request, response).login(jwtToken);
//            /**
//             * 这里抛异常处理
//             * Result(code=400, msg=Realm [mlt.boot.blog.config.UserRealm@2e2bff56] does not support authentication
//             * token [mlt.boot.blog.config.JwtToken@1db51365].
//             * Please ensure that the appropriate Realm implementation is configured correctly or that the realm accepts
//             * AuthenticationTokens of this type., data=null)
//             */
//        } catch (AuthenticationException e) {
//            //这里在全局异常里面捕获不到，所以把信息封装成json返回
//            Throwable throwable = e.getCause() == null ? e : e.getCause();
//            Result r = Result.fail(throwable.getMessage());
//            String json = JSONObject.toJSONString(r);
//            httpServletResponse.setHeader("content-type", "text/html;charset=UTF-8");//防止乱码
//            httpServletResponse.getWriter().print(json);
//            return false;
//        }
//        return true;
//    }

    /**
     * 拦截器的前置拦截，
     * 因为我们是前后端分析项目，项目中除了需要跨域全局配置之外，我们再拦截器中也需要提供跨域支持。
     * 这样，拦截器才不会在进入Controller之前就被限制了。
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-control-Allow-Origin", req.getHeader("Origin"));
        res.setHeader("Access-control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        res.setHeader("Access-control-Allow-Headers", req.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (req.getMethod().equals(RequestMethod.OPTIONS.name())) {
            res.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

}
