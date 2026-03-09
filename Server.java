import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    static Vector<ClientHandler> clients = new Vector<>();

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server Started...");

        while (true) {

            Socket socket = serverSocket.accept();
            System.out.println("New client connected");

            ClientHandler client = new ClientHandler(socket);
            clients.add(client);

            Thread t = new Thread(client);
            t.start();
        }
    }
}

class ClientHandler implements Runnable {

    Socket socket;
    BufferedReader input;
    PrintWriter output;

    ClientHandler(Socket socket) throws Exception {
        this.socket = socket;
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);
    }

    public void run() {

        try {
            String message;

            while ((message = input.readLine()) != null) {

                for (ClientHandler client : Server.clients) {
                    client.output.println(message);
                }
            }

        } catch (Exception e) {
            System.out.println("Client disconnected");
        }
    }
}