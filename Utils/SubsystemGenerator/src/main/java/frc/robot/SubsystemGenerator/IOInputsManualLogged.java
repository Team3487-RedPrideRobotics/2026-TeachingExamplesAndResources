package frc.robot.SubsystemGenerator;

import java.lang.Cloneable;
import java.lang.Override;
import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class IOInputsManualLogged extends GeneratedSubsystemMotorIO.IOInputs implements LoggableInputs, Cloneable {
  @Override
  public void toLog(LogTable table) {
    table.put("MotorCanBus&ID", canID);
    table.put("Motor"+canID+"CurrentDraw", MotorCurrentDraw);
    table.put("Motor"+canID+"RelativeRPM", MotorRelativeRPM);
    table.put("Motor"+canID+"RelativePosition", MotorRelativePosition);
    table.put("Motor"+canID+"IntigratedPosition", MotorIntigratedPosition);
    table.put("Motor"+canID+"Volts", MotorVolts);
    table.put("Motor"+canID+"Temp", MotorTemp);
  }

  @Override
  public void fromLog(LogTable table) {
    canID                   = table.get("MotorCanBus&ID", canID);
    MotorRelativeRPM        = table.get("Motor"+canID+"CurrentDraw", MotorCurrentDraw);
    MotorRelativeRPM        = table.get("Motor"+canID+"RelativeRPM", MotorRelativeRPM);
    MotorRelativePosition   = table.get("Motor"+canID+"RelativePosition", MotorRelativePosition);
    MotorIntigratedPosition = table.get("Motor"+canID+"IntigratedPosition", MotorIntigratedPosition);
    MotorVolts              = table.get("Motor"+canID+"Volts", MotorVolts);
    MotorTemp               = table.get("Motor"+canID+"Temp", MotorTemp);
  }

  public IOInputsManualLogged clone() {
    IOInputsManualLogged copy = new IOInputsManualLogged();
    copy.canID = this.canID;
    copy.MotorCurrentDraw = this.MotorCurrentDraw;
    copy.MotorRelativeRPM = this.MotorRelativeRPM;
    copy.MotorRelativePosition = this.MotorRelativePosition;
    copy.MotorIntigratedPosition = this.MotorIntigratedPosition;
    copy.MotorVolts = this.MotorVolts;
    copy.MotorTemp = this.MotorTemp;
    return copy;
  }
}