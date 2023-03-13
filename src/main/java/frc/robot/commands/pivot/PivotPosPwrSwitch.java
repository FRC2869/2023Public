package frc.robot.commands.pivot;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.PivotSubsystem;

public class PivotPosPwrSwitch extends InstantCommand {
	private PivotSubsystem pivot;
	private boolean pos;
	public PivotPosPwrSwitch(boolean position){
		this.pivot = PivotSubsystem.getInstance();
		this.pos = position;
	}

	@Override
	public void execute(){
		pivot.setPositionControl(pos);
	}
}
