package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
}
