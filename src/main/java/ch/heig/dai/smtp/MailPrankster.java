package ch.heig.dai.smtp;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class MailPrankster {
    public static void main(String[] args) {
        System.out.println("Welcome to MailPrankster!");
        MailPrankster prankster = new MailPrankster();
        prankster.run();
    }

    private void run() {
        ConfigReader configReader = new ConfigReader();
        AddressReader addressReader = new AddressReader();

        MailManager mailManager = new MailManager(addressReader, new MessageReader(), configReader.getNumberOfGroups());
        mailManager.createGroups();

        ArrayList<Group> groups = mailManager.getGroups();

        mailManager.assignAMessagePerGroups();

        try (Socket socket = new Socket(configReader.getServerAddress(), configReader.getServerPort());
            var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
            System.out.println("Server connection successful!");
            out.write("EHLO MailPrankster\n");
            TimeUnit.SECONDS.sleep(1);

            for(Group group : groups){

                out.write("MAIL FROM: <" + group.getVictims().get(0)+ ">\n");
                TimeUnit.SECONDS.sleep(1);
                out.flush();
                for (int i = 1; i < group.getGroupSize(); i++){
                    out.write("RCPT TO: <" + group.getVictims().get(i) + ">\n");
                }
                out.write("DATA\n");
                out.write("FROM: <" + group.getVictims().get(0) + ">\n");
                out.write("TO:");
                for (int i = 1; i < group.getGroupSize(); i++){
                    out.write("<" + group.getVictims().get(i) + ">");
                    if(i != group.getGroupSize() - 1){
                        out.write(", ");
                    }
                }
                out.write("\n");
                out.write("SUBJECT: " + group.getMessage().subject() + "\n\n");;
                out.write(group.getMessage().body() + "\n");
                out.write("\r\n.\r\n\n");
                out.flush();
            }
            out.write("QUIT\n");
            out.flush();
            System.out.println("The emails have been sent successfully!");

        } catch (IOException e) {
            System.out.println("Client: exception while using client socket: " + e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}