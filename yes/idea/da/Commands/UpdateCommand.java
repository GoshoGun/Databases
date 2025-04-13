package yes.idea.da.Commands;

import yes.idea.da.Interface.DatabaseManager;

public class UpdateCommand implements Command {
    private DatabaseManager dbManager;

    public UpdateCommand(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public void execute(String args) {
        if (args.isEmpty()) {
            System.out.println("Използване: update <име на таблица> <търсеща колона> <търсена стойност> <целева колона> <нова стойност>");
            return;
        }
        String[] parts = args.split("\\s+");
        if (parts.length < 5) {
            System.out.println("Използване: update <име на таблица> <търсеща колона> <търсена стойност> <целева колона> <нова стойност>");
            return;
        }
        String tableName = parts[0];
        try {
            int searchColumn = Integer.parseInt(parts[1]);
            String searchValue = parts[2];
            int targetColumn = Integer.parseInt(parts[3]);
            StringBuilder sb = new StringBuilder();
            for (int i = 4; i < parts.length; i++) {
                sb.append(parts[i]);
                if (i < parts.length - 1) sb.append(" ");
            }
            String newValue = sb.toString();
            dbManager.updateRows(tableName, searchColumn, searchValue, targetColumn, newValue);
        } catch (NumberFormatException e) {
            System.out.println("Номерът на колоната трябва да бъде цяло число.");
        }
    }

    @Override
    public String getDescription() {
        return "Актуализира стойността на редовете, отговарящи на условието";
    }
}