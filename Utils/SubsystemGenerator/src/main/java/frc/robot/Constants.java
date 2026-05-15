package frc.robot;

import frc.robot.SubsystemGenerator.GainConfig;
import frc.robot.SubsystemGenerator.GeneratedSubsystemMotorConfig;

public class Constants {

    public static GainConfig intakePositionPidGains = new GainConfig()
                                                            .withkP(0.5)
                                                            .withkI(0)
                                                            .withkD(0);
                                                            

    public static GeneratedSubsystemMotorConfig intakeConfig = new GeneratedSubsystemMotorConfig()
                                                                    .withCanID(2)
                                                                    .withCanBus("")
                                                                    .withForwardLimitSwitchEnabled(true)
                                                                    .withForwardLimitSwitchPosition(20)
                                                                    .withReverseLimitSwitchEnabled(true)
                                                                    .withReverseLimitSwitchPosition(0)
                                                                    .withMotorToMechanismRatio(10)
                                                                    .withStatorCurrentLimit(60)
                                                                    .withSupplyCurrentLimit(20);

                
}
