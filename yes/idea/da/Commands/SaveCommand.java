package yes.idea.da.Commands;

import yes.idea.da.Interface.DatabaseManager;

public class SaveCommand implements Command {
    private DatabaseManager dbManager;

    public SaveCommand(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public void execute(String args) {
        dbManager.saveDatabase();
    }

    @Override
    public String getDescription() {
        return "Запазва базата данни";
    }
}
