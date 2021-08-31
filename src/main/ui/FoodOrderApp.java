package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


//Source: Mainly from the Teller application posted on course edx site
public class FoodOrderApp {

    private String jsonStore;
    private Scanner input;
    private WorkRoom workRoom;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private String customerName;
    private Customer customer;


    //EFFECTS: run the Food Order application
    public FoodOrderApp() {
        runFoodOrder();
    }

    //MODIFIES: this
    //EFFECTS: process user input
    private void runFoodOrder() {
        boolean keepGoing = true;
        String command = null;

        init();
        input = new Scanner(System.in);
        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("f")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("Are you interested in saving your order history? 1 for yes, 0 for no");
        Scanner input = new Scanner(System.in);
        String history;
        history = input.next();
        history.toLowerCase();
        if (history.equals("1")) {
            saveWorkRoom();
        }

        System.out.println("\nPlease visit again!");
    }

    //MODIFIES: this
    //EFFECTS: process user command
    private void processCommand(String command) {
        if (command.equals("d")) {
            processDrink();
        } else if (command.equals("b")) {
            processBurger();
        } else if (command.equals("c")) {
            processCancel();
        } else if (command.equals("l")) {
            loadWorkRoom();
        } else if (command.equals("p")) {
            printThingies();
        } else {
            System.out.println("Selection not valid ...");
        }
    }


    //MODIFIES: this
    //EFFECTS: initializes Customer
    private void init() {
        System.out.println("What is your name?");
        Scanner nameInput = new Scanner(System.in);
        customerName = nameInput.next();
        customerName.toLowerCase();
        customer = new Customer(customerName);
        workRoom = new WorkRoom(customerName);
        boolean keepSelecting;
        keepSelecting = true;
        while (keepSelecting) {
            System.out.println("Are you first time here? Press 1 for yes, and 0 for no");
            Scanner input = new Scanner(System.in);
            String yesNo;
            yesNo = input.next();
            if (yesNo.equals("1")) {
                createNewJson();
                keepSelecting = false;
            } else if (yesNo.equals("0")) {
                loadOldJson();
                keepSelecting = false;
            } else {
                System.out.println("Invalid Selection, please enter 1 or 0 only");
            }
        }
    }

    //EFFECTS: display menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\td -> drinks (Coca-cola or Pepsi)");
        System.out.println("\tb -> burgers (Chicken or Beef)");
        System.out.println("\tc -> Cancel items");
        System.out.println("\tp -> print all of your history of orderings");
        System.out.println("\tl -> load work room from file");
        System.out.println("\tf -> finished ordering!");
    }

    //MODIFIES: this
    //EFFECTS: add drink to the order list
    private void processDrink() {
        int coke = 0;
        int pep = 0;
        FoodItem fd1 = new FoodItem(1);
        FoodItem fd2 = new FoodItem(2);

        System.out.println("How many cokes? (Coke is $1 each)");
        coke = input.nextInt();
        Thingy co = new Thingy("Coca cola", Category.DRINK);
        if (coke > 0) {
            for (int i = 0; i < coke; i++) {
                customer.addFood(fd1);
                workRoom.addThingy(co);
            }
        }
        System.out.println("How many pepsi? (Pepsi is $2 each)");
        pep = input.nextInt();
        Thingy p = new Thingy("Pepsi", Category.DRINK);
        if (pep > 0) {
            for (int i = 0; i < pep; i++) {
                customer.addFood(fd2);
                workRoom.addThingy(p);
            }
        }
        printBalanceAndFood();
    }


    //MODIFIES: this
    //EFFECTS: add burgers to the order list
    private void processBurger() {
        int chicken = 0;
        int beef = 0;
        FoodItem fd3 = new FoodItem(3);
        FoodItem fd4 = new FoodItem(4);

        System.out.println("How many chickens sandwiches? (Chicken is $6 each)");
        chicken = input.nextInt();
        Thingy chi = new Thingy("Chicken", Category.FOOD);
        if (chicken > 0) {
            for (int i = 0; i < chicken; i++) {
                customer.addFood(fd3);
                workRoom.addThingy(chi);
            }
        }

        System.out.println("How many beef sandwiches? (Beef is $8 each)");
        beef = input.nextInt();
        Thingy bf = new Thingy("Beef", Category.FOOD);
        if (beef > 0) {
            for (int i = 0; i < beef; i++) {
                customer.addFood(fd4);
                workRoom.addThingy(bf);
            }
        }
        printBalanceAndFood();
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
        printBalanceAndFood();

    }

    //EFFECTS: Show previous order history
    private void processHistory() {
        printThingies();
    }

    //EFFECTS: create new .json file if new user
    public void createNewJson() {
        System.out.println("first time");
        jsonStore = "./data/" + customerName + "workroom.json";
    }

    //EFFECTS: load old .json file
    public void loadOldJson() {
        System.out.println("have been here");
        jsonStore = "./data/" + customerName + "workroom.json";
    }



    // EFFECTS: saves the workroom to file
    private void saveWorkRoom() {
        try {
            jsonWriter = new JsonWriter(jsonStore);
            jsonWriter.open();
            jsonWriter.write(workRoom);
            jsonWriter.close();
            System.out.println("Saved " + workRoom.getName() + " to " + jsonStore);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + jsonStore);
        }
    }


    private void printThingies() {
        List<Thingy> thingies = workRoom.getThingies();
        for (Thingy t : thingies) {
            System.out.println(t);
        }
    }



    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadWorkRoom() {
        try {
            JsonReader jsonReader = new JsonReader(jsonStore);
            workRoom = jsonReader.read();
            System.out.println("Loaded " + workRoom.getName() + " from " + jsonStore);

        } catch (IOException e) {
            System.out.println("Unable to read from file: " + jsonStore);
        }
    }


    //Effects: prints current balance and food ordered
    private void printBalanceAndFood() {
        System.out.println("Your current orders are following:");
        System.out.println("\tCoke " + customer.countIndividualItems(1));
        System.out.println(
                 "\tPepsi " + customer.countIndividualItems(2));
        System.out.println(
                "\tChicken " + customer.countIndividualItems(3));
        System.out.println(
                "\tBeef " + customer.countIndividualItems(4));
        System.out.println("Your current"
                + "total is: " + customer.totalCost());
    }
}





