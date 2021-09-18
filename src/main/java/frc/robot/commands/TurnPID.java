/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.subsystems.Drivetrain;

public class TurnPID extends PIDCommand {
  /**
   * Creates a new TurnPID.
   */
  public TurnPID(double targetAngleDegrees, Drivetrain drive) {
    super(
      new PIDController(DrivetrainConstants.kP, DrivetrainConstants.kI, DrivetrainConstants.kD),
      // Close loop on heading
      drive::getHeading,
      // Set reference to target
      targetAngleDegrees,
      // Pipe output to turn robot
      output -> drive.arcadeDrive(0, output),
      // Require the drive
      drive);
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    getController().enableContinuousInput(-180, 180);
    // Set the controller tolerance - the delta tolerance ensures the robot is stationary at the
    // setpoint before it is considered as having reached the reference
    getController().setTolerance(2.0f);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
  return getController().atSetpoint();
}
}
