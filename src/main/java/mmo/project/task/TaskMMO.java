package mmo.project.task;

import mmo.project.function.GoogleFunctionUI;
import mmo.project.function.UtilsFunction;
import mmo.project.model.MmoConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TaskMMO implements Runnable {
    private static final Logger logger = LogManager.getLogger(TaskMMO.class);

    @Override
    public void run() {
        try {
            while (true) {
                logger.info("TaskMMO started");
                MmoConfig mc = TaskSyncDB.getCurrentConfig();
                System.out.println("Run config: " + mc.toString());
                int p_userAgent = mc.getPerMobile();
                String user_agent = "";
                if(p_userAgent < UtilsFunction.randomRange(1, 100)) {
                    user_agent = TaskSyncDB.getRandomUsersMobile().getUserAgent();
                }else{
                    user_agent = TaskSyncDB.getRandomUsersPC().getUserAgent();
                }
//                boolean flag = new GoogleFunctionUI().requestAPI(mc.getTerm(), mc.getSiteUrl(), user_agent, mc.getActionsMain(), mc.getActionsAds());
                GoogleFunctionUI.getWindowSize();
                boolean flag = GoogleFunctionUI.requestAPI(mc.getTerm(), mc.getSiteUrl(), user_agent, mc.getActionsMain(), mc.getActionsAds());
//                new TestChromeDriver().requestAPI(mc.getTerm(), mc.getSiteUrl());
                if(flag) {
                    System.out.println("GoogleFunction run successfully");
                }else{
                    System.out.println("GoogleFunction run failed");
                }
                logger.info("Task finished: " + Thread.currentThread().getName());
                Thread.sleep(mc.getIntervalS()*1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
