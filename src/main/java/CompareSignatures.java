public class CompareSignatures {
    private int[] a;
    private int[] b;

    public CompareSignatures(int[] a, int[] b) {
        this.a = a;
        this.b = b;
    }

    public double compare() {
        assert a.length == b.length;
        int similar = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == b[i])
                similar++;
        }
        return (double)similar / a.length;
    }
}