/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.beans.PropertyChangeListener;

import frc.robot.Robot;
import frc.robot.OI.driveButtons;

/**
 * An example command.  You can replace me with your own command.
 */
public class SeekVisionTarget extends Command {
	public SeekVisionTarget() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.kLimelight);
	}
	
	//Doubles to hold the margin of error for distance and angle
	double xError;
	double zError;
	
	//Doubles to hold the power adjustment to give the motors
	double speedAdjust;
    double steeringAdjust;
    
    //Double to hold the +- value of xError (Holds 1 or -1)
    double pole;
    
    //Doubles to hold the final amount of power given to the motors
    double lSpeed;
    double rSpeed;
    
    //Max value of steeringAdjust
    public final double steeringCap = 0.3;
    
    //If the absolute value of xError is ever lower than this it sets xError to 0
	public final double xToleranceThreshold = 10;
	
	//The higher this value the less the robot will correct for zError
	public final double distanceConstant = 100;
	
	//The value added to lSpeed and rSpeed by default
	public final double defaultSpeed = 0.2;
	
	//The speed the robot will turn at when searching for the target
	// public final double turnSpeed = 0.25;
	public final double turnSpeed = 0;


	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	//Sets the starting values for variables
	xError = 0;
	zError = 0;
	steeringAdjust = 0;
	pole = 1;
	Robot.kLimelight.setPipe(1);

	System.out.println("Inititalized");

	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

        //Checks for a target
		if (Robot.kLimelight.targetVisible()) {
			
			//Checks the error of the angle
			xError = Robot.kLimelight.getLimeX();
			pole = xError/Math.abs(xError);
			if (Math.abs(xError) < xToleranceThreshold) {
				xError = 0;
			}
			//Checks how far away the target is
			zError = Robot.kLimelight.distanceByArea();
			speedAdjust = zError/distanceConstant;
			//Determines steering adjustment based on how far off the angle is
			steeringAdjust = xError/zError;
			if (steeringAdjust > steeringCap) {
				steeringAdjust = steeringCap;
			}
			else if (steeringAdjust < -steeringCap)
			{
				steeringAdjust = -steeringCap;
			}
						
			lSpeed = speedAdjust + steeringAdjust - defaultSpeed;
			rSpeed = speedAdjust - steeringAdjust - defaultSpeed;
			SmartDashboard.putNumber("zError:", zError);
			
			//Drives toward the target
			Robot.Drive.driveTank(lSpeed, rSpeed);
			
		}
		//Runs if the limelight can't see a target
		else {
			//Turns right if the limelight last saw the target to the right of it's field of view
			if (pole > 0) {
				Robot.Drive.driveTank(turnSpeed, -turnSpeed);
			}
			//Turns left if the limelight last saw the target to the left of it's field of view			
			else {
				Robot.Drive.driveTank(-turnSpeed, turnSpeed);

			}
			
		}
		
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		if ((Math.abs(zError) < 8) || driveButtons.select.get()) {
			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.Drive.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
