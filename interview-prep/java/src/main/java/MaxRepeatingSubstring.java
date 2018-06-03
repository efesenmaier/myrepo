import org.testng.collections.Maps;

import java.util.Map;

public class MaxRepeatingSubstring {
    static public int find(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        Map<Integer, CharAndIndex> seen = Maps.newHashMap();
        int remove = 0;
        int max = 1;
        int length = 1;
        int c = s.codePointAt(0);
        seen.put(c, new CharAndIndex(c, 0));
        for (int i = 1; i < s.length(); ++i) {
            c = s.codePointAt(i);
            if (seen.containsKey(c)) {
                if (length > max) {
                    max = length;
                }
                CharAndIndex dupe = seen.get(c);
                while (remove <= dupe.index) {
                    seen.remove(s.codePointAt(remove));
                    ++remove;
                }
                seen.put(c, new CharAndIndex(c, i));
                length = i - dupe.index;
            } else {
                seen.put(c, new CharAndIndex(c, i));
                ++length;
            }
        }
        if (length > max) {
            max = length;
        }
        return max;
    }
    static class CharAndIndex {
        final int c;
        final int index;
        CharAndIndex(int c, int index) {
            this.c = c;
            this.index = index;
        }
    }
}
