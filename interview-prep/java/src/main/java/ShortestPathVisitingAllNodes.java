import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

/**
 * From https://leetcode.com/problems/shortest-path-visiting-all-nodes/description/
 */
public class ShortestPathVisitingAllNodes {

    private static class NodeState {
        private int node;
        private int visited;
        private int numVisited;
        private int pathLength;

        public NodeState(int i) {
            this.node = i;
            markVisited();
        }

        public NodeState(int i, NodeState prev) {
            this.node = i;
            this.visited = prev.getVisited();
            this.pathLength = prev.getPathLength() + 1;
            this.numVisited = prev.getNumVisited();
            markVisited();
        }

        public int getVisited() {
            return visited;
        }

        public boolean isVisited(int i) {
            return (visited & (1 << i)) != 0;
        }

        private void markVisited() {
            if (!isVisited(node)) {
                visited |= (1 << node);
                ++numVisited;
            }
        }

        public int getPathLength() {
            return pathLength;
        }

        public int getNode() {
            return node;
        }

        public int getNumVisited() {
            return numVisited;
        }

        @Override
        public boolean equals(Object o) {
            NodeState p = (NodeState) o;
            return visited == p.getVisited() && node == p.getNode();
        }

        @Override
        public int hashCode() {
            return Objects.hash(visited, node);
        }
    }

    public static int shortestPathLength(int[][] graph) {
        Queue<NodeState> nodesToVisit = new LinkedList<>();
        Set<NodeState> seenNodes = new HashSet<>();


        for (int i = 0; i < graph.length; ++i){
            NodeState start = new NodeState(i);
            nodesToVisit.add(start);
            seenNodes.add(start);
        }

        while (!nodesToVisit.isEmpty()) {
            NodeState current = nodesToVisit.poll();
            //System.out.println("Visiting node " + current.getNode() + " with visited state " + toString(current.getVisited()) + " and path length " + current.getPathLength());

            for (int i : getNeighbors(current.getNode(), graph)) {
                NodeState neighbor = new NodeState(i, current);
                if (!seenNodes.contains(neighbor)) {
                    if (current.getNumVisited() == graph.length) {
                        return current.getPathLength();
                    }
                    nodesToVisit.add(neighbor);
                    seenNodes.add(neighbor);
                } else {
                    //System.out.println("Ignoring node " + i + " with visited state " + toString(neighbor.getVisited()));
                }
            }
        }

        return 0;
    }

    private static int[] getNeighbors(int i, int[][] graph) {
        return graph[i];
    }

    private static String toString(int visited) {
        return String.format("%16s", Integer.toBinaryString(visited)).replace(' ', '0');
    }
}
