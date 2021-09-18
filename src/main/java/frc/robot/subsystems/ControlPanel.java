/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ControlPanelConstants;

public class ControlPanel extends SubsystemBase {

  private final VictorSPX ControlPanel = new VictorSPX(ControlPanelConstants.controlPanelPort);

  private final DoubleSolenoid PanelRelease = new DoubleSolenoid(ControlPanelConstants.ControlRelease[0],
                                                              ControlPanelConstants.ControlRelease[1]);

  private boolean pos = ControlPanelConstants.ControlPanelDefault;

  public ControlPanel() {
  }

  public void PanelRelease() {
    if (!pos) {
      PanelRelease.set(Value.kForward);
      pos = true;
    } else if (pos) {
      PanelRelease.set(Value.kReverse);
      pos = false;
    } else {
      PanelRelease.set(Value.kOff);
      pos = false;
    }
  }

  public void PanelM(double speed) {
    ControlPanel.set(ControlMode.PercentOutput, speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
