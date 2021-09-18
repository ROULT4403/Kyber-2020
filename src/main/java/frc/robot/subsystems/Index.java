package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IndexConstants;

public class Index extends SubsystemBase {

  private final VictorSPX indexLeft = new VictorSPX(IndexConstants.indexLeftPort);
  private final VictorSPX indexRight = new VictorSPX(IndexConstants.indexRightPort);
  
  private final VictorSPX turret = new VictorSPX(IndexConstants.turretPort);

  public Index() {
  }

  public void StorageControl(double speed) {
    indexLeft.set(ControlMode.PercentOutput, speed);
    indexRight.set(ControlMode.PercentOutput, -speed);
  }
  
  public void TurretControl(double speed) {
    turret.set(ControlMode.PercentOutput, speed);
  }

  public void IndexControl(double speed) {
    turret.set(ControlMode.PercentOutput, -speed);
    indexLeft.set(ControlMode.PercentOutput, -speed-0.2);
    indexRight.set(ControlMode.PercentOutput, (speed-0.2));
  }
  
  public void StorageStop() {
    indexLeft.set(ControlMode.PercentOutput, 0);
    indexRight.set(ControlMode.PercentOutput, 0);
  }
  
  public void TurretStop() { 
    turret.set(ControlMode.PercentOutput, 0);
  }

  public void IndexStop() {
    indexLeft.set(ControlMode.PercentOutput, 0);
    indexRight.set(ControlMode.PercentOutput, 0);
    turret.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
