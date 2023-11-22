package entity;

import java.util.Date;

public class Message {
    private final String sender;
    private final String content;
    private final Date date;

    public Message(String sender, String content, Date date) {
        this.sender = sender;
        this.content = content;
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }
}
