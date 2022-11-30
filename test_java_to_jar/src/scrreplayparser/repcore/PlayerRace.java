package scrreplayparser.repcore;

import java.util.HashMap;
import java.util.Map;

public enum PlayerRace {
  ZERG((byte)0, "Zerg", "Z"),
  TERRAN((byte)1, "Terran", "T"),
  PROTOSS((byte)2, "Protoss", "P"),
  UNKNOWN((byte)-103, "Unknown", "UNK");
  
  byte id;
  
  String name;
  
  String shortName;
  
  private static final Map<Byte, PlayerRace> lookupByByte;
  
  PlayerRace(byte paramByte, String paramString1, String paramString2) {
    this.id = paramByte;
    this.name = paramString1;
    this.shortName = paramString2;
  }
  
  public String getName() {
    return this.name;
  }
  
  public String getShortName() {
    return this.shortName;
  }
  
  public static PlayerRace getByID(byte paramByte) {
    PlayerRace playerRace1 = lookupByByte.get(Byte.valueOf(paramByte));
    if (playerRace1 != null)
      return playerRace1; 
    PlayerRace playerRace2 = UNKNOWN;
    playerRace2.id = paramByte;
    return playerRace2;
  }
  
  static {
    lookupByByte = new HashMap<>();
    for (PlayerRace playerRace : values())
      lookupByByte.put(Byte.valueOf(playerRace.id), playerRace); 
    lookupByByte.remove(Byte.valueOf((byte)-103));
  }
}
