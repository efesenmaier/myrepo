package part3.hw1;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Scanner;

@Test
public class PrimsMinimumSpanningTreeTest {

    public void test() {
        PrimsMinimumSpanningTree prims = new PrimsMinimumSpanningTree();

        try (Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream("primsEdges.txt"))) {
            // Ignore vertex edge size
            scanner.nextLine();

            while (scanner.hasNextLine()) {
                try (Scanner lineScanner = new Scanner(scanner.nextLine())) {
                    if (lineScanner.hasNextInt()) {
                        int u = lineScanner.nextInt();
                        if (lineScanner.hasNextInt()) {
                            int v = lineScanner.nextInt();
                            if (lineScanner.hasNextInt()) {
                                int weight = lineScanner.nextInt();

                                prims.addEdge(u, v, weight);
                            }
                        }
                    }
                }
            }
        }

        long mstWeight = prims.run();
        System.out.println("MST size: " + mstWeight);
        Assert.assertEquals(mstWeight, -885636L);
    }
}
