public class CountingValleys {
    // Complete the countingValleys function below.
    static int countingValleys(int n, String s) {
        int height = 0; // sea level
        int numValleys = 0;
        for (int i = 0; i < n && i < s.length(); ++i) {
            char c = s.charAt(i);

            if (c == 'D') {
                --height;
            } else if (c == 'U') {
                ++height;
                if (height == 0) {
                    ++numValleys;
                }
            } else {
                System.out.println("Unrecognized char: " + c);
                assert false;
            }
        }

        return numValleys;
    }
}
