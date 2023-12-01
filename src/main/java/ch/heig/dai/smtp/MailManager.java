package ch.heig.dai.smtp;

import java.util.ArrayList;

// tod
public class MailManager {
    private final AddressReader addressReader;
    private final int nbGroups;
    private final ArrayList<Group> groups = new ArrayList<>();
    public MailManager(AddressReader addressReader, int nbGroups){
        this.addressReader = addressReader;
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

    public ArrayList<Group> getGroups(){
        return groups;
    }
}
