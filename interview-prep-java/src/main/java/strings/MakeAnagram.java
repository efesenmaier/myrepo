package strings;

public class MakeAnagram {
    public static final int NUM_CHARS_IN_ALPHABET = indexOf('z') + 1;

    // Complete the makeAnagram function below.
    static int makeAnagram(String a, String b) {
        int[] aCharCount = countChars(a.toLowerCase());
        int[] bCharCount = countChars(b.toLowerCase());

        return calculateDifferences(aCharCount, bCharCount);
    }

    private static int[] countChars(String a) {
        int[] charCount = new int[NUM_CHARS_IN_ALPHABET];
        for (int i = 0; i < a.length(); ++i) {
            char c = a.charAt(i);
            int idx = indexOf(c);
            charCount[idx]++;
        }
        return charCount;
    }

    private static int calculateDifferences(int[] a, int[] b) {
        int diff = 0;
        for (int i = 0; i < a.length && i < b.length; ++i) {
            diff += Math.abs(a[i] - b[i]);
        }
        return diff;
    }

    private static int indexOf(char c) {
        return c - 'a';
    }
}
