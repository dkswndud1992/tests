package scrreplayparser.commands;

import scrreplayparser.repcore.Frame;

public class LeaveGameCommand extends BaseCommand {
  LeaveReason leaveReason;
  
  public LeaveGameCommand(Frame paramFrame, int paramInt, CommandType paramCommandType, LeaveReason paramLeaveReason) {
    super(paramFrame, paramInt, paramCommandType);
    this.leaveReason = paramLeaveReason;
  }
  
  public String getParameterString() {
    return "LeaveReason:" + this.leaveReason.getName();
  }
}
