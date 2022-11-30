package scrreplayparser.commands;

import scrreplayparser.repcore.Frame;

public class ChatCommand extends BaseCommand {
  private int sendingSlotID;
  
  private String message;
  
  public ChatCommand(Frame paramFrame, int paramInt1, CommandType paramCommandType, int paramInt2, String paramString) {
    super(paramFrame, paramInt1, paramCommandType);
    this.sendingSlotID = paramInt2;
    this.message = paramString;
  }
  
  public String getMessage() {
    return this.message;
  }
  
  public int getSendingSlotID() {
    return this.sendingSlotID;
  }
  
  public String toString() {
    return "ChatCommand [frame=" + getFrame().getSeconds() + ", playerID=" + getPlayerID() + ", sendingSlotID=" + this.sendingSlotID + ", message=" + this.message + "]";
  }
  
  public String getParameterString() {
    return "sendingSlotID:" + this.sendingSlotID + " Message:" + this.message;
  }
}
