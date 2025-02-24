/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import org.opencv.core.Mat;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;



/**
 * An example command.  You can replace me with your own command.
 */
public class NavXSeeker extends Command {

    //This value will hold the value for the angle that was last recorded before the limelight was not within distance of the target
    public double lastRecorded0;

    //default speed for the motors
    public final double kdrivespeed = 0.2;
    
    //speeds for the motors as they are pushing up against the wall
    final double crashspeed = 0.4;

    //Keeps track of what stage the command is in
    public int stage;

    //timer for the robot to give it time to push up against the wall
    public Timer time
    = new Timer();
    
    //The command we will be using for aligning with the target
    public static SeekVisionTarget kSeekVisionTarget;

	public NavXSeeker() {
    //requires(Robot.kExampleSubsystem);
		requires(Robot.kNavX);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
        kSeekVisionTarget = new SeekVisionTarget();
        stage = 1;
    }
   
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
        //Sets the arm to move up
        Robot.kShooter.armPositionUp = true;
        
        //Runs through the different steps for scoring
        switch(stage){
            case 1 : 
                //Uses the limelight to align itself and move towards the goal
                Scheduler.getInstance().add(kSeekVisionTarget);
                if(kSeekVisionTarget.isCompleted()){
                    stage ++;   
                } 
                break; 
            case 2 :
                //Records the angle of the target relative to the robot.
                lastRecorded0 = Robot.kLimelight.getLimeX() + Robot.kNavX.getYaw();
                stage ++;
                break;
            case 3 : 
                //Waits for the limit switch to be pressed
                if (!Robot.map.lswitchTop.get()) {
                    stage++;
                }
                break;
            case 4 :
                //Drives toward the target
                Robot.Drive.driveAtAngle(kdrivespeed, lastRecorded0);
                if(Robot.hasCrashed){
                    stage++;
                    //Prepares the timer for the next step
                    time.reset();
                    time.start();
                }
                break;
            case 5 :
                //Runs until the robot crashes into the wall
                Robot.Drive.driveTank(crashspeed, crashspeed);
                if(time.get() > 2){
                    Robot.Drive.stop();
                    stage++;
                }
                break;
            case 6 :
                //Prepares the timer for the next step
                time.reset();
                stage++;
                break;
            case 7 :
                //Shoots the power cells into the goal
                Robot.kShooter.shoot();
                if(time.get() > 1.5){
                    Robot.kShooter.Stop();
                    stage++;
                }
                break;
            default:
                //Stops the robot
                Robot.Drive.stop();
                Robot.kShooter.Stop();
            }

            SmartDashboard.putNumber("Stage: ", stage);
    }
    // Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
        if(stage == 8){
            return true;
        }
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
        Robot.Drive.stop();
        Robot.kShooter.Stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}