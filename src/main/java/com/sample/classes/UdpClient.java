package com.sample.classes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClient {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; // Change this to the server's IP address if needed
        int port = 4711;

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(serverAddress);
            byte[] buffer = new byte[512];

            while (true) {
                String message = "Hello, server"; // Replace with actual message
                buffer = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
                socket.send(packet);

                packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Server response: " + received);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}