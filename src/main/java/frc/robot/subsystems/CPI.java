/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class CPI extends SubsystemBase {
    private Talon spin;
	public CPI(){
        spin = new Talon(Constants.CPI.CPISpinnerIndex);
    }
    // Run lift motor until it is at the appropriate level with the control panel
    public static void lift() {
        // PID system is necessary for the accuracy of this method 
    }
    // Rotation Control
    public  void FullPower() {
        spin.set(1);
    }
    public  void Stop() {
        spin.set(0);
    }
    public  void Reverse() {
        spin.set(-1);
    }
}
