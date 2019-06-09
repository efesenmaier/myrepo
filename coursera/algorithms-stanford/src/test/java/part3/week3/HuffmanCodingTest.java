package part3.week3;

import com.google.common.collect.ImmutableMap;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Scanner;

@Test
public class HuffmanCodingTest {

    public void test() {
        HuffmanCoding huffmanCoding = new HuffmanCoding();
        for (int i = 1; i <= 4; ++i) {
            huffmanCoding.addSymbol(Integer.toString(i), 1);
        }

        huffmanCoding.run();

        Assert.assertEquals(huffmanCoding.symbolLength, ImmutableMap.builder().put("1", 2).put("2", 2).put("3", 2).put("4", 2).build());
    }

    public void moreComplexTest() {
        HuffmanCoding huffmanCoding = new HuffmanCoding();
        huffmanCoding.addSymbol("A", 3);
        huffmanCoding.addSymbol("B", 2);
        huffmanCoding.addSymbol("C", 6);
        huffmanCoding.addSymbol("D", 8);
        huffmanCoding.addSymbol("E", 2);
        huffmanCoding.addSymbol("F", 6);
        huffmanCoding.run();
        System.out.println(huffmanCoding.symbolLength);
        Assert.assertEquals(huffmanCoding.symbolLength, ImmutableMap.builder().put("A", 3).put("B", 4).put("C", 2).put("D", 2).put("E", 4).put("F", 2).build());
    }

    public void testAssignmentHuffmanData() {
        HuffmanCoding huffmanCoding = new HuffmanCoding();

        long i = 1;
        try (Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream("huffman.txt"))) {
            // Ignore # of symbols
            scanner.nextLine();

            while (scanner.hasNextLong()) {
                Long weight = scanner.nextLong();
                huffmanCoding.addSymbol(Long.toString(i), weight);
                ++i;
            }
        }

        huffmanCoding.run();

        int maxLengthEncoding = huffmanCoding.symbolLength.values().stream()
                .max(Integer::compare).get();
        System.out.println("Max encoding length: " + maxLengthEncoding);
        Assert.assertEquals(maxLengthEncoding, 19);

        int minLengthEncoding = huffmanCoding.symbolLength.values().stream()
                .min(Integer::compare).get();
        System.out.println("Min encoding length: " + minLengthEncoding);
        Assert.assertEquals(minLengthEncoding, 9);
    }

}
