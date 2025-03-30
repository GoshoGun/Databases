package yes.idea.da;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DatabaseManager {
    private Map<String, Table> tables;

    public DatabaseManager() {
        tables = new HashMap<>();
    }

    public void showTables() {
        if (tables.isEmpty()) {
            System.out.println("Няма заредени таблици.");
        } else {
            System.out.println("Налични таблици:");
            for (String tableName : tables.keySet()) {
                System.out.println("  " + tableName);
            }
        }
    }

    public void importTable(String fileName) {
        if (fileName.isEmpty()) {
            System.out.println("Трябва да въведете име на файл.");
            return;
        }
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            String tableName = fileName;
            Table table = new Table(tableName);

            if (!lines.isEmpty()) {
                String headerLine = lines.get(0);
                String[] headers = headerLine.split(",");
                for (String header : headers) {
                    table.addColumn(new Column(header.trim(), "string"));
                }
                for (int i = 1; i < lines.size(); i++) {
                    String[] cells = lines.get(i).split(",");
                    List<String> row = new ArrayList<>();
                    for (String cell : cells) {
                        row.add(cell.trim());
                    }
                    table.addRow(row);
                }
            }
            tables.put(tableName, table);
            System.out.println("Таблицата " + tableName + " е успешно импортирана. (Брой редове: " + (lines.size()-1) + ")");
        } catch (IOException e) {
            System.out.println("Грешка при импортирането на таблицата: " + e.getMessage());
        }
    }

    public void saveDatabase() {
        saveDatabaseAs("database.txt");
    }

    public void saveDatabaseAs(String fileName) {
        if (fileName.isEmpty()) {
            System.out.println("Трябва да зададете име на файл.");
            return;
        }
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
            for (String tableName : tables.keySet()) {
                writer.write(tableName);
                writer.newLine();
            }
            System.out.println("Базата данни е успешно запазена във файл: " + fileName);
        } catch (IOException e) {
            System.out.println("Грешка при запазването на базата данни: " + e.getMessage());
        }
    }

    public void describeTable(String tableName) {
        Table table = tables.get(tableName);
        if (table == null) {
            System.out.println("Таблицата " + tableName + " не е намерена.");
            return;
        }
        System.out.println("Описание на таблицата " + tableName + ":");
        if (table.getColumns().isEmpty()) {
            System.out.println("Няма дефинирани колони.");
        } else {
            for (Column column : table.getColumns()) {
                System.out.println("  " + column.getName() + " - " + column.getType());
            }
        }
    }

    public void printTable(String tableName) {
        Table table = tables.get(tableName);
        if (table == null) {
            System.out.println("Таблицата " + tableName + " не е намерена.");
            return;
        }
        System.out.println("Принтиране на таблицата " + tableName + ":");

        if (!table.getColumns().isEmpty()) {
            for (Column column : table.getColumns()) {
                System.out.print(column.getName() + "\t");
            }
            System.out.println();
        }

        if (table.getRows().isEmpty()) {
            System.out.println("Няма данни в таблицата.");
            return;
        }

        int pageSize = 5;
        int totalRows = table.getRows().size();
        int totalPages = (int) Math.ceil(totalRows / (double) pageSize);
        int currentPage = 1;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            int start = (currentPage - 1) * pageSize;
            int end = Math.min(start + pageSize, totalRows);
            System.out.println("Страница " + currentPage + " от " + totalPages + ":");

            for (int i = start; i < end; i++) {
                List<String> row = table.getRows().get(i);
                for (String cell : row) {
                    System.out.print(cell + "\t");
                }
                System.out.println();
            }

            if (totalPages <= 1) break;

            System.out.println("Команди: [n] следваща, [p] предишна, [e] изход");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("n") && currentPage < totalPages) {
                currentPage++;
            } else if (input.equals("p") && currentPage > 1) {
                currentPage--;
            } else if (input.equals("e")) {
                break;
            } else {
                System.out.println("Невалидна команда.");
            }
        }
    }
}