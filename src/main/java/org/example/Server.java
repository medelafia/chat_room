package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket ;
    public Server(ServerSocket socket) {
        this.serverSocket = socket ;
    }
    public void startServer() {
        while(!this.serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept() ;
                System.out.println("a new client connected to room");
                ClientHandler clientHandler = new ClientHandler(socket) ;
                Thread thread = new Thread(clientHandler) ;
                thread.start();
            } catch(IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
    public void closeSocket() {
        try {
            if(serverSocket != null)
                this.serverSocket.close();
        }catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            ServerSocket socket =  new ServerSocket(1000 ) ;
            Server server = new Server(socket) ;
            server.startServer();
            server.closeSocket();
        }catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
}
