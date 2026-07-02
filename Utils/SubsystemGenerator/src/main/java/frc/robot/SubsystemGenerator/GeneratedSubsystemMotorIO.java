package frc.robot.SubsystemGenerator;


/**Default IO interface for a generated subsystem */
public interface GeneratedSubsystemMotorIO {
    
    public static class IOInputs{
        public String canID                  = "";
        public double MotorRelativeRPM       = 0;
        public double MotorRelativePosition  = 0;
        public double MotorIntigratedPosition= 0;
        public double MotorVolts             = 0;
        public double MotorTemp              = 0;
        public double MotorCurrentDraw       = 0;
    }

    /**supply a generatedSubsystemMotorConfig to instance the motor
     * <p> This must be done immediately upon addition of the motor
     * <p> 
    */
    public default void setConfig(GeneratedSubsystemMotorConfig config){}

    /**Set the motor to PID to the provided position on the motor*
     *@param goal rotations
     */
    public default void setPositionGoal(double goal){}

    /**Update the current percieved rotation of the motor
     *@param position rotations
     */
    public default void setPosition(double position){}

    /**Set the motor to PID to the provided position on the mechanism
     *@param goal rotations
     */
    public default void setIntigratedPositionGoal(double goal){}

    /**Set the motor to start spinning at the provided speed from -1 to 1*/
    public default void setFeedForwardSpeed(double speed){}

    /**Set the motor to start spinning at the provided speed from -1 to 1 relative to its */
    public default void setIntigratedFeedForwardSpeed(double speed){}

    /**Set the motor to PID to the provided velocity of itself*/
    public default void setVelocityGoal(double goal){}

    /**Set the neutral mode of the motor*/
    public default void setNuetralMode(boolean braking){}

    public default void updateIOInputs(IOInputs IOInputs){}

    /**Set the motor to start following its leader motor*/
    public default void followLeaderMotor(){}

    /**Set the motor to full stop */
    public default void stopMotor(){}

    /**Sets the motor's angle in sim to the provided angle
     * <p> For use by GeneratedSubsystemMecanism during sim
     *    @param Angle rotations
     */
    public default void updateMotorSimAngle(double Angle){}

    /**Sets the motor's anglular velocity in sim to the provided angle
     * <p>For use by GeneratedSubsystemMecanism during sim
     *    @param AngularVelocity rps
     */
    public default void updateMotorSimAnglularVelocity(double AngularVelocity){}
}