package mmo.project;


import java.util.Vector;
import java.util.concurrent.*;

import mmo.project.model.MmoConfig;
import mmo.project.model.MmoUserAgent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import mmo.project.task.TaskMMO;
import mmo.project.task.TaskSyncDB;

/**
 * Hello world!
 */
public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public static Vector<MmoConfig> l_configs;
    public static Vector<MmoUserAgent> l_users;

    public App() throws InterruptedException {
        ExecutorService executor = null;

        // Wait for the task to complete or timeout
        try {
            executor = Executors.newFixedThreadPool(2);
            Future<?> f_config = executor.submit(new TaskSyncDB());
//            Thread.sleep(5000);
            Future<?> f_task = executor.submit(new TaskMMO());

            logger.info("Task completed successfully.");
        } catch (Exception e) {
            logger.info("Task encountered an error: " + e.getCause());
        } finally {
            if(executor != null) {
                executor.shutdown(); // Shut down the executor when finished
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        logger.info("Hello World!");
        new App();
    }
}
