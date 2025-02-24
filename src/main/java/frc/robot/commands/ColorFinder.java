/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

/**
 * An example command.  You can replace me with your own command.
 */
public class ColorFinder extends Command {
	public ColorFinder() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.kColorSensor);
    }

	public String colorTargetValue;
	public String colorFound;
	public String lastColor;

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		lastColor = "Unknown";
	}

	// Called repeatedly when this Command is scheduled to run
    @Override
    //for this function put it to a button and then make the program run based off of what the button is returning
	public void execute() {
		if ((lastColor == "Unknown") || (lastColor == null)) {
			lastColor = Robot.kColorSensor.RobotColorDetector();
			return;
		}

		Robot.map.CPISpinner.set(0.2);
		colorFound = Robot.kColorSensor.RobotColorDetector();
		if (colorFound == Robot.kColorSensor.nextColorClockwise(lastColor)) {
			lastColor = colorFound;
		}
		System.out.println("Searching for " + colorTargetValue);
		System.out.println("Currently veiwing " + colorFound);
		SmartDashboard.putString("Detected Color: ", colorFound);

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		if(lastColor.equals(colorTargetValue)) {
			return true;
		}
		else {
			return false;
		}
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
        Robot.map.CPISpinner.set(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
