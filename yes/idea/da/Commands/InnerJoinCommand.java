package yes.idea.da.Commands;

import yes.idea.da.Interface.DatabaseManager;

public class InnerJoinCommand implements Command {
    private DatabaseManager dbManager;
    public InnerJoinCommand(DatabaseManager dbManager) { this.dbManager = dbManager; }
    @Override
    public void execute(String args) {
        String[] p = args.split("\\s+");
        if (p.length != 5) {
            System.out.println("Използване: innerjoin <таблица1> <col1> <таблица2> <col2> <резултат>"); return;
        }
        try {
            dbManager.innerJoinTables(p[0], Integer.parseInt(p[1]),
                    p[2], Integer.parseInt(p[3]), p[4]);
        } catch(NumberFormatException e) {
            System.out.println("Номерата на колоните трябва да са числа.");
        }
    }
    @Override public String getDescription() {
        return "Създава нова таблица чрез inner join";
    }
}
