/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Shooter extends SubsystemBase {
	public boolean armPositionUp = false;
	private DigitalInput topLimitSwitch;
	private DigitalInput botLimitSwitch;
	private Victor shooter;
	private Victor shooterLift;
	
	/**
	 * Cookies intake comprised of 
	 * Shooter: Picks up game pieces with a rotating tube
	 * ShooterLift: Moves the shooter up and down so that the 
	 * intake can be protected when not in use
	 * limitSwitches: limit switches ensure cookie's shooterLift does not keep
	 * driving the shooterLift motor and burn it out
	*/
	public Shooter(){
		topLimitSwitch = new DigitalInput(Constants.Shooter.topLimitSwitch);
		botLimitSwitch = new DigitalInput(Constants.Shooter.botLimitSwitch);
		shooter = new Victor(Constants.Shooter.shooterIndex);
		shooterLift =  new Victor(Constants.Shooter.shooterLiftIndex);
		shooterLift.set(0);
	}

	//It is assumed that the motors will spin in opposite directions
	//Therefore, we will always activate them using this method
	public void shooterPower(final double power) {
		shooter.set(power);
		SmartDashboard.putNumber("Shooter power: ", power);
	}

	public void shoot() {
		shooterPower(0.7);
	}
    public void Stop() {
		shooterPower(0);
	}
    public void intake() {
        shooterPower(-0.3);
	}

	public void moveToPosition() {
		DigitalInput lswitch;
		double armSpeed;
		//Determines the limit switch and speed based off if we are going up or down
		if (armPositionUp) {
			lswitch = topLimitSwitch;
			armSpeed = 0.3;
		}
		else {
			lswitch = botLimitSwitch;
			armSpeed = -0.1;
		}

		//Moves to the appropriate position
		if (lswitch.get()) {
			shooterLift.set(armSpeed);
			SmartDashboard.putNumber("Arm Speed: ", shooterLift.get());
		}
		else {
			shooterLift.set(0);
			SmartDashboard.putNumber("Arm Speed: ", shooterLift.get());
		}
	}
}
