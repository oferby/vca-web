package com.huawei.vca.repository;

import com.huawei.vca.repository.controller.Turn;

import java.time.LocalDateTime;

public class ConversationTurn {

    public ConversationTurn() {
    }

    public ConversationTurn(Turn turn, String text) {
        this.turn = turn;
        this.localDateTime = LocalDateTime.now();
        this.text = text;
    }

    private Turn turn;
    private LocalDateTime localDateTime;
    private String text;

    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
