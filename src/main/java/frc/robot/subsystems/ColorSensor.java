/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Robot;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class ColorSensor extends Subsystem {
	// Put methods for controlling this subsystem
    // here. Call these from Commands.
    //All of the k(color)Targets need to be calibrated
    public RobotMap map = Robot.map;
    private final ColorMatch colourMatch = new ColorMatch();
    public final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    public final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    public final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    public final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
    
    public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
    }
    //gives the red value detected from the color sensor
    public int red(){
        return map.colourSensor.getRed();
    }
    //gives the red value detected from the color sensor
    public int green(){
        return map.colourSensor.getGreen();
    }
    //gives the red value detected from the color sensor
    public int blue(){
        return map.colourSensor.getBlue();
    }
    //gives the red value detected from the color sensor
    public double ir(){
        return map.colourSensor.getIR();
    }
    //gives the red value detected from the color sensor
    public int proximity(){
        return map.colourSensor.getProximity();
    }
    //Returns the color that the robot is detecting
    public Color detectedColor(){
        return map.colourSensor.getColor();
    }

    // I still have no idea to explain why to use this but I will figure it out
    public void robotColorValues(){
        colourMatch.addColorMatch(kBlueTarget);
        colourMatch.addColorMatch(kGreenTarget);
        colourMatch.addColorMatch(kRedTarget);
        colourMatch.addColorMatch(kYellowTarget);
    }

    //This runs the color detection algorithm so that the robot says what it is detecting
    public String RobotColorDetector(){
        String colorString;
        ColorMatchResult match = colourMatch.matchClosestColor(detectedColor());

        if (proximity()<120) {
            colorString = "Unknown";
        } else if (match.color == kBlueTarget){
            colorString = "Blue";
        } else if (match.color == kRedTarget) {
            colorString = "Red";
        } else if (match.color == kGreenTarget) {
            colorString = "Green";
        } else if (match.color == kYellowTarget) {
            colorString = "Yellow";
        } else {
            colorString = "Unknown";
        }
        return colorString;
    }

    public String nextColorCounterClockwise(String currentColor) {
		switch (currentColor) {
			case "Green": return "Blue";
			case "Blue": return "Yellow";
			case "Yellow": return "Red";
			case "Red": return "Green";
			default: return "Unknown";
		}
	}

	public String nextColorClockwise(String currentColor) {
		switch (currentColor) {
			case "Green": return "Red";
			case "Red": return "Yellow";
			case "Yellow": return "Blue";
			case "Blue": return "Green";
			default: return "Unknown";
		}
    }
    
    
    
}
