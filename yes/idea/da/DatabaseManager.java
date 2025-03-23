package yes.idea.da;

import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {
    private Map<String, Table> tables;

    public DatabaseManager() {
        tables = new HashMap<>();
    }

    public void showTables() {
        if (tables.isEmpty()) {
            System.out.println("Няма заредени таблици.");
        } else {
            System.out.println("Налични таблици:");
            for (String tableName : tables.keySet()) {
                System.out.println("  " + tableName);
            }
        }
    }

    public void importTable(String fileName) {
        if (fileName.isEmpty()) {
            System.out.println("Трябва да въведете име на файл.");
            return;
        }
        // TODO: Импортиране от файл – логика за четене на файл
        System.out.println("Таблицата от " + fileName + " е заредена (симулирано).");
        tables.put(fileName, new Table(fileName));
    }

    public void saveDatabase() {
        // TODO: Реална логика за записване
        System.out.println("Базата данни е запазена (симулирано).");
    }

    public void saveDatabaseAs(String fileName) {
        if (fileName.isEmpty()) {
            System.out.println("Трябва да зададете име на файл.");
            return;
        }
        // TODO: Реално записване във файл
        System.out.println("Базата е запазена като " + fileName + " (симулирано).");
    }
}
