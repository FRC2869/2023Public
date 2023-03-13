package frc.robot.commands.grabber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.GrabberSubsystem;

public class OffGrabber extends CommandBase {
	GrabberSubsystem grabber;
	public OffGrabber (){
		grabber = GrabberSubsystem.getInstance();
		addRequirements(grabber);
	}

	@Override
	public void execute(){
		grabber.offGrabber();
	}
}
