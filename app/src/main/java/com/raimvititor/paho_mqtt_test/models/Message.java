package com.raimvititor.paho_mqtt_test.models;

import com.raimvititor.paho_mqtt_test.libs.Utils;
import com.stfalcon.chatkit.commons.models.IMessage;

import java.util.Date;

/**
 * Created by Ray on 2017/3/1.
 */

public class Message implements IMessage {

    private int id;
    private String text;
    private Author author;
    private String createdAt;

    public Message(int id, String text, Author author, String createdAt) {
        this.id = id;
        this.text = text;
        this.author = author;
        this.createdAt = createdAt;
    }

    @Override
    public String getId() {
        return id + "";
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Author getUser() {
        return author;
    }

    @Override
    public Date getCreatedAt() {
        return Utils.convertChatTime(createdAt);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}