package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 *
 */
public class DriveTrain extends SubsystemBase {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    //Declaring motors, two motors controlled by one motor controller
    public static  Victor leftMotors;
    public static Victor rightMotors;

    public DriveTrain(){
        leftMotors = new Victor(Constants.DriveTrain.leftDriveIndex);
        rightMotors = new Victor(Constants.DriveTrain.rightDriveIndex);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    //Stops the drive train
    public void stop() {
    	leftMotors.set(0);
        rightMotors.set(0);
    }
    
    //Drives the robot with two separate powers for the wheels
    public void driveTank(double Lspeed, double Rspeed) {
    	leftMotors.set(Lspeed);
        rightMotors.set(-Rspeed);
    }

    //Drives the robot as if the front were the back
    public void driveTankInverted(double Lspeed, double Rspeed) {
    	leftMotors.set(-Rspeed);
    	rightMotors.set(Lspeed);
    }
    
    //Drives straight
    public void driveStraight(double speed) {
    	leftMotors.set(-speed);
    	rightMotors.set(speed);
    }
    
    //Function pending
    //Will allow you to drive with separate powers for the wheels for a set amount of time
    public void driveTank(double Lspeed, double Rspeed, long time) {
    	driveTank(Lspeed, Rspeed);
    	//insert wait command here
    	stop();
    }
    
    //Function pending
    //Will allow you to drive straight for a set amount of time
    public void driveStraight(double speed, double time) {
    	driveStraight(speed);
    	//insert wait command here
    	stop();
    }

    //TODO Implement this method 
    // public void driveAtAngle(double speed, double angle) {
    //     double error = Robot.kNavX.getYaw() - angle;
    //     double steeringConstant = 0.1;
    //     double maxSteeringAdjust = 0.3;
    //     if (error > 180) {
    //         error =- 360;
    //     }
    //     if (error < -180) {
    //         error =+ 360;
    //     }
    //     double steeringAdjust = error*steeringConstant;
    //     if (steeringAdjust > maxSteeringAdjust) {
    //         steeringAdjust = maxSteeringAdjust;
    //     }
    //     if (steeringAdjust < -maxSteeringAdjust) {
    //         steeringAdjust = -maxSteeringAdjust;
    //     }

    //     driveTank((speed - steeringAdjust), (speed + steeringAdjust));
    // }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("Left Drive Speed: ", leftMotors.get());
        SmartDashboard.putNumber("Right Drive Speed: ", rightMotors.get());
    }
}

