package robots;

import org.apache.log4j.Logger;
import robots.abs.Robot;

import java.util.Random;

public class RandRobot extends Robot {
    private final Random random = new Random();
    // settings in src/main/resources/log4j.properties
    public static Logger log = Logger.getLogger(RandRobot.class.getName());

    public RandRobot(int robotId) {
        super(robotId);
        System.out.println("RandRobot is ready");
    }

    @Override
    public boolean takeTools() throws InterruptedException {
        if (getCharge() != 100) {
            if (getCable().isFree()) {
                getCable().setFree(false);
                setHaveCable(true);
                log.info("\t\tRobot-"+ getRobotId() + " have cable = " + isHaveCable());
            }
            if (getFork().isFree()) {
                getFork().setFree(false);
                setHaveFork(true);
                log.info("\t\tRobot-"+ getRobotId() + " have fork = " + isHaveFork());
            }
        }
        return isHaveFork() && isHaveCable();
    }

    @Override
    public void chargingRobot() throws InterruptedException {
        synchronized (this) {
            int tempCharge = getCharge() + 10;
            setCharge(tempCharge);
        }
        log.info("\t\tRobot-"+ getRobotId() + " increased the charge = " + getCharge());
        sleep(500);
        getFork().setFree(true);
        setHaveFork(false);
        getCable().setFree(true);
        setHaveCable(false);
        log.info("\t\tRobot-"+ getRobotId() + " have cable = " + isHaveCable());
        log.info("\t\tRobot-"+ getRobotId() + " have fork = " + isHaveFork());
        sleep((random.nextInt(200)+100));
    }

    @Override
    public void setNeighbors(Robot[] robots) {
    }
}
