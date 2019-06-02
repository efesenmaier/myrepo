package part3.hw1;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Test
public class GreedySchedulerTest {

    /**
     * Runs too slow for unit tests.
     */
    @Test
    public void testAssignmentData() {
        String filename = "jobs.txt";
        List<GreedyScheduler.Job> jobs = new ArrayList<>();
        try (Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream(filename))) {
            int numJobs = scanner.nextInt();
            int id = 0;
            while (scanner.hasNextLine()) {
                Scanner lineScanner = new Scanner(scanner.nextLine());
                if (lineScanner.hasNextInt()) {
                    int weight = lineScanner.nextInt();
                    if (lineScanner.hasNextInt()) {
                        int length = lineScanner.nextInt();
                        jobs.add(new GreedyScheduler.Job(id++, weight, length));
                    }
                }
            }
        }

        long completionTime = GreedyScheduler.scheduleJobs(jobs, new GreedyScheduler.DifferenceComparator());
        System.out.println("Completion Time: " + completionTime);
        Assert.assertEquals(completionTime, 69119377652L);

        completionTime = GreedyScheduler.scheduleJobs(jobs, new GreedyScheduler.RatioComparator());
        System.out.println("Completion Time: " + completionTime);
        Assert.assertEquals(completionTime, 67311454237L);
    }
}
