package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONWriter;

public class Conversation {
    /**
     * A list of the users in the conversation. Must be non empty.
     */
    private final ArrayList<String> participants = new ArrayList<>();
    private final ArrayList<Message> messages = new ArrayList<>();



    public Conversation() {

    }

    public List<String> getParticipants() {
        return participants;
    }

    public void addParticipant(String userID) {
        participants.add(userID);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void sendMessage(Message msg) {
        messages.add(msg);
    }

    public static class Message {
        public final String sender;
        public final String content;
        public final Date date;

        public Message(String sender, String content, Date date) {
            this.sender = sender;
            this.content = content;
            this.date = date;
        }
    }
}
