package com.company;

import com.company.Table;

import java.util.Scanner;

public class Main {
    public static final int PRINT = 1;
    public static final int EDIT = 2;
    public static final int EXIT = 4;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Table table = new Table(0, 0);

        int choice;
        String fileName = "";

        do {
            System.out.println("Menu");
            System.out.println("1. Print table");
            System.out.println("2. Edit table");
            System.out.println("3. Set file path");
            System.out.println("4. Exit");

            System.out.print("Choose: ");
            choice = scanner.nextInt();

            switch (choice) {
                case PRINT:
                    if (fileName.isEmpty()) {
                        System.out.println("Please set the file path first.");
                    } else {
                        table.loadFromFile(fileName);
                        table.printTable();
                    }
                    break;
                case EDIT:
                    if (fileName.isEmpty()) {
                        System.out.println("Please set the file path first.");
                    } else {
                        System.out.print("Select row and col for edit: ");
                        int row = scanner.nextInt();
                        int col = scanner.nextInt();
                        System.out.print("Enter new value: ");
                        String newValue = scanner.next();
                        table.editCell(row, col, newValue);
                    }
                    break;
                case 3:
                    System.out.print("Enter file path: ");
                    fileName = scanner.next();
                    break;
                case EXIT:
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != EXIT);

        scanner.close();
    }
}
