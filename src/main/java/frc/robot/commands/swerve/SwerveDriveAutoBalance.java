package frc.robot.commands.swerve;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DrivetrainSubsystem;

public class SwerveDriveAutoBalance extends CommandBase {
	private DrivetrainSubsystem swerve;

	private int counter = 0;
	public SwerveDriveAutoBalance(){
		swerve = DrivetrainSubsystem.getInstance();
		addRequirements(swerve);
	}

	@Override
	public void execute(){
		var pitch = swerve.getGyroPitch().getDegrees();
		if(pitch>2.5){
			swerve.drive(() -> 0,() ->  -.25, () -> 0);
		}else if(pitch<-2.5){
			swerve.drive(() -> 0	,() ->  .25, () -> 0);
		}
	}

	@Override
	public boolean isFinished(){
		if(Math.abs(swerve.getGyroPitch().getDegrees())<2.5){
			counter++;
		}else{
			counter = 0;
		}

		if (counter > Constants.pidCounter) {
			return true;
		} else {
			return false;
		}
	}
}
