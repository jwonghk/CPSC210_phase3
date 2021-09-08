package ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
    JButton displayCurrentOrder;


    JPanel jpanel = new JPanel();
    String customerName;
    JLabel jlabel2 = new JLabel();

    JTextField pepsiField;
    JTextField cocaField;
    JTextField beefField;
    JTextField chickenField;

    JTextField pepsiCancelField;
    JTextField cocaCancelField;
    JTextField beefCancelField;
    JTextField chickenCancelField;

    private String jsonStore;
    private Scanner input;
    private WorkRoom workRoom;
    private WorkRoom currentOrderRoom;
    private WorkRoom completeWorkRoom; // this is used as alternative to workRoom in case
                                   // customer wants to save current history. Then
                                 // the completeWorkRoom will be written instead of workRoom
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

        pepsiField = new JTextField(10);
        cocaField = new JTextField(10);
        beefField = new JTextField(10);
        chickenField = new JTextField(10);

        spanel.add(new JLabel("Pepsi"));
        spanel.add(pepsiField);
        spanel.add(new JLabel("Coca-cola"));
        spanel.add(cocaField);
        spanel.add(new JLabel("Beef Burger"));
        spanel.add(beefField);
        spanel.add(new JLabel("Chicken Burger"));
        spanel.add(chickenField);

        foodDrinkAddListen(pepsiField);
        foodDrinkAddListen(cocaField);
        foodDrinkAddListen(beefField);
        foodDrinkAddListen(chickenField);
    }

    public void cancelOrderPanel() {
        JLabel cancelLabel = new JLabel("Enter number of cancel here: ");
        setBoundAndAddtoPanel(jpanel, cancelLabel,290, 500, 200, 70);

        JPanel spanel = new JPanel();
        spanel.setLayout(new GridLayout(2,2));
        setBoundAndAddtoPanel(jpanel, spanel, 290, 548, 450, 140);

        pepsiCancelField = new JTextField(10);
        cocaCancelField = new JTextField(10);
        beefCancelField = new JTextField(10);
        chickenCancelField = new JTextField(10);

        spanel.add(new JLabel("Pepsi"));
        spanel.add(pepsiCancelField);
        spanel.add(new JLabel("Coca-cola"));
        spanel.add(cocaCancelField);
        spanel.add(new JLabel("Beef Burger"));
        spanel.add(beefCancelField);
        spanel.add(new JLabel("Chicken Burger"));
        spanel.add(chickenCancelField);

        foodDrinkCancelListen(pepsiCancelField);
        foodDrinkCancelListen(cocaCancelField);
        foodDrinkCancelListen(beefCancelField);
        foodDrinkCancelListen(chickenCancelField);
    }

    public void checkOutPanel() {
        JPanel jcheckOut = new JPanel();
        jcheckOut.setLayout(null);
        setBoundAndAddtoPanel(jpanel, jcheckOut,290, 700, 400, 210);
        displayCurrentSum = new JButton("Current Total");
        doneOrdering = new JButton("Finish ordering");
        saveHistory = new JButton("Save current order history");
        loadHistory = new JButton("Load previous order history");
        displayCurrentOrder = new JButton("Show current order");


        setBoundAndAddtoPanel(jcheckOut, displayCurrentSum, 0, 0, 200, 70);
        setBoundAndAddtoPanel(jcheckOut, doneOrdering, 0, 70, 200, 70);
        setBoundAndAddtoPanel(jcheckOut, saveHistory, 200, 70, 200, 70);
        setBoundAndAddtoPanel(jcheckOut, loadHistory, 200, 0, 200, 70);
        setBoundAndAddtoPanel(jcheckOut, displayCurrentOrder, 0, 140, 400, 70);

        keyListen(displayCurrentSum);
        keyListen(doneOrdering);
        keyListen(saveHistory);
        keyListen(loadHistory);
        keyListen(displayCurrentSum);
        displayCurrentSum.addActionListener(this);
        doneOrdering.addActionListener(this);
        saveHistory.addActionListener(this);
        loadHistory.addActionListener(this);
        displayCurrentOrder.addActionListener(this);
    }



    //EFFECT: Add component to panel and set sizes and coordinates on the panel
    public void setBoundAndAddtoPanel(JPanel jp, Component cp, int x, int y, int width, int height) {
        cp.setBounds(x,y, width, height);
        jp.add(cp);
    }

    //EFFECT: Listen for the "Enter" key in the JTextArea provided
    public void nameListen(JTextField comp) {
        comp.addActionListener(new ActionListener() {
            //JTextField jtf2;

            @Override
            public void actionPerformed(ActionEvent e) {
//                jtf2 = (JTextField)e.getSource();
//                customerName = jtf2.getText();
                customerName = comp.getText();
                customerName = customerName.toLowerCase();
                customer = new Customer(customerName);
                workRoom = new WorkRoom(customerName);
                currentOrderRoom = new WorkRoom(customerName);
                jlabel2.setText("The name of the customer is: " + customerName);
            }
        });
    }

    public void createNewJson() {
        jsonStore = "./data/" + customerName + "workroom.json";
        completeWorkRoom = new WorkRoom(customerName);
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
            jta.setText("The current total price is:   " + calculateTotal());
        } else if (e.getSource() == doneOrdering) {
            jta.setText("Have a nice day! Please visit again!");
        } else if (e.getSource() == saveHistory) {
            saveWorkRoom();
            jta.setText("Saving your current order to history.");
        } else if (e.getSource() == loadHistory) {
            jta.setText(printThingies(false));
        } else if (e.getSource() == jb1) {
            createNewJson();
            jta.setText("Hello, welcome! This is your first time here!!");
        } else if (e.getSource() == jb2) {
            jta.setText("Welcome Back!!");
            loadWorkRoom();
        } else if (e.getSource() == displayCurrentOrder) {
            jta.setText(loadCurrentOrder());
        }
    }

    //EFFECT: Calculate total costs for current purchase
    private float calculateTotal() {
        float total = 0;

        for (Thingy t : currentOrderRoom.getThingies()) {
            if (t.getName().equals("Pepsi")) {
                total = total + (float)1.2;
            } else if (t.getName().equals("Coca cola")) {
                total = total + (float)1.5;
            } else if (t.getName().equals("Chicken Burger")) {
                total = total + (float)3.5;
            } else if (t.getName().equals("Beef Burger")) {
                total = total + (float)3.8;
            }
        }
        return total;
    }


    //EFFECT: load only order from this time
    private String loadCurrentOrder() {
        return printThingies(true);
    }

    private void foodDrinkAddListen(JTextField jtf) {
        jtf.addActionListener(new ActionListener() {
            int order = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == cocaField) {
                    order = Integer.parseInt(cocaField.getText());
                    processDrinkandFood(1, order);
                    jta.setText("Coca Cola ordered");
                } else if (e.getSource() == pepsiField) {
                    order = Integer.parseInt(pepsiField.getText());
                    processDrinkandFood(2, order);
                    jta.setText("Pepsi ordered");
                } else if (e.getSource() == chickenField) {
                    order = Integer.parseInt(chickenField.getText());
                    processDrinkandFood(3, order);
                    jta.setText("Chicken burger ordered");
                } else if (e.getSource() == beefField) {
                    order = Integer.parseInt(beefField.getText());
                    processDrinkandFood(4, order);
                    jta.setText("Beef burger ordered");
                }
            }
        });
    }




    private void foodDrinkCancelListen(JTextField jtf) {
        jtf.addActionListener(new ActionListener() {
            int numbCancel;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == pepsiCancelField) {
                    numbCancel = Integer.parseInt(pepsiCancelField.getText());
                    shortern25("Pepsi", numbCancel);

                } else if (e.getSource() == cocaCancelField) {
                    numbCancel = Integer.parseInt(cocaCancelField.getText());
                    shortern25("Coca cola", numbCancel);

                } else if (e.getSource() == chickenCancelField) {
                    numbCancel = Integer.parseInt(chickenCancelField.getText());
                    shortern25("Chicken Burger", numbCancel);

                } else if (e.getSource() == beefCancelField) {
                    numbCancel = Integer.parseInt(beefCancelField.getText());
                    shortern25("Beef Burger", numbCancel);

                }
            }
        });
    }

    //EFFECT: maximum only 25 lines allowed, so put part of the for
    //        foodDrinkCancelListen here
    private void shortern25(String typeFood, int cancel) {
        currentOrderRoom = removeFood(typeFood, cancel);
        completeWorkRoom = removeFood(typeFood, cancel);
        jta.setText(cancel + " " + typeFood + " got cancelled");
    }

    private WorkRoom removeFood(String foodName, int numbCancel) {
        List<Thingy> foodThingies = currentOrderRoom.getThingies();
        WorkRoom shortTermWr = new WorkRoom(customerName);

        int count = 0;

        for (Thingy t : foodThingies) {
            if (!t.getName().equals(foodName)) {
                shortTermWr.addThingy(t);
            } else if (t.getName().equals(foodName) && count >= numbCancel) {
                shortTermWr.addThingy(t);
            } else if (t.getName().equals(foodName)) {
                count++;
            }
        }
        return shortTermWr;
    }

    private void loadWorkRoom() {
        try {
            jsonStore = "./data/" + customerName + "workroom.json";
            JsonReader jsonReader = new JsonReader(jsonStore);
            workRoom = jsonReader.read();
            System.out.println("Loaded " + workRoom.getName() + " from " + jsonStore);
            completeWorkRoom = jsonReader.read();

        } catch (IOException e) {
            System.out.println("Unable to read from file: " + jsonStore);
        }
    }


    private void saveWorkRoom() {
        try {
            jsonWriter = new JsonWriter(jsonStore);
            jsonWriter.open();
            jsonWriter.write(completeWorkRoom);
            jsonWriter.close();
            System.out.println("Saved " + completeWorkRoom.getName() + " to " + jsonStore);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + jsonStore);
        }
    }

    //EFFECT: Print thingies from workRoom
    private String printThingies(boolean current) {
        List<Thingy> thingies = new ArrayList<>();
        if (current) {
            thingies = currentOrderRoom.getThingies();
        } else if (current == false) {
            thingies = workRoom.getThingies();
        }
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
    public void processDrinkandFood(int foodID, int quantity) {
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
            //customer.addFood(fd1);
            completeWorkRoom.addThingy(co);
            currentOrderRoom.addThingy(co);
        }
    }

    private void addPepsi(int pep) {
        FoodItem fd2 = new FoodItem(2); // 2 is pepsi
        Thingy co = new Thingy("Pepsi", Category.DRINK);
        for (int i = 0; i < pep; i++) {
            //customer.addFood(fd2);
            completeWorkRoom.addThingy(co);
            currentOrderRoom.addThingy(co);
        }
    }

    private void addChicken(int chicken) {
        FoodItem fd3 = new FoodItem(3); // 3 is chicken
        Thingy co = new Thingy("Chicken Burger", Category.FOOD);
        for (int i = 0; i < chicken; i++) {
            //customer.addFood(fd3);
            completeWorkRoom.addThingy(co);
            currentOrderRoom.addThingy(co);
        }
    }

    private void addBeef(int beef) {
        FoodItem fd4 = new FoodItem(4); // 4 is beef
        Thingy co = new Thingy("Beef Burger", Category.FOOD);
        for (int i = 0; i < beef; i++) {
            //customer.addFood(fd4);
            completeWorkRoom.addThingy(co);
            currentOrderRoom.addThingy(co);
        }
    }
}