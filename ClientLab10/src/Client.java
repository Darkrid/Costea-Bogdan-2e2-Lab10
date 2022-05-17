import java.net.*;
import java.io.*;

public class Client {
    public static void main(String args[]) throws IOException{
        String serverAddress = "127.0.0.1";
        int PORT = 8100;

        try {
            Socket socket = new Socket(serverAddress, PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader (new InputStreamReader(socket.getInputStream()));
            DataInputStream input = new DataInputStream(System.in);

            boolean isFinished = false;

            while (!isFinished) {
                String request = input.readLine();
                out.println(request);

                String response = in.readLine();
                System.out.println(response);
            }
        }
        catch (UnknownHostException e){
            System.err.println("No server listening: " + e);
        }
    }
}
