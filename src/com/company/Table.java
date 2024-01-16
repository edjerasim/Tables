package com.company;

import java.io.*;
import java.util.Arrays;

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
        // Проверка дали реда съществува
        if (row < 0 || row >= getRowCount()) {
            System.out.println("Invalid row.");
            return;
        }

        // Проверка дали колоната съществува
        if (col < 0 || col >= getColCount()) {
            System.out.println("Invalid column.");
            return;
        }

        Cell cell = data[row][col];
        CellType oldType = cell.getType();

        // Опитайте се да преобразувате новата стойност към типа на клетката
        CellType newType = determineCellType(newValue);

        // Проверка дали типът на клетката съвпада с новия тип
        if (newType != oldType) {
            System.out.println("Invalid data type. Expected: " + oldType + ", Got: " + newType);
            return;
        }

        // Променете стойността на текущата клетка
        if (newType == oldType) {
            cell.setValue(newValue);
        } else {
            // Ако новият тип не съвпада с предишния, създайте нова клетка с новия тип
            Cell newCell = new Cell(newValue, newType);

            // Проверка дали редът е създаден
            if (data[row] == null) {
                data[row] = new Cell[col + 1];
            } else if (col >= data[row].length) {
                // Ако редът е вече създаден, но колоната е по-голяма, уголеми ги
                int newSize = col + 1;
                data[row] = Arrays.copyOf(data[row], newSize);

                for (int i = data[row].length; i < newSize; i++) {
                    data[row][i] = new Cell("", CellType.TEXT); // По подразбиране, задайте празни стойности на новите клетки
                }
            }

            // Задайте новата клетка в матрицата
            data[row][col] = newCell;
        }

        System.out.println("Cell edited successfully.");
    }

    public void loadFromFile(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            int row = 0;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                // Ако текущият ред не съдържа данни, пропусни го
                if (values.length == 0) {
                    continue;
                }

                // Инициализация на броя на колоните, ако не е направена
                if (data.length <= row) {
                    data = Arrays.copyOf(data, row + 1);
                }

                if (data[row] == null) {
                    data[row] = new Cell[values.length];
                } else {
                    // Ако редът е вече създаден, но колоните са по-малко, уголеми ги
                    if (values.length > data[row].length) {
                        data[row] = Arrays.copyOf(data[row], values.length);
                    }
                }

                for (int col = 0; col < values.length; col++) {
                    String cellValue = values[col].trim();

                    // Премахване на кавичките, ако са присътствали
                    if (cellValue.startsWith("\"") && cellValue.endsWith("\"")) {
                        cellValue = cellValue.substring(1, cellValue.length() - 1);
                    }

                    CellType cellType = determineCellType(cellValue);

                    // Извиквай setCell само ако col е валиден индекс за текущия ред
                    if (col < data[row].length) {
                        setCell(row, col, new Cell(cellValue, cellType));
                    }
                }
                row++;
            }

            reader.close();
            System.out.println("Данните са заредени успешно от файл " + fileName);
        } catch (Exception e) {
            System.out.println("Грешка при зареждане от файл " + fileName);
            e.printStackTrace();
        }
    }


    private CellType determineCellType(String value) {
        if (value.matches("-?\\d+")) {
            return CellType.INTEGER;
        } else if (value.matches("-?\\d+(\\.\\d+)?")) {
            return CellType.DECIMAL;
        } else {
            return CellType.TEXT;
        }
    }
    public int getRowCount() {
        return data.length;
    }

    public int getColCount() {
        if (data.length == 0 || data[0] == null) {
            return 0;
        }
        return data[0].length;
    }

}

