package scrreplayparser.commands;

import scrreplayparser.repcore.Frame;

public abstract class BaseCommand {
  private Frame frame;
  
  private int playerID;
  
  private CommandType type;
  
  public Frame getFrame() {
    return this.frame;
  }
  
  public void setFrame(Frame paramFrame) {
    this.frame = paramFrame;
  }
  
  public int getPlayerID() {
    return this.playerID;
  }
  
  public void setPlayerID(int paramInt) {
    this.playerID = paramInt;
  }
  
  public CommandType getType() {
    return this.type;
  }
  
  public void setType(CommandType paramCommandType) {
    this.type = paramCommandType;
  }
  
  public BaseCommand(Frame paramFrame, int paramInt, CommandType paramCommandType) {
    this.frame = paramFrame;
    this.playerID = paramInt;
    this.type = paramCommandType;
  }
  
  public BaseCommand() {}
  
  public abstract String getParameterString();
}
