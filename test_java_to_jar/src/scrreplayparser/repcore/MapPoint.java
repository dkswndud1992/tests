package scrreplayparser.repcore;

public class MapPoint {
  public int x;
  
  public int y;
  
  public MapPoint(int paramInt1, int paramInt2) {
    this.x = paramInt1;
    this.y = paramInt2;
  }
  
  public String toString() {
    return "x=" + this.x + ", y=" + this.y;
  }
}
