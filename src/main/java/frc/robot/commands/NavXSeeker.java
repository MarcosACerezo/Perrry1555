/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.NavX;



/**
 * An example command.  You can replace me with your own command.
 */
public class NavXSeeker extends Command {
    //necessary subsystems
    private NavX m_navX;
    private DriveTrain m_driveTrain;
    private Shooter m_shooter;
    private Limelight m_limelight;

    public NavXSeeker(NavX navX, DriveTrain driveTrain, Shooter shooter, Limelight limelight) {
        m_navX = navX;
        m_driveTrain = driveTrain;
        m_shooter = shooter;
        m_limelight = limelight;
        addRequirements(m_navX, m_driveTrain, m_shooter, limelight);
    }

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

	

	// Called just before this Command runs the first time
	@Override
	public void initialize() {
        kSeekVisionTarget = new SeekVisionTarget(m_driveTrain, m_limelight);
        stage = 1;
    }
   
	// Called repeatedly when this Command is scheduled to run
	@Override
	public void execute() {
        //Sets the arm to move up
        m_shooter.setArmPosition(true);
        //Runs through the different steps for scoring
        switch(stage){
            case 1 : 
                //Uses the limelight to align itself and move towards the goal
                CommandScheduler.getInstance().schedule(kSeekVisionTarget);
                if(kSeekVisionTarget.isFinished()){
                    stage ++;   
                } 
                break; 
            case 2 :
                //Records the angle of the target relative to the robot.
                lastRecorded0 = m_limelight.getLimeX() + m_navX.getYaw();
                stage ++;
                break;
            case 3 : 
                //Waits for the limit switch to be pressed
                if (m_shooter.topLimSwitchPressed()) {
                    stage++;
                }
                break;
            case 4 :
                //Drives toward the target
                m_driveTrain.driveAtAngle(kdrivespeed, lastRecorded0, m_navX);
                if(m_navX.hasCrashed()){
                    stage++;
                    //Prepares the timer for the next step
                    time.reset();
                    time.start();
                }
                break;
            case 5 :
                //Runs until the robot crashes into the wall
                m_driveTrain.driveTank(crashspeed, crashspeed);
                if(time.get() > 2){
                    m_driveTrain.stop();
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
                m_shooter.shoot();
                if(time.get() > 1.5){
                    m_shooter.stop();
                    stage++;
                }
                break;
            default:
                //Stops the robot
                m_driveTrain.stop();
                m_shooter.stop();
            }

            SmartDashboard.putNumber("Stage: ", stage);
    }
    // Make this return true when this Command no longer needs to run execute()
	@Override
	public boolean isFinished() {
        if(stage == 8){
            return true;
        }
		return false;
	}

	// Called once after isFinished returns true
	@Override
	public void end(boolean interrupted) {
        m_driveTrain.stop();
        m_shooter.stop();
	}
}