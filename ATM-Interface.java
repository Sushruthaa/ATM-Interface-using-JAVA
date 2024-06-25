import java.util.ArrayList;
import java.util.Scanner;

// Transaction class to store transaction details
class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return type + ": $" + amount;
    }
}

// Account class to manage account operations
class Account {
    private double balance;
    private ArrayList<Transaction> transactionHistory;

    public Account(double balance) {
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount));
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void transfer(Account recipient, double amount) {
        if (balance >= amount) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.add(new Transaction("Transfer to " + recipient.hashCode(), amount));
        } else {
            System.out.println("Insufficient balance for transfer.");
        }
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History:");
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}

// ATM class to handle user interactions
public class ATM {
    private Account account;
    private Scanner scanner;

    public ATM(Account account) {
        this.account = account;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("Welcome to the ATM!");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Transaction History");
        System.out.println("6. Quit");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                System.out.println("Your balance is: $" + account.getBalance());
                break;
            case 2:
                System.out.print("Enter deposit amount: $");
                double depositAmount = scanner.nextDouble();
                account.deposit(depositAmount);
                System.out.println("Deposited $" + depositAmount + " successfully.");
                break;
            case 3:
                System.out.print("Enter withdrawal amount: $");
                double withdrawAmount = scanner.nextDouble();
                account.withdraw(withdrawAmount);
                break;
            case 4:
                System.out.print("Enter transfer amount: $");
                double transferAmount = scanner.nextDouble();
                System.out.print("Enter recipient's account balance: ");
                double recipientBalance = scanner.nextDouble();
                Account recipientAccount = new Account(recipientBalance);
                account.transfer(recipientAccount, transferAmount);
                break;
            case 5:
                account.printTransactionHistory();
                break;
            case 6:
                System.out.println("Thank you for using the ATM. Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }

        displayMenu(); // Recursively display menu after each operation
    }

    public static void main(String[] args) {
        // Create an account with initial balance
        Account account = new Account(1000.0);
        // Create an ATM instance with the account
        ATM atm = new ATM(account);
        // Display the ATM menu
        atm.displayMenu();
    }
}
