package com.test.lab10;

public class Message {

    private String Subject, Message;

    public Message(String subject, String message) {
        Subject = subject;
        Message = message;
    }

    public Message() {
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
