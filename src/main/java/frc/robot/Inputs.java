package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.PivotConstants;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class Inputs {
	
    private static final XboxController driver = new XboxController(OperatorConstants.kDriverControllerPort);
	private static final CommandXboxController driverCmd = new CommandXboxController(OperatorConstants.kDriverControllerPort);
    private static final XboxController operator = new XboxController(OperatorConstants.kOperatorControllerPort);
	private static final CommandXboxController operatorCmd = new CommandXboxController(OperatorConstants.kOperatorControllerPort);
    
	
	public static double getTranslationX(){
		// return 0.0;
		double speed = driver.getLeftX();
		if(Math.abs(speed) < .05){
			speed = 0;
		}

		if(driver.getRightBumper()){
			speed *= .5;
		}else{
			speed *= .75;
		}
        return speed;
    }
    public static double getTranslationY(){
		// return 0.0;
		double speed = driver.getLeftY();
		if(Math.abs(speed) < .05){
			speed = 0;
		}
		if(driver.getRightBumper()){
			speed *= .5;
		}else{
			speed *= .75;
		}
        return speed;
    }
    public static double getRotation(){
        // return	 0.0;
		double speed = driver.getRightX();
		if(Math.abs(speed) < .1){
			speed = 0;
		}
		if(driver.getRightBumper()){
			speed *= .25;
		}else{
			speed *= .5;
		}
        return speed;
    }

	public static Trigger getBalanceButton() {
		return driverCmd.b();
	}
	public static Trigger getResetGyroButton() {
		return driverCmd.start();
	}

    //Arm
    public static double getExtension(){
        //right trigger goes out, left trigger goes in
        // return (driver.getRightTriggerAxis()-driver.getLeftTriggerAxis());
		return 0.0;
	}

	//Pivot
	public static double getPivotPower() {
		var speed = -operator.getLeftY();
		if(Math.abs(speed)<.3){
			speed = 0;
		}
		return speed;
	}
	public static double getPivotPosition() {
		double pos = -operator.getLeftY(); // [-1, 1]
		pos = pos + 1; // [0, 2]
		pos = pos/2.0; // [0, 1]
		pos = pos * (PivotConstants.kMaxAngle-PivotConstants.kMinAngle); // [0, (kMaxAngle-kMinAngle)]
		pos = pos + PivotConstants.kMinAngle; // [kMinAngle, kMaxAngle]
		return pos;
	}

	/**
	 * 
	 * @return the button that switches to position control on the pivot
	 */
	public static Trigger getPivotPos(){
		return operatorCmd.a();
	}

	/**
	 * 
	 * @return the button that switches to position control on the pivot
	 */
	public static Trigger getPivotPwr(){
		return operatorCmd.b();
	}
/**
 * Says hello to ankur for being such a good programmer :)
 */
	public void hello() {
		System.out.println("Hello Ankur! How is your day?");
	}

	public static Trigger getArmConeLow(){
		return driverCmd.pov(0);
	}
	public static Trigger getArmConeMid(){
		return driverCmd.pov(90);
	}
	public static Trigger getArmConeHigh(){
		return driverCmd.pov(180);
	}
	public static Trigger getArmCubeLow(){
		return driverCmd.pov(270);
	}
	public static Trigger getArmCubeMid(){
		return driverCmd.x();
	}
	public static Trigger getArmCubeHigh(){
		return driverCmd.y();
	}
	public static Trigger getArmDoubleSubStation(){
		return operatorCmd.a();
	}
	
	public static Trigger getCloseGrabber(){
		return operatorCmd.b();
	}
	public static Trigger getOpenGrabber(){
		return operatorCmd.x();
	}
	public static Trigger getOffGrabber(){
		return operatorCmd.y();
	}
    public static boolean getOverrideButton() {
        return operator.getBackButton();
    }
	public static boolean getSwerveReset() {
		return driver.getStartButton();
	}
}
