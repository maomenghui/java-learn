package com.socket.server;
import java.io.*;
import java.net.*;
/**
 * TODO
 *
 * @Description
 * @Author 56359
 * @Date 2024/8/6 15:44
 **/
public class TCPServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Server is listening on port 1234");

        Socket socket = serverSocket.accept();
        System.out.println("Client connected");

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

        String message = input.readLine();
        System.out.println("Received: " + message);
        output.println("Hello from server!");

        input.close();
        output.close();
        socket.close();
        serverSocket.close();
    }
}
