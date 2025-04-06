package yes.idea.da.Commands;

import yes.idea.da.Interface.DatabaseManager;

public class ExportCommand implements Command {
    private DatabaseManager dbManager;

    public ExportCommand(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public void execute(String args) {
        if (args.isEmpty()) {
            System.out.println("Използване: export <име на таблица> <име на файл>");
            return;
        }
        String[] parts = args.split("\\s+");
        if (parts.length < 2) {
            System.out.println("Използване: export <име на таблица> <име на файл>");
            return;
        }
        String tableName = parts[0];
        String fileName = parts[1];
        dbManager.exportTable(tableName, fileName);
    }

    @Override
    public String getDescription() {
        return "Записва таблица във файл";
    }
}
