package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Inputs;
import frc.robot.Constants.Motors;
import frc.robot.Constants.PivotConstants;

public class PivotSubsystem extends SubsystemBase {
	private static PivotSubsystem instance;
	private CANSparkMax pivotMotor;
	private RelativeEncoder pivotEncoder;
	private SparkMaxPIDController pivotPID;
	private double speed = 0;
	private double pos = 0;
	private boolean isPositionControl = false;
	private ArmFeedforward pivotFF;

	public static PivotSubsystem getInstance(){
		if(instance==null){
			instance = new PivotSubsystem();
		}
		return instance;
	}

	public PivotSubsystem(){
		pivotMotor = new CANSparkMax(PivotConstants.pivotMotorId, MotorType.kBrushless);
		configurePivotMotor();
		pivotFF = new ArmFeedforward(PivotConstants.kS, PivotConstants.kG, PivotConstants.kV);
	}

	private void configurePivotMotor() {
		pivotMotor.restoreFactoryDefaults();
        
        pivotEncoder = pivotMotor.getEncoder();
        pivotEncoder.setPositionConversionFactor(PivotConstants.GEAR_RATIO);
        pivotEncoder.setVelocityConversionFactor(PivotConstants.VELOCITY_CONVERSION);
        
        pivotPID = pivotMotor.getPIDController();
        pivotPID.setFeedbackDevice(pivotEncoder);

        pivotPID.setP(PivotConstants.kP);
        pivotPID.setI(PivotConstants.kI);
        pivotPID.setD(PivotConstants.kD);
        pivotPID.setOutputRange(-.4, .4);

        pivotMotor.enableVoltageCompensation(12.0);
		pivotMotor.setInverted(Motors.Pivot.kInverted);
		pivotMotor.setIdleMode(Motors.Pivot.idlemode);
		pivotMotor.setSmartCurrentLimit(Motors.Pivot.currentLimit);
		pivotMotor.setOpenLoopRampRate(Motors.Pivot.openLoopRampRate);
		pivotMotor.burnFlash();

		pivotEncoder.setPosition(PivotConstants.startingPosition);
	}

	/**
	 * Sets the pivot motor to the specified speed
	 * Only applies if isPositionControl == false
	 * @param pwr the speed to set the motor to [-1,1]
	 */
	public void power(double pwr){
		// speed = MathUtil.clamp(pwr, -PivotConstants.kMaxPower, PivotConstants.kMaxPower);
		speed = pwr*PivotConstants.kMaxPower;
	}

	/**
	 * Sets the pivot motor to the specified position
	 * Only applies if isPositionControl == true
	 * @param pos the position to set the motor to [kMinAngle, kMaxAngle]
	 */
	public void position(double pos){
		this.pos = MathUtil.clamp(pos, PivotConstants.kMinAngle, PivotConstants.kMaxAngle);
	}

	public double getAngle(){
		return pivotEncoder.getPosition();
	}

	public double getVelocity(){
		return pivotEncoder.getVelocity();
	}

	/**
	 * Changes whether  the mode is position or speed control
	 * @param positionControl true for position control, false for speed control
	 */
	public void setPositionControl(boolean positionControl){
		isPositionControl = positionControl;
	}

	@Override
	public void periodic(){
		if(isPositionControl){
			pivotPID.setReference(pos, ControlType.kPosition, 0, pivotFF.calculate(getAngle(), getVelocity()));
		}else{
			// System.out.println(pivotEncoder.getPosition());
			if((Inputs.getOverrideButton()) && speed<0 && pivotEncoder.getPosition()<=PivotConstants.kMinAngle)
				speed = 0;
			if((Inputs.getOverrideButton()) && speed>0 && pivotEncoder.getPosition()>=PivotConstants.kMaxAngle)
				speed = 0;
			pivotMotor.set(speed);
		}
	}
}
