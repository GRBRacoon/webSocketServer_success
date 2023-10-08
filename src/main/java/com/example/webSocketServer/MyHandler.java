package com.example.webSocketServer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.logging.Logger;
@RequiredArgsConstructor
@Component
public class MyHandler extends TextWebSocketHandler {
    Logger logger=Logger.getLogger(MyHandler.class.getName());
    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("connection established");
        super.afterConnectionEstablished(session);

    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        logger.info(payload);
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);

        ChatRoom chatRoom = chatService.findRoomById(chatMessage.getRoomId());
        chatRoom.handlerActions(session, chatMessage, chatService);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("connection closed");
        super.afterConnectionClosed(session, status);
    }
}
