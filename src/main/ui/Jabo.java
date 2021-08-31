package ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class Jabo extends JFrame implements ActionListener {

    JButton jb1 = new JButton("First time here");
    JButton jb2 = new JButton("Have been here");
    JTextField jtf = new JTextField();
    JTextArea jta;

    JButton displayCurrentSum;
    JButton doneOrdering;
    JButton saveHistory;

    JPanel jpanel = new JPanel();
    String customerName;
    JLabel jlabel2 = new JLabel();



    public static void main(String[] args) {
        Jabo jabo = new Jabo();
        jabo.makePanel();

    }

    public void makePanel() {
        jpanel.setLayout(null);
        setBoundAndAddtoPanel(jpanel, jb1, 70, 50, 150, 30);
        setBoundAndAddtoPanel(jpanel, jb2, 280, 50, 200, 30);

        setBoundAndAddtoPanel(jpanel, jtf, 70, 250, 150, 30);

        jtf.setToolTipText("Enter without spaces");
        jtf.setBackground(Color.yellow);
        JLabel jlab = new JLabel("Enter Your Name here: ");
        setBoundAndAddtoPanel(jpanel, jlab,70, 200, 200, 70);


        // set Panel size and location
        jpanel.setBackground(Color.lightGray);

        Border blackline = BorderFactory.createLineBorder(Color.black);
        jpanel.setBorder(blackline);


        add(jpanel);
        setVisible(true);

        setTitle("Food Ordering");
        setSize(1800, 2400);

        setBoundAndAddtoPanel(jpanel, jlabel2, 70, 350, 400, 100);

        nameListen(jtf);

        messageArea();
        placeOrderPanel();
        cancelOrderPanel();
        checkOutPanel();

    }


    public void placeOrderPanel() {
        JLabel orderLabel = new JLabel("Enter order here: ");
        setBoundAndAddtoPanel(jpanel, orderLabel,290, 200, 100, 70);

        JPanel spanel = new JPanel();
        spanel.setLayout(new GridLayout(2,2));
        setBoundAndAddtoPanel(jpanel, spanel, 290, 248, 450, 140);

        JTextField pepsiField = new JTextField(10);
        JTextField cocaField = new JTextField(10);
        JTextField beefField = new JTextField(10);
        JTextField porkField = new JTextField(10);

        spanel.add(new JLabel("Pepsi"));
        spanel.add(pepsiField);
        spanel.add(new JLabel("Coca-cola"));
        spanel.add(cocaField);
        spanel.add(new JLabel("Beef Burger"));
        spanel.add(beefField);
        spanel.add(new JLabel("Pork Burger"));
        spanel.add(porkField);

    }

    public void cancelOrderPanel() {
        JLabel cancelLabel = new JLabel("Enter number of cancel here: ");
        setBoundAndAddtoPanel(jpanel, cancelLabel,290, 500, 200, 70);

        JPanel spanel = new JPanel();
        spanel.setLayout(new GridLayout(2,2));
        setBoundAndAddtoPanel(jpanel, spanel, 290, 548, 450, 140);

        JTextField pepsiField = new JTextField(10);
        JTextField cocaField = new JTextField(10);
        JTextField beefField = new JTextField(10);
        JTextField porkField = new JTextField(10);

        spanel.add(new JLabel("Pepsi"));
        spanel.add(pepsiField);
        spanel.add(new JLabel("Coca-cola"));
        spanel.add(cocaField);
        spanel.add(new JLabel("Beef Burger"));
        spanel.add(beefField);
        spanel.add(new JLabel("Pork Burger"));
        spanel.add(porkField);

    }

    public void checkOutPanel() {

        JPanel jcheckOut = new JPanel();
        jcheckOut.setLayout(null);
        setBoundAndAddtoPanel(jpanel, jcheckOut,290, 700, 200, 210);
        displayCurrentSum = new JButton("Current Total");
        doneOrdering = new JButton("Finish ordering");
        saveHistory = new JButton("Save current order history");

        setBoundAndAddtoPanel(jcheckOut, displayCurrentSum, 0, 0, 200, 70);
        setBoundAndAddtoPanel(jcheckOut, doneOrdering, 0, 70, 200, 70);
        setBoundAndAddtoPanel(jcheckOut, saveHistory, 0, 140, 200, 70);

//        displayCurrentSum.addActionListener(this);
//        doneOrdering.addActionListener(this);
//        saveHistory.addActionListener(this);
//
        keyListen(displayCurrentSum);
        keyListen(doneOrdering);
        keyListen(saveHistory);

    }



    //EFFECT: Add component to panel and set sizes and coordinates on the panel
    public void setBoundAndAddtoPanel(JPanel jp, Component cp, int x, int y, int width, int height) {
        cp.setBounds(x,y, width, height);
        jp.add(cp);
    }

    //EFFECT: Listen to number of order
    public void orderListen(JButton comp){

    }

    //EFFECT: Listen for the "Enter" key in the JTextArea provided
    public void nameListen(JTextField comp) {
        comp.addActionListener(new ActionListener() {
            JTextField jtf2;

            @Override
            public void actionPerformed(ActionEvent e) {
                jtf2 = (JTextField)e.getSource();
                customerName = jtf2.getText();
                jlabel2.setText("The name of the customer is: " + customerName);
            }
        });

    }



    public void keyListen(JButton jbut) {
        jbut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jlabel2.setText("button hit " + e.getActionCommand());
            }
        });
    }

    //EFFECTS: Display an area to display message
    public void messageArea() {
        jta = new JTextArea();
        jta.setBounds(900, 10, 450, 500);
        jpanel.add(jta);
        jpanel.setSize(1200, 1300);
        jta.setEditable(false);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == displayCurrentSum) {
            jta.setText("The number of drink you have ordered so far is: ");
        } else if (e.getSource() == doneOrdering) {
            jta.setText("You have ordered x number of burgers");
        } else if (e.getSource() == saveHistory) {
            jta.setText("Ordering history");
        }

    }
}