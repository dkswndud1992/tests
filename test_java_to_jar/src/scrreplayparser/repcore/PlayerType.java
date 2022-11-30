package scrreplayparser.repcore;

import java.util.HashMap;
import java.util.Map;

public enum PlayerType {
  INACTIVE((byte)0, "Inactive"),
  COMPUTER((byte)1, "Computer"),
  HUMAN((byte)2, "Human"),
  RESCUE_PASSIVE((byte)3, "Rescue Passive"),
  UNUSED((byte)4, "(Unused)"),
  COMPUTER_CONTROLLED((byte)5, "Computer Controlled"),
  OPEN((byte)6, "Open"),
  NEUTRAL((byte)7, "Neutral"),
  CLOSED((byte)8, "Closed"),
  UNKNOWN((byte)-103, "Unknown");
  
  byte id;
  
  String name;
  
  private static final Map<Byte, PlayerType> lookupByByte;
  
  PlayerType(byte paramByte, String paramString1) {
    this.id = paramByte;
    this.name = paramString1;
  }
  
  public String getName() {
    return this.name;
  }
  
  public static PlayerType getByID(byte paramByte) {
    PlayerType playerType1 = lookupByByte.get(Byte.valueOf(paramByte));
    if (playerType1 != null)
      return playerType1; 
    PlayerType playerType2 = UNKNOWN;
    playerType2.id = paramByte;
    return playerType2;
  }
  
  static {
    lookupByByte = new HashMap<>();
    for (PlayerType playerType : values())
      lookupByByte.put(Byte.valueOf(playerType.id), playerType); 
    lookupByByte.remove(Byte.valueOf((byte)-103));
  }
}
