package robots;

import org.apache.log4j.Logger;
import robots.abs.Robot;

public class GreedyRobot extends Robot {
    // settings in src/main/resources/log4j.properties
    public static Logger log = Logger.getLogger(GreedyRobot.class.getName());

    public GreedyRobot(int robotId) {
        super(robotId);
        System.out.println("GreedyRobot is ready");
    }

    @Override
    public boolean takeTools() throws InterruptedException {
        if (getCharge() < 60){ //robots.abs.Robot takes instruments if charge < 60
            if (getCable().isFree()){
                getCable().setFree(false);
                setHaveCable(true);
                log.info("\t\tRobot-"+ getRobotId() + " have cable = " + isHaveCable());
            }
            if (getFork().isFree()){
                getFork().setFree(false);
                setHaveFork(true);
                log.info("\t\tRobot-"+ getRobotId() + " have fork = " + isHaveFork());
            }
            if (isHaveFork() && isHaveCable()){
                return true;
            }
        }else{ //if charge > 60
            if (isHaveCable()){
                getCable().setFree(true);
                setHaveCable(false);
                log.info("\t\tRobot-"+ getRobotId() + " have cable = " + isHaveCable());
            }
            if (isHaveFork()){
                getFork().setFree(true);
                setHaveFork(false);
                log.info("\t\tRobot-"+ getRobotId() + " have fork = " + isHaveFork());
            }
            sleep(500);
        }
        return false;
    }

    @Override
    public void chargingRobot() throws InterruptedException {
        while (getCharge() != 100){
            synchronized (this) {
                int tempCharge = getCharge() + 10;
                setCharge(tempCharge);
            }
            log.info("\t\trobots.abs.Robot-"+ getRobotId() + " increased the charge = " + getCharge());
            sleep(500);
        }
        getFork().setFree(true);
        this.setHaveFork(false);
        getCable().setFree(true);
        this.setHaveCable(false);
        log.info("\t\tRobot-"+ getRobotId() + " have cable = " + isHaveCable());
        log.info("\t\tRobot-"+ getRobotId() + " have fork = " + isHaveFork());
    }

    @Override
    public void setNeighbors(Robot[] robots) {
    }
}
