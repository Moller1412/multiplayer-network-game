package Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class WriteThread extends Thread{
	Socket connSocket;
	
	public WriteThread(Socket connSocket) {
		this.connSocket = connSocket;
	}
	public void run() {
        try {
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			DataOutputStream outToClient = new DataOutputStream(connSocket.getOutputStream());

			String sentence;

			while ((sentence = inFromUser.readLine()) != null) {
				outToClient.writeBytes(sentence + "\n");

				if (sentence.equals("Stop")) break;
			}
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
