// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


package frc.robot;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.SubsystemGenerator.GeneratedSubsystem;
import frc.robot.SubsystemGenerator.GeneratedSubsystemTalonFxIO;
import frc.robot.SubsystemGenerator.GeneratedSubsystemMechanism;
import frc.robot.SubsystemGenerator.GeneratedSubsystemMotorConfig;
import frc.robot.SubsystemGenerator.GeneratedSubsystemMotorIO; 

public class RobotContainer {

  GeneratedSubsystem armSubsystem;
  GeneratedSubsystemMechanism armSubsystemMechanism;
  GeneratedSubsystemMotorConfig armMotorConfig; 

  GeneratedSubsystem intakeSubsystem;

  CommandXboxController controller;

  public RobotContainer() {
    DriverStation.silenceJoystickConnectionWarning(true);
    controller = new CommandXboxController(0);
    if(Robot.currentMode != Robot.Mode.REPLAY){
      //if is in sim, or is real, use TalonFxIO
      intakeSubsystem = new GeneratedSubsystem("intake")
                              .assignMechanism(new GeneratedSubsystemMechanism("IntakeSlapdown")
                                .addMotor("slapdown1", new GeneratedSubsystemTalonFxIO().addTalonFx(2))
                                .applyMotorConfig("slapdown1", Constants.intakeMotorConfig)
                                .applySimLinearSystem(Constants.intakeSlapDownLinearSystem)
                                .applySimDCMotor(DCMotor.getKrakenX60(1))
                                .setMechanismType(GeneratedSubsystemMechanism.mechanismType.ELEVATOR)
                                .applyMechanismProperties(0.1, 0.25)
                                .applyMechanismDrumProperties(1, 0.2));}
    else{
      //if is in replay, use blankIO
      intakeSubsystem = new GeneratedSubsystem("intake")
                              .assignMechanism(new GeneratedSubsystemMechanism("IntakeSlapdown")
                                .addMotor("slapdown1", new GeneratedSubsystemMotorIO(){})
                                .applyMotorConfig("slapdown1", Constants.intakeMotorConfig)
                                .applySimLinearSystem(Constants.intakeSlapDownLinearSystem)
                                .applySimDCMotor(DCMotor.getKrakenX60(1))
                                .setMechanismType(GeneratedSubsystemMechanism.mechanismType.ELEVATOR)
                                .applyMechanismProperties(0.1, 0.25)
                                .applyMechanismDrumProperties(1, 0.2));}
    
    intakeSubsystem.setMechanism2dOutput(3, 3).setMechanism2dRootPosition(1.5, 1.5);
    intakeSubsystem.getMechanism("IntakeSlapdown").appendMechanismLigament(intakeSubsystem.getMechanismRoot2d());
    configureBindings();
  }

  private void configureBindings() {
    controller.a().whileTrue(intakeSubsystem.getMechanismPIDPositionCommand("IntakeSlapdown", 0.25, 0.01,true));
    controller.b().whileTrue(intakeSubsystem.getMechanismPIDPositionCommand("IntakeSlapdown", 0, 0.01,true));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
