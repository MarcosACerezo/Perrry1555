/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Limelight;

/**
 * An example command.  You can replace me with your own command.
 */
public class SeekVisionTarget extends Command {
	private Limelight m_limelight;
	private DriveTrain m_driveTrain;

	public SeekVisionTarget(DriveTrain driveTrain, Limelight limelight) {
		m_driveTrain = driveTrain;
		m_limelight = limelight;
		addRequirements(m_driveTrain, m_limelight);
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
	public void initialize() {
	//Sets the starting values for variables
	xError = 0;
	zError = 0;
	steeringAdjust = 0;
	pole = 1;
	m_limelight.setPipe(1);

	System.out.println("Inititalized");

	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	public void execute() {

        //Checks for a target
		if (m_limelight.targetVisible()) {
			
			//Checks the error of the angle
			xError = m_limelight.getLimeX();
			pole = xError/Math.abs(xError);
			if (Math.abs(xError) < xToleranceThreshold) {
				xError = 0;
			}
			//Checks how far away the target is
			zError = m_limelight.distanceByArea();
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
			m_driveTrain.driveTank(lSpeed, rSpeed);
			
		}
		//Runs if the limelight can't see a target
		else {
			//Turns right if the limelight last saw the target to the right of it's field of view
			if (pole > 0) {
				m_driveTrain.driveTank(turnSpeed, -turnSpeed);
			}
			//Turns left if the limelight last saw the target to the left of it's field of view			
			else {
				m_driveTrain.driveTank(-turnSpeed, turnSpeed);

			}
			
		}
		
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	public boolean isFinished() {
		//TODO || driveButtons.select.get() implement this in the robotContainer class
		if ((Math.abs(zError) < 8)) {
			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	@Override
	public void end(boolean isFinished) {
		m_driveTrain.stop();
	}
}
