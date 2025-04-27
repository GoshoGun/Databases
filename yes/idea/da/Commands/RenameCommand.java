package yes.idea.da.Commands;

import yes.idea.da.Interface.DatabaseManager;

public class RenameCommand implements Command {
    private DatabaseManager dbManager;
    public RenameCommand(DatabaseManager dbManager) { this.dbManager = dbManager; }
    @Override
    public void execute(String args) {
        String[] p = args.split("\\s+");
        if (p.length != 2) {
            System.out.println("Използване: rename <старо име> <ново име>"); return;
        }
        dbManager.renameTable(p[0], p[1]);
    }
    @Override public String getDescription() {
        return "Преименува таблица";
    }
}
