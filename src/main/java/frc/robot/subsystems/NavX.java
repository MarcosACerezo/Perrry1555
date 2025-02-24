/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;



//Find out why we can't use this import
//import edu.wpi.first.wpilibj2.PIDController;


//Needs the navX2 for this code implementation
public class NavX extends SubsystemBase {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
    AHRS ahrs;
    double last_world_linear_accel_x;
    double last_world_linear_accel_y;

    final static double kCollisionThreshold_DeltaG = 0.5f;
    
	public NavX(){
        ahrs = new AHRS(NavXComType.kMXP_SPI);
    }

    public void resetNavx(){
        ahrs.reset();
    }
        
    public double Angle() {
        return ahrs.getAngle();
    }
    
    //this is used for steering
    public float getIMUPitch(){
        return ahrs.getPitch();
    }

    public float getYaw(){
        return ahrs.getYaw();
    }

    public float getRoll(){
        return ahrs.getRoll();
    }

    //Velocities in all three dimensions for the navx
    public double getXVelocity(){
        Float floatXVelocity = ahrs.getVelocityX();
        Double doubleXVelocity = (double) floatXVelocity;
        return doubleXVelocity;
    }

    public double getYVelocity(){
        Float floatYVelocity = ahrs.getVelocityY();
        Double doubleYVelocity = (double) floatYVelocity;
        return doubleYVelocity;
    }
    
    public double getZvelocity(){
        Float floatZVelocity = ahrs.getVelocityZ();
        Double doubleZVelocity = (double) floatZVelocity;
        return doubleZVelocity;
    }
    //resets displacement
    public void resetDisplacement(){
        ahrs.resetDisplacement();
    }

    //Displacements for all thre dimensions
    public double getXDisplacement(){
        Float floatXDisplacement = ahrs.getDisplacementX();
        Double doubleXDisplacement = (double) floatXDisplacement;
        return doubleXDisplacement;
    }

    public double getYDisplacement(){
        Float floatYDisplacement = ahrs.getDisplacementY();
        Double doubleYDisplacement = (double) floatYDisplacement;
        return doubleYDisplacement;
    }

    public double getZDisplacement(){
        Float floatZDisplacement = ahrs.getDisplacementZ();
        Double doubleZDisplacement = (double) floatZDisplacement;
        return doubleZDisplacement;
    }
//acceleration in all 3 dimensions
    public double getXAcceleration(){
        Float floatXAcceleration = ahrs.getWorldLinearAccelX();
        Double doubleXAcceleration = (double) floatXAcceleration;
        return doubleXAcceleration;
    }
    public double getYAcceleration(){
        Float floatYAcceleration = ahrs.getWorldLinearAccelY();
        Double doubleYAcceleration = (double) floatYAcceleration;
        return doubleYAcceleration;
    }
    public double getZAcceleration(){
        Float floatZAcceleration = ahrs.getWorldLinearAccelZ();
        Double doubleZAcceleration = (double) floatZAcceleration;
        return doubleZAcceleration;
    }

    //Detects collisions
    //You must start running this method before you want to use it.
    //Additionally, once you start running it you can't stop until you are done with it.
    public boolean hasCrashed() {

        boolean collisionDetected = false;
          
          double curr_world_linear_accel_x = getXAcceleration();
          double currentJerkX = curr_world_linear_accel_x - last_world_linear_accel_x;
          last_world_linear_accel_x = curr_world_linear_accel_x;
          double curr_world_linear_accel_y = getYAcceleration();
          double currentJerkY = curr_world_linear_accel_y - last_world_linear_accel_y;
          last_world_linear_accel_y = curr_world_linear_accel_y;
          
          if ( ( Math.abs(currentJerkX) > kCollisionThreshold_DeltaG ) ||
               ( Math.abs(currentJerkY) > kCollisionThreshold_DeltaG) ) {
              collisionDetected = true;
          }
        return collisionDetected;
    }
}
