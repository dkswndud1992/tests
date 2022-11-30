package scrreplayparser.commands;

import scrreplayparser.repcore.Frame;
import scrreplayparser.repcore.MapPoint;

public class RightClickCommand extends BaseCommand {
  MapPoint mapPoint;
  
  UnitTag unitTag;
  
  Unit unit;
  
  boolean queued;
  
  public RightClickCommand(Frame paramFrame, int paramInt, CommandType paramCommandType, MapPoint paramMapPoint, UnitTag paramUnitTag, Unit paramUnit, boolean paramBoolean) {
    super(paramFrame, paramInt, paramCommandType);
    this.mapPoint = paramMapPoint;
    this.unitTag = paramUnitTag;
    this.unit = paramUnit;
    this.queued = paramBoolean;
  }
  
  public String getParameterString() {
    return "Mappoint:" + this.mapPoint + " UnitTag:" + this.unitTag.getIndex() + "," + this.unitTag.getRecycle() + "  Unit:" + this.unit.getName() + " Queued:" + this.queued;
  }
}
