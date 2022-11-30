package scrreplayparser.commands;

import scrreplayparser.repcore.Frame;

public class ParseErrorCommand extends BaseCommand {
  private BaseCommand previousCommand;
  
  public BaseCommand getPreviousCommand() {
    return this.previousCommand;
  }
  
  public void setPreviousCommand(BaseCommand paramBaseCommand) {
    this.previousCommand = paramBaseCommand;
  }
  
  public ParseErrorCommand(Frame paramFrame, int paramInt, CommandType paramCommandType, BaseCommand paramBaseCommand) {
    super(paramFrame, paramInt, paramCommandType);
    this.previousCommand = paramBaseCommand;
  }
  
  public String getParameterString() {
    return "PreviousCommand:" + this.previousCommand;
  }
}
