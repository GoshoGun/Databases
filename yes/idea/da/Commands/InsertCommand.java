package yes.idea.da.Commands;

import yes.idea.da.Interface.DatabaseManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsertCommand implements Command {
    private DatabaseManager dbManager;

    public InsertCommand(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public void execute(String args) {
        if (args.isEmpty()) {
            System.out.println("Използване: insert <име на таблица> <стойност1> <стойност2> ... <стойностN>");
            return;
        }
        String[] parts = args.split("\\s+");
        if (parts.length < 2) {
            System.out.println("Използване: insert <име на таблица> <стойност1> <стойност2> ... <стойностN>");
            return;
        }
        String tableName = parts[0];
        List<String> row = new ArrayList<>(Arrays.asList(parts).subList(1, parts.length));
        dbManager.insertRow(tableName, row);
    }

    @Override
    public String getDescription() {
        return "Добавя нов ред в таблицата";
    }
}
