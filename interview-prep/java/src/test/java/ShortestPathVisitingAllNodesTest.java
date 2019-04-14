import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class ShortestPathVisitingAllNodesTest {
    public void test() {
        {
            int[][] graph = {{1, 2, 3}, {0}, {0}, {0}};
            Assert.assertEquals(ShortestPathVisitingAllNodes.shortestPathLength(graph), 4);
        }

        {
            int[][] graph = {{1}, {0, 2, 4}, {1, 3, 4}, {2}, {1, 2}};
            Assert.assertEquals(ShortestPathVisitingAllNodes.shortestPathLength(graph), 4);
        }
    }

    public void test2() {
        {
            int[][] graph = {{6,7},{6},{6},{5,6},{6},{3},{2,0,3,4,1},{0}};
            Assert.assertEquals(ShortestPathVisitingAllNodes.shortestPathLength(graph), 10);
        }
    }
}
