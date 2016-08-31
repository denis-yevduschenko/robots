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
                takeCable();
            }
            if (getFork().isFree()){
                takeFork();
            }
            if (isHaveFork() && isHaveCable()){
                return true;
            }
        }else{ //if charge > 60
            if (isHaveCable()){
                setCableFree();
            }
            if (isHaveFork()){
                setForkFree();
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
            log.info("\t\tRobot-"+ getRobotId() + " increased the charge = " + getCharge());
            sleep(500);
        }
        setForkFree();
        setCableFree();
    }

    @Override
    public void setNeighbors(Robot[] robots) {
    }
}
