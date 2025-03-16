package yes.idea.da;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Добре дошли в системата за управление на бази от данни!");
        printHelp();

        while (running) {
            System.out.print("\n> ");
            String command = scanner.nextLine().trim();

            switch (command.toLowerCase()) {
                case "help":
                    printHelp();
                    break;
                case "exit":
                    System.out.println("Изход от програмата...");
                    running = false;
                    break;
                case "test":
                    showTables();
                    break;
                default:
                    System.out.println("Невалидна команда. Въведете 'help' за списък с команди.");
            }
        }

        scanner.close();
    }

    private static void printHelp() {
        System.out.println("\nНалични команди:");
        System.out.println("help - показва списък с команди");
        System.out.println("test - показва наличните функции");
        System.out.println("exit - изход от програмата");
    }

    private static void showTables() {
        System.out.println("Функцията ще бъде реализирана.");
    }
}