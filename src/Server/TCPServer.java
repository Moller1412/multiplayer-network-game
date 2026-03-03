package Server;

import Server.ReadThread;
import Server.WriteThread;

import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args)throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(6677);
        Socket connectionSocket = welcomeSocket.accept();
            new ReadThread(connectionSocket).start();
            new WriteThread(connectionSocket).start();


    }
}
