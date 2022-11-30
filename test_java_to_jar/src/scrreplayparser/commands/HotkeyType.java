package scrreplayparser.commands;

import java.util.HashMap;
import java.util.Map;

public enum HotkeyType {
  ASSIGN((byte)0, "Assign"),
  SELECT((byte)1, "Select"),
  ADD((byte)2, "Add"),
  UNKNOWN((byte)-103, "Unknown");
  
  int id;
  
  String name;
  
  private static final Map<Integer, HotkeyType> lookupByByte;
  
  HotkeyType(byte paramByte, String paramString1) {
    this.id = paramByte;
    this.name = paramString1;
  }
  
  public String getName() {
    return this.name;
  }
  
  public static HotkeyType getByID(int paramInt) {
    HotkeyType hotkeyType1 = lookupByByte.get(Integer.valueOf(paramInt));
    if (hotkeyType1 != null)
      return hotkeyType1; 
    HotkeyType hotkeyType2 = UNKNOWN;
    hotkeyType2.id = paramInt;
    return hotkeyType2;
  }
  
  static {
    lookupByByte = new HashMap<>();
    for (HotkeyType hotkeyType : values())
      lookupByByte.put(Integer.valueOf(hotkeyType.id), hotkeyType); 
    lookupByByte.remove(Integer.valueOf(153));
  }
}
