package strings;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SpecialPalindromicSubstrings {

    public static class Substring {
        String original;
        String substring;
        int begin;
        int endExclusive;

        public Substring(String s, int begin, int endExclusive) {
            this.original = s;
            this.begin = begin;
            this.endExclusive = endExclusive;
            this.substring = s.substring(begin, endExclusive);
        }

        public String getSubstring() {
            return substring;
        }

        @Override
        public int hashCode() {
            return Objects.hash(begin, endExclusive);
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Substring)) {
                return false;
            }

            Substring otherSubstring = (Substring)other;
            return begin == otherSubstring.begin &&
                    endExclusive == otherSubstring.endExclusive;
        }

        public String toString() {
            return substring + "[" + begin + "," + endExclusive + "]";
        }
    }

    // Complete the substrCount function below.
    static long substrCount(int n, String s) {
        long count = 0;

        // Find repeating substrings starting at i, using recursive method
        count += countRepeatingSubstringsIterative(s);

        // Iterate over each character
        Set<Substring> substrings = new HashSet<>();
        for (int i = 0; i < s.length(); ++i) {
            // Find palindromic substrings centered at i, using recursive method
            findSpecialPalindromes(s, i, 2, substrings);
        }
        return count + substrings.size();
    }

    /**
     * Finds substrings where all characters except the middle one are the same, e.g. aadaa.
     */
    static void findSpecialPalindromes(String s, int center, int length, Set<Substring> substrings) {
        if (length < 1) {
            return;
        }

        int left = center - length + 1;
        int right = center + length - 1;

        // If either left or right is OOB, then no more palindromes
        if (left < 0 || right >= s.length()) {
            return;
        }

        // If checking 2nd char and it is same as center then it is not a special palindrome
        // (It is counted in repeating substrings instead)
        if (length == 2 && s.charAt(right) == s.charAt(center)) {
            return;
        }

        // If next char does not match previous char (after 2nd char), then not a special palindrome
        if (length > 2 && s.charAt(right) != s.charAt(right-1)) {
            return;
        }

        // All chars must match their opposite
        if (s.charAt(right) != s.charAt(left)) {
            return;
        }

        substrings.add(new Substring(s, left, right+1));
        findSpecialPalindromes(s, center, length + 1, substrings);
    }

    static long countRepeatingSubstringsIterative(String s) {
        int begin = 0;
        int count = 0;

        while (begin < s.length()) {
            int length = 1;
            int end = begin;
            while (end < s.length() && s.charAt(begin) == s.charAt(end)) {
                count += length;
                ++length;
                ++end;
            }
            begin += (length - 1);
        }
        return count;
    }
}
