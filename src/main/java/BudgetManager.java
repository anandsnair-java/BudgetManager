import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class BudgetManager {
    public static void main(String[] args) {
        // write your code here

        ArrayList<PurchaseEntry> listOfPurchases = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        double balance = 0;

        mainMenu:
        while (true) {
            System.out.println("");
            System.out.println("Choose your action:");
            System.out.println("1) Add income");
            System.out.println("2) Add purchase");
            System.out.println("3) Show list of purchases");
            System.out.println("4) Balance");
            System.out.println("5) Save");
            System.out.println("6) Load");
            System.out.println("0) Exit");
            String inputMenu = scanner.nextLine();
            System.out.println("");

            if (inputMenu.equals("0")) {
                System.out.println("Bye!");
                break;
            }
            if (inputMenu.equals("1")) {
                System.out.println("Enter income:");
                double incomeAdded = Double.valueOf(scanner.nextLine());
                balance += incomeAdded;
                System.out.println("Income was added!");
                continue mainMenu;
            }
            if (inputMenu.equals("4")) {
                System.out.println("Balance: $" + balance);
                continue mainMenu;
            }
            if (inputMenu.equals("3")) {
                if (listOfPurchases.isEmpty()) {
                    System.out.println("Purchase list is empty");
                    continue mainMenu;
                }
                purchaseViewMenu:
                while (true) {
                    System.out.println("");
                    System.out.println("Choose the type of purchase");
                    System.out.println("1) Food");
                    System.out.println("2) Clothes");
                    System.out.println("3) Entertainment");
                    System.out.println("4) Other");
                    System.out.println("5) All");
                    System.out.println("6) Back");
                    String purchaseCategoryForList = scanner.nextLine();
                    System.out.println("");
                    PurchaseCategory itemCategory = null;
                    switch (purchaseCategoryForList) {
                        case "1":
                            itemCategory = PurchaseCategory.FOOD;
                            break;
                        case "2":
                            itemCategory = PurchaseCategory.CLOTHES;
                            break;
                        case "3":
                            itemCategory = PurchaseCategory.ENTERTAINMENT;
                            break;
                        case "4":
                            itemCategory = PurchaseCategory.OTHER;
                            break;
                        case "5":
                            itemCategory = null;
                            break;
                        case "6":
                            continue mainMenu;
                    }

                    double totalPurchaseCost = 0;
                    if (itemCategory == null) {
                        if (listOfPurchases.isEmpty()) {
                            System.out.println("Purchase list is empty");
                            continue purchaseViewMenu;
                        }
                        System.out.println("All:");
                        for (PurchaseEntry purchase : listOfPurchases) {
                            System.out.println(purchase);
                            totalPurchaseCost += purchase.getItemCost();
                        }

                    } else {
                        if (listOfPurchases.isEmpty()) {
                            System.out.println("Purchase list is empty");
                            continue purchaseViewMenu;
                        }
                        System.out.println(itemCategory.name());
                        PurchaseCategory finalItemCategory = itemCategory;
                        if (listOfPurchases.stream().filter(purchaseEntry -> purchaseEntry.getItemCategory() == finalItemCategory).count() == 0) {
                            System.out.println("Purchase list is empty");
                            continue purchaseViewMenu;
                        }
                        for (PurchaseEntry purchase : listOfPurchases) {
                            if (purchase.getItemCategory() == itemCategory) {
                                System.out.println(purchase);
                                totalPurchaseCost += purchase.getItemCost();
                            }
                        }
                    }

                    System.out.println("Total sum: $" + totalPurchaseCost);
                }
            }
            if (inputMenu.equals("2")) {

                while (true) {
                    System.out.println("");
                    System.out.println("Choose the type of purchase");
                    System.out.println("1) Food");
                    System.out.println("2) Clothes");
                    System.out.println("3) Entertainment");
                    System.out.println("4) Other");
                    System.out.println("5) Back");

                    String categoryChoice = scanner.nextLine();
                    System.out.println("");

                    PurchaseCategory itemCategory = null;
                    switch (categoryChoice) {
                        case "1":
                            itemCategory = PurchaseCategory.FOOD;
                            break;
                        case "2":
                            itemCategory = PurchaseCategory.CLOTHES;
                            break;
                        case "3":
                            itemCategory = PurchaseCategory.ENTERTAINMENT;
                            break;
                        case "4":
                            itemCategory = PurchaseCategory.OTHER;
                            break;
                        case "5":
                            continue mainMenu;
                    }

                    System.out.println("Enter purchase name:");
                    String itemName = scanner.nextLine();
                    System.out.println("Enter its price:");
                    double itemCost = Double.valueOf(scanner.nextLine());
                    listOfPurchases.add(new PurchaseEntry(itemName, itemCost, itemCategory));
                    balance -= itemCost;
                    System.out.println("Purchase was added!");
                }

            }
            if (inputMenu.equals("5")) {
                File backupFile = new File("purchases.txt");
                try (FileWriter writer = new FileWriter(backupFile)) {
                    for (PurchaseEntry purchase : listOfPurchases) {
                        writer.write(purchase.save());
                    }
                    System.out.println("Purchases were saved!");
                } catch (Exception e) {
                    System.out.println();
                }

            }
            if (inputMenu.equals("6")) {
                listOfPurchases = new ArrayList<>();
                File backupFile = new File("purchases.txt");
                try (Scanner reader = new Scanner(backupFile)) {
                    while (reader.hasNextLine()) {
                        String line = reader.nextLine();
                        String[] parsedLine = line.split(":");
                        listOfPurchases.add(new PurchaseEntry(parsedLine[0], Double.valueOf(parsedLine[1]), PurchaseCategory.valueOf(parsedLine[2])));
                    }
                    System.out.println("Purchases were loaded!");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }

        }
    }
}

