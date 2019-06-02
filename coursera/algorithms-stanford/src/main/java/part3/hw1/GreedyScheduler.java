package part3.hw1;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class GreedyScheduler {
    public static class Job {
        public int weight;
        public int length;
        public int id;
        public int diffScore;
        public double ratioScore;

        public Job(int id, int weight, int length) {
            this.id = id;
            this.weight = weight;
            this.length = length;
            this.diffScore = weight - length;
            this.ratioScore = (double)weight/(double)length;
        }

        public int diffScore() {
            return diffScore;
        }

        public double ratioScore() {
            return ratioScore;
        }
    }


    public static class DifferenceComparator implements Comparator<Job> {
        @Override
        public int compare(Job o1, Job o2) {
            int score = o1.diffScore();
            int otherScore = o2.diffScore();
            int weight = o1.weight;
            int otherWeight = o2.weight;
            if (score > otherScore) {
                return -1;
            } else if (score < otherScore) {
                return 1;
            } else {
                if (weight > otherWeight) {
                    return -1;
                } else if (weight < otherWeight) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }

    public static class RatioComparator implements Comparator<Job> {
        @Override
        public int compare(Job o1, Job o2) {
            double score = o1.ratioScore();
            double otherScore = o2.ratioScore();
            if (score > otherScore) {
                return -1;
            } else if (score < otherScore) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public static long scheduleJobs(List<Job> jobs, Comparator<Job> comparator) {
        long totalCompletionTime = 0;
        PriorityQueue<Job> priorityQueue = new PriorityQueue<>(comparator);

        for (Job job : jobs) {
            priorityQueue.add(job);
        }

        long completionTime = 0;
        while (!priorityQueue.isEmpty()) {
            Job job = priorityQueue.poll();

            completionTime += job.length;
            long weightedCompletionTime = job.weight * completionTime;
            totalCompletionTime += weightedCompletionTime;
        }

        return totalCompletionTime;
    }
}
