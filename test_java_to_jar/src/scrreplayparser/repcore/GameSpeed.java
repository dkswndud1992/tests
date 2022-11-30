package scrreplayparser.repcore;

import java.util.HashMap;
import java.util.Map;

public enum GameSpeed {
  SLOWEST((byte)0, "Slowest"),
  SLOWER((byte)1, "Slower"),
  SLOW((byte)2, "Slow"),
  NORMAL((byte)3, "Normal"),
  FAST((byte)4, "Fast"),
  FASTER((byte)5, "Faster"),
  FASTEST((byte)6, "Fastest"),
  UNKNOWN((byte)-103, "Unknown");
  
  int id;
  
  String name;
  
  private static final Map<Integer, GameSpeed> lookupByByte;
  
  GameSpeed(byte paramByte, String paramString1) {
    this.id = paramByte;
    this.name = paramString1;
  }
  
  public String getName() {
    return this.name;
  }
  
  public static GameSpeed getByID(int paramInt) {
    GameSpeed gameSpeed1 = lookupByByte.get(Integer.valueOf(paramInt));
    if (gameSpeed1 != null)
      return gameSpeed1; 
    GameSpeed gameSpeed2 = UNKNOWN;
    gameSpeed2.id = paramInt;
    return gameSpeed2;
  }
  
  static {
    lookupByByte = new HashMap<>();
    for (GameSpeed gameSpeed : values())
      lookupByByte.put(Integer.valueOf(gameSpeed.id), gameSpeed); 
    lookupByByte.remove(Integer.valueOf(153));
  }
}
