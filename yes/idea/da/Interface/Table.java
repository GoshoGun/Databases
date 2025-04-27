package yes.idea.da.Interface;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private String name;
    private List<Column> columns;
    private List<List<String>> rows;

    public Table(String name) {
        this.name = name;
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public List<List<String>> getRows() {
        return rows;
    }

    public void addColumn(Column column) {
        columns.add(column);
    }

    public void addRow(List<String> row) {
        rows.add(row);
    }

    public void setName(String name) {
        this.name = name;
    }
}