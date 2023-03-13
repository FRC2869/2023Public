package frc.robot.commands.pivot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Inputs;
import frc.robot.subsystems.PivotSubsystem;

public class PivotDefault extends CommandBase{
	private PivotSubsystem pivot;

	public PivotDefault(){
		this.pivot = PivotSubsystem.getInstance();
		addRequirements(pivot);
	}

	@Override
	public void execute(){
		pivot.power(Inputs.getPivotPower());
		pivot.position(Inputs.getPivotPosition());
	}
}
