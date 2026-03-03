package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
public class ReadThread extends Thread{
    Socket connSocket;

    public ReadThread(Socket connSocket) {
        this.connSocket = connSocket;
    }
    public void run() {
        try {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
            String clientSentence;

            while ((clientSentence = inFromClient.readLine()) != null) {
                System.out.println( clientSentence);

                if (clientSentence.equals("Stop")) break;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
