import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Test
public class SolutionTest {

    @DataProvider
    private Object[][] testData() {
        return new Object[][] {
                {"DEPEND TELNET TCPIP NETCARD\nDEPEND TCPIP NETCARD\nDEPEND NETCARD TCPIP\nDEPEND DNS TCPIP NETCARD\nDEPEND BROWSER TCPIP HTML\nINSTALL NETCARD\nINSTALL TELNET\nINSTALL foo\nREMOVE NETCARD\nINSTALL BROWSER\nINSTALL DNS\nLIST\nREMOVE TELNET\nREMOVE NETCARD\nREMOVE DNS\nREMOVE NETCARD\nINSTALL NETCARD\nREMOVE TCPIP\nREMOVE BROWSER\nREMOVE TCPIP\nLIST\n"},
        };
    }

    @Test(dataProvider = "testData")
    public void test(String input) {

        String[] lines = parse(input);

        Solution.doIt(lines);
    }

    String[] parse(String input) {
        Scanner scanner = new Scanner(input);
        List<String> lines = new ArrayList<>();

        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        String[] linesArr = new String[lines.size()];

        lines.toArray(linesArr);
        return linesArr;
    }
}
