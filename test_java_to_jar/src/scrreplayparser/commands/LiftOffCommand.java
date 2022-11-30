package scrreplayparser.commands;

import scrreplayparser.repcore.Frame;
import scrreplayparser.repcore.MapPoint;

public class LiftOffCommand extends BaseCommand {
  MapPoint mapPoint;
  
  public LiftOffCommand(Frame paramFrame, int paramInt, CommandType paramCommandType, MapPoint paramMapPoint) {
    super(paramFrame, paramInt, paramCommandType);
    this.mapPoint = paramMapPoint;
  }
  
  public String getParameterString() {
    return "MapPoint:" + this.mapPoint;
  }
}
