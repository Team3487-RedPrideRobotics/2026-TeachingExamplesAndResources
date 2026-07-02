package frc.robot;

import edu.wpi.first.math.numbers.*;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import frc.robot.SubsystemGenerator.GainConfig;
import frc.robot.SubsystemGenerator.GeneratedSubsystemMotorConfig;

public class Constants {

    public static GainConfig intakePositionPidGains = new GainConfig()
                                                            .withkP(1)
                                                            .withkI(0)
                                                            .withkD(0);

    public static GeneratedSubsystemMotorConfig intakeMotorConfig = new GeneratedSubsystemMotorConfig()
                                                                    .withCanID(2)
                                                                    .withCanBus("")
                                                                    .withForwardLimitSwitchEnabled(true)
                                                                    .withForwardLimitSwitchPosition(200)
                                                                    .withReverseLimitSwitchEnabled(true)
                                                                    .withReverseLimitSwitchPosition(0)
                                                                    .withMotorToMechanismRatio(80)
                                                                    .withStatorCurrentLimit(60)
                                                                    .withSupplyCurrentLimit(20)
                                                                    .withDCMotorSim(DCMotor.getKrakenX60(1))
                                                                    .withPositionGainConfig(intakePositionPidGains)
                                                                    .withNeutralBreakingMode(false);

    public static LinearSystem<N2,N1,N2> intakeSlapDownLinearSystem = LinearSystemId.createElevatorSystem(DCMotor.getKrakenX60(1), 1, 0.2, 80);
                
}
