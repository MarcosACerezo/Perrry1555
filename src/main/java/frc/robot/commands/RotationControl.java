/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * An example command.  You can replace me with your own command.
 */
public class RotationControl extends Command {
    //
	String startColor; 
	//
	String Color2;

	public String lastColor;

	Talon spinner;
		
	//
	boolean Flag;
	//
	int Ticks;
    
    public RotationControl() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.kColorSensor);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		lastColor = "Unknown";
		Flag = true;
		Ticks = 0;
		spinner = Robot.map.CPISpinner;
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// Spin for 4 rotations
        // Store the current color
		if (lastColor == "Unknown") {
			lastColor = Robot.kColorSensor.RobotColorDetector();
			return;
		}
		spinner.set(0.2);

		// Check for a color change
		
		Color2 = Robot.kColorSensor.RobotColorDetector();
		
		if (Color2 == Robot.kColorSensor.nextColorClockwise(lastColor)) {
			Ticks += 1;
			lastColor = Color2;
		}
		System.out.println(Ticks);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		if (Ticks == 32) {
			return true;
		}
		else {
			return false;
		}
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		spinner.set(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}

	
}
