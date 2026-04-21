package frc.robot.SubsystemGenerator;

import org.littletonrobotics.junction.AutoLog;

/**Default IO interface for a generated subsystem */
public interface GeneratedSubsystemMotorIO {
    
    @AutoLog
    public static class IOInputs{
        public double MotorRelativeRPM       = 0;
        public double MotorRelativePosition  = 0;
        public double MotorIntigratedPosition= 0;
        public double MotorVolts             = 0;
        public double MotorTemp              = 0;
    }

    public default void setConfig(GeneratedSubsystemMotorConfig config){}

    public default void setPositionGoal(double goal){}

    public default void setPosition(double position){}

    public default void setFeedForwardSpeed(double speed){}

    public default void setVelocityGoal(double goal){}

    public default void setNuetralMode(boolean braking){}

    public default void updateIOInputs(IOInputs IOInputs){}

    public default void followLeaderMotor(){}

    public default void stopMotor(){}
    
}