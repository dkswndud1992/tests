package scrreplayparser.commands;

import scrreplayparser.repcore.Frame;

public class UpgradeCommand extends BaseCommand {
  private Upgrade upgrade;
  
  public Upgrade getUpgrade() {
    return this.upgrade;
  }
  
  public UpgradeCommand(Frame paramFrame, int paramInt, CommandType paramCommandType, Upgrade paramUpgrade) {
    super(paramFrame, paramInt, paramCommandType);
    this.upgrade = paramUpgrade;
  }
  
  public String getParameterString() {
    return "Upgrade:" + this.upgrade.getName();
  }
}
