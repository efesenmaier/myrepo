package part3.hw2;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class MaxKClustersWithMinHammingDistance {
    public static final int MIN_HAMMING_DIST = 3;

    Map<Integer, Set<Long>> nodesByCardinality = new HashMap<>();
    UnionFind unionFind = new UnionFind();

    public static List<Long> toBitsets(List<String> bitStrings) {
        return bitStrings.stream()
                .map(s -> fromString(s))
                .filter(bitSet -> bitSet != null)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public long find(Collection<Long> nodes) {
        for (Iterator<Long> i = nodes.iterator(); i.hasNext();) {
            Long node = i.next();
            addToCluster(node);
        }
        return unionFind.numConnectedComponents;
    }

    private void addToCluster(Long a) {
        int cardinalityOfA = Long.bitCount(a);
        if (!unionFind.add(a.toString())) {
            return;
        }

        for (int cardinality = cardinalityOfA - 2; cardinality <= cardinalityOfA + 2; ++cardinality) {
            Set<Long> nearestNeighbors = nodesByCardinality.get(cardinality);
            if (nearestNeighbors != null) {
                for (Long b : nearestNeighbors) {
                    if (hammingDistance(a, b) < MIN_HAMMING_DIST) {
                        unionFind.union(a.toString(), b.toString());
                    }
                }
            }
        }

        if (nodesByCardinality.containsKey(cardinalityOfA)) {
            nodesByCardinality.get(cardinalityOfA).add(a);
        } else {
            Set<Long> nodes = new HashSet<>();
            nodes.add(a);
            nodesByCardinality.put(cardinalityOfA, nodes);
        }
    }

    public static int hammingDistance(long a, long b) {
        long xor = a ^ b;
        return Long.bitCount(xor);
    }

    public static Long fromString(String bitString) {
        if (bitString != null && !bitString.isEmpty()) {
            try (Scanner scanner = new Scanner(bitString)) {
                BitSet bitSet = new BitSet();
                for (int i = 0; scanner.hasNextInt(); ++i) {
                    int val = scanner.nextInt();
                    if (val == 1) {
                        bitSet.set(i);
                    }
                }
                long[] data = bitSet.toLongArray();
                if (data.length == 0) {
                    return 0L;
                } else {
                    assert data.length == 1;
                    return data[0];
                }
            }
        }
        return null;
    }
}
