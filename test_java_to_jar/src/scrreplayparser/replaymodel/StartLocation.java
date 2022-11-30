package scrreplayparser.replaymodel;

import scrreplayparser.repcore.MapPoint;

public class StartLocation {
  private MapPoint point;
  
  private int slotID;
  
  public StartLocation(MapPoint paramMapPoint, int paramInt) {
    this.point = paramMapPoint;
    this.slotID = paramInt;
  }
  
  public MapPoint getPoint() {
    return this.point;
  }
  
  public int getSlotID() {
    return this.slotID;
  }
}
