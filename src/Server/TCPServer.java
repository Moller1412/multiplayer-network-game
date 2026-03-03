package Server;

import Server.ReadThread;
import Server.WriteThread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args)throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(6789);
        Socket connectionSocket = welcomeSocket.accept();
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        String up = inFromUser.readLine();
        System.out.println(up);
            new ReadThread(connectionSocket).start();
            new WriteThread(connectionSocket).start();


    }
}
