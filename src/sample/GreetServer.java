package sample;

import java.net.*;
import java.io.*;

public class GreetServer {
    private ServerSocket serverSocket;
    private Socket clientSocket1;
    private Socket clientSocket2;
    private PrintWriter out1;
    private BufferedReader in1;
    private PrintWriter out2;
    private BufferedReader in2;

    public void start(int port) throws IOException {
        boolean isConnected1 = false;
        boolean isConnected2 = false;
        serverSocket= new ServerSocket(port);
        clientSocket1 = serverSocket.accept();
        clientSocket2 = serverSocket.accept();
        out1 = new PrintWriter(clientSocket1.getOutputStream(), true);
        in1 = new BufferedReader(new InputStreamReader(clientSocket1.getInputStream()));
        out2 = new PrintWriter(clientSocket2.getOutputStream(), true);
        in2 = new BufferedReader(new InputStreamReader(clientSocket2.getInputStream()));
        String greeting1 = in1.readLine();
        String greeting2 = in2.readLine();
        System.out.println(greeting1);
        if ("hello server".equals(greeting1)) {
            out1.println("hello client 1");
        }else {
            out1.println("unrecognised greeting");
        }
        System.out.println(greeting2);
        if ("hello server".equals(greeting2)) {
            out2.println("hello client 2");
        }else {
            out2.println("unrecognised greeting");
        }
    }

    void communicate1() throws IOException {
        String input = in1.readLine();
        if(input!=""){
            out2.println(input);
        }
    }

    void communicate2() throws IOException{
        String input = in2.readLine();
        if(input!=""){
            out1.println(input);
        }
    }

    public void stop() throws IOException {
        in1.close();
        out1.close();
        in2.close();
        out2.close();
        clientSocket1.close();
        clientSocket2.close();
        serverSocket.close();
    }
    public static void main(String[] args) throws IOException {
        GreetServer server=new GreetServer();
        server.start(6666);
    }
}
