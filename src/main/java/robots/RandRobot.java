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
                takeCable();
            }
            if (getFork().isFree()) {
                takeFork();
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
        setForkFree();
        setCableFree();
        sleep((random.nextInt(200)+100));
    }

    @Override
    public void setNeighbors(Robot[] robots) {
    }
}
