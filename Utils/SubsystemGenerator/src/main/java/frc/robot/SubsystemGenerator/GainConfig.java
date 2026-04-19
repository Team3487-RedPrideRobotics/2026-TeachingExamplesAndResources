package frc.robot.SubsystemGenerator;

public class GainConfig {
    double kA = 0;
    double kS = 0;
    double kP = 0;
    double kI = 0;
    double kD = 0;
    double kG = 0;

    public GainConfig withkA(double newkA){
        kA = newkA;
        return this;
    }
    
    public GainConfig withkS(double newkS){
        kS = newkS;
        return this;
    }
    
    public GainConfig withkP(double newkP){
        kP = newkP;
        return this;
    }
    
    public GainConfig withkI(double newkI){
        kI = newkI;
        return this;
    }
    
    public GainConfig withkD(double newkD){
        kD = newkD;
        return this;
    }
    
    public GainConfig withkG(double newkG){
        kG = newkG;
        return this;
    }
    
}
