package general;

import instruments.Cable;
import instruments.Fork;
import org.apache.log4j.Logger;
import robots.GentlemanRobot;
import robots.GreedyRobot;
import robots.RandRobot;
import robots.abs.Robot;

public class Main {
    //settings in src/main/resources/log4j.properties
    public static Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {
        Robot[] robots = new Robot[6];

        init(robots, args);

        //Starting threads
        for (Robot robot : robots) {
            robot.start();
        }

        //Main thread works until all robots will be stopped or will have 100%
        int k = 2;
        while(true){
            if(checkEnd(robots)){
                for (Robot robot : robots) {
                    if (!robot.isStop()) {
                        robot.setStopMe();
                    }
                }
                break;
            }
            currentState(robots);
            for (Robot robot : robots) {
                robot.dischargeRobot();
            }
            Thread.sleep(500);
            currentState(robots);
            Thread.sleep(500);

        }

        System.out.println("Results:");
        System.out.println("#1\t#2\t#3\t#4\t#5\t#6");
        System.out.println(robots[0].getCharge() + "\t" + robots[1].getCharge() + "\t" + robots[2].getCharge() + "\t"
                + robots[3].getCharge() + "\t" + robots[4].getCharge() + "\t" + robots[5].getCharge());
    }

    public static void init(Robot[] robots, String[] args) throws Exception {

        Fork fork12 = new Fork();   //Fork for robot-1 and robot-2
        Fork fork34 = new Fork();   //Fork for robot-3 and robot-4
        Fork fork56 = new Fork();   //Fork for robot-5 and robot-6

        Cable cable23 = new Cable();    //Cable for robot-2 and robot-3
        Cable cable45 = new Cable();    //Cable for robot-4 and robot-5
        Cable cable61 = new Cable();    //Cable for robot-6 and robot-1

        //Check incoming args and create robots with their strategy
        for (int i = 0; i < 6; i++) {
            int n = Integer.parseInt(args[i]);
            switch (n){
                case 1:
                    robots[i] = new RandRobot(i);
                    break;
                case 2:
                    robots[i] = new GreedyRobot(i);
                    break;
                case 3:
                    robots[i] = new GentlemanRobot(i);
                    break;
                default:
                    log.error("\t\tWrong incoming argument: " + args[i]);
                    throw new Exception("The parameter is incorrect. robots.abs.Robot isn't ready!");
            }
        }
        //Set neighbors for gentleman robot
        for (int i = 0; i < 6; i++) {
            int n = Integer.parseInt(args[i]);
            if (n == 3){
                robots[i].setNeighbors(robots);
            }
        }

        //1 robot and his fork and cable which it can takeTools
        robots[0].setFork(fork12);
        robots[0].setCable(cable61);
        //2 robot and his fork and cable which it can takeTools
        robots[1].setFork(fork12);
        robots[1].setCable(cable23);
        //3 robot and his fork and cable which it can takeTools
        robots[2].setFork(fork34);
        robots[2].setCable(cable23);
        //4 robot and his fork and cable which it can takeTools
        robots[3].setFork(fork34);
        robots[3].setCable(cable45);
        //5 robot and his fork and cable which it can takeTools
        robots[4].setFork(fork56);
        robots[4].setCable(cable45);
        //6 robot and his fork and cable which it can takeTools
        robots[5].setFork(fork56);
        robots[5].setCable(cable61);
    }

    public static boolean checkEnd(Robot[] robots){
        return (robots[0].getCharge() == 100 || robots[0].isStop()) &&
                (robots[1].getCharge() == 100 || robots[1].isStop()) &&
                (robots[2].getCharge() == 100 || robots[2].isStop()) &&
                (robots[3].getCharge() == 100 || robots[3].isStop()) &&
                (robots[4].getCharge() == 100 || robots[4].isStop()) &&
                (robots[5].getCharge() == 100 || robots[5].isStop());
    }

    public static void currentState(Robot[] robots){
        System.out.println("robot-" + robots[0].getRobotId() + "    cable:" + robots[0].isHaveCable() + "   fork:" + robots[0].isHaveFork());
        System.out.println("robot-" + robots[1].getRobotId() + "    cable:" + robots[1].isHaveCable() + "   fork:" + robots[1].isHaveFork());
        System.out.println("robot-" + robots[2].getRobotId() + "    cable:" + robots[2].isHaveCable() + "   fork:" + robots[2].isHaveFork());
        System.out.println("robot-" + robots[3].getRobotId() + "    cable:" + robots[3].isHaveCable() + "   fork:" + robots[3].isHaveFork());
        System.out.println("robot-" + robots[4].getRobotId() + "    cable:" + robots[4].isHaveCable() + "   fork:" + robots[4].isHaveFork());
        System.out.println("robot-" + robots[5].getRobotId() + "    cable:" + robots[5].isHaveCable() + "   fork:" + robots[5].isHaveFork());
        System.out.println(robots[0].getCharge() + " " + robots[1].getCharge() + " " + robots[2].getCharge() + " "
                + robots[3].getCharge() + " " + robots[4].getCharge() + " " + robots[5].getCharge());
        System.out.println();
    }

}
