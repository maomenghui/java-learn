package com.socket.client;
import java.io.*;
import java.net.*;

/**
 * TODO
 *
 * @Author Mao
 * @@Date  2024/8/6 16:11         
 * @Version:    1.0     
 **/
public class TCPClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        System.out.println("Connected to server");

        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        output.println("Hello from client!");
        String response = input.readLine();
        System.out.println("Server response: " + response);

        input.close();
        output.close();
        socket.close();
    }
}

