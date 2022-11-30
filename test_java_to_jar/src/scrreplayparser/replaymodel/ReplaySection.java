package scrreplayparser.replaymodel;

public enum ReplaySection {
  REPLAYID(0, 4),
  HEADER(1, 633),
  COMMANDS(2, 0),
  MAPDATA(3, 0);
  
  int id;
  
  private int size;
  
  public int getSize() {
    return this.size;
  }
  
  ReplaySection(int paramInt1, int paramInt2) {
    this.id = paramInt1;
    this.size = paramInt2;
  }
}
