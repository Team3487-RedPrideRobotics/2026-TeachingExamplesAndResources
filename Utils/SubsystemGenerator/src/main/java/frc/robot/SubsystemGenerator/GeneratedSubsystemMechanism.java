package frc.robot.SubsystemGenerator;

import java.util.HashMap;

import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.mechanism.LoggedMechanismLigament2d;
import org.littletonrobotics.junction.mechanism.LoggedMechanismRoot2d;

/*TODO:
* Implement live tuning support
* Finish javadoc comments
* Test limits of system
* Add sparkMax support
*/ 

/** A moving mechanism that is apart of a generated subystem
 * <p> Handles motor and simulation functions
 */
public class GeneratedSubsystemMechanism extends SubsystemBase{

    HashMap<String,GeneratedSubsystemMotorIO> motors;
    HashMap<String,IOInputsManualLogged> ioInputs;

    public double systemLength;
    public double systemAngle;
    public double systemRadius;
    public double systemMass;

    @SuppressWarnings({ "rawtypes"})
    private LinearSystem linearSystemSim;
    private DCMotor      dcMotor;

    private SingleJointedArmSim singleArmSim;
    private FlywheelSim         flywheelSim;
    private ElevatorSim         elevatorSim;
    private boolean             simGravity;

    private LoggedMechanismLigament2d mechanismLigament;

    public enum mechanismType{
        ARM,
        ELEVATOR,
        FLYWHEEL
    }

    private mechanismType thisMechanismType;

    GeneratedSubsystem parentSubsystem;

    /**Default constructor
     * <p>No two mechanisms are allowed to have the same name*/
    public GeneratedSubsystemMechanism(String name){
        this.setName(name);
        motors = new HashMap<>(2);
        ioInputs = new HashMap<>(2);
        systemAngle = 0;
        systemRadius = -1;
        systemLength = -1;
        systemMass   = 0;
        thisMechanismType = mechanismType.ARM;

        mechanismLigament = new LoggedMechanismLigament2d(name, systemLength, systemAngle);
    }

    protected void setParentSubsystem(GeneratedSubsystem parentSubsystem){
        this.parentSubsystem = parentSubsystem;
    } 

    /**Adds a motor with the listed Name and motorIO to the mechanism
     *<p> No two motors can have the same name
    */
    public GeneratedSubsystemMechanism addMotor(String Name,GeneratedSubsystemMotorIO motorIO){
        motors.put(Name, motorIO);
        ioInputs.put(Name, new IOInputsManualLogged());
        return this;
    }

    /**Using PID control, move the mechanism's motors or mechanim to the SetPoint
     * @param SetPoint The goal to reach
     * @param Tolerance The minimum distance from the setpoint required to set done to true
     * @param isIntigrated If true, the set point will be realative to the mechanism, not the motors
     * @return Done
     */
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
                if(!isIntigrated){motors.get(motor).setPositionGoal(SetPoint);}
                else{motors.get(motor).setIntigratedPositionGoal(SetPoint);}
            }
            else{motors.get(motor).stopMotor(); done = true;}
        }
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

    /**Set the feed forward speed across all mechanisms equally
     * <p> <b>Warning!<b:> if there are motors across different gear ratios this could cause problems
     * @param speed %
     */
    public void feedForwardMechanism(double speed){
        for (String motor : motors.keySet()) {
            motors.get(motor).setFeedForwardSpeed(speed);
        }
    }

    /**Applies the listed motor config to the named motor */
    public GeneratedSubsystemMechanism applyMotorConfig(String MotorName,GeneratedSubsystemMotorConfig Config){
        motors.get(MotorName).setConfig(Config);
        return this;
    }

    /**Applies the mechanism properties listed for display and sim of a system 
     * <p><b>System Length:</b> the length of the pivoting arm
     * <p><b>System Angle:</b> the Angle of the pivoting arm
     * @param systemLength Meters
     * @param systemAngle  Degrees
     */
    public GeneratedSubsystemMechanism applyMechanismProperties(double systemLength,double systemAngle){
        this.systemLength = systemLength;
        this.systemAngle = systemAngle;
        return this;
    }

    /**Applies the mechanism properties listed for display and sim
     * <p><b>System Mass:</b> the mass of the drum of either an elevator or flywheel
     * <p><b>System Radius:</b> the radius of the drum of either an elevator or flywheel
     * @param systemMass   Kg
     * @param systemRadius Meters
     */
    public GeneratedSubsystemMechanism applyMechanismDrumProperties(double systemMass,double systemRadius){
        this.systemRadius = systemRadius;
        this.systemMass = systemMass;
        return this;
    }

    /**Set the mechanism type to Arm ,Elevator ,or flywheel
     * <p> this determines how the sim is computed and data is displayed
     * @return returns self for easy method chaining
     */
    public GeneratedSubsystemMechanism setMechanismType(mechanismType mechanismtType){
        thisMechanismType = mechanismtType;
        return this;
    }

    /**Applies the LinearSystem used for sim
     * <p> this can be aquired using the LinearSystemId class
     * <p> will NOT update live
     * @param linearSystem
     * @return
     */
    @SuppressWarnings("rawtypes")
    public GeneratedSubsystemMechanism applySimLinearSystem(LinearSystem linearSystem){
        linearSystemSim = linearSystem;
        return this;
    }

    /**Applies the DCMotor used for sim
     * <p> will NOT update live
     * @param motor DCMotor
     * @return returns self for easy method chaining
     */
    public GeneratedSubsystemMechanism applySimDCMotor(DCMotor motor){
        dcMotor = motor;
        return this;
    }

    @SuppressWarnings("unchecked")
    private void updateSimObject(double voltage,double currentDraw){
        double MotorIntigratedAngle = 0;
        double MotorIntigratedAngularVelocity = 0;

        switch(thisMechanismType){
            case ARM:
                if (singleArmSim == null){singleArmSim = new SingleJointedArmSim(linearSystemSim, dcMotor, 1, systemLength, -100, 100, simGravity, 0);}
                singleArmSim.setInputVoltage(voltage);
                singleArmSim.update(0.020);
                RoboRioSim.setVInVoltage(BatterySim.calculateDefaultBatteryLoadedVoltage(currentDraw));

                MotorIntigratedAngle = singleArmSim.getAngleRads()/(2*Math.PI);
                MotorIntigratedAngularVelocity = singleArmSim.getVelocityRadPerSec()/(2*Math.PI);
                break;
            case ELEVATOR:
                if (elevatorSim == null){elevatorSim = new ElevatorSim(linearSystemSim, dcMotor, -100, 100, simGravity, 0);}
                elevatorSim.setInputVoltage(voltage);

                elevatorSim.update(0.020);
                RoboRioSim.setVInVoltage(BatterySim.calculateDefaultBatteryLoadedVoltage(currentDraw));

                MotorIntigratedAngle = elevatorSim.getPositionMeters()/systemRadius/(2*Math.PI);
                MotorIntigratedAngularVelocity = elevatorSim.getVelocityMetersPerSecond()/systemRadius/(2*Math.PI);
                break;
            case FLYWHEEL:
                if (flywheelSim == null){flywheelSim = new FlywheelSim(linearSystemSim, dcMotor);}
                flywheelSim.setInputVoltage(voltage);

                flywheelSim.update(0.020);
                RoboRioSim.setVInVoltage(BatterySim.calculateDefaultBatteryLoadedVoltage(currentDraw));

                MotorIntigratedAngularVelocity = flywheelSim.getAngularVelocityRadPerSec()/(2*Math.PI);
                break;

        }

        for (String key : motors.keySet()){
            motors.get(key).updateMotorSimAngle(MotorIntigratedAngle);
            motors.get(key).updateMotorSimAnglularVelocity(MotorIntigratedAngularVelocity);
        } 
    }

    /**get the LoggedMechanismLigament2d of the mechanism*/
    public LoggedMechanismLigament2d getMechanismLigament2d(){
        return mechanismLigament;
    }

    /**Appends the mechanism's LoggedMechanismLigament2d to the provided LoggedMechanismObject2d
     * @return returns self for easy method chaining
    */
    public GeneratedSubsystemMechanism appendMechanismLigament(LoggedMechanismLigament2d mechanismLigament){
        mechanismLigament.append(this.mechanismLigament);
        return this;
    }

    /**Appends the mechanism's LoggedMechanismLigament2d to the provided LoggedMechanismObject2d
     * @return returns self for easy method chaining
    */
    public GeneratedSubsystemMechanism appendMechanismLigament(LoggedMechanismRoot2d mechanismRoot){
        mechanismRoot.append(mechanismLigament);
        return this;
    }

    @Override
    public void periodic() {
        double avgIntigratedAngle = 0;
        for(String key : ioInputs.keySet()){
            avgIntigratedAngle = avgIntigratedAngle == 0?ioInputs.get(key).MotorIntigratedPosition:((avgIntigratedAngle/ioInputs.get(key).MotorIntigratedPosition)/2);
            motors.get(key).updateIOInputs(ioInputs.get(key));
            Logger.processInputs(this.getName()+" Motor: "+key,ioInputs.get(key));
        }
        
        if(thisMechanismType == GeneratedSubsystemMechanism.mechanismType.ARM){
                mechanismLigament.setLength(systemLength);
                mechanismLigament.setAngle(avgIntigratedAngle*360);}
        else{if(thisMechanismType == mechanismType.ELEVATOR){
                mechanismLigament.setLength(avgIntigratedAngle*(2*Math.PI)*systemRadius+systemLength);
                mechanismLigament.setAngle(systemAngle*360);
            }   
            else{}
        }

    }

    @Override
    public void simulationPeriodic() {
        double avgvoltage = 0;
        double avgCurrentDraw = 0;
        for(String key:ioInputs.keySet()){
            avgvoltage = avgvoltage==0?ioInputs.get(key).MotorVolts:((avgvoltage+ioInputs.get(key).MotorVolts)/2);
            avgCurrentDraw = avgCurrentDraw==0?ioInputs.get(key).MotorCurrentDraw:((avgCurrentDraw+ioInputs.get(key).MotorCurrentDraw)/2);
        }
        updateSimObject(avgvoltage, avgCurrentDraw);
    }

    
}
