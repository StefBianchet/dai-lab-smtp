package ch.heig.dai.smtp;

import java.util.ArrayList;

public class Group {
    static int index = 0;
    private final ArrayList<String> victims = new ArrayList<>();
    private final int size;
    private Message message;

    public Group(int nbVictims, AddressReader addressReader){
        this.size = nbVictims;
        for(int i = 0; i < size; i++){
            if(index < addressReader.getVictims().size())
                victims.add(addressReader.getVictims().get(index++));
        }
    }

    public ArrayList<String> getVictims() {
        return victims;
    }
    public int getGroupSize() {
        return size;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
