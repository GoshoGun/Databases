package yes.idea.da.Commands;

import yes.idea.da.Interface.DatabaseManager;

public class CountCommand implements Command {
    private DatabaseManager db;
    public CountCommand(DatabaseManager db){ this.db=db; }
    @Override public void execute(String a){
        String[] p=a.split("\\s+");
        if(p.length!=3) System.out.println("count <табл> <col> <val>");
        else {
            try{ db.countRows(p[0],Integer.parseInt(p[1]),p[2]); }
            catch(NumberFormatException e){ System.out.println("col трябва да е число"); }
        }
    }

    @Override public String getDescription(){ return "Брой по условие"; }
}
