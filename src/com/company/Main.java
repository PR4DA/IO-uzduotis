package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static List<Product> rows;
    public static Scanner SCANNER = new Scanner(System.in);
    
    public static void main(String[] args) throws IOException {
        rows = new LinkedList<>();
        File csvFile = new File("sample.csv");
        if (csvFile.isFile()) {
            read();
        } else {
            System.out.println("File doesn't exist");
        }
        if (rows.isEmpty()) {
            System.out.println("The file is empty.");
        } else {
            printList(rows);
        }

        boolean quite=false;
        while (!quite) {
            String choice =SCANNER.nextLine();
            switch (choice) {
                case "1":
                    addNewItem();
                    break;
                case "2":
                    findByExpirationDate();
                    break;
                case "3":
                    showOutOfStockItems();
                    break;
                case "0":
                    quite=true;
                    write();
                    break;
                case "4":
                    printList(rows);
                    break;
                default:
                    System.out.println("Wrong Symbol Used");
            }
        } write();
    }

    private static void printList(List<Product> list) {
        for(Product product : list) {
            System.out.println(product.toString());
        }
        printMenu();
    }

    private static void printMenu() {
        System.out.println("1-ADD NEW ITEM\n2-FIND BY EXPIRATION DATE\n3-SHOW OUT OF STOCK GOING ITEMS\n4-SHOW LIST\n0-EXIT");
    }

    private static void showOutOfStockItems() {
        System.out.println("Enter items quantity: "); Integer quantity = SCANNER.nextInt();
        List<Product> sorted = new LinkedList<>();
        for (Product product : rows) {
            if(product.itemsQuantity<=(quantity)) {
                sorted.add(product);
            }
        }
        Collections.sort(sorted, Product.COMPARE_BY_QUANTITY);
        if(sorted.isEmpty()) {
            System.out.println("No Items In Stock less than " + quantity);
        } else { printList(sorted); }
    }

    private static void findByExpirationDate() {
        System.out.println("Enter Item Date (xxxx-MM-DD):"); String date = SCANNER.nextLine();
        List<Product> sorted = new LinkedList<>();
        for (Product product : rows) {
            if(product.expirationDate.isBefore(LocalDate.parse(date))) {
                sorted.add(product);
            }
        }
        Collections.sort(sorted, Product.COMPARE_BY_EXPIRATION_DATE);
        if(sorted.isEmpty()) {
            System.out.println("No Items Going To Expire Before " + date);
        } else { printList(sorted); }
    }

    private static void addNewItem() {
        System.out.println("Enter Item Name :"); String name = SCANNER.nextLine();
        System.out.println("Enter Item Code :"); String code = SCANNER.nextLine();
        System.out.println("Enter Item Date (xxxx-MM-DD):"); String date = SCANNER.nextLine();
        System.out.println("Enter items quantity: "); Integer quantity = SCANNER.nextInt();
        SCANNER.nextLine();
        Product product = new Product(name, code, quantity, LocalDate.parse(date));
        rows.add(product);
        margeSameProducts(rows);
        printMenu();
    }

    public static void read() throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader("sample.csv"));
        csvReader.readLine();
        while (csvReader.ready()) {
            String row = csvReader.readLine();
            String[] data = row.split(",");
            Product product = new Product(data[0], data[1], Integer.parseInt(data[2]), LocalDate.parse(data[3]));
                rows.add(product);
            }
        margeSameProducts(rows);
        Collections.sort(rows, Product.COMPARE_BY_NAME);
        csvReader.close();
    }

    private static void margeSameProducts(List<Product> list) {
        List<Product> listWithoutDuplicates = new LinkedList<>();
        list.forEach(a -> {
            if (listWithoutDuplicates.stream().noneMatch(ai-> ai.getItemName().equalsIgnoreCase(a.getItemName()) && ai.getCode().equalsIgnoreCase(a.getCode()) && ai.getExpirationDate().isEqual(a.getExpirationDate()))) {
                listWithoutDuplicates.add(a);
            }
        });
        for (Product product : rows) {
            for (Product noDubProduct : listWithoutDuplicates) {
                if(!noDubProduct.equals(product) && noDubProduct.getItemName().equalsIgnoreCase(product.getItemName()) && noDubProduct.getCode().equalsIgnoreCase(product.getCode()) && noDubProduct.getExpirationDate().isEqual(product.getExpirationDate())){
                    noDubProduct.itemsQuantity+=product.getItemsQuantity();
                }
            }
        }
        rows= new LinkedList<>(listWithoutDuplicates);
    }

    public static void write() throws IOException {
        FileWriter csvWriter = new FileWriter("sample.csv");
        csvWriter.append("Item Name,Code,Quantity,Expiration Date");
        csvWriter.append("\n");
        for (Product product : rows) {
            csvWriter.append(product.itemName +","+product.code +","+product.itemsQuantity +","+ product.expirationDate);
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
    }
}
