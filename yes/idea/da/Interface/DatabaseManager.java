package yes.idea.da.Interface;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Map;

/**
 * Основна логика за опериране върху таблици и файлове.
 */
public class DatabaseManager {
    private static final int DEFAULT_PAGE_SIZE = 5;
    private final Map<String, Table> tables = new HashMap<>();

    public void showTables() {
        if (tables.isEmpty()) {
            System.out.println("Няма таблици.");
        } else {
            System.out.println("Таблици:");
            tables.keySet().forEach(n -> System.out.println("  " + n));
        }
    }

    public void importTable(String fileName) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            if (lines.isEmpty()) {
                System.out.println("Файлът е празен.");
                return;
            }

            Table table = new Table(fileName);

            String headerLine = lines.get(0).replace("\uFEFF", "");
            String[] headers = headerLine.split(";");
            for (String h : headers) {
                table.addColumn(new Column(h.trim(), "string"));
            }

            for (int i = 1; i < lines.size(); i++) {
                String rowLine = lines.get(i).replace("\uFEFF", "");
                String[] cells = rowLine.split(";");
                List<String> row = new ArrayList<>();
                for (String cell : cells) {
                    row.add(cell.trim());
                }
                table.addRow(row);
            }

            tables.put(fileName, table);
            System.out.println("Таблицата " + fileName + " е успешно импортирана. (Колони: "
                    + table.getColumns().size() + ", Редове: " + table.getRows().size() + ")");
        } catch (IOException e) {
            System.out.println("Грешка при импортирането: " + e.getMessage());
        }
    }


    public void saveDatabaseAs(String tableName, String fileName) {
        Table table = tables.get(tableName);
        if (table == null) {
            System.out.printf("Таблицата \"%s\" не е намерена.%n", tableName);
            return;
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
            String header = table.getColumns().stream()
                    .map(Column::getName)
                    .collect(Collectors.joining(";"));
            writer.write(header);
            writer.newLine();

            for (List<String> row : table.getRows()) {
                writer.write(String.join(";", row));
                writer.newLine();
            }

            System.out.printf("Таблицата \"%s\" е записана в \"%s\".%n", tableName, fileName);
        } catch (IOException e) {
            System.out.println("Грешка при запис: " + e.getMessage());
        }
    }

    public void describeTable(String name) {
        Table t = tables.get(name);
        if (t == null) {
            System.out.println("Не е намерена: " + name);
            return;
        }
        System.out.println("Описание на " + name + ":");
        t.getColumns().forEach(c ->
                System.out.printf("  %s – %s%n", c.getName(), c.getType()));
    }

    public void printTable(String name) {
        Table t = tables.get(name);
        if (t == null) {
            System.out.println("Не е намерена: " + name);
            return;
        }
        List<String> headers = t.getColumns().stream()
                .map(Column::getName)
                .collect(Collectors.toList());
        ConsolePager.page(headers, t.getRows(), DEFAULT_PAGE_SIZE);
    }

    public void exportTable(String t, String f) {
        Table table = tables.get(t);
        if (table == null) { System.out.println("Не е намерена."); return; }
        try (BufferedWriter w = Files.newBufferedWriter(Paths.get(f))) {
            List<Column> cols = table.getColumns();
            for (int i = 0; i < cols.size(); i++) {
                w.write(cols.get(i).getName());
                if (i < cols.size() - 1) w.write(",");
            }
            w.newLine();
            for (List<String> row : table.getRows()) {
                for (int i = 0; i < row.size(); i++) {
                    w.write(row.get(i));
                    if (i < row.size() - 1) w.write(",");
                }
                w.newLine();
            }
            System.out.println("Експортирано: " + f);
        } catch (IOException e) {
            System.out.println("Грешка export: " + e.getMessage());
        }
    }

    public void selectFromTable(String tableName,
                                int columnIndex,
                                String value,
                                boolean fullMatch) {
        Table table = tables.get(tableName);
        if (table == null) {
            System.out.println("Таблицата \"" + tableName + "\" не е намерена.");
            return;
        }

        int col0 = columnIndex - 1;
        if (col0 < 0 || col0 >= table.getColumns().size()) {
            System.out.println("Невалиден номер на колона: " + columnIndex);
            return;
        }

        List<List<String>> matched = table.getRows().stream()
                .filter(row -> {
                    if (row.size() <= col0) return false;
                    String cell = row.get(col0);
                    return fullMatch
                            ? cell.equals(value)
                            : cell.contains(value);
                })
                .collect(Collectors.toList());

        if (matched.isEmpty()) {
            System.out.println("Няма редове, отговарящи на условието.");
            return;
        }

        List<String> headers = table.getColumns().stream()
                .map(Column::getName)
                .collect(Collectors.toList());
        ConsolePager.page(headers, matched, DEFAULT_PAGE_SIZE);
    }


    public void insertRow(String t, List<String> row) {
        Table table = tables.get(t);
        if (table == null) { System.out.println("Не е намерена."); return; }
        if (row.size() != table.getColumns().size()) {
            System.out.println("Очакват се "+table.getColumns().size()+" стойности."); return;
        }
        table.addRow(row);
        System.out.println("Ред добавен.");
    }

    public void updateRows(String t, int sc, String sv, int tc, String tv) {
        Table table = tables.get(t);
        if (table == null) { System.out.println("Не е намерена."); return; }
        if (sc<1||sc>table.getColumns().size()||tc<1||tc>table.getColumns().size()) {
            System.out.println("Невалиден номер на колона."); return;
        }
        int cnt=0;
        for (List<String> row : table.getRows()) {
            if (row.size()>=sc && row.get(sc-1).equals(sv)) {
                while (row.size()<tc) row.add("");
                row.set(tc-1, tv);
                cnt++;
            }
        }
        System.out.println("Актуализирани: "+cnt);
    }

    public void deleteRows(String t, int col, String val) {
        Table table = tables.get(t);
        if (table == null) { System.out.println("Не е намерена."); return; }
        table.getRows().removeIf(r -> r.size()>=col && r.get(col-1).equals(val));
        System.out.println("Редове изтрити.");
    }

    public void addColumn(String t, String name, String type) {
        Table table = tables.get(t);
        if (table == null) { System.out.println("Не е намерена."); return; }
        table.addColumn(new Column(name,type));
        table.getRows().forEach(r -> r.add("NULL"));
        System.out.println("Колона добавена.");
    }

    public void renameTable(String oldN, String newN) {
        if (!tables.containsKey(oldN)) { System.out.println("Не е намерена."); return; }
        if (tables.containsKey(newN)) { System.out.println("Вече съществува."); return; }
        Table tbl = tables.remove(oldN);
        tbl.setName(newN);
        tables.put(newN, tbl);
        System.out.println("Преименувана.");
    }

    public void innerJoinTables(String t1,int c1,String t2,int c2,String res) {
        Table A = tables.get(t1), B = tables.get(t2);
        if (A==null||B==null) { System.out.println("Липсва."); return; }
        Table R = new Table(res);
        R.getColumns().addAll(A.getColumns());
        R.getColumns().addAll(B.getColumns());
        for (List<String> rA: A.getRows())
            for (List<String> rB: B.getRows())
                if (rA.size()>=c1 && rB.size()>=c2 && rA.get(c1-1).equals(rB.get(c2-1))) {
                    List<String> jr = new ArrayList<>(rA);
                    jr.addAll(rB);
                    R.addRow(jr);
                }
        tables.put(res,R);
        System.out.println("Inner join готов.");
    }

    public void countRows(String t, int col, String val) {
        Table table = tables.get(t);
        if (table==null) { System.out.println("Не е намерена."); return; }
        long cnt = table.getRows().stream()
                .filter(r->r.size()>=col && r.get(col-1).equals(val)).count();
        System.out.println("Брой: "+cnt);
    }

    public void aggregate(String t,int sc,String sv,int tc,String op) {
        Table table = tables.get(t);
        if (table==null) { System.out.println("Не е намерена."); return; }
        List<Double> vals = table.getRows().stream()
                .filter(r->r.size()>=sc && r.get(sc-1).equals(sv))
                .map(r->Double.parseDouble(r.get(tc-1))).toList();
        if (vals.isEmpty()) { System.out.println("Няма данни."); return; }
        switch(op) {
            case "sum"      -> System.out.println("Sum: "+vals.stream().mapToDouble(d->d).sum());
            case "product"  -> System.out.println("Prod: "+vals.stream().reduce(1.0,(a,b)->a*b));
            case "max"  -> System.out.println("Max: "+vals.stream().mapToDouble(d->d).max().orElse(Double.NaN));
            case "min"  -> System.out.println("Min: "+vals.stream().mapToDouble(d->d).min().orElse(Double.NaN));
            default         -> System.out.println("Непознато.");
        }
    }
}
