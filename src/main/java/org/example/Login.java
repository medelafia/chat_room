package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Login extends JFrame  {
    private JPanel center = new JPanel(new GridLayout(2 , 1 )) ;
    private JPanel bottom = new JPanel(new BorderLayout()) ;
    public JTextField textField  ;
    public JButton button ;
    public Login() {
        /*initialize the attributes*/
        this.textField = new JTextField() ;
        this.button = new JButton("login") ;
        /* frame params*/
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400 , 200);
        this.setLocationRelativeTo(null);
        this.center.setBorder(new EmptyBorder(20 , 20 , 20 , 20 ));
        this.center.add(new JLabel("username")) ;
        this.center.add(textField ) ;
        this.bottom.setBorder(new EmptyBorder(20 , 50 , 20 , 50)) ;
        this.bottom.add(button , BorderLayout.CENTER) ;
        this.add(center , BorderLayout.CENTER) ;
        this.add(bottom , BorderLayout.PAGE_END) ;
    }

}
