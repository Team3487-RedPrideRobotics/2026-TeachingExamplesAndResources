// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.SubsystemGenerator.GeneratedSubsystem;
import frc.robot.SubsystemGenerator.GeneratedSubsystemMechanism;
import frc.robot.SubsystemGenerator.GeneratedSubsystemMotorConfig;
import frc.robot.SubsystemGenerator.GeneratedSubsystemTalonFxIO;

public class RobotContainer {

  GeneratedSubsystem armSubsystem;
  GeneratedSubsystemMechanism armSubsystemMechanism;
  GeneratedSubsystemMotorConfig armMotorConfig; 

  CommandXboxController controller;

  public RobotContainer() {
    configureBindings();

    armSubsystem = new GeneratedSubsystem("Arm");

    armSubsystemMechanism = new GeneratedSubsystemMechanism("armPivot")
                                .addMotor("armMotor1", 
                            new GeneratedSubsystemTalonFxIO())
                            .applyMotorConfig("armMotor1", armMotorConfig);
  }

  private void configureBindings() {
    controller.a().whileTrue(armSubsystem.getMechanismPIDPositionCommand("armPivot", 1, 0.1));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
