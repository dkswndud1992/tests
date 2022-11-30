package scrreplayparser.commands;

public class UnitTag {
  int index;
  
  public UnitTag(int paramInt) {
    this.index = paramInt;
  }
  
  public int getIndex() {
    return this.index;
  }
  
  public byte getRecycle() {
    return (byte)(this.index >> 12);
  }
  
  public boolean isValid() {
    return (this.index != 65535);
  }
}
