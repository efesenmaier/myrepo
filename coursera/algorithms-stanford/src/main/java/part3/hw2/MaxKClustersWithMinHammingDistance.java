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

    Map<Long, Set<Long>> nodesByByte1 = new HashMap<>();
    Map<Long, Set<Long>> nodesByByte2 = new HashMap<>();
    Map<Long, Set<Long>> nodesByByte3 = new HashMap<>();
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
        if (!unionFind.add(a.toString())) {
            return;
        }
        Set<Long> nearestNodes = new HashSet<>();
        nearestNodes.addAll(nodesByByte1.getOrDefault(getByte1(a), new HashSet<>()));
        nearestNodes.addAll(nodesByByte2.getOrDefault(getByte2(a), new HashSet<>()));
        nearestNodes.addAll(nodesByByte3.getOrDefault(getByte3(a), new HashSet<>()));

        for (Long b : nearestNodes) {
            if (hammingDistance(a, b) < MIN_HAMMING_DIST) {
                unionFind.union(a.toString(), b.toString());
            }
        }

        addToSet(nodesByByte1, getByte1(a), a);
        addToSet(nodesByByte2, getByte2(a), a);
        addToSet(nodesByByte3, getByte3(a), a);
    }

    private void addToSet(Map<Long, Set<Long>> nodeSetByByte, Long byteKey, Long val) {
        if (nodeSetByByte.containsKey(byteKey)) {
            nodeSetByByte.get(byteKey).add(val);
        } else {
            Set<Long> nodes = new HashSet<>();
            nodes.add(val);
            nodeSetByByte.put(byteKey, nodes);
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

    public Long getByte1(Long word) {
        return word & 0x000000FF;
    }

    public Long getByte2(Long word) {
        return word & 0x0000FF00;
    }

    public Long getByte3(Long word) {
        return word & 0x00FF0000;
    }
}
