import java.util.ArrayList;
import java.util.Random;

public class HashNumbers {
    private int numHashes;
    private ArrayList<Integer> hashA = new ArrayList<>();
    private ArrayList<Integer> hashB = new ArrayList<>();

    public HashNumbers(int numHashes) {
        this.numHashes = numHashes;
        randomSelect(hashA);
        randomSelect(hashB);
    }

    public int getNumHashes() {
        return numHashes;
    }

    public int getHashA(int i) {
        return hashA.get(i);
    }

    public int getHashB(int i) {
        return hashB.get(i);
    }

    private void randomSelect( ArrayList<Integer> hashArray) {
        Random rng = new Random();
        for (int i = 0; i < numHashes; i++) {
            int k;
            do {
                k = rng.nextInt(Integer.MAX_VALUE);
            } while (hashArray.contains(k));
            hashArray.add(k);
        }
    }
}