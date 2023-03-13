package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.ArmConstants.Extension;
import frc.robot.Constants.Motors;

public class ArmSubsystem extends SubsystemBase{
    private static ArmSubsystem instance;
    private CANSparkMax extensionMotor;
    private double speed;
    private RelativeEncoder extensionEncoder;
    private SparkMaxPIDController extensionPID;
	private double pos;
	private boolean isPositionControl;

    public static ArmSubsystem getInstance(){
        if(instance==null){
            instance = new ArmSubsystem();
        }
        return instance;
    }

    public ArmSubsystem() {
        extensionMotor = new CANSparkMax(ArmConstants.Extension.extensionMotorID, MotorType.kBrushless); 
        configureExtensionMotor(); 
    }
    private void configureExtensionMotor() {
        extensionMotor.restoreFactoryDefaults();
        
        extensionEncoder = extensionMotor.getEncoder();
        extensionEncoder.setPositionConversionFactor(Extension.Encoder.POSITION_CONVERSION);
        extensionEncoder.setVelocityConversionFactor(Extension.Encoder.VELOCITY_CONVERSION);
        
        extensionPID = extensionMotor.getPIDController();
        extensionPID.setFeedbackDevice(extensionEncoder);

        extensionPID.setP(Extension.kP);
        extensionPID.setI(Extension.kI);
        extensionPID.setD(Extension.kD);
        extensionPID.setOutputRange(-Extension.kMaxSpeed, Extension.kMaxSpeed);

        extensionMotor.enableVoltageCompensation(12.0);
        
        extensionMotor.setInverted(Motors.Arm.Extension.kInverted);
        extensionMotor.setIdleMode(Motors.Arm.Extension.idlemode);
        extensionMotor.setSmartCurrentLimit(Motors.Arm.Extension.currentLimit);
        extensionMotor.setOpenLoopRampRate(Motors.Arm.Extension.openLoopRampRate);
        extensionMotor.burnFlash();
        
        extensionEncoder.setPosition(Extension.startingPosition);
    }

    // GET
    public double getPosition(){
        return extensionEncoder.getPosition();
    }

    // SET
    /**
     * Extends the arm at Constants.Arm.Extension.kMaxSpeed
     */
    public void extend(){
        speed = Extension.kMaxSpeed;
    }
    /**
     * Extends the arm at a percentage of the max speed
     * @param speed the percentage between -1 and 1 where 1 is out and -1 is in
     */
    public void extend(double speed){
        this.speed = speed * Extension.kMaxSpeed;
    }
    /**
     * Retracts the arm at Constants.Arm.Extension.kMaxSpeed 
     */
    public void retract(){
        speed = -Extension.kMaxSpeed;
    }

	public void position(double pos){
		this.pos = MathUtil.clamp(pos, ArmConstants.Extension.kMinDistance, ArmConstants.Extension.kMaxDistance);

	}

	public void setPositionControl(boolean isPosition){
		isPositionControl = isPosition;
	}

    @Override
    public void periodic(){
        //don't go too fast
        if(speed>Extension.kMaxSpeed)
            speed = Extension.kMaxSpeed;
        else if(speed<-Extension.kMaxSpeed)
            speed = -Extension.kMaxSpeed;

        //don't go too far
		// System.out.println(getPosition());
        if(getPosition()>=Extension.kMaxDistance && speed>0)
            speed = 0;
        else if(getPosition()<=Extension.kMinDistance && speed<0)
            speed = 0;
        
		if(isPositionControl){
			extensionPID.setReference(pos, ControlType.kPosition);
		}else{
			extensionMotor.set(speed);
		}
    }
}
