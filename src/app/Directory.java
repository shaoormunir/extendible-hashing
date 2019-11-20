package app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Directory
 */
public class Directory {
    private int depth;
    private int bucketSize;
    private Bucket[] buckets;
    private HashMap<String, Bucket> bucketMap;

    public int getDepth() {
        return this.depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public Directory depth(int depth) {
        this.depth = depth;
        return this;
    }

    public int getBucketSize() {
        return this.bucketSize;
    }

    public void setBucketSize(int bucketSize) {
        this.bucketSize = bucketSize;
    }

    public Directory bucketSize(int bucketSize) {
        this.bucketSize = bucketSize;
        return this;
    }

    public Bucket[] getBuckets() {
        return this.buckets;
    }

    public void setBuckets(Bucket[] buckets) {
        this.buckets = buckets;
    }

    public Directory buckets(Bucket[] buckets) {
        this.buckets = buckets;
        return this;
    }

    public HashMap<String, Bucket> getBucketMap() {
        return this.bucketMap;
    }

    public void setBucketMap(HashMap<String, Bucket> bucketMap) {
        this.bucketMap = bucketMap;
    }

    public Directory bucketMap(HashMap<String, Bucket> bucketMap) {
        this.bucketMap = bucketMap;
        return this;
    }

    public Directory(int bucketSize) {
        this.bucketSize = bucketSize;
        bucketMap = new HashMap<>();
        bucketMap.put("0", new Bucket().size(bucketSize).depth(1).pattern("0"));
        bucketMap.put("1", new Bucket().size(bucketSize).depth(1).pattern("1"));
        depth = 1;
    }

    private void splitBucket(Bucket bucketToBeSplit) {
        int currentDepth = bucketToBeSplit.getDepth();
        String currentPattern = bucketToBeSplit.getPattern();
        Bucket firstBucket = new Bucket().depth(currentDepth + 1).pattern("0" + currentPattern)
                .size(bucketToBeSplit.getSize());
        Bucket secondBucket = new Bucket().depth(currentDepth + 1).pattern("1" + currentPattern)
                .size(bucketToBeSplit.getSize());
        bucketMap.put(firstBucket.getPattern(), firstBucket);
        bucketMap.put(secondBucket.getPattern(), secondBucket);
        bucketToBeSplit.getData()
                .forEach((dataKey, dataValue) -> bucketMap.get(getStringHash(dataKey)).insert(dataKey, dataValue));
    }

    private void expandDirectory() {

        List<String> keys = new ArrayList<>(bucketMap.keySet());

        keys.forEach(key -> {
            Bucket pointedBucket = bucketMap.get(key);
            bucketMap.put("1" + key, pointedBucket);
            bucketMap.put("0" + key, pointedBucket);
            bucketMap.remove(key);
        });
        depth++;
    }

    private String getSisPattern(String pattern) {
        return pattern.substring(0, pattern.length() - 1) + (pattern.charAt(pattern.length() - 1) == '1' ? "0" : "1");
    }

    private Bucket getBucketByPattern(String pattern) {
        return getBucketMap().values().stream().filter(bucket -> bucket.getPattern().equals(pattern)).findFirst()
                .orElse(null);
    }

    private boolean checkIfMergeNeeded(Bucket bucket) {
        if (bucket.getPattern().length() > 1) {
            String sisPattern = getSisPattern(bucket.getPattern());
            Bucket sisBucket = getBucketByPattern(sisPattern);
            if (sisBucket == null)
                return false;
            return (bucket.getData().size() + sisBucket.getData().size() <= bucketSize);
        }
        return false;
    }

    private void mergeBuckets(Bucket bucket) {
        String pattern = bucket.getPattern();
        String sisPattern = getSisPattern(pattern);
        Bucket sisBucket = getBucketByPattern(sisPattern);
        String newPattern = bucket.getPattern().substring(0, pattern.length() - 1);
        HashMap<Integer, String> newData = new HashMap<>();
        newData.putAll(bucket.getData());
        newData.putAll(sisBucket.getData());
        Bucket newBucket = new Bucket().pattern(newPattern).depth(bucket.getDepth() - 1).size(this.bucketSize)
                .data(newData);
        bucketMap.forEach((key, value) -> {
            if (value == bucket || value == sisBucket)
                bucketMap.put(key, newBucket);
        });
    }

    private String getStringHash(int key) {
        return HashGenerator.hash(key).substring(8 - depth, 8);
    }

    public void insert(int key, String value) {
        String stringHash = getStringHash(key);
        if (App.DEBUG)
            System.out.println("The value of hash is: " + stringHash);

        Bucket bucket = bucketMap.get(stringHash);

        if (bucket.insert(key, value)) {
            if (App.DEBUG)
                System.out.println("The value has been inserted.");
        } else {
            if (bucket.getDepth() < this.depth) {
                if (App.DEBUG)
                    System.out.println("The bucket has a depth level less than global depth, bucket needs to be split");
                String pattern = bucket.getPattern();
                splitBucket(bucket);
                bucketMap.remove(pattern);
                insert(key, value);
            } else if (bucket.getDepth() == this.depth) {
                if (App.DEBUG)
                    System.out.println(
                            "The bucket has a depth level equal to the global depth, both bucket and directory needs to be split and extended.");
                String pattern = bucket.getPattern();
                expandDirectory();
                bucketMap.remove(pattern);
                splitBucket(bucket);
                insert(key, value);
            } else {
                if (App.DEBUG)
                    System.out.println(
                            "The bucket has a depth level greater than global depth which is not possible. Exiting the program.");
                System.exit(-1);
            }
        }

    }

    public String lookup(int key) {
        String stringHash = getStringHash(key);
        Bucket bucket = bucketMap.get(stringHash);
        if (bucket == null)
            return "";
        return Optional.of(bucket.getData().get(key)).orElse("");

    }

    public void remove(int key) {
        String stringHash = getStringHash(key);
        Bucket bucket = bucketMap.get(stringHash);
        if (bucket != null) {
            bucket.getData().remove(key);
            if (checkIfMergeNeeded(bucket)) {
                mergeBuckets(bucket);
            }
        }
    }

    // @Override
    // public String toString() {
    // return "Directory [bucketMap=" + bucketMap + ", bucketSize=" + bucketSize +
    // ", buckets="
    // + Arrays.toString(buckets) + ", depth=" + depth + "]";
    // }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        List<String> keys = new ArrayList<>(getBucketMap().keySet());
        Collections.sort(keys);
        for (String key : keys) {
            if (!builder.toString().contains(key + ":")) {
                builder.append(key + ": ───────────── >");
                Bucket temp = getBucketMap().get(key);
                if (temp != null) {
                    builder.append(temp.toString() + System.lineSeparator());
                    if (temp.getData().size() > 0) {
                        keys.stream().filter(tempKey -> !tempKey.equals(key))
                                .filter(tempKey -> getBucketMap().get(tempKey).getPattern().equals(temp.getPattern()))
                                .forEach(tempKey -> builder
                                        .append(tempKey + ": ─────────────┘" + System.lineSeparator()));
                    }
                    builder.append(System.lineSeparator());
                } else
                    builder.append(System.lineSeparator());
            }
        }

        return "(Global depth: " + getDepth() + ")" + System.lineSeparator() + System.lineSeparator()
                + builder.toString();
    }

}