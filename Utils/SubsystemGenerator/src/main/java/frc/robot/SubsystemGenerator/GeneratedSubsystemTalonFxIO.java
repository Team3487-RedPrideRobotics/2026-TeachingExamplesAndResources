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

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;

/**IO interface to be used with TalonFX motor controllers in the subsystem generator */
public class GeneratedSubsystemTalonFxIO implements GeneratedSubsystemMotorIO {

    //status signal definition
    private final StatusSignal<Angle>           position;
    private final StatusSignal<AngularVelocity> rps;
    private final StatusSignal<Temperature>     temperature;
    private final StatusSignal<Voltage>         voltage;

    //motor definition
    private TalonFX motor;

    private double ratio;

    private boolean isFollower;

    //control definitions
    private PositionDutyCycle positionPID;
    private VelocityDutyCycle velocityPID;
    private DutyCycleOut      dutyCycle;
    private Follower          followerControl;

    public GeneratedSubsystemTalonFxIO(){
        positionPID = new PositionDutyCycle(0);
        velocityPID = new VelocityDutyCycle(0);
        dutyCycle   = new DutyCycleOut(0);

        position    = motor.getPosition();
        temperature = motor.getDeviceTemp();
        rps         = motor.getVelocity();
        voltage     = motor.getMotorVoltage();
        BaseStatusSignal.setUpdateFrequencyForAll(50, position,rps,temperature,voltage);
    } 

    @Override
    public void updateIOInputs(IOInputs IOInputs) {
        IOInputs.MotorIntigratedPosition = ratio*position.getValueAsDouble();
        IOInputs.MotorRelativePosition   = position.getValueAsDouble();
        IOInputs.MotorRelativeRPM        = rps.getValueAsDouble()*60;
        IOInputs.MotorTemp               = temperature.getValueAsDouble();
        IOInputs.MotorVolts              = voltage.getValueAsDouble();
        BaseStatusSignal.refreshAll(position,rps,temperature,voltage);
    }

    private TalonFXConfiguration convertMotorConfigToTalonConfig(GeneratedSubsystemMotorConfig inputConfig){
        TalonFXConfiguration config = new TalonFXConfiguration();
        
        Slot0Configs positionConfig = new Slot0Configs();
        Slot1Configs velocityConfig = new Slot1Configs();
        
        config.MotorOutput.withInverted(inputConfig.isInverted?InvertedValue.Clockwise_Positive:InvertedValue.CounterClockwise_Positive);
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

    @Override
    public void setConfig(GeneratedSubsystemMotorConfig config) {
        if(motor==null){motor = new TalonFX(config.motorCanID,new CANBus(config.motorCanBus));}
        motor.getConfigurator().apply(convertMotorConfigToTalonConfig(config));
        positionPID.Slot = 0;
        velocityPID.Slot = 1;

        followerControl.withLeaderID((int) config.leaderCanID);

        isFollower = config.isFollower;
    }

    @Override
    public void followLeaderMotor() {
        if(isFollower){
            motor.setControl(followerControl);
        }
    }

    @Override
    public void setPositionGoal(double position) {
        positionPID.withPosition(position);
        motor.setControl(positionPID);
    }

    @Override
    public void setVelocityGoal(double goal) {
        velocityPID.withVelocity(goal/60);
        motor.setControl(velocityPID);
    }

    @Override
    public void setPosition(double position) {
        motor.setPosition(position);
    }

    @Override
    public void stopMotor() {
        dutyCycle.withOutput(0);
        motor.setControl(dutyCycle);
        motor.stopMotor();
    }
}