package sorting;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class HandOfStraights {
    public static boolean isNStraightHand(int[] hand, int W) {
        if (hand == null || W < 1 || hand.length < W || hand.length % W != 0) {
            return false;
        }

        TreeMap<Integer, Integer> cardCount = new TreeMap<>();

        // Count the cards of each value
        for (int i = 0; i < hand.length; ++i) {
            cardCount.put(hand[i], cardCount.getOrDefault(hand[i], 0) + 1);
        }

        // Iterate across the cards, making a hand if possible starting with the lowest card
        // Otherwise return false
        Set<Map.Entry<Integer, Integer>> entrySet = cardCount.entrySet();
        Iterator<Map.Entry<Integer,Integer>> i = entrySet.iterator();
        Map.Entry<Integer,Integer> entry = null;
        while (i.hasNext()) {
            if (entry == null || entry.getValue() == 0) {
                entry = i.next();
            }
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            if (value == 0) {
                continue;
            }

            cardCount.put(key, value-1);

            for (int j = 1; j < W; ++j) {
                ++key;
                value = cardCount.getOrDefault(key, -1);
                if (value <= 0) {
                    return false;
                }
                cardCount.put(key, value-1);
            }
        }

        return true;
    }
}
