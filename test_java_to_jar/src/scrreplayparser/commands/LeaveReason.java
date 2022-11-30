package scrreplayparser.commands;

import java.util.HashMap;
import java.util.Map;

public enum LeaveReason {
  QUIT(0, "Quit"),
  DEFEAT(1, "Defeat"),
  VICTORY(2, "Victory"),
  FINISHED(3, "Finished"),
  DRAW(4, "Draw"),
  DROPPED(5, "Dropped"),
  UNKNOWN(-103, "Unknown");
  
  int id;
  
  String name;
  
  private static final Map<Integer, LeaveReason> lookupByByte;
  
  LeaveReason(int paramInt1, String paramString1) {
    this.id = paramInt1;
    this.name = paramString1;
  }
  
  public String getName() {
    return this.name;
  }
  
  public static LeaveReason getByID(int paramInt) {
    LeaveReason leaveReason1 = lookupByByte.get(Integer.valueOf(paramInt));
    if (leaveReason1 != null)
      return leaveReason1; 
    LeaveReason leaveReason2 = UNKNOWN;
    leaveReason2.id = paramInt;
    return leaveReason2;
  }
  
  static {
    lookupByByte = new HashMap<>();
    for (LeaveReason leaveReason : values())
      lookupByByte.put(Integer.valueOf(leaveReason.id), leaveReason); 
    lookupByByte.remove(Integer.valueOf(153));
  }
}
