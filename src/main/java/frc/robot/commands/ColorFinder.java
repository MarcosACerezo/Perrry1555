/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.CPI;
import frc.robot.subsystems.ColorSensor;

/**
 * An example command.  You can replace me with your own command.
 */
public class ColorFinder extends Command {
	private CPI m_cpi;
	private ColorSensor m_colorSensor;
	
	public ColorFinder(CPI cpi, ColorSensor colorSensor) {
		// Use requires() here to declare subsystem dependencies
		this.m_cpi = cpi;
		this.m_colorSensor = colorSensor;
		addRequirements(m_colorSensor, m_cpi);
    }

	public String colorTargetValue;
	public String colorFound;
	public String lastColor;

	// Called just before this Command runs the first time
	@Override
	public void initialize() {
		lastColor = "Unknown";
	}

	// Called repeatedly when this Command is scheduled to run
    @Override
    //for this function put it to a button and then make the program run based off of what the button is returning
	public void execute() {
		if ((lastColor == "Unknown") || (lastColor == null)) {
			lastColor = m_colorSensor.RobotColorDetector();
			return;
		}

		m_cpi.setSpeed(0.2);
		colorFound = m_colorSensor.RobotColorDetector();
		if (colorFound == m_colorSensor.nextColorClockwise(lastColor)) {
			lastColor = colorFound;
		}
		System.out.println("Searching for " + colorTargetValue);
		System.out.println("Currently veiwing " + colorFound);
		SmartDashboard.putString("Detected Color: ", colorFound);

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	public boolean isFinished() {
		if(lastColor.equals(colorTargetValue)) {
			return true;
		}
		else {
			return false;
		}
	}

	// Called once after isFinished returns true
	@Override
	public void end(boolean interrupted) {
        m_cpi.setSpeed(0);
	}

}
