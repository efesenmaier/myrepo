import java.util.HashMap;

public class RansomNote {
    static void checkMagazine(String[] magazine, String[] note) {
        if (containsWords(magazine, note)) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }

    static boolean containsWords(String[] magazine, String[] note) {
        HashMap<String, Integer> magazineWordCount = new HashMap<>();
        for (int i = 0 ; i < magazine.length; ++i) {
            String word = magazine[i];
            magazineWordCount.put(word, magazineWordCount.getOrDefault(word, 0) + 1);
        }

        for (int i = 0; i < note.length; ++i) {
            String word = note[i];
            if (magazineWordCount.getOrDefault(word, 0) > 0) {
                magazineWordCount.put(word, magazineWordCount.get(word) - 1);
            } else {
                return false;
            }
        }

        return true;
    }
}
