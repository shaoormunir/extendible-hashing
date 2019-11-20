package app;

import java.util.HashMap;

/**
 * Bucket
 */
public class Bucket {
    private int depth;
    private int size;
    private String pattern;
    private HashMap<Integer, String> data;

    public Bucket() {
        data = new HashMap<>();
    }

    public boolean insert(int key, String value) {
        if (data.size() == size)
            return false;
        data.put(key, value);
        return true;
    }

    public void increaseDepth() {
        this.depth++;
    }

    public void decreaseDepth() {
        this.depth--;
    }

    public int getDepth() {
        return this.depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public Bucket depth(int depth) {
        this.depth = depth;
        return this;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Bucket size(int size) {
        this.size = size;
        return this;
    }

    public String getPattern() {
        return this.pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Bucket pattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public HashMap<Integer, String> getData() {
        return this.data;
    }

    public void setData(HashMap<Integer, String> data) {
        this.data = data;
    }

    public Bucket data(HashMap<Integer, String> data) {
        this.data = data;
        return this;
    }

    // @Override
    // public String toString() {
    // return "Bucket [data=" + data + ", pattern=" + pattern + ", depth=" + depth +
    // ", size=" + size + "]";
    // }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (getData().size() > 0) {
            getData().forEach((key, value) -> builder.append(" [" + value + " (" + key + ")]"));
        } else
            builder.append(" Empty bucket");
        builder.append(" (Local depth: " + getDepth() + ")");
        return builder.toString();
    }

}