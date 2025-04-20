package yes.idea.da.Commands;

import yes.idea.da.Interface.DatabaseManager;

public class DeleteCommand implements Command {
    private DatabaseManager dbManager;
    public DeleteCommand(DatabaseManager dbManager) { this.dbManager = dbManager; }
    @Override
    public void execute(String args) {
        String[] p = args.split("\\s+");
        if (p.length != 3) {
            System.out.println("Използване: delete <таблица> <номер на колона> <стойност>"); return;
        }
        try {
            String t = p[0]; int col = Integer.parseInt(p[1]); String val = p[2];
            dbManager.deleteRows(t, col, val);
        } catch(NumberFormatException e) {
            System.out.println("Номерът на колоната трябва да е число.");
        }
    }
    @Override public String getDescription() {
        return "Изтрива редове по условие";
    }
}
