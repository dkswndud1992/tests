package scrreplayparser.commands;

import scrreplayparser.repcore.Frame;
import scrreplayparser.repcore.GameSpeed;

public class GameSpeedCommand extends BaseCommand {
  GameSpeed gameSpeed;
  
  public GameSpeedCommand(Frame paramFrame, int paramInt, CommandType paramCommandType, GameSpeed paramGameSpeed) {
    super(paramFrame, paramInt, paramCommandType);
    this.gameSpeed = paramGameSpeed;
  }
  
  public String getParameterString() {
    return "GameSpeed:" + this.gameSpeed.getName();
  }
}
