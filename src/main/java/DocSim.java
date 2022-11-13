import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
// import java.nio.file.StandardOpenOption;
// import java.util.Iterator;

public class DocSim {
    public static void main(String[] args) throws IOException {
        //Get Files in resources folder
        int k = 8;
        Path rpath = Paths.get("../resources");
        List<Path> filePaths;
        try (Stream<Path> paths = Files.find(rpath, 1,
            (p, BasicFileAttributes) -> {
                if(Files.isDirectory(p)) return false;
                return true;
            })) {
                filePaths = paths.collect(Collectors.toList());
        }
        ArrayList<Shingling> documents = new ArrayList<>();
        //Create shingling of each file
        for (Path p : filePaths) {
            Shingling sh = new Shingling();
            sh.shingleDocument(p, k);
            documents.add(sh);
            // Path wpath = Paths.get("./hashvalues.txt");
            // Files.deleteIfExists(wpath);
            // for (Iterator<Integer> it = sh.getDocument().iterator(); it.hasNext();) {
            //     Files.write(wpath, (String.valueOf(it.next()) + "\n").getBytes(),
            //                 StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            // }
        }
        //Calculating Jaccard Similarity of hashed shingles
        CompareSets sim = new CompareSets(documents.get(0), documents.get(0));
        System.out.println("Jaccard Similarity: " + sim.compare());
        CompareSets sim2 = new CompareSets(documents.get(0), documents.get(1));
        System.out.println("Jaccard Similarity: " + sim2.compare());
        //MinHashing
        ArrayList<MinHashing> mHashedDocuments = new ArrayList<>(documents.size());
        HashNumbers hashGen = new HashNumbers(400);
        IntStream.range(0, documents.size()).forEachOrdered(i -> {
            mHashedDocuments.add(i, new MinHashing(hashGen));
            mHashedDocuments.get(i).minHash(documents.get(i));
        });
        CompareSignatures sig = new CompareSignatures(
            mHashedDocuments.get(0).getSignature(), mHashedDocuments.get(0).getSignature());
        System.out.println("Signature similarity: " + sig.compare());
        CompareSignatures sig2 = new CompareSignatures(
            mHashedDocuments.get(0).getSignature(), mHashedDocuments.get(1).getSignature());
        System.out.println("Signature similarity: " + sig2.compare());

    }
}
