package scrreplayparser.commands;

import scrreplayparser.repcore.Frame;
import scrreplayparser.repcore.MapPoint;

public class TargetedOrderCommand extends BaseCommand {
  MapPoint mapPoint;
  
  UnitTag unitTag;
  
  Unit unit;
  
  Order order;
  
  boolean queued;
  
  public TargetedOrderCommand(Frame paramFrame, int paramInt, CommandType paramCommandType, MapPoint paramMapPoint, UnitTag paramUnitTag, Unit paramUnit, Order paramOrder, boolean paramBoolean) {
    super(paramFrame, paramInt, paramCommandType);
    this.mapPoint = paramMapPoint;
    this.unitTag = paramUnitTag;
    this.unit = paramUnit;
    this.order = paramOrder;
    this.queued = paramBoolean;
  }
  
  public String getParameterString() {
    return "Mappoint:" + this.mapPoint + " UnitTag:" + this.unitTag.getIndex() + "," + this.unitTag.getRecycle() + "  Unit:" + this.unit.getName() + " Order:" + this.order.getName() + " Queued:" + this.queued;
  }
}
