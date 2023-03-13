package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;

public class AutoChargeStationOn extends CommandBase{
	private DrivetrainSubsystem swerve;

	public AutoChargeStationOn(){
		swerve = DrivetrainSubsystem.getInstance();
		addRequirements(swerve);
	}

	@Override
	public void execute(){
		swerve.drive(()->0,()->-.5,()->0);
	}

	@Override
	public boolean isFinished(){
		if(swerve.getGyroPitch().getDegrees()>10){
			swerve.drive(()->0,()->0,()->0);
			return true;
		}else{
			return false;
		}
	}
}
