package robots.Impl;

import org.apache.log4j.Logger;
import robots.Robot;

public class GentlemanRobot extends Robot {
    // settings in src/main/resources/log4j.properties
    public static Logger log = Logger.getLogger(GentlemanRobot.class.getName());

    private Robot neighborFork;
    private Robot neighborCable;

    public GentlemanRobot(int robotId) {
        super(robotId);
        System.out.println("GentlemanRobot is ready");
    }

    @Override
    public void setNeighbors(Robot[] neighbors){
        if (getRobotId() == 0){
            neighborFork = neighbors[1];    //neighbor who competes for a fork id = 1
            neighborCable = neighbors[5];   //neighbor who competes for cable id = 5
        }
        if (getRobotId() == 1){
            neighborFork = neighbors[0];    //neighbor who competes for a fork id = 0
            neighborCable = neighbors[2];   //neighbor who competes for cable id = 2
        }
        if (getRobotId() == 2){
            neighborFork = neighbors[3];    //neighbor who competes for a fork id = 3
            neighborCable = neighbors[1];   //neighbor who competes for cable id = 1
        }
        if (getRobotId() == 3){
            neighborFork = neighbors[2];    //neighbor who competes for a fork id = 2
            neighborCable = neighbors[4];   //neighbor who competes for cable id = 4
        }
        if (getRobotId() == 4){
            neighborFork = neighbors[5];    //neighbor who competes for a fork id = 5
            neighborCable = neighbors[3];   //neighbor who competes for cable id = 3
        }
        if (getRobotId() == 5){
            neighborFork = neighbors[4];    //neighbor who competes for a fork id = 4
            neighborCable = neighbors[1];   //neighbor who competes for cable id = 1
        }
    }

    //Check charges neighbors robots, if charge > neighbors's charge give them fork/cable
    public boolean checkChargeNeighbors() throws InterruptedException {
        if (isHaveFork() && neighborFork.getCharge() < getCharge() && !neighborFork.isStop()){
            setForkFree();
            return true;
        }
        if (isHaveCable() && neighborCable.getCharge() < getCharge() && !neighborFork.isStop()){
            setCableFree();
            return true;
        }
        return false;
    }

    @Override
    public boolean takeTools() throws InterruptedException {
        if (checkChargeNeighbors()){
            sleep(200);
        }else{
            if (getCable().isFree()) {
                takeCable();
            }
            if (getFork().isFree()) {
                takeFork();
            }
            if (isHaveFork() && isHaveCable()) {
                return true;
            }
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
            if (checkChargeNeighbors()){
                sleep(200);
                break;
            }
        }
        if (getCharge() == 100) {
            setCableFree();
            setForkFree();
        }
    }
}
