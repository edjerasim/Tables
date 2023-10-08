package com.company;

import java.io.*;

public class Table {
    private Cell[][] data; // Матрица от клетки

    public Table(int rows, int cols) {
        data = new Cell[rows][cols];
        // Инициализирайте таблицата с клетки, например с празни стойности
    }

    // Методи за работа с клетките
    public void setCell(int row, int col, Cell cell) {
        data[row][col] = cell;
    }

    public Cell getCell(int row, int col) {
        return data[row][col];
    }

    // ...

    public void saveToFile(String fileName) {
        try {
            File file = new File(fileName);

            // Проверка и създаване на файла, ако не съществува
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter writer = new FileWriter(file);

            // Записване на данните във файл
            for (int i = 0; i < data.length; i++) {
                boolean isEmptyRow = true; // Флаг, указващ дали редът е празен
                for (int j = 0; j < data[i].length; j++) {
                    if (data[i][j] != null) {
                        String cellValue = data[i][j].getValue().toString();
                        writer.write(cellValue);
                        isEmptyRow = false; // Ако има стойност в клетката, редът не е празен
                    }
                    if (j < data[i].length - 1) {
                        writer.write(","); // Разделител между клетките (запетая)
                    }
                }
                if (!isEmptyRow) {
                    writer.write("\n"); // Нов ред след всеки ред от таблицата (ако редът не е празен)
                }
            }

            writer.close();
            System.out.println("Данните са записани успешно във файл " + fileName);
        } catch (IOException e) {
            System.out.println("Грешка при запис във файл " + fileName);
            e.printStackTrace();
        }
    }


    public void printTable() {
        // Прочетете броя на редовете и колоните в таблицата
        int numRows = data.length;
        int numCols = 0;
        for (int i = 0; i < numRows; i++) {
            if (data[i] != null && data[i].length > numCols) {
                numCols = data[i].length;
            }
        }

        // Изведете таблицата с разделители и равен размер на клетките
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (data[i] != null && j < data[i].length) {
                    Cell cell = data[i][j];
                    if (cell != null) {
                        String cellValue = cell.getValue().toString();
                        System.out.print("| " + cellValue);
                        // Изчислете броя на празните места за да постигнете равен размер
                        int numSpaces = cellValue.length() < 20 ? 20 - cellValue.length() : 0;
                        for (int k = 0; k < numSpaces; k++) {
                            System.out.print(" ");
                        }
                    } else {
                        // Ако клетката липсва, изведете празна клетка
                        System.out.print("| ");
                        for (int k = 0; k < 20; k++) {
                            System.out.print(" ");
                        }
                    }
                } else {
                    // Ако клетката липсва, изведете празна клетка
                    System.out.print("| ");
                    for (int k = 0; k < 20; k++) {
                        System.out.print(" ");
                    }
                }
            }
            System.out.println("|"); // Завършете реда
        }
    }




    public void editCell(int row, int col, String newValue) {
        // По-напред може да добавите валидация и обработка на грешки, ако е необходимо
        CellType newType = CellType.TEXT; // Тип по подразбиране - текст

        try {
            // Опитайте се да преобразувате новата стойност към друг тип според определени правила
            if (newValue.matches("-?\\d+")) {
                newType = CellType.INTEGER;
            } else if (newValue.matches("-?\\d+(\\.\\d+)?")) {
                newType = CellType.DECIMAL;
            }
        } catch (NumberFormatException e) {
            // Ако не успеете да преобразувате, запазвайте типа като текст
            newType = CellType.TEXT;
        }

        // Задаване на новия тип на клетката
        data[row][col] = new Cell(newValue, newType);
    }

}

