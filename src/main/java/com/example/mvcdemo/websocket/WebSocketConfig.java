package com.example.mvcdemo.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Slf4j
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(myHandler(), "/myHandler").addInterceptors(new MyHandshakeInterceptor()).setAllowedOrigins("*").withSockJS().setHeartbeatTime(5000);
    }

    @Bean
    public WebSocketHandler myHandler(){
        return new MyHandler();
    }

    class MyHandler extends TextWebSocketHandler{
        protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
            log.info("handleTextMessage message:{}", new String(message.asBytes(), "utf-8"));
            session.sendMessage(new TextMessage("hello world"));
        }

    }

    class MyHandshakeInterceptor implements HandshakeInterceptor {

        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
            log.info("beforeHandshake httpHeaders :{}", request.getHeaders());
            return true;
        }

        @Override
        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
            log.info("afterHandshake httpHeaders :{} responseHeaders", request.getHeaders(), response.getHeaders());

        }
    }
}
