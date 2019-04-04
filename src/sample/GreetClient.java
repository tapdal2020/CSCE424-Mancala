package sample;

import java.net.*;
import java.io.*;
import java.util.Scanner;


public class GreetClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendReady() throws IOException{
        String ready = "ready";
        out.print(ready);
        String resp = in.readLine();
        return resp;
    }


    public String sendMessage(int move) throws IOException {

        out.println(move);
        String resp = in.readLine();
        return resp;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) throws IOException {
        GreetClient client = new GreetClient();
        client.startConnection("127.0.0.1", 6666);
        Scanner keyboard = new Scanner(System.in);
        String msg = keyboard.nextLine();
        int move = Integer.parseInt(msg);
        String response = client.sendMessage(move);
        System.out.println(response);
    }
}