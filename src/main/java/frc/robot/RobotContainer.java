package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.Constants.RobotConstants;
import frc.robot.commands.*;
import frc.robot.commands.Autos.*;
import frc.robot.subsystems.*;

public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain s_Drive = new Drivetrain();
  private final Shooter s_Shooter = new Shooter();
  private final Intake s_Intake = new Intake();
  private final Climber s_Climber = new Climber();
  private final Index s_Index = new Index();

  private final Command a_AutoOne = new AutoOne(s_Drive, s_Shooter, s_Index, s_Intake);
  private final Command a_AutoTwo = new AutoTwo(s_Drive);
  private final Command a_AutoThree = new AutoThree(s_Drive);

  SendableChooser<Command> m_chooser = new SendableChooser<>();

  Joystick driver = new Joystick(RobotConstants.driverPort);
  Joystick controller = new Joystick(RobotConstants.controllerPort);

    // Driver Control
    Button d_A = new JoystickButton(driver, 1);
    Button d_B = new JoystickButton(driver, 2);
    Button d_X = new JoystickButton(driver, 3);
    Button d_Y = new JoystickButton(driver, 4);
    Button d_LB = new JoystickButton(driver, 5);
    Button d_RB= new JoystickButton(driver, 6);
    Button d_Select = new JoystickButton(driver, 7);
    Button d_Start = new JoystickButton(driver, 8);
    Button d_LSClick = new JoystickButton(driver, 9);
    Button d_RSClick = new JoystickButton(driver, 10);
    POVButton d_Pad0 = new POVButton(driver, 0);
    POVButton d_Pad90 = new POVButton(driver, 90);
    POVButton d_Pad180 = new POVButton(driver, 180);
    POVButton d_Pad270 = new POVButton(driver, 270);
  
    // Mechanism Control
    Button c_A = new JoystickButton(controller, 1);
    Button c_B = new JoystickButton(controller, 2);
    Button c_X = new JoystickButton(controller, 3);
    Button c_Y = new JoystickButton(controller, 4);
    Button c_LB = new JoystickButton(controller, 5);
    Button c_RB= new JoystickButton(controller, 6);
    Button c_Select = new JoystickButton(controller, 7);
    Button c_Start = new JoystickButton(controller, 8);
    Button c_LSClick = new JoystickButton(controller, 9);
    Button c_RSClick = new JoystickButton(controller, 10);
    POVButton c_Pad0 = new POVButton(controller, 0);
    POVButton c_Pad90 = new POVButton(controller, 90);
    POVButton c_Pad180 = new POVButton(controller, 180);
    POVButton c_Pad270 = new POVButton(controller, 270);

  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    s_Drive.setDefaultCommand(new DefaultDrive(s_Drive, 
                              () -> Deadband(driver.getRawAxis(1)), 
                              () -> Deadband(driver.getRawAxis(4))));

    m_chooser.addOption("Shoot+Move", a_AutoOne);
    m_chooser.addOption("Just Move Back", a_AutoTwo);
    m_chooser.addOption("Just Move Fwd", a_AutoThree);

    Shuffleboard.getTab("Autonomous").add(m_chooser);
  }

  private void configureButtonBindings() {
    // Driver Control
    // Drivetrain Control
    d_LSClick.whenPressed(new InstantCommand(s_Drive::DockShift, s_Drive));

    // LEDs
     d_A.whenPressed(new InstantCommand(s_Shooter::LedToggle, s_Shooter));

    // Intake control
    d_LB.whenPressed(new InstantCommand(s_Intake::intakeRelease, s_Intake));
    d_RB.whenPressed(new RunCommand(() -> s_Intake.IntakeControl(-0.69), s_Intake));
    d_RB.whenReleased(new InstantCommand(s_Intake::IntakeStop, s_Intake));
    d_Pad0.whenPressed(new RunCommand(() -> s_Intake.IntakeControl(0.69), s_Intake));
    d_Pad0.whenReleased(new InstantCommand(s_Intake::IntakeStop, s_Intake));
    
    // Vision
    d_Pad90.whenPressed(new Turn(Robot.yaw, s_Drive).withTimeout(2));
    

    // Mechanism Control
    // Shooter Control
    c_RB.whenPressed(new RunCommand(s_Shooter::shootM, s_Shooter));
    c_RB.whenReleased(new InstantCommand(s_Shooter::ShootStop, s_Shooter));
    
    // Hood Control
    c_RSClick.whenPressed(new InstantCommand(s_Shooter::HoodControl, s_Shooter));

    // Climber Control
    c_B.whenPressed(new InstantCommand(s_Climber::Extend, s_Climber));
    c_Y.whenPressed(new RunCommand(() -> s_Climber.WinchControl(0.6), s_Climber));
    c_Y.whenReleased(new InstantCommand(s_Climber::WinchStop, s_Climber));
    c_X.whenPressed(new RunCommand(() -> s_Climber.WinchControl(-0.6), s_Climber));
    c_X.whenReleased(new InstantCommand(s_Climber::WinchStop, s_Climber));

    // Index Control
    c_LB.whenPressed(new RunCommand(() -> s_Index.IndexControl(1), s_Index));
    c_LB.whenReleased(new InstantCommand(s_Index::IndexStop, s_Index));
    c_A.whenPressed(new RunCommand(() -> s_Index.TurretControl(1), s_Index));
    c_A.whenReleased(new InstantCommand(s_Index::TurretStop, s_Index));

    // Compressor Cpntrol
    c_LSClick.whenPressed(new InstantCommand(s_Climber::CompressorControl, s_Climber));

  }

  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
  }

  double Deadband(double value) {
		/* Upper deadband */
		if (Math.abs(value) >= +0.1) {
			return value;
    } else {
    return 0;
    }
  }
}
