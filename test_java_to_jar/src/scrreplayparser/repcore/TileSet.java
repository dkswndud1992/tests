package scrreplayparser.repcore;

import java.util.HashMap;
import java.util.Map;

public enum TileSet {
  BADLANDS((short)0, "Badlands"),
  SPACE_PLATFORM((short)1, "Space Platform"),
  INSTALLATION((short)2, "Installation"),
  ASHWORLD((short)3, "Ashworld"),
  JUNGLE((short)4, "Jungle"),
  DESERT((short)5, "Desert"),
  ARCTIC((short)6, "Arctic"),
  TWILIGHT((short)7, "Twilight"),
  UNKNOWN((short)153, "Unknown");
  
  short id;
  
  String name;
  
  private static final Map<Short, TileSet> lookupByByte;
  
  TileSet(short paramShort, String paramString1) {
    this.id = paramShort;
    this.name = paramString1;
  }
  
  public String getName() {
    return this.name;
  }
  
  public static TileSet getByID(short paramShort) {
    TileSet tileSet1 = lookupByByte.get(Short.valueOf(paramShort));
    if (tileSet1 != null)
      return tileSet1; 
    TileSet tileSet2 = UNKNOWN;
    tileSet2.id = paramShort;
    return tileSet2;
  }
  
  static {
    lookupByByte = new HashMap<>();
    for (TileSet tileSet : values())
      lookupByByte.put(Short.valueOf(tileSet.id), tileSet); 
    lookupByByte.remove(Short.valueOf((short)153));
  }
}
