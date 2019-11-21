package app;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class FileIO {
    private static Scanner scanner = new Scanner(System.in);

    private static boolean containsOnlyNumbers(String input) {
        return input.matches("^[0-9]+$");
    }

    public static <T> T getSelectionFromUser(List<T> list) {
        AtomicInteger integer = new AtomicInteger(1);
        list.forEach(item -> System.out.printf("(%s) %s" + System.getProperty("line.separator"),
                integer.getAndIncrement(), item.toString()));
        System.out.printf("Please enter a number between 1 and %d to make your selection: ", list.size());
        while (true) {
            int selection = getIntegerInputFromUser("");
            if (selection > 0 && selection <= list.size()) {
                return list.get(selection - 1);
            } else {
                System.out.println("Selection is out of bounds. Please try again.");
            }
        }

    }

    public static <T> T getSelectionFromUser(List<T> list, String message) {
        System.out.println(message);
        AtomicInteger integer = new AtomicInteger(1);
        list.forEach(item -> System.out.printf("(%s) %s" + System.getProperty("line.separator"),
                integer.getAndIncrement(), item.toString()));
        System.out.printf("Please enter a number between 1 and %d to make your selection: ", list.size());
        while (true) {
            int selection = getIntegerInputFromUser("");
            if (selection > 0 && selection <= list.size()) {
                return list.get(selection - 1);
            } else {
                System.out.println("Selection is out of bounds. Please try again.");
            }
        }
    }

    public static String getStringInputFromUser(String message) {
        System.out.print(message);
        String response = scanner.nextLine();
        return response;
    }

    public static int getIntegerInputFromUser(String message) {
        System.out.print(message);
        while (true) {
            String input = scanner.nextLine();
            if (containsOnlyNumbers(input)) {
                return Integer.parseInt(input);
            } else {
                System.out.println("Please enter only integers. Try again.");
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        scanner.close();
    }

}