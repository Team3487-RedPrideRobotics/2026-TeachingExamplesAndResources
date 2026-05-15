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

    /**supply a generatedSubsystemMotorConfig to instance the motor
     * <p> This must be done immediately upon addition of the motor
     * <p> 
    */
    public default void setConfig(GeneratedSubsystemMotorConfig config){}

    /**Set the motor to PID to the provided position on the motor*
     * <p> <b>Units:</b> Turns*/
    public default void setPositionGoal(double goal){}

    /**Update the current percieved rotation of the motor */
    public default void setPosition(double position){}

    /**Set the motor to PID to the provided position on the mechanism
     *<p> <b>Units:</b> Turns*/
    public default void setIntigratedPositionGoal(double goal){}

    /**Set the motor to start spinning at the proveded speed from -1-1*/
    public default void setFeedForwardSpeed(double speed){}

    /**Set the motor to PID to the provided velocity of itself*/
    public default void setVelocityGoal(double goal){}

    /**Set the neutral mode of the motor*/
    public default void setNuetralMode(boolean braking){}

    public default void updateIOInputs(IOInputs IOInputs){}

    /**Set the motor to start following its leader motor*/
    public default void followLeaderMotor(){}

    public default void stopMotor(){}
    
}