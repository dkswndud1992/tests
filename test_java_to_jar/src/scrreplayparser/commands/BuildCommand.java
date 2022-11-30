package scrreplayparser.commands;

import scrreplayparser.repcore.Frame;
import scrreplayparser.repcore.MapPoint;

public class BuildCommand extends BaseCommand {
  private Order order;
  
  private MapPoint mapPoint;
  
  private Unit unit;
  
  public Order getOrder() {
    return this.order;
  }
  
  public MapPoint getMapPoint() {
    return this.mapPoint;
  }
  
  public Unit getUnit() {
    return this.unit;
  }
  
  public BuildCommand(Frame paramFrame, int paramInt, CommandType paramCommandType, Order paramOrder, MapPoint paramMapPoint, Unit paramUnit) {
    super(paramFrame, paramInt, paramCommandType);
    this.order = paramOrder;
    this.mapPoint = paramMapPoint;
    this.unit = paramUnit;
  }
  
  public String getParameterString() {
    return "Order:" + this.order.getName() + " Mappoint:" + this.mapPoint + " Unit:" + this.unit.getName();
  }
}
