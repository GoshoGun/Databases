package yes.idea.da.Interface;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

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
        if (fileName.isBlank()) {
            System.out.println("Използване: import <file>");
            return;
        }
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));  // :contentReference[oaicite:1]{index=1}
            Table table = new Table(fileName);
            if (!lines.isEmpty()) {
                // Заглавия
                Arrays.stream(lines.get(0).split(","))
                        .map(String::trim)
                        .forEach(h -> table.addColumn(new Column(h, "string")));
                // Редове
                lines.stream().skip(1)
                        .map(line -> Arrays.stream(line.split(","))
                                .map(String::trim)
                                .collect(Collectors.toList()))
                        .forEach(table::addRow);
            }
            tables.put(fileName, table);
            System.out.println("Импортирано: " + fileName);
        } catch (IOException e) {
            System.out.println("Грешка import: " + e.getMessage());
        }
    }

    public void saveDatabase() {
        saveDatabaseAs("database.txt");
    }

    public void saveDatabaseAs(String fileName) {
        if (fileName.isBlank()) {
            System.out.println("Използване: saveas <file>");
            return;
        }
        try (BufferedWriter w = Files.newBufferedWriter(Paths.get(fileName))) {  // :contentReference[oaicite:2]{index=2}
            for (String name : tables.keySet()) {
                w.write(name);
                w.newLine();
            }
            System.out.println("Запазено: " + fileName);
        } catch (IOException e) {
            System.out.println("Грешка saveas: " + e.getMessage());
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

    public void selectFromTable(String t, int col, String val) {
        Table table = tables.get(t);
        if (table == null) { System.out.println("Не е намерена."); return; }
        if (col < 1 || col > table.getColumns().size()) {
            System.out.println("Невалиден номер на колона."); return;
        }
        List<List<String>> m = table.getRows().stream()
                .filter(r -> r.size() >= col && r.get(col-1).equals(val))
                .collect(Collectors.toList());
        if (m.isEmpty()) { System.out.println("Няма съвпадения."); return; }
        int pageSize = 5, total = m.size(), pages = (total + pageSize - 1) / pageSize;
        int page = 1;
        Scanner sc = new Scanner(System.in);
        while (true) {
            int s = (page-1)*pageSize, e = Math.min(s+pageSize, total);
            table.getColumns().forEach(c -> System.out.print(c.getName()+"\t"));
            System.out.println();
            for (int i = s; i < e; i++) {
                m.get(i).forEach(cell->System.out.print(cell+"\t"));
                System.out.println();
            }
            if (pages <= 1) break;
            System.out.print("[n]/[p]/[e]: ");
            String in = sc.nextLine().trim();
            if (in.equals("n") && page<pages) page++;
            else if (in.equals("p") && page>1) page--;
            else if (in.equals("e")) break;
        }
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
        table.getRows().removeIf(r -> r.size()>=col && r.get(col-1).equals(val));  // :contentReference[oaicite:8]{index=8}
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
            case "maximum"  -> System.out.println("Max: "+vals.stream().mapToDouble(d->d).max().orElse(Double.NaN));
            case "minimum"  -> System.out.println("Min: "+vals.stream().mapToDouble(d->d).min().orElse(Double.NaN));
            default         -> System.out.println("Непознато.");
        }
    }
}
