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
                            // Въведете новото подменю за "Save As"
                            System.out.println("\nSave options:");
                            System.out.println("------------------");
                            System.out.println("Save as a new file");
                            System.out.println("Save changes to the current file");
                            System.out.println("------------------\n");
                            System.out.print("Write save option: ");
                            String saveOption = scanner.nextLine();
                            if ("save_as".equals(saveOption)) {
                                System.out.print("Enter file path to save as: ");
                                String newFilePath = scanner.nextLine();
                                table.editCell(editRow, editCol, newValue);
                                table.saveToFile(newFilePath);
                                System.out.println("Cell edited successfully and saved as " + newFilePath);
                            } else if ("save".equals(saveOption)) {
                                table.editCell(editRow, editCol, newValue);
                                table.saveToFile(fileName);
                                System.out.println("Cell edited successfully and changes saved to the current file.");
                            } else {
                                System.out.println("Invalid save option.");
                            }
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
            } else if ("close".equals(choice)) {
                table = new Table(0, 0); // Изчистване на текущата таблица
                fileName = "";
                System.out.println("Successfully closed the file.");
            } else if ("help".equals(choice)) {
                System.out.println(
                        "\nAvailable commands:\n" +
                                "------------------------------\n" +
                                "set_path - Set file path\n" +
                                "print - Print table\n" +
                                "edit - Edit table\n" +
                                " - save_as - Save as new file\n" +
                                " - save - Save changes to current file\n" +
                                "exit - Exit program\n" +
                                "close - Close file\n" +
                                "help - Display help\n" +
                                "------------------------------\n"
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
