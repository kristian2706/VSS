package com.vss;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class Aufg42_UDP_Client {
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            DatagramSocket socket = new DatagramSocket();
            socket.setSoTimeout(50);
            byte[] receiveBuffer = new byte[512];
            DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            InetAddress localhost = InetAddress.getByName("localhost");
            while (true) {
                try {
                    packet.setData(receiveBuffer);
                    packet.setLength(receiveBuffer.length);
                    socket.receive(packet);
                    System.out.println("---received: " + new String(packet.getData(), 0, packet.getLength()));
                } catch (SocketTimeoutException e) { /**/}
                if (in.ready()) {
                    String line;
                    packet.setAddress(localhost);
                    packet.setPort(4711);
                    if ((line = in.readLine()) == null || line.equals("")) {
                        System.out.println("end program");
                        byte[] buffer = "---shutdown".getBytes();
                        packet.setData(buffer);
                        packet.setLength(buffer.length);
                        socket.send(packet);
                        break;
                    }
                    packet.setData(line.getBytes());
                    packet.setLength(line.length());
                    socket.send(packet);
                }
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
