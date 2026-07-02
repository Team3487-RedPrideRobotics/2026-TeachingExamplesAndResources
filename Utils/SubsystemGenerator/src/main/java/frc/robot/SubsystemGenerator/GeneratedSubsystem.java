package frc.robot.SubsystemGenerator;

import java.util.HashMap;

import org.littletonrobotics.junction.mechanism.LoggedMechanism2d;
import org.littletonrobotics.junction.mechanism.LoggedMechanismRoot2d;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

/** General parent and handler class for generated subsystem components
 * <p> Made for team 3487; If you are another team, use at your own discretion 
 * <p> Made for use with Advantage Kit logging*/
public class GeneratedSubsystem{
    
    String subsystemName;

    LoggedMechanism2d     mechanismsMechanism2d;
    LoggedMechanismRoot2d mechanismsMechanismRoot2d;

    HashMap<String,GeneratedSubsystemMechanism> mechanisms;

    boolean isTuning;

    /**Default constructor
     * <p> Provide with the new subsystem name*/
    public GeneratedSubsystem(String Name){
        subsystemName = Name;
        mechanisms = new HashMap<>(2);
        isTuning = false;
    }

    /**Alternate constructor
     * <p> Provide with name and starting mechanism*/
    public GeneratedSubsystem(String Name, GeneratedSubsystemMechanism baseMechansim){
        subsystemName = Name;
        mechanisms = new HashMap<>(2);
        assignMechanism(baseMechansim);
        isTuning = false;
    }

    /** Returns the isTuning boolean, or Tuning State, of the subsystem */
    public boolean getTuningState(){
        try{return isTuning;}
        catch(Exception e){System.err.println("Error getting "+subsystemName+" tuning state! \nFalse has been returned for safety, make sure tuning state is set! ");}
        return false;
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
        mechanism.setParentSubsystem(this);
        return this;
    }

    public GeneratedSubsystemMechanism getMechanism(String mechanismName){
        try{return mechanisms.get(mechanismName);}
        catch(Exception e){System.err.println("Error getting mechanism!! \nThe mechanism: "+mechanismName+" doesnt exist!!");}
        return null;
    }

    public Command getMechansimFeedForwardCommand(String mechanismName,double speed){
        try{
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
        return mechanismCommand;}
        catch(Exception e){System.err.println("Error getting mechanism command!! \nThe mechanism: "+mechanismName+" doesnt exist!!");}
        return new Command() {};
    }

    public Command getMechanismPIDPositionCommand(String mechanismName, double position, double tolerance, boolean inIntigrated){
        try{
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
        return mechanismCommand;}
        catch(Exception e){System.err.println("Error getting mechanism command!! \nThe mechanism: "+mechanismName+" doesnt exist!!");}
        return new Command() {};
    }

    public Command getMechanismPIDVelocityCommand(String mechanismName, double rpm){
        try{
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
        return mechanismCommand;}
        catch(Exception e){System.err.println("Error getting mechanism command!! \nThe mechanism: "+mechanismName+" doesnt exist!!");}
        return new Command() {};
    }

    /**Returns the HashMap with the key type of strings and set type of GeneratedSubsystemMechanism */
    public HashMap<String, GeneratedSubsystemMechanism> getMechanisms() {
        try{return mechanisms;}
        catch(Exception e){System.err.println("Error getting "+subsystemName+" mechanism hashMap! \nThe hashMap must exist!");}
        return null;
    }

    /**Makes mechanism 2d display
     * <p> returns self for easy method chaining
     */
    public GeneratedSubsystem setMechanism2dOutput(double xSize,double ySize){
        if(mechanismsMechanism2d == null){
            mechanismsMechanism2d = new LoggedMechanism2d(xSize, ySize);
            SmartDashboard.putData(subsystemName, mechanismsMechanism2d);
            System.out.println("Mechanism2d for "+subsystemName+" didn't exist, but does now!");}
        else{
            mechanismsMechanism2d = new LoggedMechanism2d(xSize, ySize);
            System.out.println("Mechansim2d for "+subsystemName+" has been replaced");}
        return this;
    }

    /**Sets the mechanism 2d root to the provided position
     * <p> will error out if no mechanism2d is present
     * <p> returns self for easy method chaining
     */
    public GeneratedSubsystem setMechanism2dRootPosition(double xPosition,double yPosition){
        try{mechanismsMechanismRoot2d = mechanismsMechanism2d.getRoot(subsystemName, xPosition, yPosition);}
        catch(Exception e){System.err.println("Error when assigning mechanism root!! \nCannot assign mechanism root if the mechansim doesn't exist!");}
        return this;
    }

    /**Returns the 
     * LoggedMechanismRoot2d of the mechansim2d of the subsystem 
     * <p> will return null if a root or mechanism 2d is not made
     */
    public LoggedMechanismRoot2d getMechanismRoot2d(){
        if(mechanismsMechanismRoot2d == null){System.err.println("Error when getting "+subsystemName+"mechanism root!! \nCannot get mechanism root if the mechansim root doesn't exist!");}
        return mechanismsMechanismRoot2d;
    }
}