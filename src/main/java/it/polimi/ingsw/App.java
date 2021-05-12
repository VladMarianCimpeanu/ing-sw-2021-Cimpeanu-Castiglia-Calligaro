package it.polimi.ingsw;

import it.polimi.ingsw.controller.EchoServerClientHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ){
        InetAddress addr = null;
        try {
            addr = InetAddress.getByName("192.168.1.100");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(1234,50, addr);
        } catch (IOException e) {
            System.err.println(e.getMessage()); // ip address or port already used
            return;
        }
        System.out.println("Server ready");

        BufferedReader in = null;
        PrintWriter out = null;
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            System.out.println("New connection accepted");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        } catch(IOException e) {
            System.out.println("Errore a caso");
            // Entrerei qui se serverSocket venisse chiuso
        }

//        try {
//            Thread.sleep(20*1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("sono passati 20 sec");
//        out.println("Se leggi questo messaggio sei crashato.");
//        out.flush();
//        System.out.println("gli ho detto che è crashato");

        while(true){
            try {
                socket.setSoTimeout(10 * 1000);
                String letto = in.readLine();
                if(letto == null){
                    System.out.println("E' crashatooooooo!");
                    break;
                }
                System.out.println(letto);
            } catch (SocketTimeoutException e) {
                System.out.println("SocketTimeout lanciata");
                out.println("PING");
                out.flush();
            } catch (IOException e) {
                System.out.println("IOException lanciata");
                break;
            }
        }
    }
}

