/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;

public class AutoTwo extends SequentialCommandGroup {

  public AutoTwo(Drivetrain s_Drivetrain) {
    // super(new FooCommand(), new BarCommand());
    super(
      new RunCommand(() -> s_Drivetrain.arcadeAccel(-0.6, 0), s_Drivetrain).withTimeout(6)
    );
  }
}
