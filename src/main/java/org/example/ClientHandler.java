package org.example;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {
    protected static List<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket ;
    private BufferedReader bufferedReader ;
    private BufferedWriter bufferedWriter ;
    private String username ;
    public ClientHandler(Socket socket) {
        try{
            this.socket = socket ;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()) ) ;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) ;
            this.clientHandlers.add(this) ;
            this.username = bufferedReader.readLine() ;
            sendMessage("server: " + this.username + " has connected now!");
        }catch (IOException ioException) {
            closeConnection();
        }
    }

    public String getUsername() {
        return username;
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    public void run()
    {
        while(socket.isConnected()) {
            try {
                sendMessage(bufferedReader.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void sendMessage(String message) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                clientHandler.getBufferedWriter().write(message);
                clientHandler.getBufferedWriter().newLine();
                clientHandler.getBufferedWriter().flush();
            } catch (IOException e) {
                closeConnection();
            }
        }
    }
    public void closeConnection() {
        try {
            this.socket.close();
            this.bufferedWriter.close();
            this.bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
