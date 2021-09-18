package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DefaultDrive extends CommandBase {

  private final Drivetrain s_Drive;
  private final DoubleSupplier v_Fwd;
  private final DoubleSupplier v_Rot;

  public DefaultDrive(Drivetrain subsystem, DoubleSupplier fwd, DoubleSupplier rot) {
    // Use addRequirements() here to declare subsystem dependencies.
    s_Drive = subsystem;
    v_Fwd = fwd;
    v_Rot = rot;
    
    addRequirements(s_Drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    s_Drive.arcadeDrive(v_Fwd.getAsDouble(), v_Rot.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
