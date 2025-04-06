package yes.idea.da.Commands;

import yes.idea.da.Interface.DatabaseManager;

public class SelectCommand implements Command {
    private DatabaseManager dbManager;

    public SelectCommand(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public void execute(String args) {
        if (args.isEmpty()) {
            System.out.println("Използване: select <номер на колона> <стойност> <име на таблица>");
            return;
        }
        String[] parts = args.split("\\s+");
        if (parts.length < 3) {
            System.out.println("Използване: select <номер на колона> <стойност> <име на таблица>");
            return;
        }
        try {
            int columnIndex = Integer.parseInt(parts[0]);
            String value = parts[1];
            StringBuilder sb = new StringBuilder();
            for (int i = 2; i < parts.length; i++) {
                sb.append(parts[i]);
                if (i < parts.length - 1) {
                    sb.append(" ");
                }
            }
            String tableName = sb.toString();
            dbManager.selectFromTable(tableName, columnIndex, value);
        } catch (NumberFormatException e) {
            System.out.println("Номерът на колоната трябва да бъде цяло число.");
        }
    }

    @Override
    public String getDescription() {
        return "Извежда редове от таблицата, където в указаната колона има зададената стойност";
    }
}