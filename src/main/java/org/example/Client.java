package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Flow;

public class Client extends JFrame implements KeyListener {
    private Socket socket ;
    private String username ;
    private BufferedWriter bufferedWriter ;
    private BufferedReader bufferedReader ;
    private JTextField message = new JTextField() ;
    private JButton button = new JButton("send") ;
    JTextArea jTextArea = new JTextArea() ;
    private JScrollPane jScrollPane = new JScrollPane(jTextArea) ;
    public Client(Socket socket , String username ) {
        try{
            //init the socket params
            this.socket = socket ;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream() ) ) ;
            this.username = username ;
            this.bufferedWriter.write(username) ;
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();
            //frame params
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
            this.setSize(400 , 500);
            this.setResizable(false);
            this.setLayout(null);
            this.setLocationRelativeTo(null);
            // components params
            message.setBounds(50 , 300 , 300 , 50 );
            this.add(message) ;

            button.setBounds(50, 370 , 300 , 50 );
            this.add(button) ;

            jScrollPane.setBounds(50 , 20 , 300 , 250  );
            this.add(jScrollPane) ;

            this.sendMessage();
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }
    public void sendMessage() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String messageToSend = message.getText() ;
                    if(socket.isConnected() && !messageToSend.trim().isEmpty() ) {
                        bufferedWriter.write(username + " : " + messageToSend );
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                        message.setText("");
                    }
                }catch (IOException ioException) {
                    ioException.printStackTrace(); ;
                }
            }
        });
    }
    public void listening() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(socket.isConnected()) {
                    try {
                        String msg ;
                        if(( msg = bufferedReader.readLine()) != null ) {
                            EventQueue.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    jTextArea.append(msg + "\n") ;
                                }
                            });
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        Login login = new Login() ;
        login.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(login.textField.getText().trim().isEmpty() ) {
                    JOptionPane.showMessageDialog(login , "enter a valid name");
                }else {
                    login.dispose();
                    try {
                        Socket socket1 = new Socket("localhost" , 1000) ;
                        Client client = new Client(socket1 , login.textField.getText()) ;
                        client.listening();
                    } catch (IOException ioException) {
                        throw new RuntimeException(ioException);
                    }
                }
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) this.button.doClick();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
