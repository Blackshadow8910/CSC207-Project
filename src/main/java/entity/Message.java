package entity;

import java.util.Date;

public class Message {
    // Name of sender
    private final String sender;
    private final String content;
    private final Date date;

    public Message(User sender, String content, Date date) {
        this.sender = sender.username;
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
