package com.vss;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Aufg41_UDP_Server {
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
                System.out.println("received: " + line);
                socket.send(packet);
            }
            socket.close();
            System.out.println("---server stopped");

        } catch (IOException e) {
            System.out.println("IOException in UDP-Echo-Server: " + e.getMessage());
        }


    }
}
