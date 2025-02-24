/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class CPI extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
    public static Talon Spin = Robot.map.CPISpinner;
    //public static Victor AL = Robot.map.armLift;
    public static ColorSensor Eye = Robot.kColorSensor;

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
    }
    // Run lift motor until it is at the appropriate level with the control panel
    public static void lift() {
        // PID system is necessary for the accuracy of this method 
    }
    // Rotation Control
    public static void FullPower() {
        Spin.set(1);
    }
    public static void Stop() {
        Spin.set(0);
    }
    public static void Reverse() {
        Spin.set(-1);
    }
    // Position Control

}
