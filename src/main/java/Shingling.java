import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class Shingling {
    private LinkedHashSet<Integer> shingledDoc = new LinkedHashSet<>();
    private int shingleSize;
    private int numShingles;

    //Default constructor

    public LinkedHashSet<Integer> getDocument() {
        return shingledDoc;
    }

    public int getShingleSize() {
        return shingleSize;
    }

    public int getNumShingles() {
        return numShingles;
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
        if (k > 32) k = 32;
        this.shingleSize = k;
        FileReader source = new FileReader(filePath.toString());
        int character, i = 0;
        ArrayList<ArrayList<Integer>> document = new ArrayList<>();
        boolean ws = false;
        while ((character = source.read()) != -1) {
            //Create k windows at the start
            if (i < k) {
                document.add(new ArrayList<Integer>());
                i++;
            }
            //Write nothing for non-alphanumeric
            if (character < 48 || character > 57 && character < 65 || character > 90 && character < 97 || character > 122)
                ws = true;
            else {
                //Fill windows; when full write them to set and clear them
                //Not worth multi-threading (would have to start/stop for every character)
                for (int j=0; j < i; j++) {
                    ArrayList<Integer> shingle = document.get(j); //Ref to actual array
                    if (shingle.size() == k) {
                        int hashvalue = hashShingle(shingle);
                        shingledDoc.add(hashvalue);
                        shingle.clear();
                    }
                    if (ws = true) {
                        shingle.add(32);
                        if (shingle.size() == k) {
                            int hashvalue = hashShingle(shingle);
                            shingledDoc.add(hashvalue);
                            shingle.clear();
                        }
                    }
                    shingle.add(character);
                }
                ws = false;
            }
        }
        numShingles = shingledDoc.size();
    }

    /**
     * Hashing of a shingle, derived from MurmurHash3.
     * 
     * @param shingle Target shingle.
     * @param k Shingle size.
     * @return
     */
    public int hashShingle(ArrayList<Integer> shingle) {
        long hash = 0;
        for (long i : shingle) {
            i = Long.remainderUnsigned(i * 3432918353L, Integer.MAX_VALUE);
            Integer.rotateLeft((int)i, 15);
            i = Long.remainderUnsigned(i * 461845907L, Integer.MAX_VALUE);
            hash ^= i;
            Integer.rotateLeft((int)hash, 13);
            hash = Long.remainderUnsigned(hash * 5 + 106195812L, Integer.MAX_VALUE);
        }
        return (int)hash;
    }
}
