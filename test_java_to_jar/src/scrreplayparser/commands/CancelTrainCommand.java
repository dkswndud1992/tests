package scrreplayparser.commands;

import scrreplayparser.repcore.Frame;

public class CancelTrainCommand extends BaseCommand {
  UnitTag unitTag;
  
  public CancelTrainCommand(Frame paramFrame, int paramInt, CommandType paramCommandType, UnitTag paramUnitTag) {
    super(paramFrame, paramInt, paramCommandType);
    this.unitTag = paramUnitTag;
  }
  
  public String getParameterString() {
    return "UnitTag:" + this.unitTag.getIndex() + "," + this.unitTag.getRecycle();
  }
}
