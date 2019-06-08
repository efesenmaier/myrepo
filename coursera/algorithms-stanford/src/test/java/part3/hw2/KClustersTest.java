package part3.hw2;

import org.testng.annotations.Test;

import java.util.Scanner;

/**
 * T
 */
@Test
public class KClustersTest {

    public void test() {
        KClusters kClusters;

        try (Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream("clustering1.txt"))) {
            // Number of nodes
            int n = scanner.nextInt();
            kClusters = new KClusters(n);

            while (scanner.hasNextLine()) {
                try (Scanner lineScanner = new Scanner(scanner.nextLine())) {
                    if (lineScanner.hasNextInt()) {
                        int u = lineScanner.nextInt();
                        if (lineScanner.hasNextInt()) {
                            int v = lineScanner.nextInt();
                            if (lineScanner.hasNextInt()) {
                                Long weight = lineScanner.nextLong();
                                kClusters.addEdge(Integer.toString(u), Integer.toString(v), weight);
                            }
                        }
                    }
                }
            }
        }

        if (kClusters != null) {
            int k = 4;
            long maxSpacing = kClusters.find(k);
            System.out.println("Max size: " + maxSpacing);
            //Assert.assertEquals(maxSpacing, -885636L);
        }
    }
}
