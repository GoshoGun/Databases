package yes.idea.da.Commands;

import yes.idea.da.Interface.DatabaseManager;

public class SelectCommand implements Command {
    private final DatabaseManager dbManager;

    public SelectCommand(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public void execute(String rawArgs) {
        if (rawArgs == null || rawArgs.isBlank()) {
            System.out.println("Usage: select <colIndex> <value> <tableName> [part]");
            return;
        }
        String[] parts = rawArgs.split("\\s+");
        if (parts.length < 3) {
            System.out.println("Usage: select <colIndex> <value> <tableName> [part]");
            return;
        }
        try {
            int colIndex = Integer.parseInt(parts[0]);
            String value = parts[1];
            String tableName = parts[2];
            boolean fullMatch = parts.length < 4 || !"part".equalsIgnoreCase(parts[3]);
            dbManager.selectFromTable(tableName, colIndex, value, fullMatch);
        } catch (NumberFormatException e) {
            System.out.println("Номерът на колоната трябва да е цяло число.");
        }
    }

    @Override
    public String getDescription() {
        return "select <colIndex> <value> <tableName> [part] – exact match или contains (part)";
    }
}