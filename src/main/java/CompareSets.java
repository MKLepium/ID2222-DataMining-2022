import java.util.HashSet;

public class CompareSets {
    private Shingling a;
    private Shingling b;

    public CompareSets(Shingling a, Shingling b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Compute the Jaccard similarity between the two sets of hashed shingles.
     * Formula: cardinal of the intersection over cardinal of the union.
     * 
     * @return
     */
    public double compare() {
        HashSet<Integer> intersection = new HashSet<Integer>(a.getDocument());
        intersection.retainAll(b.getDocument());
        HashSet<Integer> union = new HashSet<Integer>(a.getDocument());
        union.addAll(b.getDocument());
        return (double)intersection.size() / union.size();
    }
}
