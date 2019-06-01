package sorting;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class HandOfStraights {
    public static class MutableInteger {
        private int value;
        public MutableInteger(int value) {
            this.value = value;
        }
        public void set(int value) {
            this.value = value;
        }
        public int intValue() {
            return value;
        }
    }
    public static boolean isNStraightHand(int[] hand, int W) {
        // Sort the hand
        Arrays.sort(hand);

        LinkedHashMap<Integer, MutableInteger> cardCount = new LinkedHashMap<>();

        // Count the cards of each value
        for (int i = 0; i < hand.length;) {
            int value = hand[i];
            int count = 1;
            int j = i+1;
            for (; j < hand.length; ++j) {
                if (hand[j] == hand[i]) {
                    ++count;
                } else {
                    break;
                }
            }
            i = j;
            // Use mutable integer to avoid modifying map after ordering it
            cardCount.put(value, new MutableInteger(count));
        }

        // Iterate across the cards, making a hand if possible starting with the lowest card
        // Otherwise return false
        for (Iterator<Map.Entry<Integer,MutableInteger>> i = cardCount.entrySet().iterator(); i.hasNext();) {
            Map.Entry<Integer, MutableInteger> entry = i.next();
            Integer firstCard = entry.getKey();
            MutableInteger firstCardCount = entry.getValue();

            if (firstCardCount.intValue() > 0) {
                int end = firstCard.intValue() + W;
                for (int nextCard = firstCard.intValue() + 1; nextCard < end; ++nextCard) {
                    MutableInteger nextCardCount = cardCount.getOrDefault(nextCard, new MutableInteger(0));
                    if (nextCardCount.intValue() < firstCardCount.intValue()) {
                        return false;
                    }
                    nextCardCount.set(nextCardCount.intValue() - firstCardCount.intValue());
                }
            }
        }

        return true;
    }
}
