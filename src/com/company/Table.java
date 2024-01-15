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

