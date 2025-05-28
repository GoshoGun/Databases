package yes.idea.da.Interface;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private String name;
    private final List<Column> columns = new ArrayList<>();
    private final List<List<String>> rows    = new ArrayList<>();

    public Table(String sourceFile) {
        this.name = sourceFile;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public List<Column> getColumns(){
        return columns;
    }
    public List<List<String>> getRows(){
        return rows;
    }
    public void addColumn(Column c){
        columns.add(c);
    }
    public void addRow(List<String> r){
        rows.add(r);
    }
}