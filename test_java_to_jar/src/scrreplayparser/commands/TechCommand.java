package scrreplayparser.commands;

import scrreplayparser.repcore.Frame;

public class TechCommand extends BaseCommand {
  private Tech tech;
  
  public Tech getTech() {
    return this.tech;
  }
  
  public TechCommand(Frame paramFrame, int paramInt, CommandType paramCommandType, Tech paramTech) {
    super(paramFrame, paramInt, paramCommandType);
    this.tech = paramTech;
  }
  
  public String getParameterString() {
    return "Tech:" + this.tech.getName();
  }
}
