package Server;

import Server.ReadThread;
import Server.WriteThread;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args)throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(6789);
        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            ServerTraad serverTraad = new ServerTraad(connectionSocket);
            serverTraad.start();
        }


            //new ReadThread(connectionSocket).start();
           // new WriteThread(connectionSocket).start();


    }
}
