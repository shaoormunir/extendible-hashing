package app;

import java.util.Arrays;

/**
 * Hello world!
 */
public final class App {
    public static boolean DEBUG = false;

    private App() {
    }

    public static void main(String[] args) {
        String choice = FileIO.getSelectionFromUser(Arrays.asList("Run the program yourself", "Run example scenario"),
                "Please choose how you want to run the program.");
        if (choice.equals("Run the program yourself")) {
            runFromUserInput();
        } else {
            runExampleSequence();
        }
    }

    private static void runFromUserInput() {
        int bucketSize = FileIO.getIntegerInputFromUser("Please enter the bucket size: ");
        Directory directory = new Directory(bucketSize);
        boolean exit = false;
        while (!exit) {
            int key = 0;
            String value = "";
            System.out.println("Current state of hash table: " + System.lineSeparator());
            System.out.println(directory.toString());
            String choice = FileIO.getSelectionFromUser(Arrays.asList("Insert", "Remove", "Lookup", "Exit"),
                    "Please select one option to continue: ");
            switch (choice) {
            case "Insert":
                key = FileIO.getIntegerInputFromUser("Enter the key: ");
                value = FileIO.getStringInputFromUser("Enter the value: ");
                directory.insert(key, value);
                break;
            case "Remove":
                key = FileIO.getIntegerInputFromUser("Enter the key to remove");
                directory.remove(key);
                break;
            case "Lookup":
                key = FileIO.getIntegerInputFromUser("Enter a key to lookup: ");
                System.out.println("The value at key " + key + " is " + directory.lookup(key));
                break;
            default:
                exit = true;
                break;
            }
        }
    }

    private static void runExampleSequence() {
        Directory directory = new Directory(2);
        System.out.println("Initial state of the table: " + System.lineSeparator());
        System.out.println(directory.toString());
        System.out.println("Adding Dog with key 5: " + System.lineSeparator());
        directory.insert(5, "Dog");
        System.out.println(directory.toString());
        System.out.println("Adding Goat with key 6: " + System.lineSeparator());
        directory.insert(6, "Goat");
        System.out.println(directory.toString());
        System.out.println("Adding Horse with key 1: " + System.lineSeparator());
        directory.insert(1, "Horse");
        System.out.println(directory.toString());
        System.out.println("Adding Hippo with key 7: " + System.lineSeparator());
        directory.insert(7, "Hippo");
        System.out.println(directory.toString());
        System.out.println("Adding Dragon with key 2: " + System.lineSeparator());
        directory.insert(2, "Dragon");
        System.out.println(directory.toString());
        System.out.println("Adding Ghost with key 8: " + System.lineSeparator());
        directory.insert(8, "Ghost");
        System.out.println(directory.toString());
        System.out.println("Adding Panda with key 9: " + System.lineSeparator());
        directory.insert(9, "Panda");
        System.out.println(directory.toString());
        System.out.println("Adding Snake with key 3: " + System.lineSeparator());
        directory.insert(3, "Snake");
        System.out.println(directory.toString());
        System.out.println("Adding Cat with key 4: " + System.lineSeparator());
        directory.insert(4, "Cat");
        System.out.println(directory.toString());
        System.out.println("Removing keys 3, 6, and 8: " + System.lineSeparator());
        directory.remove(3);
        directory.remove(6);
        directory.remove(8);
        System.out.println(directory.toString());
        System.out.println("Removing keys 9, 7, and 1: " + System.lineSeparator());
        directory.remove(9);
        directory.remove(7);
        directory.remove(1);
        System.out.println(directory.toString());
        System.out.println("Removing keys 5, 4, and 2: " + System.lineSeparator());
        directory.remove(5);
        directory.remove(4);
        directory.remove(2);
        System.out.println(directory.toString());
    }
}