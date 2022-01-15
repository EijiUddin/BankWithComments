package bankstuff2;

import java.io.*;
import java.util.Scanner;
/*Checks if there is any existant file with getAccount(), then moves to bank balance management in main() method
  Bank class stores name and balance, 
  Deposit adds amomunt, Withdraw subtracts amount
  updateFile updates any deposit/withdraw transaction
  getAccount() Takes Int input(1 to 4) to create/search/delete accounts
  main() method takes balance in account and takes input to add/subtract/check balance
  */

//Stores name and balance
class Bank {

    private String name;
    private double balance;

    public Bank() {
    }

    public Bank(String name, double balance) throws FileNotFoundException {
        this.name = name;
        this.balance = balance;
        File file = new File(name + ".txt");
        try {
            if (file.createNewFile()) {
                System.out.println("New account created");
            } else {
                System.out.println("Account found");
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        try (java.io.PrintWriter out = new java.io.PrintWriter(file)) {
            out.println(this.name);
            out.println(this.balance);
        }
    }
    //Getter Methods
    public String getName() {
        return this.name;
    }

    public double getBalance() {
        return this.balance;
    }
    //Setter Methods
    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    //Helper Method, Updates file after deposit and withdraw methods
    public void updateFile() throws FileNotFoundException {
        File file = new File(name + ".txt");
        try (java.io.PrintWriter out = new java.io.PrintWriter(file)) {
            out.println(this.name);
            out.println(this.balance);
        }
    }

    @Override
    public String toString() {
        return "\nName: " + this.name
                + "\nBalance: " + this.balance;
    }
    //Adds amount to account balance
    public void deposit(double amount) throws FileNotFoundException {
        if(amount >= 0){
            this.balance += amount;
            System.out.println(amount + " deposited to account");
            this.updateFile();
        }
        else{
            System.out.println("ERROR: Deposit amount cannot be below 0");
        }
    }
    //Subtracts amount from account balance
    public void withdraw(double amount) throws FileNotFoundException {
        if(amount < 0){
            System.out.println("ERROR: Withdraw amount cannot be negative");
            return;
        }
        if (this.balance - amount < 0) {
            System.out.println("ERROR: Withdraw amount larger than balance");
            return;
        }
        this.balance -= amount;
        System.out.println(amount + " withdrawn from account");
        this.updateFile();
    }
}

public class BankStuff2 {
    //Account set-up. 
    public static Bank getAccount() throws FileNotFoundException{
        Scanner in = new Scanner(System.in);
        boolean accNotFound = true;
        do {
            System.out.print("\nPick an action number\n1.Add new account\n2.Login\n3.Delete account\n4.Exit\nInput: ");
            int pick = in.nextInt();
            switch (pick) {
                case 1: {
                    System.out.print("Input first name: ");
                    String name = in.next();
                    System.out.print("Input balance: ");
                    double balance = in.nextDouble();
                    Bank acc = new Bank(name, balance);
                    return acc;
                }
                case 2: {
                    System.out.print("Input first name: ");
                    String name = in.next();                    
                    File file = new File(name + ".txt");
                    if (file.exists()) {
                        System.out.println("\nAccount found");
                        Scanner fin = new Scanner(file);
                        Bank acc = new Bank();
                        while (fin.hasNext()) {
                            acc.setName(fin.next());
                            acc.setBalance(fin.nextDouble());
                        }
                        return acc;
                    } else {
                        System.out.println("\nAccount not found");
                    }
                    break;
                }
                case 3:{
                    System.out.print("Input first name: ");
                    String name = in.next();
                    
                    File file = new File(name + ".txt");
                    if (file.exists()) {
                        System.out.println("\nAccount found.\nDeleting account.");
                        file.delete();
                    }
                    else{
                        System.out.println("\nAccount not found.");
                    }
                    break;
                }
                case 4:{
                    System.exit(0);
                }
                default:{
                    System.out.println("Invalid input.");
                }
            }
        } while (accNotFound);
        return null;
    }

    public static void main(String[] args) throws FileNotFoundException{
        Scanner in = new Scanner(System.in);
        //Account Balance work. Continues until 4 is input
        Bank acc = getAccount();
        int input = 0;
        do {
            System.out.print("\nPick an action number\n1.View Account\n2.Deposit\n3.Withdraw\n4.Exit\nInput: ");
            input = in.nextInt();
            switch (input) {
                case 1: {
                    System.out.println(acc.toString());
                    break;
                }
                case 2: {
                    System.out.print("\nInput amount to deposit: ");
                    acc.deposit(in.nextDouble());
                    break;
                }
                case 3: {
                    System.out.print("\nInput amount to withdraw: ");
                    acc.withdraw(in.nextDouble());
                    break;
                }
                case 4:{
                    break;
                }
                default: {
                    System.out.println("\nInvalid input.");
                }
            }
        } while (input != 4);
    }
}
