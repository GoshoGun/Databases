package yes.idea.da.Interface;

import java.util.List;
import java.util.Scanner;

/**
 * Помощен клас за странициране на списъци от данни в конзолата.
 */
public class ConsolePager {
    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Принтира таблица с навигация [n] [p] [e].
     *
     * @param headers  заглавия на колоните
     * @param rows     редове с данни
     * @param pageSize брой реда на страница
     */
    public static void page(List<String> headers,
                            List<List<String>> rows,
                            int pageSize) {
        if (!headers.isEmpty()) {
            System.out.println(String.join("\t", headers));
        }
        int totalRows = rows.size();
        int totalPages = (totalRows + pageSize - 1) / pageSize;
        int currentPage = 1;

        while (true) {
            int start = (currentPage - 1) * pageSize;
            int end = Math.min(start + pageSize, totalRows);
            System.out.printf("Страница %d/%d%n", currentPage, totalPages);

            for (int i = start; i < end; i++) {
                System.out.println(String.join("\t", rows.get(i)));
            }
            if (totalPages <= 1) break;

            System.out.print("[n]следваща [p]предишна [e]изход: ");
            String cmd = SCANNER.nextLine().trim().toLowerCase();
            if ("n".equals(cmd) && currentPage < totalPages) {
                currentPage++;
            } else if ("p".equals(cmd) && currentPage > 1) {
                currentPage--;
            } else if ("e".equals(cmd)) {
                break;
            } else {
                System.out.println("Невалидна команда.");
            }
        }
    }
}
