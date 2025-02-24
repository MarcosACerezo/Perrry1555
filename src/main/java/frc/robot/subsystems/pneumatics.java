/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class pneumatics extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	DoubleSolenoid solenoid = Robot.map.exampleSolenoid;
	public Compressor compressor = new Compressor();
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	public void extend() {
		Robot.map.exampleSolenoid.set(Value.kReverse);
	}
	
	public void retract() {
		Robot.map.exampleSolenoid.set(Value.kForward);
	}
	
	public void solenoidOff() {
		Robot.map.exampleSolenoid.set(Value.kOff);
	}
	
	public void compressorOn() {
		compressor.start();
	}
	
	public void compressorOff() {
		compressor.stop();
	}
	
	public void clearStickyFault() {
		compressor.clearAllPCMStickyFaults();
	}
	
	public void test() {
		
	}
}
