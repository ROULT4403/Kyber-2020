package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {

  private final VictorSPX shooterLeft = new VictorSPX(ShooterConstants.shooterLeftPort);
  private final VictorSPX shooterRight = new VictorSPX(ShooterConstants.shooterRightPort);

  private final Servo HoodServo = new Servo(ShooterConstants.servoPort);
  
  private final DoubleSolenoid HoodSolenoid = new DoubleSolenoid(ShooterConstants.HoodPort[0],
                                              ShooterConstants.HoodPort[1]);

  private final Encoder shooterEnc = new Encoder(ShooterConstants.shootEncPort[0], ShooterConstants.shootEncPort[1], 
                                     true, EncodingType.k4X);

  private final Relay Led = new Relay(ShooterConstants.relayPort);

  private PIDController ShooterPID;

  private boolean toggle = ShooterConstants.relayDefault;
  private boolean hood = ShooterConstants.hoodDefault;
  private double angle;

  public Shooter() {
        // The PIDController used by the subsystem
        ShooterPID = new PIDController(ShooterConstants.kP, ShooterConstants.kI, ShooterConstants.kD);
        ShooterPID.setTolerance(10);
        shooterEnc.setDistancePerPulse(0.00097656);
        ShooterPID.setSetpoint(300);
  }

  public void shootM() {
    shooterLeft.set(ControlMode.PercentOutput, 1);
    shooterRight.set(ControlMode.PercentOutput, 1);
  }

  public void shootPID() {
    double pidOut = ShooterPID.calculate(shooterEnc.getRate());
    if (pidOut < 0.1) {
      pidOut = 0.1;
    } else if (pidOut > 1) {
      pidOut = 1;
    }
    shooterLeft.set(ControlMode.PercentOutput, -pidOut);
    shooterRight.set(ControlMode.PercentOutput, -pidOut);
  }

  public void ShootStop() {
    shooterLeft.set(ControlMode.PercentOutput, 0);
    shooterRight.set(ControlMode.PercentOutput, 0);
  }

  public void LedToggle() {
    if (!toggle) {
      Led.set(Relay.Value.kForward);
      toggle = true;
    } else if (toggle) {
      Led.set(Relay.Value.kReverse);
      toggle = false;
    }
  }

  public void ServoControl() {
    if (!hood) {
      angle = ShooterConstants.hoodHigh;
      hood = true;
    } else if (hood){
      angle = ShooterConstants.hoodLow;
      hood = false;
    }
    HoodServo.setAngle(angle);
  }

  public void HoodControl() {
    if (!hood) {
      HoodSolenoid.set(Value.kForward);
      hood = true;
    } else if (hood){
      HoodSolenoid.set(Value.kReverse);
      hood = false;
    } else {
      hood = false;
      HoodSolenoid.set(Value.kOff);
    }
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Flywheel Rate", shooterEnc.getRate());
    SmartDashboard.putBoolean("Hood", hood);

  }

}
