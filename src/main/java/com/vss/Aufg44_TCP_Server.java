package com.vss;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Aufg44_TCP_Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(4711)) {
            System.out.println("Server is listening on port 4711");
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    System.out.println("New client connected");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("Received: " + line);
                        pw.println(line);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
