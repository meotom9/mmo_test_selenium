package mmo.project.task;

import mmo.project.dao.MmoConfigDAO;
import mmo.project.dao.MmoUserAgentDAO;
import mmo.project.function.UtilsFunction;
import mmo.project.model.MmoConfig;
import mmo.project.model.MmoUserAgent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Vector;
import java.util.stream.Collectors;


public class TaskSyncDB implements Runnable {
    private static final Logger logger = LogManager.getLogger(TaskSyncDB.class);

    private static Vector<MmoConfig> l_configs = new Vector<MmoConfig>();
    private static Vector<MmoUserAgent> l_users_mobile = new Vector<MmoUserAgent>();
    private static Vector<MmoUserAgent> l_users_pc = new Vector<MmoUserAgent>();

    public static int num_flag = 0;

    public static synchronized Vector<MmoConfig> getConfigs() {
        return l_configs;
    }

    public synchronized void setConfigs(Vector<MmoConfig> l_configs) {
        this.l_configs = l_configs;
    }

    public static synchronized Vector<MmoUserAgent> getUsersMobile() {
        return l_users_mobile;
    }

    public static synchronized MmoUserAgent getRandomUsersMobile() {
        return l_users_mobile.get(UtilsFunction.randomRange(0, l_users_mobile.size() - 1));
    }

    public synchronized void setUsersMobile(Vector<MmoUserAgent> l_users_mobile) {
        this.l_users_mobile = l_users_mobile;
    }

    public static synchronized Vector<MmoUserAgent> getUsersPC() {
        return l_users_pc;
    }

    public static synchronized MmoUserAgent getRandomUsersPC() {
        return l_users_pc.get(UtilsFunction.randomRange(0, l_users_pc.size() - 1));
    }

    public synchronized void setUserPC(Vector<MmoUserAgent> l_users_pc) {
        this.l_users_pc = l_users_pc;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String ip_db = System.getenv("DB_IP_ADDRESS");
                String port = System.getenv("DB_PORT");
                if(ip_db != null && port != null) {
                    if(ip_db.length()>0 && port.length()>0) {

                    }
                }

                logger.info("TaskSyncDB started");
                Vector<MmoConfig> v_configs = new MmoConfigDAO().getAllConfigs();
                logger.info("Done get active configs with: " + v_configs.size() + " elements");
                Vector<MmoConfig> v_configs_2 = new Vector<MmoConfig>();
                for (MmoConfig c : v_configs) {
//                    logger.info("Config: " + c.toString());
                    if (c.getStatus() == 1) {
                        for (int i = 0; i < c.getCountT(); i++) {
                            v_configs_2.add(c);
                        }
                    }
                }
                logger.info("Done get all count configs with: " + v_configs_2.size() + " elements");

                Vector<MmoUserAgent> v_users_mb = new Vector<MmoUserAgent>(new MmoUserAgentDAO().getAllUsers()
                        .stream().filter(userAgent -> "mobile".equals(userAgent.getType()))
                        .collect(Collectors.toList()));
//                for (MmoUserAgent u : v_users_mb) {
//                    logger.info("User Mobile: " + u.toString());
//                }
                Vector<MmoUserAgent> v_users_pc = new Vector<MmoUserAgent>(new MmoUserAgentDAO().getAllUsers()
                        .stream().filter(userAgent -> "pc".equals(userAgent.getType()))
                        .collect(Collectors.toList()));
//                for (MmoUserAgent u : v_users_pc) {
//                    logger.info("User PC: " + u.toString());
//                }


                setConfigs(v_configs_2);
                setUsersMobile(v_users_mb);
                setUserPC(v_users_pc);
                logger.info("TaskSyncDB finished: " + Thread.currentThread().getName());

                Thread.sleep(300000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized MmoConfig getCurrentConfig() {
        while (l_configs.isEmpty()) {
            try {
                Thread.sleep(1000);
                System.out.println("Wait for DB sync...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (num_flag >= l_configs.size()) {
            num_flag = 0;
        }
        MmoConfig mc = l_configs.get(num_flag);
        num_flag++;
        return mc;
    }
}
