package com.huawei.vca.config;

import com.huawei.vca.message.Dialogue;
import com.huawei.vca.conversation.SessionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private SessionController sessionController;

    @Autowired
    private SimpMessagingTemplate template;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {

        Message msg = event.getMessage();
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(msg);
        String sessionId = accessor.getSessionId();
        logger.info("Received a new web socket connection, sessionId: " + sessionId);

        Map<String, Object> attr = new HashMap<>();
        attr.put("sessionId", sessionId);
        accessor.setSessionAttributes(attr);

        sessionController.addOrUpdateDialogue(sessionId, new Dialogue(sessionId));
        informConnection(sessionId);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {

        Message msg = event.getMessage();
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(msg);
        String sessionId = accessor.getSessionId();

        logger.info("Received a new web socket disconnection from: " + sessionId);

        if (sessionId != null) {
            logger.info("User Disconnected : " + sessionId);
            sessionController.removeDialogue(sessionId);
            informDisconnection(sessionId);
        }

    }

    private void informConnection(String sessionId) {
        template.convertAndSend("/topic/dialogue/monitor/connect", sessionId);
    }

    private void informDisconnection(String sessionId) {
        template.convertAndSend("/topic/dialogue/monitor/disconnect", sessionId);
    }



}
