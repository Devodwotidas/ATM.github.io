import java.util.*;
import java.util.ArrayList;

class User {
    private String userID;
    private String pin;
    private double accountBalance; // Added account balance attribute

    public User(String userID, String pin, double accountBalance) {
        this.userID = userID;
        this.pin = pin;
        this.accountBalance = accountBalance;
    }

    public boolean authenticate(String enteredPin) {
        return pin.equals(enteredPin);
    }

    public String getUserID() {
        return userID;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double newBalance) {
        accountBalance = newBalance;
    }
}

class ATMInterface {
    private Scanner sc = new Scanner(System.in);

    public void displayOptions() {
        System.out.println("\nSelect an option:");
        System.out.println("1. Amount to Withdraw");
        System.out.println("2. Amount to Deposit");
        System.out.println("3. Transfer Amount");
        System.out.println("4. Account History");
        System.out.println("5. Quit");
        System.out.print("Enter your choice: ");
    }

    public String getUserInput() {
        return sc.nextLine();
    }

    public String getUserIDInput() {
        String userID;
        while (true) {
            System.out.print("Please enter your User ID (6-Digit): ");
            userID = sc.nextLine();
            if (userID.length() == 6 && userID.matches("\\d+")) {
                break;
            } else {
                System.out.println("Invalid User ID. Please enter a 6-digit number.");
            }
        }
        return userID;
    }

    public String getPINInput() {
        String pin;
        while (true) {
            System.out.print("Please enter your PIN (4-Digit): ");
            pin = sc.nextLine();
            if (pin.length() == 4 && pin.matches("\\d+")) {
                break;
            } else {
                System.out.println("Invalid PIN. Please enter a 4-digit number.");
            }
        }
        return pin;
    }

    public void withdraw(User user) {
        System.out.print("Enter the amount to withdraw: ");
        double amountToWithdraw = Double.parseDouble(sc.nextLine());

        if (amountToWithdraw <= 0) {
            System.out.println("Invalid amount. Please enter a positive amount.");
        } else if (amountToWithdraw > user.getAccountBalance()) {
            System.out.println("Insufficient funds. Your current balance is $" + user.getAccountBalance());
        } else {
            double newBalance = user.getAccountBalance() - amountToWithdraw;
            user.setAccountBalance(newBalance);
            System.out.println("Withdrawal successful. Your new balance is $" + newBalance);
        }
    }

    public void deposit(User user) {
        System.out.print("Enter the amount to deposit: ");
        double amountToDeposit = Double.parseDouble(sc.nextLine());

        if (amountToDeposit <= 0) {
            System.out.println("Invalid amount. Please enter a positive amount.");
        } else {
            double newBalance = user.getAccountBalance() + amountToDeposit;
            user.setAccountBalance(newBalance);
            System.out.println("Deposit successful. Your new balance is $" + newBalance);
        }
    }

    public String getRecipientAccountNumber() {
        String recipientAccountNumber;
        while (true) {
            System.out.print("Enter the recipient's account number (6-Digit): ");
            recipientAccountNumber = sc.nextLine();
            if (recipientAccountNumber.length() == 6 && recipientAccountNumber.matches("\\d+")) {
                return recipientAccountNumber;
            } else {
                System.out.println("Invalid recipient account number. Please enter a 6-digit number.");
            }
        }
    }

    public void transfer(User user) {
        String recipientAccountNumber = getRecipientAccountNumber();
        System.out.print("Enter the amount to transfer: ");
        double amountToTransfer = Double.parseDouble(sc.nextLine());

        if (amountToTransfer <= 0) {
                System.out.println("Invalid amount. Please enter a positive amount.");
            } 
        else if (amountToTransfer > user.getAccountBalance()) {
                System.out.println("Insufficient funds. Your current balance is $" + user.getAccountBalance());
            } 
        else {
            // Implement transfer logic here, including updating recipient's balance
            double newBalance = user.getAccountBalance() - amountToTransfer;
            user.setAccountBalance(newBalance);
            System.out.println("Transfer successful. Your new balance is $" + newBalance);
        }
    }

    public void displayTransactionHistory(ArrayList<String> history) {
        System.out.println("Transaction History:");
        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + ". " + history.get(i));
        }
    }
}

public class ATM {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> transactionHistory = new ArrayList<>();

        ATMInterface atmInterface = new ATMInterface();
        System.out.println("WELCOME!");
        String userID = atmInterface.getUserIDInput();
        String enteredPin = atmInterface.getPINInput();

        // Create a User instance with an initial balance (replace with actual initial balance)
        User user = new User(userID, enteredPin, 500.0);

        if (user.authenticate(enteredPin)) {
            boolean quit = false;

            while (!quit) {
                atmInterface.displayOptions();
                String userChoice = atmInterface.getUserInput();

                switch (userChoice) {
                    case "1":
                        atmInterface.withdraw(user);
                        transactionHistory.add("Withdrawal: $" + user.getAccountBalance());
                        break;
                    case "2":
                        atmInterface.deposit(user);
                        transactionHistory.add("Deposit: $" + user.getAccountBalance());
                        break;
                    case "3":
                        atmInterface.transfer(user);
                        transactionHistory.add("Transfer: $" + user.getAccountBalance());
                        break;
                    case "4":
                        atmInterface.displayTransactionHistory(transactionHistory);
                        break;
                    case "5":
                        quit = true;
                        System.out.println("THANK YOU!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                        break;
                }
            }
        } else {
            System.out.println("Authentication failed. Please try again.");
        }
    }
}
