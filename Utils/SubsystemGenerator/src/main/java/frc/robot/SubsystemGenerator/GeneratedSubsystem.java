package frc.robot.SubsystemGenerator;

import java.util.Map;

import edu.wpi.first.wpilibj2.command.Command;

/** General parent and handler class for generated subsystem components
 * <p> Made for team 3487; If you are another team, use at your own discretion 
 * <p> Made for use with Advantage Kit logging*/
public class GeneratedSubsystem{
    
    String subsystemName;

    Map<String,GeneratedSubsystemMechanism> mechanisms;

    boolean isTuning;

    public enum robotState{
        CompBot,
        SimBot,
        ReplayBot
    }

    

    /**Default constructor
     * <p> Provide with the new subsystem name*/
    public GeneratedSubsystem(String Name){
        subsystemName = Name;
        mechanisms = Map.of();
    }

    /**Alternate constructor
     * <p> Provide with name and starting mechanism*/
    public GeneratedSubsystem(String Name, GeneratedSubsystemMechanism baseMechansim){
        subsystemName = Name;
        mechanisms = Map.of();
        assignMechanism(baseMechansim);
    }

    /** Returns the isTuning boolean, or Tuning State, of the subsystem */
    public boolean getTuningState(){
        return isTuning;
    }

    /** Sets the isTuning boolean */
    public GeneratedSubsystem setTuningState(boolean isTuning){
        this.isTuning = isTuning;
        return this;
    }

    /**returns the string name of the subsystem */
    public String getName(){
        return subsystemName;
    }  

    /**Assigns the mechanism to be under this subsystem's control*/
    public GeneratedSubsystem assignMechanism(GeneratedSubsystemMechanism mechanism){
        mechanisms.put(mechanism.getName(),mechanism);
        for(String mechanismId : mechanisms.keySet()){
            mechanisms.get(mechanismId).setParentSubsystem(this);
        }
        return this;
    }

    public GeneratedSubsystemMechanism getMechanism(String mechanismName){
        return mechanisms.get(mechanismName);
    }

    public Command getMechansimFeedForwardCommand(String mechanismName,double speed){
        Command mechanismCommand = new Command() {
            private GeneratedSubsystemMechanism mechanism;
            @Override
            public void initialize() {
                mechanism = mechanisms.get(mechanismName);
                addRequirements(mechanism);
            }

            @Override
            public void execute() {
                mechanism.feedForwardMechanism(speed);
            }

            @Override
            public void end(boolean interrupted) {
                mechanism.stopMechanism();
            }
        };
        return mechanismCommand;
    }

    public Command getMechanismPIDPositionCommand(String mechanismName, double position, double tolerance, boolean inIntigrated){
        Command mechanismCommand = new Command() {
            private GeneratedSubsystemMechanism mechanism;
            private boolean done;
            @Override
            public void initialize() {
                mechanism = mechanisms.get(mechanismName);
                addRequirements(mechanism);
                done = false;
            }
            @Override
            public void execute() {
                done = mechanism.pidPositionMechanism(position, tolerance,inIntigrated);
            }
            @Override
            public boolean isFinished() {
                return done;
            }
            @Override
            public void end(boolean interrupted) {
                mechanism.stopMechanism();
            }

        };
        return mechanismCommand;
    }

    public Command getMechanismPIDVelocityCommand(String mechanismName, double rpm){
        Command mechanismCommand = new Command() {
        private GeneratedSubsystemMechanism mechanism; 
        @Override
            public void initialize() {
                mechanism = mechanisms.get(mechanismName);
                addRequirements(mechanism);
            }   
            @Override
                public void execute() {
                    mechanism.pidVelocityMechanism(rpm);
                }
        };
        return mechanismCommand;
    }

    /**Returns the Map with the key type of strings and set type of GeneratedSubsystemMechanism */
    public Map<String, GeneratedSubsystemMechanism> getMechanisms() {
        return mechanisms;
    }



}