package robots.abs;

import instruments.Cable;
import instruments.Fork;
import org.apache.log4j.Logger;

public abstract class Robot extends Thread{
    // settings in src/main/resources/log4j.properties
    public static Logger log = Logger.getLogger(Robot.class.getName());

    private int charge = 50;
    private int robotId;
    private long startTime = 1;
    private boolean haveCable = false;
    private boolean haveFork = false;

    private Cable cable;
    private Fork fork;
    private volatile boolean stopMe = false;



    public Robot() {

    }

    public Robot(int robotId) {
        this.robotId = robotId;
        this.setName("Robot-"+robotId);
        log.info("\t\tRobot-"+ robotId + " was create.");
    }

    // Change charge every 1 second (-10%)
    public void dischargeRobot() {
        if (stopMe){
            return;
        }
        if (charge == 0){
            if (haveFork){
                fork.setFree(true);
                haveFork = false;
                log.info("\t\tRobot-"+ getRobotId() + " have fork = " + isHaveFork());
            }
            if (haveCable){
                cable.setFree(true);
                haveCable = false;
                log.info("\t\tRobot-"+ getRobotId() + " have cable = " + isHaveCable());
            }
            stopMe = true;
            log.info("\t\tRobot-"+ getRobotId() + " stopped");
            return;
        }
        synchronized (this) {
            charge -= 10;
        }
        log.info("\t\tRobot-"+ robotId + " reduced the charge = " + getCharge());

    }

    @Override
    public void run() {
        while (!stopMe){
            try {
                if (takeTools()){
                    chargingRobot();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        getFork().setFree(true);
        this.setHaveFork(false);
        getCable().setFree(true);
        this.setHaveCable(false);
    }
    // Can robot take instruments or not?
    public abstract boolean takeTools() throws InterruptedException;
    // Method increase charge
    public abstract void chargingRobot() throws InterruptedException;

    public abstract void setNeighbors(Robot[] robots);

    public boolean isStop() {
        return stopMe;
    }

    public boolean isHaveFork() {
        return haveFork;
    }

    public void setHaveFork(boolean haveFork) {
        this.haveFork = haveFork;
    }

    public boolean isHaveCable() {
        return haveCable;
    }

    public void setHaveCable(boolean haveCable) {
        this.haveCable = haveCable;
    }

    public Fork getFork() {
        return fork;
    }

    public void setFork(Fork fork) {
        this.fork = fork;
    }

    public Cable getCable() {
        return cable;
    }

    public void setCable(Cable cable) {
        this.cable = cable;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public void setStopMe() {
        this.stopMe = true;
        log.info("\t\tRobot-"+ robotId + " stopped.");
    }

    public int getCharge() {
        return charge;
    }

    public int getRobotId() {
        return robotId;
    }
}
