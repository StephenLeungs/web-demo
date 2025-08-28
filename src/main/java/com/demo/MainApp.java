package com.demo;

public class MainApp {
    public static void main(String[] args) {
        try {
            int port = 8080;
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            }
            new WebServer(port).start();
            System.out.println("Server started at http://localhost:" + port);
        } catch (Exception e) {
            System.err.println("Server start failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}