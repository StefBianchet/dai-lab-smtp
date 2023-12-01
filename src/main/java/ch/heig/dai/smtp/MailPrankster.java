package ch.heig.dai.smtp;

import java.util.ArrayList;
import java.util.Arrays;


public class MailPrankster {
    public static void main(String[] args) {
        System.out.println("Welcome to MailPrankster!");
        ConfigReader configReader = new ConfigReader();

        //System.out.println(configReader.getServerPort() + " "+ configReader.getServerAddress() + " " + configReader.getNumberOfGroups());

        AddressReader addressReader = new AddressReader();

        ArrayList<String> listOfAddresses = addressReader.getVictims();
        //for(String address : listOfAddresses) System.out.println("The address is " + address);

        MailManager mailManager = new MailManager(addressReader, new MessageReader(), configReader.getNumberOfGroups());
        mailManager.createGroups();

        ArrayList<Group> groups = mailManager.getGroups();

        mailManager.assignAMessagePerGroups();

        for(Group g : groups){
            System.out.print("[");
            for(String s : g.getVictims()){
                System.out.print(s + ", ");
            }
            System.out.print("\b\b]\n");
            System.out.println(g.getMessage().subject());
        }
    }
}