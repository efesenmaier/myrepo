import java.util.HashMap;
import java.util.Map;

public class SockMerchant {

    // Complete the sockMerchant function below.
    static int sockMerchant(int n, int[] ar) {
        Map<Integer, Integer> socksByColor = new HashMap<>();

        int numPairs = 0;
        for (int i = 0; i < ar.length && i < n; ++i) {
            int sockCount = socksByColor.getOrDefault(ar[i], 0) + 1;
            if (sockCount == 2) {
                numPairs++;
                socksByColor.put(ar[i], 0);
            } else {
                socksByColor.put(ar[i], sockCount);
            }
        }
        return numPairs;
    }
}
