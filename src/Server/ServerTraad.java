package Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerTraad extends Thread {
    private final Socket connSocket;
    private final int playerID;

    private DataOutputStream outToClient;
    private BufferedReader inFromClient;

    public ServerTraad(Socket connSocket, int playerID) {
        this.connSocket = connSocket;
        this.playerID = playerID;
    }

    public synchronized void send(String message) {
        try {
            if (outToClient != null) {
                outToClient.writeBytes(message + "\n");
            }
        } catch (IOException e) {
            System.out.println("Kunne ikke sende til client " + playerID);
        }
    }

    @Override
    public void run() {
        try {
            inFromClient = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
            outToClient = new DataOutputStream(connSocket.getOutputStream());

            send("PLAYER:" + playerID);

            String message;
            while ((message = inFromClient.readLine()) != null) {
                if (message.startsWith("MOVE:")) {
                    String direction = message.substring(5);
                    TCPServer.broadcast("MOVE:" + playerID + ":" + direction);
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected: " + playerID);
        } finally {
            TCPServer.removeClient(this);
            try {
                connSocket.close();
            } catch (IOException ignored) {
            }
        }
    }
}