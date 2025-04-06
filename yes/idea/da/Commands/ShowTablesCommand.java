package yes.idea.da.Commands;

import yes.idea.da.Interface.DatabaseManager;

public class ShowTablesCommand implements Command {
    private DatabaseManager dbManager;

    public ShowTablesCommand(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public void execute(String args) {
        dbManager.showTables();
    }

    @Override
    public String getDescription() {
        return "Показва всички таблици";
    }
}
