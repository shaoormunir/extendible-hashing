package app;

/**
 * HashGenerator
 */
public class HashGenerator {

    public static String hash(int key) {
        String value = Integer.toBinaryString(key);
        for (int i = value.length(); i < 8; i++) {
            value = "0" + value;
        }
        return value;
    }
}