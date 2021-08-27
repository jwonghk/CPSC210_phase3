package ui;


import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;

public class Japara extends JFrame implements WindowListener,ActionListener {
    TextField text = new TextField(20);
    JButton button,b2,b3;
    private int numClicks = 0;




    public static void main(String[] args) {
        Japara myWindow = new Japara("My first window");
        myWindow.setSize(750,500);
        myWindow.setVisible(true);
    }


    public Japara(String title) {

        super(title);
        setLayout(null);
        addWindowListener(this);
        button = new JButton("Click me");
        b2 = new JButton ("click this");
        button.setBounds(130,120,100,100);


        b2.setBounds(430,120,100,55);


        add(button);
        add(b2);
        add(text);
        text.setBounds(455,230, 140, 70);
        button.addActionListener(this);
        b2.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        String displayMessage;
        numClicks++;
        text.setText("Button Clicked " + numClicks + " times");
        displayMessage = String.format("Your name is" , e.getActionCommand());

        JOptionPane.showMessageDialog(null, displayMessage);


    }


    @Override
    public void windowOpened(WindowEvent e) {

    }

    public void windowClosing(WindowEvent e) {
        dispose();
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }


}
