package com.ds.config.websocket;

import com.ds.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private MyWebSocket myWebSocket;

    /*@Autowired
    public WebSocketConfig(MyWebSocket myWebSocket) {
        this.myWebSocket = myWebSocket;
    }*/

    @Autowired
    public void setWebSocketController(MyWebSocket myWebSocket) {
        this.myWebSocket = myWebSocket;
    }

    public MyWebSocket getMyWebSocket() {
        return myWebSocket;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myWebSocket, "/websocket")
                .addInterceptors(new HandShakeInterceptor())
                .setAllowedOrigins("*");
        registry.addHandler(myWebSocket, "/sockjs")
                .addInterceptors(new HandShakeInterceptor())
                .setAllowedOrigins("*")
                // 开启sockJs⽀持
                .withSockJS();
    }

    public class HandShakeInterceptor extends HttpSessionHandshakeInterceptor {
        /*
         * 在WebSocket连接建立之前的操作，以鉴权为例
         */
        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                       WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            ServletServerHttpResponse serverResponse = (ServletServerHttpResponse) response;
            String user = serverRequest.getServletRequest().getParameter("user");
            if (StringUtil.isNotEmpty(user)) {
                attributes.put("user", user);
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Exception ex) {
            // 省略根据业务处理
        }
    }
}
