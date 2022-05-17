import java.io.*;
import java.net.*;

public class Server {
    public static final int PORT = 8100;
    public Server() throws IOException{
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("// Server initiated.");
            while (true) {
                System.out.println("// Waiting for new connection...");
                Socket socket = serverSocket.accept();
                System.out.println("// Client connected.");
                new ClientThread(socket).start();
            }
        }
        catch (IOException i) {
            System.out.println(i);
        }
        finally {
            serverSocket.close();
        }
    }
    public static void main(String[] args) throws IOException {
        Server server = new Server();
    }
}
