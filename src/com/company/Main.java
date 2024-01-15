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
                        System.out.print("Enter row for edit: ");
                        int editRow = scanner.nextInt();
                        scanner.nextLine(); // Добави този ред, за да прочетеш новия ред// Проверка дали реда съществува
                        if (editRow >= 0 && editRow < table.getRowCount()) {
                            System.out.print("Enter column for edit: ");
                            int editCol = scanner.nextInt();
                            scanner.nextLine(); // Добави този ред

                            // Проверка дали колоната съществува
                            if (editCol >= 0 && editCol < table.getColCount()) {
                                System.out.print("Enter new value: ");
                                String newValue = scanner.nextLine();
                                table.editCell(editRow, editCol, newValue);
                            } else {
                                System.out.println("Invalid column.");
                            }
                        } else {
                            System.out.println("Invalid row.");
                        }
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