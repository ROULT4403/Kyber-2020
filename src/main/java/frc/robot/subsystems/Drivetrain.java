package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;

public class Drivetrain extends SubsystemBase {

  private final TalonFX leftMaster = new TalonFX(DrivetrainConstants.leftMasterPort);
  private final TalonFX rightMaster = new TalonFX(DrivetrainConstants.rightMasterPort);
  
  private final TalonFX leftFollower = new TalonFX(DrivetrainConstants.leftFollowerPort);
  private final TalonFX rightFollower = new TalonFX(DrivetrainConstants.rightFollowePort);

  private final DoubleSolenoid DockShift = new DoubleSolenoid(DrivetrainConstants.DockShiftPort[0],
                                                              DrivetrainConstants.DockShiftPort[1]);

  private final AHRS NavX = new AHRS(Port.kMXP);

  private boolean shift = DrivetrainConstants.DockShiftDefault;

  // Acceleration Variables
  private double previousX = 0;
	private double dx = 0.3;

	private double previousY = 0;
	private double dy = 0.3;

  public Drivetrain() {
        leftFollower.follow(leftMaster);
        rightFollower.follow(rightMaster);

        rightMaster.setInverted(true);
        //rightFollower.setInverted(true);

  }

  public void arcadeDrive(double fwd, double rot) {
    leftMaster.set(ControlMode.PercentOutput, -fwd * DrivetrainConstants.maxFwd, DemandType.ArbitraryFeedForward, rot * DrivetrainConstants.maxRot);
    rightMaster.set(ControlMode.PercentOutput, -fwd * DrivetrainConstants.maxFwd, DemandType.ArbitraryFeedForward, -rot * DrivetrainConstants.maxRot);
  }

  public void arcadeAccel(double fwd, double rot) {
    double y = fwd * DrivetrainConstants.maxFwd;
    if (y > previousY + dy) {
      y = previousY + dy;
    } else if (y < previousY - dy) {
      y = previousY - dy;
    }
    previousY = y;
    // Restrict X
    double x = rot * DrivetrainConstants.maxRot;
    if (x > previousX + dx) {
      x = previousX + dx;
    } else if (x < previousX - dx) {
      x = previousX - dx;
    }
    previousX = x;

    leftMaster.set(ControlMode.PercentOutput, -y * DrivetrainConstants.maxFwd, DemandType.ArbitraryFeedForward, x * DrivetrainConstants.maxRot);
    rightMaster.set(ControlMode.PercentOutput, -y * DrivetrainConstants.maxFwd, DemandType.ArbitraryFeedForward, -x * DrivetrainConstants.maxRot);
    
  }

  public void DockShift() {
    if (!shift) {
      DockShift.set(Value.kForward);
      shift = true;
    } else if (shift) {
      DockShift.set(Value.kReverse);
      shift = false;
    } else {
      DockShift.set(Value.kOff);
      shift = false;
    }
  }

  public void DriveStop() {
    leftMaster.set(ControlMode.PercentOutput, 0);
    rightMaster.set(ControlMode.PercentOutput, 0);
  }

  public double getHeading() {
    return NavX.getYaw();
  }
  
  public void resetHeading() {
    NavX.reset();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Yaw", getHeading());
    SmartDashboard.putBoolean("DockShift", shift);
  }
}
