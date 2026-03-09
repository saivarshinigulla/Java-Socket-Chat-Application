import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("localhost", 5000);

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

        BufferedReader serverInput = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        Thread readThread = new Thread(() -> {
            try {
                String msg;
                while ((msg = serverInput.readLine()) != null) {
                    System.out.println(msg);
                }
            } catch (Exception e) {}
        });

        readThread.start();

        while (true) {
            String message = input.readLine();
            output.println(message);
        }
    }
}