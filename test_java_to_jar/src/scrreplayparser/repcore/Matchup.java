package scrreplayparser.repcore;

import java.util.HashMap;
import java.util.Map;

public enum Matchup {
  NONE((short)0, "None"),
  CUSTOM((short)1, "Custom"),
  MELEE((short)2, "Melee"),
  FREE_FOR_ALL((short)3, "FFA"),
  ONE_ON_ONE((short)4, "1v1"),
  CAPTURE_THE_FLAG((short)5, "CTF"),
  GREED((short)6, "Greed"),
  SLAUGHTER((short)7, "Slaughter"),
  SUDDEN_DEATH((short)8, "Sudden Death"),
  LADDER((short)9, "Ladder"),
  USE_MAP_SETTINGS((short)10, "UMS"),
  TEAM_MELEE((short)11, "Team Melee"),
  TEAM_FREE_FOR_ALL((short)12, "Team FFA"),
  TEAM_CAPTURE_THE_FLAG((short)13, "Team CTF"),
  TOP_VS_BOTTOM((short)15, "TvB"),
  IRON_MAN_LADDER((short)16, "Iron Man Ladder"),
  UNKNOWN((short)153, "Unknown");
  
  short id;
  
  String name;
  
  private static final Map<Short, Matchup> lookupByByte;
  
  Matchup(short paramShort, String paramString1) {
    this.id = paramShort;
    this.name = paramString1;
  }
  
  public String getName() {
    return this.name;
  }
  
  public static Matchup getByID(short paramShort) {
    Matchup matchup1 = lookupByByte.get(Short.valueOf(paramShort));
    if (matchup1 != null)
      return matchup1; 
    Matchup matchup2 = UNKNOWN;
    matchup2.id = paramShort;
    return matchup2;
  }
  
  static {
    lookupByByte = new HashMap<>();
    for (Matchup matchup : values())
      lookupByByte.put(Short.valueOf(matchup.id), matchup); 
    lookupByByte.remove(Short.valueOf((short)153));
  }
}
