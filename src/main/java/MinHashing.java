import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.stream.IntStream;

public class MinHashing {
    private int[] signature;
    private HashNumbers hashArrays;

    public MinHashing(HashNumbers hashArrays) {
        this.hashArrays = hashArrays;
        this.signature = new int[hashArrays.getNumHashes()];
    }

    public int[] getSignature() {
        return signature;
    }

    /**
     * Computes min hash signature, using numHashes hash functions.
     * The signature array is initialized wwith MAX_VALUE in all positions.
     * The hash functions are of the form ax+b, where a and b are randomly
     * chosen integers, and x is the row number.
     * 
     * @param shingling
     * @throws IOException
     */
    public void minHash(Shingling shingling) {
        Arrays.fill(signature, Integer.MAX_VALUE);
        LinkedHashSet<Integer> shingledDoc = shingling.getDocument();
        Integer[] shingleArray = new Integer[shingledDoc.size()];
        shingledDoc.toArray(shingleArray);
        Arrays.sort(shingleArray);
        for (int row = 0; row < shingleArray.length; row++) {
            int hash = shingleArray[row];
            IntStream.range(0, hashArrays.getNumHashes()).forEach(i -> {
                int newHash = (int)Long.remainderUnsigned(hashArrays.getHashA(i)
                            * hash + hashArrays.getHashB(i), 4591713823L);
                if (newHash > 0 && newHash < signature[i])
                    signature[i] = hash;
            });
        }
    }
}
