package scrreplayparser.commands;

import scrreplayparser.repcore.Frame;

public class HotkeyCommand extends BaseCommand {
  HotkeyType hotkeyType;
  
  int group;
  
  public HotkeyCommand(Frame paramFrame, int paramInt1, CommandType paramCommandType, HotkeyType paramHotkeyType, int paramInt2) {
    super(paramFrame, paramInt1, paramCommandType);
    this.hotkeyType = paramHotkeyType;
    this.group = paramInt2;
  }
  
  public HotkeyType getHotkeyType() {
    return this.hotkeyType;
  }
  
  public void setHotkeyType(HotkeyType paramHotkeyType) {
    this.hotkeyType = paramHotkeyType;
  }
  
  public int getGroup() {
    return this.group;
  }
  
  public void setGroup(int paramInt) {
    this.group = paramInt;
  }
  
  public String getParameterString() {
    return "Type:" + this.hotkeyType.getName() + " Group:" + this.group;
  }
}
