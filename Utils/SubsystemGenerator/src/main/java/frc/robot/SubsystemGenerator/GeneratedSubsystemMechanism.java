package frc.robot.SubsystemGenerator;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** A moving mechanism that is apart of a generated subystem
 * <p> Supports TalonFX and SparkMax motorControllers
 */
public class GeneratedSubsystemMechanism extends SubsystemBase{

    public GeneratedSubsystemMechanism(String name){
        this.setName(name);
    }


    public GeneratedSubsystemMechanism addMotor(GeneratedSubsystemMotorIO ioInterface){
        
        return this;
    }





    public enum SubsystemMechanismType{
        ELEVATOR,
        PIVOT,
        FLYWHEEL,
        SPINNER
    }
}
