package ch.heig.dai.smtp;

import java.util.ArrayList;


public class MailPrankster {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        ConfigReader configReader = new ConfigReader();
        System.out.println(configReader.getServerPort() + " "+ configReader.getServerAddress() + " " + configReader.getNumberOfGroups());
        AddressReader addressReader = new AddressReader();
        ArrayList<String> listOfAddresses = addressReader.getVictims();
        for(String address : listOfAddresses){
            System.out.println("The adddress is " + address);
        }
    }
}