package scrreplayparser.repcore;

import java.util.HashMap;
import java.util.Map;

public enum GameEngine {
  STARCRAFT((byte)0, "SC"),
  BROODWAR((byte)1, "BW"),
  UNKNOWN((byte)-103, "UNK");
  
  byte id;
  
  String name;
  
  private static final Map<Byte, GameEngine> lookupByByte;
  
  GameEngine(byte paramByte, String paramString1) {
    this.id = paramByte;
    this.name = paramString1;
  }
  
  public String getName() {
    return this.name;
  }
  
  public static GameEngine getByID(byte paramByte) {
    GameEngine gameEngine1 = lookupByByte.get(Byte.valueOf(paramByte));
    if (gameEngine1 != null)
      return gameEngine1; 
    GameEngine gameEngine2 = UNKNOWN;
    gameEngine2.id = paramByte;
    return gameEngine2;
  }
  
  static {
    lookupByByte = new HashMap<>();
    for (GameEngine gameEngine : values())
      lookupByByte.put(Byte.valueOf(gameEngine.id), gameEngine); 
    lookupByByte.remove(Byte.valueOf((byte)-103));
  }
}
