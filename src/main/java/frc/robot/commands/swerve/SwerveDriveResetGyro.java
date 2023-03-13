package frc.robot.commands.swerve;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.DrivetrainSubsystem;

public class SwerveDriveResetGyro extends InstantCommand {
	private DrivetrainSubsystem swerve;

	public SwerveDriveResetGyro(){
		swerve = DrivetrainSubsystem.getInstance();
		addRequirements(swerve);
	}

	@Override
	public void execute(){
		swerve.zeroGyroscope();
	}
}
