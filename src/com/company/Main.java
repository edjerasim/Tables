package com.company;

import com.company.Table;

import java.util.Scanner;

public class Main {
    public static final String EXIT = "exit";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Table table = new Table(0, 0);

        String choice;
        String fileName = "";

        do {
            System.out.println("\nMenu:");
            System.out.println("- Set file path");
            System.out.println("- Print table");
            System.out.println("- Edit table");
            System.out.println("- Save as new file");
            System.out.println("- Close");
            System.out.println("- Help");
            System.out.println("- Exit");

            System.out.print("Choose and write the command: ");
            choice = scanner.nextLine();

            if ("print".equals(choice)) {
                if (fileName.isEmpty()) {
                    System.out.println("Please set the file path first.");
                } else {
                    table.loadFromFile(fileName);
                    table.printTable();
                }
            } else if ("edit".equals(choice)) {
                if (fileName.isEmpty()) {
                    System.out.println("Please set the file path first.");
                } else {
                    System.out.print("Enter row for edit: ");
                    int editRow = scanner.nextInt() - 1;
                    scanner.nextLine(); // Добави този ред, за да прочетеш новия ред
                    // Проверка дали реда съществува
                    if (editRow >= 0 && editRow < table.getRowCount()) {
                        System.out.print("Enter column for edit: ");
                        int editCol = scanner.nextInt() - 1;
                        scanner.nextLine(); // Добави този ред

                        // Проверка дали колоната съществува
                        if (editCol >= 0 && editCol < table.getColCount()) {
                            System.out.print("Enter new value: ");
                            String newValue = scanner.nextLine();
                            table.editCell(editRow, editCol, newValue);
                            table.saveToFile(fileName); // Запазване след успешна редакция
                            System.out.println("Cell edited successfully.");
                        } else {
                            System.out.println("Invalid column.");
                        }
                    } else {
                        System.out.println("Invalid row.");
                    }
                }
            } else if ("set_path".equals(choice)) {
                System.out.print("Enter file path: ");
                fileName = scanner.nextLine();
            } else if ("save_as".equals(choice)) {
                if (!fileName.isEmpty()) {
                    System.out.print("Enter file path to save as: ");
                    String newFilePath = scanner.nextLine();
                    table.saveToFile(newFilePath);
                    System.out.println("Successfully saved as " + newFilePath);
                } else {
                    System.out.println("Please set the file path first.");
                }
            } else if ("close".equals(choice)) {
                table = new Table(0, 0); // Изчистване на текущата таблица
                fileName = "";
                System.out.println("Successfully closed the file.");
            } else if ("help".equals(choice)) {
                System.out.println(
                        "\nAvailable commands:\n" +
                                "set_path - Set file path\n" +
                                "print - Print table\n" +
                                "edit - Edit table\n" +
                                "exit - Exit program\n" +
                                "save_as - Save as\n" +
                                "close - Close file\n" +
                                "help - Display help\n"
                );
            } else if (EXIT.equals(choice)) {
                System.out.println("Exiting the program.");
            } else {
                System.out.println("Invalid choice.");
            }
        } while (!choice.equals(EXIT));

        scanner.close();
    }
}
