package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TCPServer {

    private static int nextPlayerID = 0;
    private static final List<ServerTraad> clients = new ArrayList<>();

    public static synchronized int assignPlayerID() {
        return nextPlayerID++;
    }

    public static synchronized void addClient(ServerTraad client) {
        clients.add(client);
    }

    public static synchronized void removeClient(ServerTraad client) {
        clients.remove(client);
    }

    public static synchronized void broadcast(String message) {
        for (ServerTraad client : clients) {
            client.send(message);
        }
    }

    public static void main(String[] args) throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(6789);

        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            int playerID = assignPlayerID();
            ServerTraad serverTraad = new ServerTraad(connectionSocket, playerID);
            addClient(serverTraad);
            serverTraad.start();
        }
    }
}
