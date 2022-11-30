package scrreplayparser.repcore;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public enum PlayerColor {
  RED(0, "Red", new Color(15991812)),
  BLUE(1, "Blue", new Color(805068)),
  TEAL(2, "Teal", new Color(2929812)),
  PURPLE(3, "Purple", new Color(8929436)),
  ORANGE(4, "Orange", new Color(16288788)),
  BROWN(5, "Brown", new Color(7352340)),
  WHITE(6, "White", new Color(13426896)),
  YELLOW(7, "Yellow", new Color(16579640)),
  GREEN(8, "Green", new Color(557064)),
  PALE_YELLOW(9, "Pale Yellow", new Color(16579708)),
  TAN(10, "Tan", new Color(15516848)),
  AQUA(11, "Aqua", new Color(4221140)),
  PALE_GREEN(12, "Pale Green", new Color(7644284)),
  BLUEISH_GREY(13, "Blueish Grey", new Color(9474232)),
  PALE_YELLOW_2(14, "Pale Yellow2", new Color(16579708)),
  CYAN(15, "Cyan", new Color(58620)),
  UNKNOWN(153, "Unknown", new Color(16777215));
  
  int id;
  
  String name;
  
  Color color;
  
  private static final Map<Integer, PlayerColor> lookupByByte;
  
  PlayerColor(int paramInt1, String paramString1, Color paramColor) {
    this.id = paramInt1;
    this.name = paramString1;
    this.color = paramColor;
  }
  
  public String getName() {
    return this.name;
  }
  
  public Color getColor() {
    return this.color;
  }
  
  public static PlayerColor getByID(int paramInt) {
    PlayerColor playerColor1 = lookupByByte.get(Integer.valueOf(paramInt));
    if (playerColor1 != null)
      return playerColor1; 
    PlayerColor playerColor2 = UNKNOWN;
    playerColor2.id = paramInt;
    return playerColor2;
  }
  
  static {
    lookupByByte = new HashMap<>();
    for (PlayerColor playerColor : values())
      lookupByByte.put(Integer.valueOf(playerColor.id), playerColor); 
    lookupByByte.remove(Integer.valueOf(153));
  }
}
