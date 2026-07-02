package frc.robot.SubsystemGenerator;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.Slot1Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.sim.TalonFXSimState;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.RobotBase;

/**IO interface to be used with TalonFX motor controllers in the subsystem generator */
public class GeneratedSubsystemTalonFxIO implements GeneratedSubsystemMotorIO {

    //status signal definition
    private StatusSignal<Angle>           position;
    private StatusSignal<AngularVelocity> rps;
    private StatusSignal<Temperature>     temperature;
    private StatusSignal<Voltage>         voltage;
    private StatusSignal<Current>         current;

    //motor definition
    private TalonFX motor;

    private TalonFXSimState motorSimState;

    private GeneratedSubsystemMotorConfig motorConfig;

    private double ratio;

    private boolean isFollower;

    //control definitions
    private PositionDutyCycle positionPID;
    private VelocityDutyCycle velocityPID;
    private DutyCycleOut      dutyCycle;
    private Follower          followerControl;

    public GeneratedSubsystemTalonFxIO(){
        motor = new TalonFX(0);
        motorSimState = motor.getSimState();

        positionPID = new PositionDutyCycle(0);
        velocityPID = new VelocityDutyCycle(0);
        dutyCycle   = new DutyCycleOut(0);

        position    = motor.getPosition();
        temperature = motor.getDeviceTemp();
        rps         = motor.getVelocity();
        voltage     = motor.getMotorVoltage();
        current     = motor.getStatorCurrent();

        BaseStatusSignal.setUpdateFrequencyForAll(50, position,rps,temperature,voltage);
    } 

    @Override
    public void updateIOInputs(IOInputs IOInputs) {
        IOInputs.canID                   = motorConfig.motorCanBus+motorConfig.motorCanID;
        IOInputs.MotorIntigratedPosition = position.getValueAsDouble()/ratio;
        IOInputs.MotorRelativePosition   = position.getValueAsDouble();
        IOInputs.MotorRelativeRPM        = rps.getValueAsDouble()*60;
        IOInputs.MotorTemp               = temperature.getValueAsDouble();
        IOInputs.MotorVolts              = voltage.getValueAsDouble();
        IOInputs.MotorCurrentDraw        = current.getValueAsDouble();
        BaseStatusSignal.refreshAll(position,rps,temperature,voltage,current);
        if(RobotBase.isSimulation()){
        IOInputs.MotorVolts              = motorSimState.getMotorVoltage();
        }
    }

    private TalonFXConfiguration convertMotorConfigToTalonConfig(GeneratedSubsystemMotorConfig inputConfig){
        TalonFXConfiguration config = new TalonFXConfiguration();
        
        Slot0Configs positionConfig = new Slot0Configs();
        Slot1Configs velocityConfig = new Slot1Configs();
        
        config.MotorOutput.withInverted(inputConfig.isInverted?InvertedValue.Clockwise_Positive:InvertedValue.CounterClockwise_Positive);
        config.MotorOutput.withNeutralMode(inputConfig.neutralBrakingMode?NeutralModeValue.Brake:NeutralModeValue.Coast);
        config.SoftwareLimitSwitch.ForwardSoftLimitEnable = inputConfig.forwardLimitSwitchEnabled;
        config.SoftwareLimitSwitch.ReverseSoftLimitEnable = inputConfig.reverseLimitSwitchEnabled;
        config.SoftwareLimitSwitch.ForwardSoftLimitThreshold = inputConfig.forwardLimitSwitchPosition;
        config.SoftwareLimitSwitch.ReverseSoftLimitThreshold = inputConfig.reverseLimitSwitchPosition;
        config.CurrentLimits.StatorCurrentLimit = inputConfig.statorCurrentLimit;
        config.CurrentLimits.SupplyCurrentLimit = inputConfig.supplyCurrentLimit;

        positionConfig.kA = inputConfig.positionGainConfig.kA;
        positionConfig.kS = inputConfig.positionGainConfig.kS;
        positionConfig.kP = inputConfig.positionGainConfig.kP;
        positionConfig.kI = inputConfig.positionGainConfig.kI;
        positionConfig.kD = inputConfig.positionGainConfig.kD;
        positionConfig.kV = inputConfig.positionGainConfig.kV;

        velocityConfig.kA = inputConfig.velocityGainConfig.kA;
        velocityConfig.kS = inputConfig.velocityGainConfig.kS;
        velocityConfig.kP = inputConfig.velocityGainConfig.kP;
        velocityConfig.kI = inputConfig.velocityGainConfig.kI;
        velocityConfig.kD = inputConfig.velocityGainConfig.kD;
        velocityConfig.kV = inputConfig.velocityGainConfig.kV;

        config.Slot0 = positionConfig;
        config.Slot1 = velocityConfig;


        return config;
    }

    public GeneratedSubsystemTalonFxIO addTalonFx(int MotorID){
        motor = new TalonFX(MotorID);
        return this;
    }

    @Override
    public void setConfig(GeneratedSubsystemMotorConfig config) {
        motorConfig = config;
        if(motor==null){
            //if motor doesnt exist, make one
            motor = new TalonFX(config.motorCanID,new CANBus(config.motorCanBus));}
        else{
            //if motor already exists, replace it
            motor.close();
            motor = new TalonFX(config.motorCanID,new CANBus(config.motorCanBus));}
        motor.getConfigurator().apply(convertMotorConfigToTalonConfig(config));
        positionPID.Slot = 0;
        velocityPID.Slot = 1;

        ratio = config.motorToMechanismRatio;

        isFollower = config.isFollower;

        if(isFollower){
        followerControl.withLeaderID((int) config.leaderCanID);}
        
        motorSimState = motor.getSimState();
        
        position    = motor.getPosition();
        temperature = motor.getDeviceTemp();
        rps         = motor.getVelocity();
        voltage     = motor.getMotorVoltage();
        current     = motor.getStatorCurrent();
    }

    @Override
    public void followLeaderMotor() {
        if(isFollower){
            motor.setControl(followerControl);
        }
    }

    @Override
    public void setPositionGoal(double goal) {
        if(motorConfig.positionGainConfig.kP == 0){
            System.err.println("ERROR!! \nP value for position gain is 0, if this is correct, carry on");}
        positionPID.withPosition(goal);
        motor.setControl(positionPID);
    }

    @Override
    public void setIntigratedPositionGoal(double goal) {
        if(motorConfig.positionGainConfig.kP == 0){System.err.println("ERROR!! \nP value for position gain is 0, if this is correct, carry on");}
        positionPID.withPosition(goal*ratio);
        motor.setControl(positionPID);
    }

    @Override
    public void setVelocityGoal(double goal) {
        if(motorConfig.velocityGainConfig.kP == 0){System.err.println("ERROR!! \nP value for velocity gain is 0, if this is correct, carry on");}
        velocityPID.withVelocity(goal);
        motor.setControl(velocityPID);
    }

    @Override
    public void setPosition(double position) {
        motor.setPosition(position);
    }

    @Override
    public void setFeedForwardSpeed(double speed) {
        dutyCycle.withOutput(speed);
        motor.setControl(dutyCycle);
    }

    @Override
    public void setIntigratedFeedForwardSpeed(double speed) {
        dutyCycle.withOutput(speed*ratio);
        motor.setControl(dutyCycle);

    }

    @Override
    public void stopMotor() {
        dutyCycle.withOutput(0);
        motor.setControl(dutyCycle);
        motor.stopMotor();
    }

    @Override
    public void setNuetralMode(boolean braking) {
        motor.setNeutralMode(braking?NeutralModeValue.Brake:NeutralModeValue.Coast);
    }

    @Override
    public void updateMotorSimAngle(double Angle) {
        motorSimState.setRawRotorPosition(Angle*ratio);
    }

    @Override
    public void updateMotorSimAnglularVelocity(double AngularVelocity) {
        motorSimState.setRotorVelocity(AngularVelocity*ratio);
    }


}