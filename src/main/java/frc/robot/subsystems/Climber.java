package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.Constants.ClimberConstants;

public class Climber extends SubsystemBase {

  private final DoubleSolenoid StartUp = new DoubleSolenoid(ClimberConstants.climbSolPort[0], 
                                                            ClimberConstants.climbSolPort[1]);

  private final VictorSPX WinchLeft = new VictorSPX(ClimberConstants.winchPortLeft);
  private final VictorSPX WinchRight = new VictorSPX(ClimberConstants.winchPortLeft);

  private boolean pos = ClimberConstants.ClimberReleaseDefault;

  private boolean cmp = ClimberConstants.CompressorDefault;

  public Climber() {

  }

  public void Extend() {
    if (!pos) {
      StartUp.set(Value.kForward);
      pos = true;
    } else if (pos) {
      StartUp.set(Value.kReverse);
      pos = false;
    } else {
      StartUp.set(Value.kOff);
      pos = false;
    }
  }

  public void WinchControl(double speed) {
    WinchLeft.set(ControlMode.PercentOutput, speed);
    WinchRight.set(ControlMode.PercentOutput, speed);
  }

  public void WinchStop() {
    WinchLeft.set(ControlMode.PercentOutput, 0);
    WinchRight.set(ControlMode.PercentOutput, 0);
  }

  public void CompressorControl() {
    if (!cmp) {
      Robot.comp.setClosedLoopControl(true);
      cmp = true;
    } else if (cmp) {
      Robot.comp.setClosedLoopControl(false);
      cmp = false;
    } else {
      Robot.comp.setClosedLoopControl(false);
      cmp = false;
    }
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Climber", pos);
    SmartDashboard.putBoolean("Compressor", cmp);
  }
}
