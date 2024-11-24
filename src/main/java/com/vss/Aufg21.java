package com.vss;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Aufg21 {
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            String line;
            while((line = in.readLine()) != null && !line.equals("")) {
                System.out.println(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
