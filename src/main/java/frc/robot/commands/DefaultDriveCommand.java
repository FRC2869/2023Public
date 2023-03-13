package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;

public class DefaultDriveCommand extends CommandBase {
    private final DrivetrainSubsystem swerve;

    private final DoubleSupplier m_translationXSupplier;
    private final DoubleSupplier m_translationYSupplier;
    private final DoubleSupplier m_rotationSupplier;

    public DefaultDriveCommand(DoubleSupplier translationXSupplier,
                               DoubleSupplier translationYSupplier,
                               DoubleSupplier rotationSupplier) {
        this.swerve = DrivetrainSubsystem.getInstance();
        this.m_translationXSupplier = translationXSupplier;
        this.m_translationYSupplier = translationYSupplier;
        this.m_rotationSupplier = rotationSupplier;

        addRequirements(swerve);
    }

    @Override
    public void execute() {
        // You can use `new ChassisSpeeds(...)` for robot-oriented movement instead of field-oriented movement
        swerve.drive(
                        m_translationXSupplier,
                        m_translationYSupplier,
                        m_rotationSupplier
        );
        // swerve.drive(new ChassisSpeeds(m_translationXSupplier.getAsDouble(),
        //                 m_translationYSupplier.getAsDouble(),
        //                 m_rotationSupplier.getAsDouble()));
    }


    @Override
    public void end(boolean interrupted) {
        swerve.drive(() -> 0.0,() ->  0.0,() ->  0.0);
    }
}
