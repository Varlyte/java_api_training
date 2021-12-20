package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Launcher {
    public static void main(String[] args) {
        try {
            HttpServer myServer = HttpServer.create(new InetSocketAddress(Integer.parseInt(args[0])), 0);
            myServer.setExecutor(Executors.newFixedThreadPool(1));
            myServer.createContext("/ping", new CallHandler());
            myServer.start();
        } catch (IOException e) {
            System.out.println("Caught error : "+e.getMessage());
        }
    }
}
