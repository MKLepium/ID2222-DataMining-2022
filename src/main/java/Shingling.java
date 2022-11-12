import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class Shingling {
    private LinkedHashSet<Long> shingledDoc = new LinkedHashSet<>();
    static final int[] primes = {97, 97, 1543, 786433, 805306457};

    //Default constructor

    public LinkedHashSet<Long> getDocument() {
        return shingledDoc;
    }

    /**
     * Shingling of a single document.
     * 
     * @param filePath Path of the target document.
     * @param k Shingle size.
     * @throws IOException
     */
    public void shingleDocument(Path filePath, int k) throws IOException {
        if (k < 2) k = 2;
        if (k > 10) k = 10;
        FileReader source = new FileReader(filePath.toString());
        int character, i = 0;
        ArrayList<ArrayList<Long>> document = new ArrayList<>();
        boolean ws = false;
        while ((character = source.read()) != -1) {
            //Whitespace and newlines
            if (character == 9 || character == 10 || character == 32) {
                //Create k windows at the start
                //unless character is secondary whitespace
                if (i < k && ws == false) {
                    document.add(new ArrayList<Long>());
                    i++;
                }
                ws = true;
            } else {
                if (i < k) {
                    document.add(new ArrayList<Long>());
                    i++;
                }
                //Fill windows; when full write them to set and clear them
                //Not worth multi-threading (would have to start/stop for every character)
                for (int j=0; j < i; j++) {
                    ArrayList<Long> shingle = document.get(j);
                    if (shingle.size() == k) {
                        long hashvalue = hashShingle(shingle, k);
                        shingledDoc.add(hashvalue);
                        shingle.clear();
                    }
                    if (ws = true) {
                        shingle.add((long)32);
                        if (shingle.size() == k) {
                            long hashvalue = hashShingle(shingle, k);
                            shingledDoc.add(hashvalue);
                            shingle.clear();
                        }
                    }
                    shingle.add((long)character);
                }
                ws = false;
            }
        }
    }

    /**
     * Simple hashing of a k-shingle. For k<=3, sum the Longs then
     * divide by a large prime. For k>3, concatenate groups of Longs
     * of size log2(k), sum the concatenated Longs then divide by
     * a large prime.
     * 
     * @param shingle Target shingle.
     * @param k Shingle size.
     * @return
     */
    public Long hashShingle(ArrayList<Long> shingle, int k) {
        int split = 0;
        if (k <= 3)
            return shingle.stream().reduce(0L, (a, b) -> a + b) % primes[1]; 
        if (k > 3 && k <= 5)
            split = 2;
        if (k > 5 && k <= 7)
            split = 3;
        if (k > 7)
            split = 4;
        long hashed = 0, group = 0, j = 0;
        for (int i=0; i < shingle.size(); i++) {
            if (j == split) {
                j = 0;
                hashed += group;
                group = 0;
            }
            int size = 1;
            long num = shingle.get(i);
            while ((num /= 10) > 0)
                size++;
            group = group * (long)Math.pow(10, size) + shingle.get(i);
            j++;
            if (i == shingle.size() - 1)
                hashed += group;
        }
        return hashed % primes[split];
    }
}
