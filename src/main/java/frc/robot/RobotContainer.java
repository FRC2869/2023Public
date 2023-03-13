// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.ArmDoubleSubStation;
import frc.robot.commands.DefaultDriveCommand;
import frc.robot.commands.grabber.CloseGrabber;
import frc.robot.commands.grabber.OffGrabber;
import frc.robot.commands.grabber.OpenGrabber;
import frc.robot.commands.pivot.PivotDefault;
import frc.robot.commands.swerve.SwerveDriveAutoBalance;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.PivotSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
	// The robot's subsystems and commands are defined here...
	// private final SwerveSubsystem swerve = SwerveSubsystem.getInstance();
	private DrivetrainSubsystem swerve = DrivetrainSubsystem.getInstance();
	// private final ArmSubsystem arm = ArmSubsystem.getInstance();
	private final PivotSubsystem pivot = PivotSubsystem.getInstance();
	// private final GrabberSubsystem grabber = GrabberSubsystem.getInstance();

	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	public RobotContainer() {
		CommandScheduler.getInstance().cancelAll();
		configureDefaultCommands();
		configureBindings();
		resetSwerve();
		// grabber.compressorOn();
	}

	/****************/
	/*** DEFAULTS ***/
	/****************/

	private void configureDefaultCommands() {
		// m_SwerveSubsystem.setDefaultCommand(new SwerveDriveDrive());
		swerve.setDefaultCommand(new DefaultDriveCommand(
            () -> -modifyAxis(Inputs.getTranslationY()) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND,
            () -> -modifyAxis(Inputs.getTranslationX()) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND,
            () -> -modifyAxis(Inputs.getRotation()) * DrivetrainSubsystem.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND
    ));
		pivot.setDefaultCommand(new PivotDefault());
		// arm.setDefaultCommand(new ArmDefault());
		// grabber.setDefaultCommand(new OffGrabber());
	}

	public void resetSwerve(){
		swerve.zeroGyroscope();

	}

	/***************/
	/*** BUTTONS ***/
	/***************/
	private void configureBindings() {
		configureDriverBindings();
	}

	/**
	 * 
	 */
	private void configureDriverBindings() {
		Inputs.getBalanceButton().onTrue(new SwerveDriveAutoBalance());
		// Inputs.getPivotPos().onTrue(new PivotPosPwrSwitch(true));
		// Inputs.getPivotPwr().onTrue(new PivotPosPwrSwitch(false));
		// Inputs.getArmConeLow().onTrue(new ArmConeLow());
		// Inputs.getArmConeMid().onTrue(new ArmConeMid());
		// Inputs.getArmConeHigh().onTrue(new ArmConeHigh());
		// Inputs.getArmCubeLow().onTrue(new ArmCubeLow());
		// Inputs.getArmCubeMid().onTrue(new ArmCubeMid());
		// Inputs.getArmCubeHigh().onTrue(new ArmCubeHigh());
		Inputs.getCloseGrabber().whileTrue(new CloseGrabber());
		Inputs.getOpenGrabber().whileTrue(new OpenGrabber());
		Inputs.getOffGrabber().onTrue(new OffGrabber());
		// Inputs.getResetGyroButton().onTrue(new SwerveDriveResetGyro());
		Inputs.getArmDoubleSubStation().onTrue(new ArmDoubleSubStation());
	}

	/**
	 * Use this to pass the autonomous command to the main {@link Robot} class.
	 *
	 * @return the command to run in autonomous
	 */
	public Command getAutonomousCommand() {
		// An example command will be run in autonomous
		return null;
		// return new SequentialCommandGroup(new AutoForwards(), new AutoBack(), new AutoRight(), new AutoChargeStationOn(), new SwerveDriveAutoBalance());
		// return new SequentialCommandGroup(new AutoChargeStationOn(), new SwerveDriveAutoBalance());
		// return new SequentialCommandGroup(new ArmConeMid(), new ParallelRaceGroup(new OpenGrabber(), new WaitCommand(1)), new ParallelRaceGroup(new OffGrabber(), new WaitCommand(1)),  new ArmBasePos(), new AutoForwards(), new WaitCommand(500));
	}
	private static double deadband(double value, double deadband) {
		if (Math.abs(value) > deadband) {
		  if (value > 0.0) {
			return (value - deadband) / (1.0 - deadband);
		  } else {
			return (value + deadband) / (1.0 - deadband);
		  }
		} else {
		  return 0.0;
		}
	  }
	  private static double modifyAxis(double value) {
		// Deadband
		value = deadband(value, 0.05);
	
		// Square the axis
		value = Math.copySign(value * value, value);
	
		return value;
	  }
}
