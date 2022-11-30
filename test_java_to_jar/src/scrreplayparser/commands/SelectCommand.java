package scrreplayparser.commands;

import scrreplayparser.repcore.Frame;

public class SelectCommand extends BaseCommand {
  UnitTag[] unitTags;
  
  public SelectCommand(Frame paramFrame, int paramInt, CommandType paramCommandType, UnitTag[] paramArrayOfUnitTag) {
    super(paramFrame, paramInt, paramCommandType);
    this.unitTags = paramArrayOfUnitTag;
  }
  
  public String getParameterString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("UnitTags: [");
    for (UnitTag unitTag : this.unitTags)
      stringBuilder.append("Index:" + unitTag.getIndex() + ",Recycle:" + unitTag.getRecycle() + ";"); 
    stringBuilder.append("]");
    return stringBuilder.toString();
  }
}
