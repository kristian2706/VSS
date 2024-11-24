package com.sample.classes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpServer {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(4711);
            byte[] data = new byte[512];
            DatagramPacket packet = new DatagramPacket(data, 512);
            while (true) {
                packet.setLength(data.length);
                socket.receive(packet);
                String line = new String(data, 0, packet.getLength());
                if (packet.getLength() == 0 || line.equals("---shutdown")) break;
                System.out.println("Received: " + line);
                socket.send(packet);
            }
            socket.close();
            System.out.println("---server stopped");

        } catch (IOException e) {
            System.out.println("IOException in UDP-Echo-Server: " + e.getMessage());
        }
    }
}
