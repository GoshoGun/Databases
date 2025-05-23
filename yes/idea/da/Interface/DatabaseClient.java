package yes.idea.da.Interface;

import yes.idea.da.Commands.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DatabaseClient {
    private DatabaseManager dbManager = new DatabaseManager();
    private Scanner scanner = new Scanner(System.in);
    private Map<String, Command> commands = new HashMap<>();

    public DatabaseClient() {
        initCommands();
    }

    private void initCommands() {
        commands.put("showtables", new ShowTablesCommand(dbManager));
        commands.put("import",     new ImportCommand(dbManager));
        commands.put("save",       new SaveCommand(dbManager));
        commands.put("saveas",     new SaveAsCommand(dbManager));
        commands.put("describe",   new DescribeCommand(dbManager));
        commands.put("print",      new PrintCommand(dbManager));
        commands.put("export",     new ExportCommand(dbManager));
        commands.put("select",     new SelectCommand(dbManager));
        commands.put("insert",     new InsertCommand(dbManager));
        commands.put("update",     new UpdateCommand(dbManager));
        commands.put("delete",     new DeleteCommand(dbManager));
        commands.put("addcolumn",  new AddColumnCommand(dbManager));
        commands.put("rename",     new RenameCommand(dbManager));
        commands.put("innerjoin",  new InnerJoinCommand(dbManager));
        commands.put("count",      new CountCommand(dbManager));
        commands.put("aggregate",  new AggregateCommand(dbManager));
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
        String key = parts[0].toLowerCase();
        String args = parts.length > 1 ? parts[1] : "";
        if (key.equals("help")) {
            printHelp();
        } else if (commands.containsKey(key)) {
            commands.get(key).execute(args);
        } else {
            System.out.println("Невалидна команда. Въведете 'help'.");
        }
    }

    private void printHelp() {
        System.out.println("Налични команди:");
        commands.forEach((k, cmd) ->
                System.out.printf("  %s - %s%n", k, cmd.getDescription()));
    }

}