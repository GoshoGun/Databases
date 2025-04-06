package yes.idea.da.Commands;

import yes.idea.da.Interface.DatabaseManager;

public class PrintCommand implements Command {
    private DatabaseManager dbManager;

    public PrintCommand(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public void execute(String args) {
        if (args.isEmpty()) {
            System.out.println("Моля, задайте име на таблица за принтиране.");
            return;
        }
        dbManager.printTable(args);
    }

    @Override
    public String getDescription() {
        return "Принтира съдържанието на таблицата с пейджинг";
    }
}
