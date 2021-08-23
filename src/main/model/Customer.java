package model;

import java.util.ArrayList;

public class Customer {

    protected static int firstCustomerID = 1; //tracks ID of the customer, kind of like order number
    protected int orderID;               //orderID of the customer
    protected double balance;               //balance of the ordered foods so far
    //private ArrayList<FoodItem> foodItemsOrdered;       //ordered list of foods so far
    protected String customerName;
    protected FoodItemList fdList;


    //REQUIRES: Customer's name is non-zero length
    //EFFECT: customer's name is set to name;
    //        orderID of each customer is automatically assigned;
    //        each customer has a list of food items stored
    //        total cost of the customer's orders is calculated
    public Customer(String name) {
        this.customerName = name;
        this.orderID = firstCustomerID++;   //firstCustomer + 1 ??????? (is it allowed??)
        this.fdList = new FoodItemList();
        this.balance = this.totalCost();
    }


    public int getId() {
        return orderID;
    }

    public String getName() {
        return customerName;
    }

    public double getBalance() {
        return balance;
    }

    //REQUIRES: food to be added has to be of type FoodItem
    //MODIFIES: this
    //EFFECT: add a FoodItem to the customer's list of foods ordered variable FoodItemList
    public void addFood(FoodItem foodItem) {
        this.fdList.addFood(foodItem);
    }

    //REQUIRES: the food id has to be integer, also number of them to be removed also needed to be integer
    //MODIFIES: this
    //EFFECT: remove foodItem from customer's list of foods ordered variable FoodItemList
    //       based on the foodID
    public void removeFood(int fdID, int numb) {
        this.fdList.removeFood(fdID, numb);
    }

    //EFFECT: return total costs of the orders in the item list
    public double totalCost() {
        double sum = 0;
        for (FoodItem fdItem: this.fdList.foodItemsOrdered) {
            sum = sum + fdItem.price;
        }
        return sum;
    }


    //EFFECT: return count of individual items
    public int countIndividualItems(int type) {
        int total = 0;
        for (FoodItem it : this.fdList.foodItemsOrdered) {
            if (it.foodID == type) {
                total = total + 1;
            }
        }
        return total;
    }


    //EFFECT: return a string representation of the total cost of items ordered so far
    @Override
    public String toString() {
        String balanceStr = String.format("%.2f", this.balance);
        return "Your total items' cost is: $" + balanceStr;

    }


}

