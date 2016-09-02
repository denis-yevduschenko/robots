import static org.junit.Assert.*;

import instruments.Cable;
import instruments.Fork;
import org.junit.Test;
import robots.Impl.GentlemanRobot;
import robots.Impl.GreedyRobot;
import robots.Impl.RandRobot;
import robots.Robot;

public class TestRobot {
    @Test
    public void testDischargeRobot() {
        Robot robot = new RandRobot(0);

        robot.dischargeRobot();
        robot.dischargeRobot();

        int currentCharge = robot.getCharge();
        assertEquals("Error!", 30, currentCharge);
    }

    @Test
    public void testRandChargingRobot(){
        RandRobot robot = new RandRobot(0);
        Fork fork = new Fork();
        Cable cable = new Cable();

        robot.setFork(fork);
        robot.setCable(cable);

        try {
            robot.chargingRobot(); //First call method chargingRobot()
            robot.chargingRobot(); //Second call method chargingRobot()
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int currentCharge = robot.getCharge();
        assertEquals("Error!", 70, currentCharge);
    }

    @Test
    public void testGreedyChargingRobot(){
        GreedyRobot robot = new GreedyRobot(0);
        Fork fork = new Fork();
        Cable cable = new Cable();

        robot.setFork(fork);
        robot.setCable(cable);

        try {
            robot.chargingRobot(); //Call method chargingRobot(), which contains in method takeTools()
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int currentCharge = robot.getCharge();
        assertEquals("Error!", 100, currentCharge);
    }

    @Test
    public void testGentlemanChargingRobot(){
        Robot[] robots = new Robot[3];
        robots[0] = new RandRobot(0);
        robots[1] = new GentlemanRobot(1);
        robots[2] = new RandRobot(2);

        robots[0].setCharge(40);
        robots[1].setCharge(40);
        robots[2].setCharge(40);

        robots[1].setNeighbors(robots);

        Fork fork = new Fork();
        Cable cable = new Cable();

        robots[1].setFork(fork);
        robots[1].setCable(cable);
        robots[0].setFork(fork);
        robots[2].setCable(cable);

        try {
            if (robots[1].takeTools()){
                robots[1].chargingRobot(); //Call method chargingRobot()
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int currentCharge = robots[1].getCharge();
        assertEquals("Charge error!", 50, currentCharge);
        assertEquals("Truth error!", false, robots[1].isHaveFork());
    }

    @Test
    public void testTakeTools(){
        GreedyRobot robot = new GreedyRobot(0);
        Robot robot1 = new RandRobot(1);

        Fork fork = new Fork();
        Cable cable = new Cable();

        robot.setFork(fork);
        robot.setCable(cable);
        robot1.setCable(cable);
        robot1.setFork(fork);


        boolean test1 = true;
        boolean test2 = true;
        try {
            test1 = robot1.takeTools();
            test2 = robot.takeTools(); //Call method takeTools(), which try to give tools
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals("Error!", true, test1);
        assertEquals("Error!", false, test2);
    }
}
