package yes.idea.da;

public class SaveAsCommand implements Command {
    private DatabaseManager dbManager;

    public SaveAsCommand(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public void execute(String args) {
        dbManager.saveDatabaseAs(args);
    }

    @Override
    public String getDescription() {
        return "Запазва базата данни във файл";
    }
}
