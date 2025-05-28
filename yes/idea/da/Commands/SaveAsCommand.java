package yes.idea.da.Commands;

import yes.idea.da.Interface.DatabaseManager;

public class SaveAsCommand implements Command {
    private final DatabaseManager dbManager;

    public SaveAsCommand(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public void execute(String args) {
        if (args == null || args.isBlank()) {
            System.out.println("Usage: saveas <tableName> <fileName>");
            return;
        }

        String[] parts = args.trim().split("\\s+", 2);
        if (parts.length < 2) {
            System.out.println("Usage: saveas <tableName> <fileName>");
            return;
        }

        String tableName = parts[0];
        String fileName = parts[1];

        dbManager.saveDatabaseAs(tableName, fileName);
    }

    @Override
    public String getDescription() {
        return "saveas <table> <file> – експортира таблица в .csv файл";
    }
}
