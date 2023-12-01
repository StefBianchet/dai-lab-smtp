package ch.heig.dai.smtp;

import java.util.ArrayList;
import java.util.Random;

// tod
public class MailManager {
    private final AddressReader addressReader;
    private final MessageReader messageReader;
    private final int nbGroups;
    private final ArrayList<Group> groups = new ArrayList<>();
    public MailManager(AddressReader addressReader, MessageReader messageReader, int nbGroups){
        this.addressReader = addressReader;
        this.messageReader = messageReader;
        this.nbGroups = nbGroups;
    }

    public void createGroups(){
        int currentSize = 0;

        if (addressReader.getVictimsSize() < nbGroups * 2){
            throw new RuntimeException("Your number of groups is too large! Decrease your number of groups in your config.json file!");
        }

        for(int i = 0; i < nbGroups; i++){
            Group g;
            if (i == nbGroups - 1){
                g = new Group(addressReader.getVictimsSize() - currentSize, addressReader);
            } else{
                int nbVictims = addressReader.getVictimsSize()/nbGroups;
                currentSize += nbVictims;
                g = new Group(nbVictims, addressReader);
            }
            groups.add(g);
        }
    }

    public void assignAMessagePerGroups(){
        for (Group g:groups) {
            int index = new Random().nextInt(messageReader.getMessageListSize());
            Message msg = messageReader.getMessages().get(index);
            g.setMessage(msg);
        }
    }

    public ArrayList<Group> getGroups(){
        return groups;
    }
}
