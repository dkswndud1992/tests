package scrreplayparser.commands;

import scrreplayparser.repcore.Frame;

public class QueueableCommand extends BaseCommand {
  boolean queued;
  
  public QueueableCommand(Frame paramFrame, int paramInt, CommandType paramCommandType, boolean paramBoolean) {
    super(paramFrame, paramInt, paramCommandType);
    this.queued = paramBoolean;
  }
  
  public String getParameterString() {
    return "Queued:" + this.queued;
  }
}
