package Server;

import Player.Player;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerTraad extends Thread{
    public List <Player> players = new ArrayList<>();
    Socket connSocket;
    int playerID;

    DataOutputStream outToClient;
    BufferedReader inFromClient;

    public ServerTraad(Socket connSocket, int playerID) {
        this.connSocket = connSocket;
        this.playerID = playerID;
    }


    public void run(){

        try {
            inFromClient = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));

            outToClient = new DataOutputStream(connSocket.getOutputStream());
            outToClient.writeBytes("Player:" + playerID + '\n');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            outToClient.writeBytes("Spillet er startet " + "\n");
            while (true) {
                String up = inFromClient.readLine();
                System.out.println(up);
                outToClient.writeBytes(up + '\n');
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
