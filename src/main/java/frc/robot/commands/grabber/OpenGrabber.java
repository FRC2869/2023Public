package frc.robot.commands.grabber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.GrabberSubsystem;

public class OpenGrabber extends CommandBase {
	GrabberSubsystem grab;
	int counter = 0;

	public OpenGrabber(){
		grab = GrabberSubsystem.getInstance();
		addRequirements(grab);
	}

	@Override
	public void execute(){
		grab.openGrabber();
		counter++;
	}

	@Override
	public boolean isFinished(){
		// if(counter>100){
		// 	return true;
		// }
		return false;
	}
}
