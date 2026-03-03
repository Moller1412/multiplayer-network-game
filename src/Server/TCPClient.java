package Server;

import java.io.IOException;
import java.net.Socket;

public class TCPClient {
    public static void main(String[] args) throws Exception, IOException {
        Socket clientSocket = new Socket("10.10.131.158", 6789);
            new ReadThread(clientSocket).start();
            new WriteThread(clientSocket).start();

    }
}
