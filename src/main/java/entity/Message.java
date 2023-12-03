package entity;

import java.util.Date;

public class Message {
    // Name of sender
    private final String sender;
    private final String content;
    private final Date date;
    private final Card cardOffer;

    public Message(User sender, String content, Date date, Card cardOffer) {
        this.sender = sender.username;
        this.content = content;
        this.date = date;
        this.cardOffer = cardOffer;
    }

    public Message(User sender, String content, Date date) {
        this(sender, content, date, null);
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

    public Card getCardOffer() {
        return cardOffer;
    }
}
