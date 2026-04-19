package frc.robot.Subsystems;

import static edu.wpi.first.units.Units.Amps;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import  edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MotorSubsystem extends SubsystemBase{

    TalonFX m_motor;
    DutyCycleOut m_motorDutyCycleOut;
    CurrentLimitsConfigs m_motorCurrentLimits;
    private MotorSubsystem thisSubsystem;

    public MotorSubsystem(){
        m_motor = new TalonFX(0);
        m_motorDutyCycleOut = new DutyCycleOut(0);
        m_motorCurrentLimits = new CurrentLimitsConfigs()
                                   .withStatorCurrentLimit(Amps.of(40))
                                   .withSupplyCurrentLimit(Amps.of(20));
        thisSubsystem = this;
        m_motor.getConfigurator().apply(m_motorCurrentLimits);
    }

    public void moveMotorDutyCycle(double speed){
        m_motorDutyCycleOut.withOutput(speed);
        m_motor.setControl(m_motorDutyCycleOut);
    } 

    public void stopMotor(){
        m_motorDutyCycleOut.withOutput(0);
        m_motor.setControl(m_motorDutyCycleOut);
        m_motor.stopMotor();
    }

    public CurrentLimitsConfigs getMotorCurrentLimits(){
        return m_motorCurrentLimits;
    }

    public Command runMotorCommand(double speed,boolean addRequirements){
        final Command thisCommand = new Command() {
            @Override
            public void execute() {
                thisSubsystem.moveMotorDutyCycle(speed);
            }
            @Override
            public void end(boolean interrupted) {
                thisSubsystem.stopMotor();
            }
        };
        if(addRequirements){
            thisCommand.addRequirements(this);
        }
        return thisCommand;
    }
}