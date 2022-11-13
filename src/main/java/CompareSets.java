import java.util.HashSet;

public class CompareSets {
    Shingling a;
    Shingling b;

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
        HashSet<Long> intersection = new HashSet<Long>(a.getDocument());
        intersection.retainAll(b.getDocument());
        HashSet<Long> union = new HashSet<Long>(a.getDocument());
        union.addAll(b.getDocument());
        return (double)intersection.size() / union.size();
    }
}
