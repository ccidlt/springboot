package com.ds.config.websocket;

import com.ds.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.sockjs.transport.session.WebSocketServerSockJsSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class MyWebSocket extends TextWebSocketHandler {

    private Logger logger = LoggerFactory.getLogger(MyWebSocket.class);

    /**
     * 存储sessionId和webSocketSession
     * 需要注意的是，webSocketSession没有提供⽆参构造，不能进⾏序列化，也就不能通过redis存储
     * 在分布式系统中，要想别的办法实现webSocketSession共享
     */
    private static Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
    private static Map<String, String> userMap = new ConcurrentHashMap<>();

    /**
     * 获取sessionId
     */
    private String getSessionId(WebSocketSession session) {
        if (session instanceof WebSocketServerSockJsSession) {
            // sock js 连接
            try {

                return ((WebSocketSession) FieldUtils.readField(session, "webSocketSession", true)).getId();
            } catch (IllegalAccessException e) {
                throw new RuntimeException("get sessionId error");
            }
        }
        return session.getId();
    }

    /**
     * webSocket创建连接后调用
     * @param session
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // 获取参数
        String user = String.valueOf(session.getAttributes().get("user"));
        String sessionId = getSessionId(session);
        userMap.put(user, getSessionId(session));
        sessionMap.put(sessionId, session);
        logger.info("WebSocket 新增连接 ：" + user);
        logger.info("WebSocket 目前存在的连接 ：" + userMap.keySet());
    }

    /**
     * 连接关闭会调用
     * @param session
     * @param status
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        if(userMap.containsValue(getSessionId(session))){
            String user = userMap.entrySet().stream().filter(entry ->
                    entry.getValue().equals(getSessionId(session))).findAny().map(Map.Entry::getKey).orElse(null);
            userMap.remove(user);
            logger.info("WebSocket 关闭连接 ：" + user);
        }
        logger.info("WebSocket 目前存在的连接 ：" + userMap.keySet());
        sessionMap.remove(getSessionId(session));
    }

    /**
     * 连接出错会调用
     * @param session
     * @param exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        if(userMap.containsValue(getSessionId(session))){
            String user = userMap.entrySet().stream().filter(entry ->
                    entry.getValue().equals(getSessionId(session))).findAny().map(Map.Entry::getKey).orElse(null);
            userMap.remove(user);
            logger.info("WebSocket 关闭连接 ：" + user);
        }
        sessionMap.remove(getSessionId(session));
    }

    /**
     * 接收到消息调用
     * @param session
     * @param message
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.debug("WebSocket服务端接受:接受来自客户端发送的信息: "+message.getPayload().toString());
        session.sendMessage(message);
    }

    public WebSocketSession getSession(String user){
        String sessionId = userMap.get(user);
        if(StringUtil.isEmpty(sessionId) || StringUtils.isEmpty(sessionMap.get(sessionId))){
            return null;
        }
        WebSocketSession session = sessionMap.get(sessionId);
        return session;
    }

    /**
     * 后端发送消息
     */
    public void sendMessage(String user, String message) {
        String sessionId = userMap.get(user);
        if(StringUtil.isEmpty(sessionId) || StringUtils.isEmpty(sessionMap.get(sessionId))){
            return;
        }
        WebSocketSession session = sessionMap.get(sessionId);
        try {
            synchronized (session){
                session.sendMessage(new TextMessage(message));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getUserMap(){
        return userMap;
    }

    public Map<String, WebSocketSession> getSessionMap(){
        return sessionMap;
    }

}
