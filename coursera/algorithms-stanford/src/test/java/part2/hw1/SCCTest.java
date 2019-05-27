package part2.hw1;

import com.google.common.collect.ImmutableSet;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

@Test
public class SCCTest {

    public void lectureExample() {
        DirectedGraph directedGraph = new DirectedGraph();

        // Connected Component 1
        directedGraph.addReversedEdge(1, 7);
        directedGraph.addReversedEdge(7, 4);
        directedGraph.addReversedEdge(4, 1);

        directedGraph.addReversedEdge(7, 9);

        // Connected Component 2
        directedGraph.addReversedEdge(9, 6);
        directedGraph.addReversedEdge(6, 3);
        directedGraph.addReversedEdge(3, 9);

        directedGraph.addReversedEdge(6, 8);

        // Connected Component 3
        directedGraph.addReversedEdge(8, 2);
        directedGraph.addReversedEdge(2, 5);
        directedGraph.addReversedEdge(5, 8);

        SCC scc = new SCC(directedGraph);
        scc.run();

        Assert.assertEquals(scc.components().size(), 3);

        Set<Integer> comp1 = ImmutableSet.of(1, 4, 7);
        Set<Integer> comp2 = ImmutableSet.of(9, 6, 3);
        Set<Integer> comp3 = ImmutableSet.of(8, 2, 5);
        boolean matchesComp1 = false;
        boolean matchesComp2 = false;
        boolean matchesComp3 = false;
        for (Set<Integer> component : scc.components().values()) {
            if (comp1.equals(component)) {
                Assert.assertFalse(matchesComp1);
                matchesComp1 = true;
            }

            if (comp2.equals(component)) {
                Assert.assertFalse(matchesComp2);
                matchesComp2 = true;
            }

            if (comp3.equals(component)) {
                Assert.assertFalse(matchesComp3);
                matchesComp3 = true;
            }

            // Ensure this component equals one of the components
            Assert.assertTrue(comp1.equals(component) ||  comp2.equals(component) || comp3.equals(component));
        }

        // Ensure each component was matched
        Assert.assertTrue(matchesComp1 && matchesComp2 && matchesComp3);

        System.out.println("Component sizes: " + getLargestComponentSizes(scc));
    }

    public void testProgrammingAssignment() throws IOException {
        DirectedGraph graph = loadGraphFromFile2();
        SCC scc = new SCC(graph);
        scc.run();
        System.out.println("Component sizes: " + getLargestComponentSizes(scc));

        // Verify answer matches the one submitted
        Assert.assertEquals(getLargestComponentSizes(scc), "434821,968,459,313,211");
    }

    private DirectedGraph loadGraphFromFile2() throws IOException {
        DirectedGraph graph = new DirectedGraph();

        int lineCount = 0;
        try (FileReader reader = new FileReader(getClass().getClassLoader().getResource("SCC.txt").getFile());
             BufferedReader br = new BufferedReader(reader)) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("\\s");
                if (tokens.length >= 2) {
                    int u = Integer.parseInt(tokens[0]);
                    int v = Integer.parseInt(tokens[1]);
                    graph.addEdge(u, v);
                    ++lineCount;
                    if (lineCount % 10000 == 0) {
                        System.out.println("Line: " + lineCount);
                    }
                }
            }
        }

        return graph;
    }

    private String getLargestComponentSizes(SCC scc) {
        String output = null;

        int[] largestComponents = new int[5];
        for (Set<Integer> component : scc.components().values()) {
            if (component.size() > largestComponents[0]) {
                largestComponents[0] = component.size();
                Arrays.sort(largestComponents);
            }
        }

        for (int i = largestComponents.length - 1; i >= 0; --i) {
            if (output == null) {
                output = "" + largestComponents[i];
            } else {
                output += "," + largestComponents[i];
            }
        }
        return output;
    }
}
