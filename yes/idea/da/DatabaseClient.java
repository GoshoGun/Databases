package yes.idea.da;

import java.util.Scanner;

public class DatabaseClient {
    private DatabaseManager dbManager;
    private Scanner scanner;

    public DatabaseClient() {
        dbManager = new DatabaseManager();
        scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("Добре дошли! Въведете команда или 'help' за списък с команди.");
        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine().trim();
            if (command.equalsIgnoreCase("exit")) break;
            processCommand(command);
        }
    }

    private void processCommand(String command) {
        String[] parts = command.split("\\s+", 2);
        String action = parts[0].toLowerCase();
        String argument = parts.length > 1 ? parts[1] : "";

        switch (action) {
            case "help":
                printHelp();
                break;
            case "showtables":
                dbManager.showTables();
                break;
            case "import":
                dbManager.importTable(argument);
                break;
            case "save":
                dbManager.saveDatabase();
                break;
            case "saveas":
                dbManager.saveDatabaseAs(argument);
                break;
            default:
                System.out.println("Невалидна команда! Въведете 'help' за списък с команди.");
        }
    }

    private void printHelp() {
        System.out.println("Налични команди:");
        System.out.println("  showtables          - Показва всички таблици");
        System.out.println("  import <файл>      - Импортира таблица от файл");
        System.out.println("  save               - Запазва базата данни");
        System.out.println("  saveas <файл>      - Запазва базата в конкретен файл");
        System.out.println("  exit               - Изход от програмата");
    }
}