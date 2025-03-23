package yes.idea.da;

public class ImportCommand implements Command {
    private DatabaseManager dbManager;

    public ImportCommand(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public void execute(String args) {
        dbManager.importTable(args);
    }

    @Override
    public String getDescription() {
        return "Импортира таблица от файл";
    }
}
