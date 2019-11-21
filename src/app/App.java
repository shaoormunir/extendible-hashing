package app;

/**
 * Hello world!
 */
public final class App {
    public static boolean DEBUG = false;

    private App() {
    }

    /**
     * Says hello to the world.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        Directory directory = new Directory(2);
        directory.insert(5, "Dog");
        System.out.println(directory.toString());
        directory.insert(6, "Goat");
        System.out.println(directory.toString());
        directory.insert(1, "Horse");
        System.out.println(directory.toString());
        directory.insert(7, "Hippo");
        System.out.println(directory.toString());
        directory.insert(2, "Dragon");
        System.out.println(directory.toString());
        directory.insert(8, "Ghost");
        System.out.println(directory.toString());
        directory.insert(9, "Panda");
        System.out.println(directory.toString());
        directory.insert(3, "Snake");
        System.out.println(directory.toString());
        directory.insert(4, "Cat");
        System.out.println(directory.toString());
        directory.remove(3);
        directory.remove(6);
        directory.remove(8);
        System.out.println(directory.toString());
        directory.remove(9);
        directory.remove(7);
        directory.remove(1);
        System.out.println(directory.toString());
        directory.remove(5);
        directory.remove(4);
        directory.remove(2);
        System.out.println(directory.toString());
    }
}