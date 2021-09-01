package ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import model.*;
import persistence.*;


public class Jabo extends JFrame implements ActionListener {


    JButton jb1 = new JButton("First time here");
    JButton jb2 = new JButton("Have been here");
    JTextField jtf = new JTextField();
    JTextArea jta;

    JButton displayCurrentSum;
    JButton doneOrdering;
    JButton saveHistory;
    JButton loadHistory;

    JPanel jpanel = new JPanel();
    String customerName;
    JLabel jlabel2 = new JLabel();

    private String jsonStore;
    private Scanner input;
    private WorkRoom workRoom;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Customer customer;




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

        jb1.addActionListener(this);
        jb2.addActionListener(this);

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
        setBoundAndAddtoPanel(jpanel, jcheckOut,290, 700, 400, 140);
        displayCurrentSum = new JButton("Current Total");
        doneOrdering = new JButton("Finish ordering");
        saveHistory = new JButton("Save current order history");
        loadHistory = new JButton("Load previous order history");


        setBoundAndAddtoPanel(jcheckOut, displayCurrentSum, 0, 0, 200, 70);
        setBoundAndAddtoPanel(jcheckOut, doneOrdering, 0, 70, 200, 70);
        setBoundAndAddtoPanel(jcheckOut, saveHistory, 200, 70, 200, 70);
        setBoundAndAddtoPanel(jcheckOut, loadHistory, 200, 0, 200, 70);


        keyListen(displayCurrentSum);
        keyListen(doneOrdering);
        keyListen(saveHistory);
        keyListen((loadHistory));
        displayCurrentSum.addActionListener(this);
        doneOrdering.addActionListener(this);
        saveHistory.addActionListener(this);
        loadHistory.addActionListener(this);
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
                customerName = customerName.toLowerCase();
                jlabel2.setText("The name of the customer is: " + customerName);
            }
        });
    }

    public void createNewJson() {
        jsonStore = "./data/" + customerName + "workroom.json";
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
            jta.setText("The number  ");
        } else if (e.getSource() == doneOrdering) {
            jta.setText("You have ");
        } else if (e.getSource() == saveHistory) {
            jta.setText("Ordering history");
        } else if (e.getSource() == loadHistory) {
            jta.append(printThingies());
        } else if (e.getSource() == jb1) {
            createNewJson();
            jta.setText("My first time here");
        } else if (e.getSource() == jb2) {
            jta.setText("Have been here");
            loadWorkRoom();
        }

    }



    private void loadWorkRoom() {
        try {
            jsonStore = "./data/" + customerName + "workroom.json";
            JsonReader jsonReader = new JsonReader(jsonStore);
            workRoom = jsonReader.read();
            System.out.println("Loaded " + workRoom.getName() + " from " + jsonStore);

        } catch (IOException e) {
            System.out.println("Unable to read from file: " + jsonStore);
        }
    }

    //EFFECT: Print thingies from workRoom
    private String printThingies() {
        List<Thingy> thingies = workRoom.getThingies();
        String thingyToText = "";
        int count = 1;
        for (Thingy t : thingies) {
            thingyToText = thingyToText.concat(count + ")" + t.getCate() + ":" + t.getName() + "\n");
            System.out.println("Current count is: " + count + "  " + thingyToText);
            count++;
        }
        return thingyToText;
    }


    //MODIFIES: this
    //EFFECTS: add drink or food to the order list
    private void processDrinkandFood(int foodID, int quantity) {
        if (foodID == 1) {
            addCoke(quantity);
        } else if (foodID == 2) {
            addPepsi(quantity);
        } else if (foodID == 3) {
            addChicken(quantity);
        } else if (foodID == 4) {
            addBeef(quantity);
        }
    }

    private void addCoke(int coke) {
        FoodItem fd1 = new FoodItem(1); // 1 is coke
        Thingy co = new Thingy("Coca cola", Category.DRINK);
        for (int i = 0; i < coke; i++) {
            customer.addFood(fd1);
            workRoom.addThingy(co);
        }
    }

    private void addPepsi(int pep) {
        FoodItem fd2 = new FoodItem(2); // 2 is pepsi
        Thingy co = new Thingy("Pepsi", Category.DRINK);
        for (int i = 0; i < pep; i++) {
            customer.addFood(fd2);
            workRoom.addThingy(co);
        }
    }

    private void addChicken(int chicken) {
        FoodItem fd3 = new FoodItem(3); // 3 is chicken
        Thingy co = new Thingy("Chicken Burger", Category.FOOD);
        for (int i = 0; i < chicken; i++) {
            customer.addFood(fd3);
            workRoom.addThingy(co);
        }
    }

    private void addBeef(int beef) {
        FoodItem fd4 = new FoodItem(4); // 4 is beef
        Thingy co = new Thingy("Beef Burger", Category.FOOD);
        for (int i = 0; i < beef; i++) {
            customer.addFood(fd4);
            workRoom.addThingy(co);
        }
    }



    //Effects: cancel (or remove) food items from ordered lists
    private void processCancel() {
        int itemId;
        int numbCancel;
        System.out.println("Please enter Item to cancel");
        System.out.println("Please type 1 for Coke");
        System.out.println("Please type 2 for Pepsi");
        System.out.println("Please type 3 for Chicken");
        System.out.println("Please type 4 for Beef");
        itemId = input.nextInt();

        System.out.println("How many to cancel?");
        numbCancel = input.nextInt();

        //for (int i = 0; i < numbCancel; i++) {
        customer.removeFood(itemId, numbCancel);
        //}
        //printBalanceAndFood();

    }




}