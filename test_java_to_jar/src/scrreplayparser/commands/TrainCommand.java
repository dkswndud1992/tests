package scrreplayparser.commands;

import scrreplayparser.repcore.Frame;

public class TrainCommand extends BaseCommand {
  private Unit unit;
  
  public Unit getUnit() {
    return this.unit;
  }
  
  public TrainCommand(Frame paramFrame, int paramInt, CommandType paramCommandType, Unit paramUnit) {
    super(paramFrame, paramInt, paramCommandType);
    this.unit = paramUnit;
  }
  
  public String getParameterString() {
    return "Unit:" + this.unit.getName();
  }
}
