package yes.idea.da.Commands;

import yes.idea.da.Interface.DatabaseManager;

public class AddColumnCommand implements Command {
    private DatabaseManager dbManager;
    public AddColumnCommand(DatabaseManager dbManager) { this.dbManager = dbManager; }
    @Override
    public void execute(String args) {
        String[] p = args.split("\\s+");
        if (p.length != 3) {
            System.out.println("Използване: addcolumn <таблица> <име на колона> <тип>"); return;
        }
        dbManager.addColumn(p[0], p[1], p[2]);
    }
    @Override public String getDescription() {
        return "Добавя нова колона към таблица";
    }
}
