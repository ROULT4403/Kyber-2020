package frc.robot;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  public static Compressor comp = new Compressor();

  NetworkTableEntry yaw_angle;
  NetworkTableEntry pitch_angle;

  public static double yaw;
  public static double pitch;
  
  @Override
  @SuppressWarnings("unused")
  public void robotInit() {
    comp.setClosedLoopControl(false);

    NetworkTableInstance ntinst = NetworkTableInstance.getDefault();
    NetworkTable table = ntinst.getTable("tablaCool");

    yaw_angle = table.getEntry("YawAngle");
    pitch_angle = table.getEntry("PitchAngle");
    
    yaw_angle.setDefaultDouble(0.0);
    pitch_angle.setDefaultDouble(0.0);

    new Thread(() -> {
      UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);
      camera.setResolution(320, 240);
      UsbCamera camera2 = CameraServer.getInstance().startAutomaticCapture(1);
      camera2.setResolution(320, 240);
      
      CvSink cvsink = CameraServer.getInstance().getVideo();
      CvSource outputstream = CameraServer.getInstance().putVideo("Blur", 640, 480);

      Mat source = new Mat();
      Mat output = new Mat();
      while (!Thread.interrupted()){
        cvsink.grabFrame(source);
        Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
        outputstream.putFrame(output);
      }
    }).start();

    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();

      yaw = yaw_angle.getDouble(0.0);
      pitch = pitch_angle.getDouble(0.0);

      SmartDashboard.putNumber("YawPRI", yaw);
      SmartDashboard.putNumber("PitchPRI", pitch);
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();
    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }
}
