package scrreplayparser.commands;

import java.util.HashMap;
import java.util.Map;

public enum Latency {
  LOW((byte)0, "Low"),
  HIGH((byte)1, "High"),
  EXTRA_HIGH((byte)2, "Extra High"),
  UNKNOWN((byte)-103, "Unknown");
  
  int id;
  
  String name;
  
  private static final Map<Integer, Latency> lookupByByte;
  
  Latency(byte paramByte, String paramString1) {
    this.id = paramByte;
    this.name = paramString1;
  }
  
  public String getName() {
    return this.name;
  }
  
  public static Latency getByID(int paramInt) {
    Latency latency1 = lookupByByte.get(Integer.valueOf(paramInt));
    if (latency1 != null)
      return latency1; 
    Latency latency2 = UNKNOWN;
    latency2.id = paramInt;
    return latency2;
  }
  
  static {
    lookupByByte = new HashMap<>();
    for (Latency latency : values())
      lookupByByte.put(Integer.valueOf(latency.id), latency); 
    lookupByByte.remove(Integer.valueOf(153));
  }
}
