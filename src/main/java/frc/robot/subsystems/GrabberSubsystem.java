package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Motors;

public class GrabberSubsystem extends SubsystemBase{
	private static GrabberSubsystem instance;
	// private Compressor compressor; 
	// private DoubleSolenoid solenoid1;
	// private DoubleSolenoid solenoid2;

	private CANSparkMax grabber1;
	private CANSparkMax grabber2;

	public static GrabberSubsystem getInstance(){
		if(instance == null){
			instance = new GrabberSubsystem();
		}
		return instance;
	}

	public GrabberSubsystem(){
		// compressor = new Compressor(16, PneumaticsModuleType.CTREPCM);
		// solenoid1 = new DoubleSolenoid(16, PneumaticsModuleType.CTREPCM, PneumaticsConstants.solenoidPortForwards, PneumaticsConstants.solenoidPortBackwards);
		// solenoid2 = new DoubleSolenoid(16, PneumaticsModuleType.CTREPCM, PneumaticsConstants.solenoidPortForwardsTwo, PneumaticsConstants.solenoidPortBackwardsTwo);
		grabber1 = new CANSparkMax(17, MotorType.kBrushless);
		grabber2 = new CANSparkMax(18, MotorType.kBrushless);
		
		configureGrabberMotors();

	}

	private void configureGrabberMotors() {
		grabber1.restoreFactoryDefaults();

        grabber1.enableVoltageCompensation(12.0);
		grabber1.setInverted(Motors.Grabber1.kInverted);
		grabber1.setIdleMode(Motors.Grabber1.idlemode);
		grabber1.setSmartCurrentLimit(Motors.Grabber1.currentLimit);
		grabber1.setOpenLoopRampRate(Motors.Grabber1.openLoopRampRate);
		grabber1.burnFlash();

		grabber2.restoreFactoryDefaults();

        grabber2.enableVoltageCompensation(12.0);
		grabber2.setInverted(Motors.Grabber1.kInverted);
		grabber2.setIdleMode(Motors.Grabber1.idlemode);
		grabber2.setSmartCurrentLimit(Motors.Grabber1.currentLimit);
		grabber2.setOpenLoopRampRate(Motors.Grabber1.openLoopRampRate);
		grabber2.burnFlash();
	}

	public void compressorOn(){
		// compressor.enableDigital();
	}

	public void compressorOff(){
		// compressor.disable();
	}

	public void closeGrabber(){
		// solenoid1.set(Value.kForward);
		// solenoid2.set(Value.kForward);
		
		grabber1.set(.6);
		grabber2.set(-.6);
		// System.out.println("in");
	}

	public void openGrabber(){
		// solenoid1.set(Value.kReverse);
		// solenoid2.set(Value.kReverse);
		grabber1.set(-.6);
		grabber2.set(.6);
		// System.out.println("out");
	}

	public void offGrabber(){
		// solenoid1.set(Value.kOff);
		// solenoid2.set(Value.kOff);
		grabber1.set(0);
		grabber2.set(0);
		// System.out.println("off");
	}

	/**
	 * 
	 * @return if the compressor is on
	 */
	// public boolean getCompressorOn(){
	// 	return compressor.isEnabled();
	// }

	@Override
	public void periodic(){
		// System.out.print(grabber1.get());
		// System.out.println(grabber2.get());
	}

}
