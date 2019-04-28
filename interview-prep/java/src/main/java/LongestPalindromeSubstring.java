public class LongestPalindromeSubstring {

    public static String find(String string) {
        char[] str = string.toCharArray();
        StringExtent max = null;
        for (int i = 0; i < str.length; ++i) {
            StringExtent extent = growPalindrome(str, new StringExtent(i, i));
            if (max == null || extent.size() > max.size()) {
                max = extent;
            }

            if (i < str.length-1 && str[i] == str[i+1]) {
                extent = growPalindrome(str, new StringExtent(i, i+1));
                if (max == null || extent.size() > max.size()) {
                    max = extent;
                }
            }
        }
        return max == null ? "" : string.substring(max.left, max.right+1);
    }

    public static StringExtent growPalindrome(char[] str, StringExtent extent) {
        while (extent.left > 0 &&
                extent.right < str.length - 1 &&
                str[extent.left-1] == str[extent.right+1]) {
            extent.left--;
            extent.right++;
        }

        return extent;
    }

    public static class StringExtent {
        public int left;
        public int right;

        public StringExtent(int left, int right) {
            this.left = left;
            this.right = right;
        }

        public int size() {
            return right - left + 1;
        }

        public StringExtent grow() {
            return new StringExtent(left-1, right+1);
        }
    }
}
