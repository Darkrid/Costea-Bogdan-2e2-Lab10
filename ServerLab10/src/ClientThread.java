import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ClientThread extends Thread {
    private Socket socket = null;
    ServerSocket serverSocket;
    public ClientThread(Socket socket) {
        this.socket = socket;
    }
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            String request = new String();
            String response = new String();
            ArrayList<String> registeredNames = new ArrayList<String>();
            String loggedUser = null;
            boolean isFinished = false;
            boolean isLoggedIn = false;

            while (!isFinished) {
                socket.setSoTimeout(300000);
                request = in.readLine();
                System.out.println("// Command received: " + request);
                String[] arr = request.split(" ", 2);
                if (arr[0].equals("stop")) {
                    System.out.println("// Server stopped.");
                    response = "SERVER: Stopped server.";
                    out.println(response);
                    out.flush();
                    isFinished = true;
                    System.exit(1);
                }
                else if (arr[0].equals("exit")) {
                    System.out.println("// Exiting connection.");
                    response = "SERVER: Exiting...";
                    out.println(response);
                    out.flush();
                    isFinished = true;
                }
                else if (arr[0].equals("register") && !isLoggedIn) {
                    System.out.println("// Registering user: " + arr[1]);
                    if (!registeredNames.contains(arr[1])) {
                        registeredNames.add(arr[1]);
                        System.out.println("// User registered.");
                        response = "SERVER: User registered.";
                        out.println(response);
                        out.flush();
                    }
                    else {
                        System.out.println("// User already registered.");
                        response = "SERVER: User already registered, try again.";
                        out.println(response);
                        out.flush();
                    }
                }
                else if (arr[0].equals("register") && isLoggedIn) {
                    System.out.println("// User already logged in.");
                    response = "SERVER: You are already logged in.";
                    out.println(response);
                    out.flush();
                }
                else if (arr[0].equals("login") && !isLoggedIn) {
                    System.out.println("// Logging in as: " + arr[1]);
                    if (registeredNames.contains(arr[1])) {
                        System.out.println("// User logged in.");
                        loggedUser = arr[1];
                        response = "SERVER: User successfully logged in. Welcome, " + loggedUser + ".";
                        isLoggedIn = true;
                        out.println(response);
                        out.flush();
                    }
                    else {
                        System.out.println("// User not found.");
                        response = "SERVER: User " + arr[1] + " not found.";
                        out.println(response);
                        out.flush();
                    }
                }
                else if (arr[0].equals("login") && isLoggedIn) {
                    System.out.println("// Already logged in.");
                    response = "SERVER: You are already logged in.";
                    out.println(response);
                    out.flush();
                }
                else {
                    System.out.println("// Unknown command.");
                    response = "SERVER: Unknown command.";
                    out.println(response);
                    out.flush();
                }
            }
        }
        catch (IOException e) {
            System.err.println("Communication error: " + e);
        }
        finally {
            try {
                socket.close();
            }
            catch(IOException e) {
                System.err.println(e);
            }
        }
    }
}