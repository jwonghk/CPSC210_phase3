package ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class Jabo extends JFrame implements ActionListener {

    JButton jb1 = new JButton("First time here");
    JButton jb2 = new JButton("Have been here");
    JTextField jtf = new JTextField();

    JPanel jpanel = new JPanel();



    public static void main(String[] args) {
        Jabo jabo = new Jabo();
        jabo.makePanel();

    }

    public void makePanel() {
        jpanel.setLayout(null);
        jpanel.add(jb1);
        jpanel.add(jb2);
        jb1.setBounds(70, 50, 150, 30);
        jb2.setBounds(280, 50, 200, 30);

        jpanel.add(jtf);
        jtf.setBounds(70, 250, 150, 30);
        jtf.setToolTipText("Enter without spaces");
        jtf.setBackground(Color.yellow);


        // set Panel size and location
        jpanel.setBackground(Color.lightGray);

        Border blackline = BorderFactory.createLineBorder(Color.black);
        jpanel.setBorder(blackline);

        add(jpanel);
        setVisible(true);

        setTitle("Food Ordering");
        setSize(1800, 2400);


    }


    public void actionPerformed(ActionEvent ae){

    }
}