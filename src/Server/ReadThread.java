package Server;

import Gui.GUI;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread extends Thread {
    private final Socket connSocket;

    public ReadThread(Socket connSocket) {
        this.connSocket = connSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader inFromServer =
                    new BufferedReader(new InputStreamReader(connSocket.getInputStream()));

            String serverMessage;
            while ((serverMessage = inFromServer.readLine()) != null) {
                if (serverMessage.startsWith("MOVE:")) {
                    String[] parts = serverMessage.split(":");
                    int playerId = Integer.parseInt(parts[1]);
                    String direction = parts[2];

                    Platform.runLater(() -> GUI.applyMoveFromServer(playerId, direction));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}