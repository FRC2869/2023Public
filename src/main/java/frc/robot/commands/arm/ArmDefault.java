package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Inputs;
import frc.robot.subsystems.ArmSubsystem;

public class ArmDefault extends CommandBase{
    private final ArmSubsystem arm;
    
    public ArmDefault(){
        this.arm = ArmSubsystem.getInstance();
		addRequirements(arm);

    }

    @Override
    public void execute(){
        arm.extend(Inputs.getExtension());
    }
}
