import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
    private ObjectInputStream sInput;
    private ObjectOutputStream sOutput;
    private Socket socket;
    private String server, username;
    private int port;

    Client(String server, int port, String username) {
        this.server = server;
        this.port = port;
        this.username = username;
    }

    public boolean start() {
        try {
            socket = new Socket(server, port);
        } catch (Exception ec) {
            System.out.println("Error connecting to server: " + ec);
            return false;
        }

        System.out.println("Connection accepted " + socket.getInetAddress() + ":" + socket.getPort());

        try {
            sInput = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException eIO) {
            System.out.println("Exception creating I/O Streams: " + eIO);
            return false;
        }

        new ListenFromServer().start();

        try {
            sOutput.writeObject(username);
        } catch (IOException e) {
            System.out.println("Exception during login: " + e);
            disconnect();
            return false;
        }

        return true;
    }

    void sendMessage(ChatMessage msg) {
        try {
            sOutput.writeObject(msg);
        } catch (IOException e) {
            System.out.println("Exception writing to server: " + e);
        }
    }

    private void disconnect() {
        try {
            if (sInput != null) sInput.close();
        } catch (Exception e) {}

        try {
            if (sOutput != null) sOutput.close();
        } catch (Exception e) {}

        try {
            if (socket != null) socket.close();
        } catch (Exception e) {}
    }

    class ListenFromServer extends Thread {
        public void run() {
            while (true) {
                try {
                    String msg = (String) sInput.readObject();
                    System.out.println(msg);
                    System.out.print("> ");
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Server has closed the connection.");
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        int portNumber = 1500;
        String serverAddress = "localhost";
        String userName = "Anonymous";

        switch (args.length) {
            case 3 -> {
                userName = args[0];
                portNumber = Integer.parseInt(args[1]);
                serverAddress = args[2];
            }
            case 2 -> {
                userName = args[0];
                portNumber = Integer.parseInt(args[1]);
            }
            case 1 -> userName = args[0];
        }

        Client client = new Client(serverAddress, portNumber, userName);
        if (!client.start()) return;

        Scanner scan = new Scanner(System.in);
        System.out.println("\nHello! Welcome to the chatroom.");
        System.out.println("Instructions:");
        System.out.println("1. Type a message to broadcast.");
        System.out.println("2. Type 'WHOISIN' to list users.");
        System.out.println("3. Type 'LOGOUT' to disconnect.");

        while (true) {
            System.out.print("> ");
            String msg = scan.nextLine();

            if (msg.equalsIgnoreCase("LOGOUT")) {
                client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, ""));
                break;
            } else if (msg.equalsIgnoreCase("WHOISIN")) {
                client.sendMessage(new ChatMessage(ChatMessage.WHOISIN, ""));
            } else {
                client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, msg));
            }
        }

        scan.close();
        client.disconnect();
    }
}
