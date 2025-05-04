package yes.idea.da.Commands;

import yes.idea.da.Interface.DatabaseManager;

public class AggregateCommand implements Command {
    private DatabaseManager db;
    public AggregateCommand(DatabaseManager db){ this.db=db; }
    @Override public void execute(String a){
        String[] p=a.split("\\s+");
        if(p.length!=5) System.out.println("aggregate <табл> <sc> <sv> <tc> <op>");
        else {
            try{
                db.aggregate(p[0],Integer.parseInt(p[1]),p[2],
                        Integer.parseInt(p[3]),p[4]);
            } catch(NumberFormatException e){ System.out.println("col трябва да е число"); }
           }
    }

    @Override public String getDescription(){ return "Агрегатна функция"; }
}

