package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {

  private final VictorSPX intake = new VictorSPX(IntakeConstants.intakePort);

  private final DoubleSolenoid intakeRelease = new DoubleSolenoid(IntakeConstants.IntakeReleasePort[0],
                                                                  IntakeConstants.IntakeReleasePort[1]);

  private boolean pos = IntakeConstants.IntakeReleaseDefault;
  
  public Intake() {
  }

  public void IntakeControl(double speed) {
    intake.set(ControlMode.PercentOutput, speed);
  }

  public void IntakeStop() {
    intake.set(ControlMode.PercentOutput, 0);
  }

  public void intakeRelease() {
    if (!pos) {
      intakeRelease.set(Value.kForward);
      pos = true;
    } else if (pos) {
      intakeRelease.set(Value.kReverse);
      pos = false;
    } else {
      intakeRelease.set(Value.kOff);
      pos = false;
    }
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Intake", pos);
  }
}
