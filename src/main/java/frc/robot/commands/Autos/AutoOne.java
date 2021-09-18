/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class AutoOne extends SequentialCommandGroup {

  public AutoOne(Drivetrain s_Drivetrain, Shooter s_Shooter, Index s_Index, Intake s_Intake) {
    // super(new FooCommand(), new BarCommand());
    super(
      new RunCommand(s_Shooter::shootM, s_Shooter).withTimeout(3),
      new ParallelCommandGroup(new RunCommand(s_Shooter::shootM, s_Shooter),
                               new RunCommand(() -> s_Index.IndexControl(-0.8))).withTimeout(8),
      new ParallelCommandGroup(new InstantCommand(s_Shooter::ShootStop, s_Shooter),
                               new InstantCommand(s_Index::IndexStop, s_Index)).withTimeout(0.5),
      new RunCommand(() -> s_Drivetrain.arcadeAccel(0.6, 0), s_Drivetrain).withTimeout(2.5)
    );
  }
}
