package com.movie.recommendation.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPoolCalculator {
    // https://stackoverflow.com/questions/43874079/how-to-decide-on-the-threadpooltaskexecutor-pools-and-queue-sizes

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolCalculator.class);

    public static void main(String[] args) {
        int availableCores = Runtime.getRuntime().availableProcessors();
        int taskCount = 100; // Set the number of tasks based on your workload (100 is arbitrary):
        // low-end: 10-20 tasks
        // mid-range: 100-200 tasks
        // high-end: 1000+ tasks

        int corePoolSize = calculateCorePoolSize(availableCores);
        int maxPoolSize = calculateMaxPoolSize(availableCores);
        int queueCapacity = calculateQueueCapacity(availableCores, taskCount);
        logger.info("Optimal corePoolSize: {}", corePoolSize);
        logger.info("Optimal maxPoolSize: {}", maxPoolSize);
        logger.info("Optimal queueCapacity: {}", queueCapacity);
    }

    /** Set corePoolSize as the number of available CPU cores
     *
     * @param availableCores - number of available CPU cores
     * @return corePoolSize - number of available CPU cores
     */
    static int calculateCorePoolSize(int availableCores) {
        return availableCores;
    }

    /** Set ``maxPoolSize`` as a multiple of the number of available CPU cores
     * Adjust the multiplier as needed based on the task characteristics:
     * <ul>
     *     <li>Low-End: 2-3 (tasks are long-running or block often)</li>
     *     <li>Mid-Range: 5-10 (tasks are short-lived and non-blocking)</li>
     *     <li>High-End: 10-20 (tasks are short-lived and non-blocking)</li>
     * </ul>
     *
     * @param availableCores - number of available CPU cores
     * @return maxPoolSize - number of available CPU cores
     */
    static int calculateMaxPoolSize(int availableCores) {
        int multiplier = 7; // Adjust this multiplier as needed
        return availableCores * multiplier;
    }

    /** Set queueCapacity based on the number of tasks and available CPU cores
     *  Adjust the scaling factor as needed based on the task characteristics (once again)
     *  <ul>
     *      <li>Low-End: 1.5-2.0</li>
     *      <li>Mid-Range: 2.5-3.0</li>
     *      <li>High-End: 5.0-10.0</li>
     *  </ul>
     *
     * @param availableCores - number of available CPU cores
     * @param taskCount - number of tasks
     * @return queueCapacity - number of available CPU cores
     */
    static int calculateQueueCapacity(int availableCores, int taskCount) {
        double scalingFactor = 3.0; // Adjust this scaling factor as needed
        double queueCapacity = (double) taskCount / (double) availableCores * scalingFactor;
        return Math.max((int) queueCapacity, 1);
    }
}
