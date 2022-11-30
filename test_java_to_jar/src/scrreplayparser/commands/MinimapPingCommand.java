package scrreplayparser.commands;

import scrreplayparser.repcore.Frame;
import scrreplayparser.repcore.MapPoint;

public class MinimapPingCommand extends BaseCommand {
  MapPoint mapPoint;
  
  public MinimapPingCommand(Frame paramFrame, int paramInt, CommandType paramCommandType, MapPoint paramMapPoint) {
    super(paramFrame, paramInt, paramCommandType);
    this.mapPoint = paramMapPoint;
  }
  
  public String getParameterString() {
    return "MapPoint:" + this.mapPoint;
  }
}
