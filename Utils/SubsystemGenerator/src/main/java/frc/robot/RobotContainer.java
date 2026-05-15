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

  GeneratedSubsystem intakeSubsystem;

  CommandXboxController controller;

  public RobotContainer() {
    controller = new CommandXboxController(0);

    intakeSubsystem = new GeneratedSubsystem("intake")
                            .assignMechanism(new GeneratedSubsystemMechanism("IntakeSlapdown")
                              .addMotor("slapdown1", new GeneratedSubsystemTalonFxIO().addTalonFx(2))
                              .applyMotorConfig("slapdown1", Constants.intakeConfig));
  
    configureBindings();
  }

  private void configureBindings() {
    controller.a().whileTrue(intakeSubsystem.getMechanismPIDPositionCommand("IntakeSlapdown", 0.25, 0.1,true));
    controller.b().whileTrue(intakeSubsystem.getMechansimFeedForwardCommand("IntakeSlapdown", 0.1));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
