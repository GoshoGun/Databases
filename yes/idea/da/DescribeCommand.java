package yes.idea.da;

public class DescribeCommand implements Command {
    private DatabaseManager dbManager;

    public DescribeCommand(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public void execute(String args) {
        if (args.isEmpty()) {
            System.out.println("Моля, задайте име на таблица за описване.");
            return;
        }
        dbManager.describeTable(args);
    }

    @Override
    public String getDescription() {
        return "Показва информация за структурата на таблицата (колони и типове)";
    }
}
