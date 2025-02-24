/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CPI;
import frc.robot.subsystems.ColorSensor;

public class RotationControl extends Command {
	//necessary subsystems
	private ColorSensor m_colorSensor;
	private CPI m_cpi;
	//start color used to know when a full rotation is completed
	String currentColor;
	String lastColor;
	boolean Flag;
	int Ticks;
    
    public RotationControl(ColorSensor colorSensor, CPI cpi) {
		this.m_colorSensor = colorSensor;
		this.m_cpi = cpi;
		addRequirements(m_colorSensor, m_cpi);
	}

	// Called just before this Command runs the first time
	@Override
	public void initialize() {
		lastColor = "Unknown";
		Flag = true;
		Ticks = 0;
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	public void execute() {
		// Spin for 4 rotations
        // Store the current color
		if (lastColor.equals("Unknown")) {
			lastColor = m_colorSensor.RobotColorDetector();
			return;
		}
		m_cpi.setSpeed(0.2);

		// Check for a color change
		currentColor = m_colorSensor.RobotColorDetector();

		if (currentColor == m_colorSensor.nextColorClockwise(lastColor)) {
			Ticks += 1;
			lastColor = currentColor;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	public boolean isFinished() {
		if (Ticks == 32) {
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
