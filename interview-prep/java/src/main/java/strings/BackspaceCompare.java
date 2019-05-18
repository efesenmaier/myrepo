package strings;

public class BackspaceCompare {
    public static boolean backspaceCompare(String S, String T) {
        String a = removeDeletes(S);
        String b = removeDeletes(T);
        return a.equals(b);
    }

    private static String removeDeletes(String s) {
        StringBuilder a = new StringBuilder();
        if (s != null) {
            for (int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                if (c == '#') {
                    if (a.length() != 0) {
                        a.setLength(a.length() - 1);
                    }
                } else {
                    a.append(c);
                }
            }
        }
        return a.toString();
    }
}
