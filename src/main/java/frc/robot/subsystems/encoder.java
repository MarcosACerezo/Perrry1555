/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class encoder extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public static Encoder encDriveL = Robot.map.encDriveL;
	public static Encoder encDriveR = Robot.map.encDriveR;
	public static Encoder encLiftCPI = Robot.map.encLiftCPI;
	public static Encoder encLiftClimber = Robot.map.encLiftClimber;
	

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		
	}
	
	//Declaring new encoders
	//We may have to move this to the robot class

	public void startEncoders() {
		//Max Period - The maximum period (in seconds) where the device is still considered moving. 
		//This value is used to determine the state of the getStopped() 
		//method and effect the output of the getPeriod() and getRate() methods.
		//This is the time between pulses on an individual channel (scale factor is accounted for). 
		//It is recommended to use the Min Rate parameter instead as it accounts for the distance per pulse,
		//allowing you to set the rate in engineering units.
		encDriveL.setMaxPeriod(0.1);
		encDriveR.setMaxPeriod(0.1);
		encLiftCPI.setMaxPeriod(0.1);
		encLiftClimber.setMaxPeriod(0.1);
		

		//Min Rate - Sets the minimum rate before the device is considered stopped. 
		//This compensates for both scale factor and distance per pulse and therefore should be 
		//entered in engineering units (RPM, RPS, Degrees/sec, In/s, etc)
		encDriveL.setMinRate(10);
		encDriveR.setMinRate(10);
		encLiftCPI.setMinRate(10);
		encLiftClimber.setMinRate(10);
		

		//Distance Per Pulse - Sets the scale factor between pulses and distance. 
		//The library already accounts for the decoding scale factor (1x, 2x, 4x) separately 
		//so this value should be set exclusively based on the encoder's Pulses per Revolution 
		//and any gearing following the encoder.
		encDriveL.setDistancePerPulse(5);
		encDriveR.setDistancePerPulse(5);
		encLiftCPI.setDistancePerPulse(5);
		encLiftClimber.setDistancePerPulse(5);
		

		//Reverse Direction - Sets the direction the encoder counts, 
		//used to flip the direction if the encoder mounting makes the default counting direction unintuitive.
		encDriveL.setReverseDirection(true);
		encDriveR.setReverseDirection(true);
		encLiftCPI.setReverseDirection(true);
		encLiftClimber.setReverseDirection(true);
		

		//Samples to Average - Sets the number of samples to average when determining the period. 
		//Averaging may be desired to account for mechanical imperfections 
		//(such as unevenly spaced reflectors when using a reflective sensor as an encoder) 
		//or as oversampling to increase resolution. Valid values are 1 to 127 samples.
		encDriveL.setSamplesToAverage(7);
		encDriveR.setSamplesToAverage(7);
		encLiftCPI.setSamplesToAverage(7);
		encLiftClimber.setSamplesToAverage(7);
		
	}


	//Count - The current count. May be reset by calling reset()
	public int encoderCount(Encoder enc) {
		int out = enc.get();
		System.out.println(out);
		return out;
	}
	//Alternate version for the CANEncoders used by the NEOs
	public double encoderCount(CANEncoder enc) {
		double out = enc.getPosition();
		System.out.println(out);
		return out;
	}

	//Raw Count - The count without compensation for decoding scale factor
	public double encoderDistance(Encoder enc) {
		return enc.getRaw();
	}

	//Rate - The current rate of the counter in units/sec. 
	//It is calculated using the DistancePerPulse divided by the period. 
	//If the counter is stopped this value may return Inf or NaN, depending on language.
	public double encoderRate(Encoder enc) {
		return enc.getRate();
	}

	public double encoderRate(CANEncoder enc) {
		return enc.getVelocity();
	}

	//Direction - The direction of the last value change (true for Up, false for Down)
	public boolean encoderDirection(Encoder enc) {
		return enc.getDirection();
	}

	//Stopped - If the counter is currently stopped (period has exceeded Max Period)
	public boolean encoderStopped(Encoder enc) {
		return enc.getStopped();
	}

	//Runs to the desired position
	public void runToPosition(Talon motor, Encoder enc, int position) {
		while (enc.get() != position) {
			if (enc.get() < position) {
				motor.set(0.3);
			}
			else if (enc.get() > position) {
				motor.set(-0.3);
			}
		}
		motor.set(0);

	}

	//Runs to the desired position
	public void runToPosition(Victor motor, Encoder enc, int position) {
		while (enc.get() != position) {
			if (enc.get() < position) {
				motor.set(0.3);
			}
			else if (enc.get() > position) {
				motor.set(-0.3);
			}
		}
		motor.set(0);
	
	}


// int count = sampleEncoder.get();
// double distance = sampleEncoder.getRaw();
// double distance = sampleEncoder.getDistance();
// double period = sampleEncoder.getPeriod();
// double rate = sampleEncoder.getRate();
// boolean direction = sampleEncoder.getDirection();
// boolean stopped = sampleEncoder.getStopped();

}
