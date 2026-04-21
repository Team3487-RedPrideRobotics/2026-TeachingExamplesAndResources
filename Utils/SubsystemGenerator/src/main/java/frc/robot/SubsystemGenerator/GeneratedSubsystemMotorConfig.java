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
    }

    /**Sets the CanID of the motor that will be used in the config
     * <p><b>WARNING</b>: if you change the CanID after making the motor IO, this cannot changed during operation!
     */
    public GeneratedSubsystemMotorConfig withCanID(int canID){
        this.motorCanID = canID;
        return this;
    }

    public GeneratedSubsystemMotorConfig withCanBus(String CanBus){
        motorCanBus = CanBus;
        return this;
    } 

    public GeneratedSubsystemMotorConfig withMotorToMechanismRatio(double ratio){
        motorToMechanismRatio = ratio;
        return this;
    }

    /** enables the forward rotating limit switch*/
    public GeneratedSubsystemMotorConfig withForwardLimitSwitchEnabled(boolean enabled){
        this.forwardLimitSwitchEnabled = enabled;
        return this;
    }

    /** enables the reverse rotating limit switch*/
    public GeneratedSubsystemMotorConfig withReverseLimitSwitchEnabled(boolean enabled){
        this.reverseLimitSwitchEnabled = enabled;
        return this;
    }

    /** sets the forward rotating limit switch position in rotations*/
    public GeneratedSubsystemMotorConfig withForwardLimitSwitchPosition(double turns){
        this.forwardLimitSwitchPosition = turns;
        return this;
    }

    /** sets the reverse rotating limit switch position in rotations*/
    public GeneratedSubsystemMotorConfig withReverseLimitSwitchPosition(double turns){
        this.reverseLimitSwitchPosition = turns;
        return this;
    }
    
    /** sets the current position gains to the ones supplied */
    public GeneratedSubsystemMotorConfig withPositionGainConfig(GainConfig positionGainConfig){
        this.positionGainConfig = positionGainConfig;
        return this;
    }

    /** sets the current velocity gains to the ones supplied */
    public GeneratedSubsystemMotorConfig withVelocityGainConfig(GainConfig velocityGainConfig){
        this.velocityGainConfig = velocityGainConfig;
        return this;
    }

    /** Sets if the motor should be a follower */
    public GeneratedSubsystemMotorConfig withFollowerStatus(boolean isFollower){
        this.isFollower = isFollower;
        return this;
    }

    /** If the motor is set to be a follower, this will set what CAN Id motor it should follow */
    public GeneratedSubsystemMotorConfig withLeaderCanID(double canID){
        leaderCanID = canID;
        return this;
    }

}