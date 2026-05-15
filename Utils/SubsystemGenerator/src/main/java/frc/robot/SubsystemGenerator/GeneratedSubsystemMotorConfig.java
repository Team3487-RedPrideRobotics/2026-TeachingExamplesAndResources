package frc.robot.SubsystemGenerator;

public class GeneratedSubsystemMotorConfig{

    int motorCanID;

    String motorCanBus;

    double supplyCurrentLimit;
    double statorCurrentLimit;

    /** inverts the rotation of the motor*/boolean isInverted;

    boolean isFollower;
    /** If the motor is a follower, the motor will copy the commands of the leader motor*/double  leaderCanID;

    /** sets the ratio between the motor rotations and mechanism rotations
     * <p> <b> EXAMPLE: </b> 
     *  A 1/10 ratio would mean the mechanism makes one turn every 10 turns of the motor
    */
    double motorToMechanismRatio;

    /** enables the forward rotating limit switch*/ boolean forwardLimitSwitchEnabled; 
    /** enables the reverse rotating limit switch*/ boolean reverseLimitSwitchEnabled;
    /** sets the forward rotating limit switch position in rotations*/ double  forwardLimitSwitchPosition;
    /** sets the reverse rotating limit switch position in rotations*/ double  reverseLimitSwitchPosition;

    GainConfig positionGainConfig;

    GainConfig velocityGainConfig;

    public GeneratedSubsystemMotorConfig(){
        motorCanID = 0;
        supplyCurrentLimit = 40;
        statorCurrentLimit = 60;
        motorToMechanismRatio = 1;
        forwardLimitSwitchEnabled = false;
        reverseLimitSwitchEnabled = false; 
        forwardLimitSwitchPosition = 0;
        reverseLimitSwitchPosition = 0; 
        isFollower = false;
        leaderCanID = 0;
        motorCanBus = "";
        positionGainConfig = new GainConfig();
        velocityGainConfig = new GainConfig();
    }

    /**Sets the CanID of the motor that will be used in the config
     * <p><b>WARNING</b>: if you change the CanID after making the motor IO, this cannot changed during operation!
     * <p>Default is 0
     */
    public GeneratedSubsystemMotorConfig withCanID(int canID){
        this.motorCanID = canID;
        return this;
    }

    /**Sets the CanBus that the motor will be on
     * <p> For the default rio canbus use ""
     * <p> Will automatically convert the inputed string into a CANBus object if on a talonFX
     * <p> Default is ""
     */
    public GeneratedSubsystemMotorConfig withCanBus(String CanBus){
        motorCanBus = CanBus;
        return this;
    } 

    /**Sets the supply current limit for the motor
     * <p> Default is 40 Amps
     * @param Amps
     */
    public GeneratedSubsystemMotorConfig withSupplyCurrentLimit(double Amps){
        supplyCurrentLimit = Amps;
        return this;
    }

    /**Sets the current limit for the stator of the motor
     * <p> Default is 60 Amps
     * @param Amps
     */
    public GeneratedSubsystemMotorConfig withStatorCurrentLimit(double Amps){
        statorCurrentLimit = Amps;
        return this;
    }

    /** sets the ratio between the motor rotations and mechanism rotations
     * <p> <b> EXAMPLE: </b> 
     *  A 1/10 ratio would mean the mechanism makes one turn every 10 turns of the motor
     * <p> Default is 1
     * @param ratio   The ratio from the motor to the mechanism
    */
    public GeneratedSubsystemMotorConfig withMotorToMechanismRatio(double ratio){
        motorToMechanismRatio = ratio;
        return this;
    }

    /** Sets if the forward rotating limit switch is enabled
     * <p> Default is false
    */
    public GeneratedSubsystemMotorConfig withForwardLimitSwitchEnabled(boolean enabled){
        this.forwardLimitSwitchEnabled = enabled;
        return this;
    }

    /** Sets if the reverse rotating limit switch is enabled
     * <p> Default is false
    */
    public GeneratedSubsystemMotorConfig withReverseLimitSwitchEnabled(boolean enabled){
        this.reverseLimitSwitchEnabled = enabled;
        return this;
    }

    /** Sets the forward rotating limit switch position in rotations
     * <p> Default is 0 Turns
    */
    public GeneratedSubsystemMotorConfig withForwardLimitSwitchPosition(double Turns){
        this.forwardLimitSwitchPosition = Turns;
        return this;
    }

    /** Sets the reverse rotating limit switch position in rotations
     * <p>Default is 0 Turns
    */
    public GeneratedSubsystemMotorConfig withReverseLimitSwitchPosition(double Turns){
        this.reverseLimitSwitchPosition = Turns;
        return this;
    }
    
    /** Sets the current position gains to the ones supplied 
     * <p>Default is a default GainConfig
    */
    public GeneratedSubsystemMotorConfig withPositionGainConfig(GainConfig positionGainConfig){
        this.positionGainConfig = positionGainConfig;
        return this;
    }

    /** Sets the current velocity gains to the ones supplied 
     * <p>Default is a default GainConfig
    */
    public GeneratedSubsystemMotorConfig withVelocityGainConfig(GainConfig velocityGainConfig){
        this.velocityGainConfig = velocityGainConfig;
        return this;
    }

    /** Sets if the motor should be a follower 
     * <p>Default is false
    */
    public GeneratedSubsystemMotorConfig withFollowerStatus(boolean isFollower){
        this.isFollower = isFollower;
        return this;
    }

    /** If the motor is set to be a follower, this will set what CAN Id motor it should follow 
     * <p> Default is 0
    */
    public GeneratedSubsystemMotorConfig withLeaderCanID(double canID){
        leaderCanID = canID;
        return this;
    }

}