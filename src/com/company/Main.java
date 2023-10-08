package com.company;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    public static final int print = 1;
    public static final int edit = 2;

    public static void main(String[] args) {

            Scanner scanner = new Scanner(System.in);
        System.out.print("Enter file name: ");

            int choice;

        Table table = new Table(3, 5);
        table.setCell(0, 0, new Cell("10", CellType.INTEGER));
        table.setCell(0, 1, new Cell("\"Hello world!\"", CellType.STRING));
        table.setCell(0, 2, new Cell("123.56", CellType.DECIMAL));
        table.setCell(1, 0, new Cell("\"Quoted\"", CellType.STRING));
        table.setCell(2, 0, new Cell("1", CellType.INTEGER));
        table.setCell(2, 1, new Cell("2", CellType.INTEGER));
        table.setCell(2, 2, new Cell("3", CellType.INTEGER));
        table.setCell(2, 3, new Cell("4", CellType.INTEGER));

        do {
                System.out.println("Menu");
                System.out.println("1. Print table");
                System.out.println("2. Edit table");
                System.out.println("4. Exit");
                System.out.print("Choose: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case print:
                        table.printTable();
                        break;
                    case edit:
                        System.out.print("Select row and col for edit: ");
                        int row = scanner.nextInt();
                        int col = scanner.nextInt();
                        System.out.print("Enter new value: ");
                        String newValue = scanner.next();
                        table.editCell(row, col, newValue);
                        break;
                    case 4:
                        System.out.println("Изход от програмата.");
                        break;
                    default:
                        System.out.println("Невалиден избор.");
                }
            } while (choice != 3);

            scanner.close();
    }


}
