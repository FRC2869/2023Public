package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Constants.PivotConstants;
import frc.robot.subsystems.PivotSubsystem;

public class ArmConeMid extends CommandBase{
	// private ArmSubsystem arm;
	private PivotSubsystem pivot;
	// private int armCounter;
	private int pivotCounter;

	public ArmConeMid(){
		// arm = ArmSubsystem.getInstance();
		pivot = PivotSubsystem.getInstance();

		// addRequirements(arm);
		addRequirements(pivot);
	}

	@Override
	public void execute(){
		// arm.setPositionControl(true);
		// arm.position(ArmConstants.Extension.midConeDistance);
		pivot.setPositionControl(true);
		pivot.position(PivotConstants.midConeAngle);
	}

	@Override
	public boolean isFinished(){
		
		// boolean armDone = Math.abs(arm.getPosition()-ArmConstants.Extension.midConeDistance) < ArmConstants.Extension.tolerance;
		boolean pivotDone = Math.abs(pivot.getAngle()-PivotConstants.midConeAngle) < PivotConstants.tolerance;

		// if(armDone){
		// 	armCounter++;
		// }else{
		// 	armCounter=0;
		// }
		if(pivotDone){
			pivotCounter++;
		}else{
			pivotCounter=0;
		}
		System.out.println(pivotCounter);
		if(pivotCounter>Constants.pidTimer){
			pivot.setPositionControl(false);
			return true;
		}else{
			return false;
		}
		// return (arm.getPosition()==ArmConstants.Extension.lowConeDistance) && (pivot.getAngle() == PivotConstants.lowConeAngle);
	}
}
