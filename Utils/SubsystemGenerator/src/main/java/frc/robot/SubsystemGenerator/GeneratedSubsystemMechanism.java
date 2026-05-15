package frc.robot.SubsystemGenerator;

import java.util.Map;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.littletonrobotics.junction.Logger;

/*TODO:
* Implement Simulation support
* Implement live tuning support
* Finish javadoc comments
* Test limits of system
* Add sparkMax support
*/ 

/** A moving mechanism that is apart of a generated subystem
 * <p> Handles motor and simulation support*/
public class GeneratedSubsystemMechanism extends SubsystemBase{

    Map<String,GeneratedSubsystemMotorIO> motors;
    Map<String,IOInputsAutoLogged> ioInputs;    

    GeneratedSubsystem parentSubsystem;

    /**Default constructor
     * <p>No two mechanisms are allowed to have the same name*/
    public GeneratedSubsystemMechanism(String name){
        this.setName(name);
        motors = Map.of();
        ioInputs = Map.of();
    }

    protected void setParentSubsystem(GeneratedSubsystem parentSubsystem){
        this.parentSubsystem = parentSubsystem;
    } 

    /**Adds a motor with the listed Name and motorIO to the mechanism
     * No two motors can have the same name
    */
    public GeneratedSubsystemMechanism addMotor(String Name,GeneratedSubsystemMotorIO motorIO){
        motors.put(Name, motorIO);
        ioInputs.put(Name, new IOInputsAutoLogged());
        return this;
    }

    public boolean pidPositionMechanism(double SetPoint,double tolerance,boolean isIntigrated){
        boolean done = false;
        double avgRotation = 0;
        for (String motor : motors.keySet()) {
            if(avgRotation == 0){avgRotation = isIntigrated?ioInputs.get(motor).MotorIntigratedPosition:ioInputs.get(motor).MotorRelativePosition;}
            avgRotation = avgRotation + (isIntigrated?ioInputs.get(motor).MotorIntigratedPosition:ioInputs.get(motor).MotorRelativePosition);
            avgRotation = avgRotation/2;
        }
        for (String motor : motors.keySet()){
            if(Math.abs(SetPoint-avgRotation)>tolerance){
            if(!isIntigrated){motors.get(motor).setPositionGoal(SetPoint);}else{motors.get(motor).setIntigratedPositionGoal(SetPoint);}}
            else{motors.get(motor).stopMotor(); done = true;}
        }
        return done;
    }

    public boolean intigratedPidPositionMechanism(double SetPoint,double tolerance){
        boolean done = pidPositionMechanism(SetPoint, tolerance,true);
        return done;
    }

    /**Set the mechanism to PID to the supplied rpm*/
    public void pidVelocityMechanism(double goalRpm){
        for (String motor : motors.keySet()) {
            motors.get(motor).setVelocityGoal(goalRpm);
        }
    }

    /**Stop all motors in the mechanism */
    public void stopMechanism(){
        for (String motor : motors.keySet()) {
            motors.get(motor).stopMotor();
        }
    }

    public void feedForwardMechanism(double speed){
        for (String motor : motors.keySet()) {
            motors.get(motor).setFeedForwardSpeed(speed);
        }
    }

    public void setMechanismMotorBrakes(){
        for (String motor : motors.keySet()) {
            motors.get(motor).stopMotor();
        }
    }

    /**Applies the listed motor config to the named motor */
    public GeneratedSubsystemMechanism applyMotorConfig(String MotorName,GeneratedSubsystemMotorConfig Config){
        motors.get(MotorName).setConfig(Config);
        return this;
    }

    @Override
    public void periodic() {
        for(String key : ioInputs.keySet()){
            Logger.processInputs(this.getName()+" Motor: "+key,ioInputs.get(key));
        }
    }

    /**Enum defining what type of mechanism this is for simulation and mechanism2d parts */
    public enum SubsystemMechanismType{
        ELEVATOR,
        PIVOT,
        FLYWHEEL,
        SPINNER
    }
}
