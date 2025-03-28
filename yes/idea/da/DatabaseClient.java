package yes.idea.da;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DatabaseClient {
    private DatabaseManager dbManager;
    private Scanner scanner;
    private Map<String, Command> commands;

    public DatabaseClient() {
        dbManager = new DatabaseManager();
        scanner = new Scanner(System.in);
        commands = new HashMap<>();
        initCommands();
    }

    private void initCommands() {
        commands.put("showtables", new ShowTablesCommand(dbManager));
        commands.put("import", new ImportCommand(dbManager));
        commands.put("save", new SaveCommand(dbManager));
        commands.put("saveas", new SaveAsCommand(dbManager));
    }

    public void run() {
        System.out.println("Добре дошли! Въведете 'help' за списък с команди.");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) break;
            processInput(input);
        }
    }

    private void processInput(String input) {
        String[] parts = input.split("\\s+", 2);
        String commandKey = parts[0].toLowerCase();
        String args = parts.length > 1 ? parts[1] : "";
        if (commandKey.equals("help")) {
            printHelp();
        } else if (commands.containsKey(commandKey)) {
            commands.get(commandKey).execute(args);
        } else {
            System.out.println("Невалидна команда. Въведете 'help' за списък с команди.");
        }
    }

    private void printHelp() {
        System.out.println("Налични команди:");
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            System.out.printf("  %s - %s%n", entry.getKey(), entry.getValue().getDescription());
        }
    }
}