// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Subsystems.MotorSubsystem;

public class RobotContainer {
  
  CommandXboxController driverXboxController;

  MotorSubsystem motorSubsystem;
  
  public RobotContainer() {
    
    driverXboxController = new CommandXboxController(21); //initializes a new Xbox Controller with triggers for command based robot programming at the 0th driverstation port
    
    motorSubsystem = new MotorSubsystem();

    configureBindings(); //always do the configiure bindings as one of the last steps in the robot container constructor
    
  }

  private void configureBindings() { //this function constructs the triggers to activate commands through external inputs
    driverXboxController.a().whileTrue(motorSubsystem.runMotorCommand(1, false));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
