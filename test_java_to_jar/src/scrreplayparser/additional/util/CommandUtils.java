package scrreplayparser.additional.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import scrreplayparser.commands.BaseCommand;
import scrreplayparser.commands.ChatCommand;
import scrreplayparser.commands.CommandType;
import scrreplayparser.commands.HotkeyCommand;
import scrreplayparser.commands.HotkeyType;
import scrreplayparser.replaymodel.Player;

public class CommandUtils {
  public static final String EAPM_ALGORITHM_VERSION = "1.01";
  
  public static List<BaseCommand> getAllCommandsOfPlayer(List<BaseCommand> paramList, Player paramPlayer) {
    ArrayList<BaseCommand> arrayList = new ArrayList<BaseCommand>();
    for (BaseCommand baseCommand : paramList) {
      if (baseCommand.getType() == CommandType.CHAT) {
        if (((ChatCommand)baseCommand).getSendingSlotID() == paramPlayer.getSlotID())
          arrayList.add(baseCommand); 
        continue;
      } 
      if (baseCommand.getPlayerID() == paramPlayer.getId())
        arrayList.add(baseCommand); 
    } 
    return arrayList;
  }
  
  public static List<BaseCommand> getAllCommandsOfType(List<BaseCommand> paramList, CommandType... paramVarArgs) {
    List<CommandType> list = Arrays.asList(paramVarArgs);
    ArrayList<BaseCommand> arrayList = new ArrayList<BaseCommand>();
    for (BaseCommand baseCommand : paramList) {
      if (list.contains(baseCommand.getType()))
        arrayList.add(baseCommand); 
    } 
    return arrayList;
  }
  
  public static boolean isActionEffective(BaseCommand[] paramArrayOfBaseCommand, int paramInt, BaseCommand paramBaseCommand) {
    if ((paramBaseCommand.getType() == CommandType.TRAIN || paramBaseCommand.getType() == CommandType.BUILD) && countSameActions(paramArrayOfBaseCommand, paramInt, paramBaseCommand) >= 6)
      return false; 
    BaseCommand baseCommand = (paramInt > 0) ? paramArrayOfBaseCommand[paramInt - 1] : null;
    if (paramInt > 0 && paramBaseCommand.getFrame().getAmount() - baseCommand.getFrame().getAmount() <= 20) {
      if (paramBaseCommand.getType() == CommandType.TRAIN && baseCommand.getType() == CommandType.CANCEL_TRAIN)
        return false; 
      if (paramBaseCommand.getType() == CommandType.TECH && baseCommand.getType() == CommandType.CANCEL_TECH)
        return false; 
      if (paramBaseCommand.getType() == CommandType.UPGRADE && baseCommand.getType() == CommandType.CANCEL_UPGRADE)
        return false; 
      if (paramBaseCommand.getType() == CommandType.UNIT_MORPH && baseCommand.getType() == CommandType.CANCEL_MORPH)
        return false; 
    } 
    if ((paramBaseCommand.getType() == CommandType.MOVE121 || paramBaseCommand.getType() == CommandType.STOP || paramBaseCommand.getType() == CommandType.HOLD_POSITION || paramBaseCommand.getType() == CommandType.Type_0X64 || paramBaseCommand.getType() == CommandType.MOVE121 || (paramBaseCommand.getType() == CommandType.HOTKEY && ((HotkeyCommand)paramBaseCommand).getHotkeyType() == HotkeyType.ASSIGN)) && paramInt > 0 && baseCommand.getType() == paramBaseCommand.getType() && paramBaseCommand.getFrame().getAmount() - baseCommand.getFrame().getAmount() <= 10)
      return false; 
    if (paramInt > 0 && (paramBaseCommand.getType() == CommandType.SELECT121 || (paramBaseCommand.getType() == CommandType.HOTKEY && ((HotkeyCommand)paramBaseCommand).getHotkeyType() == HotkeyType.SELECT)) && (baseCommand.getType() == CommandType.SELECT121 || (baseCommand.getType() == CommandType.HOTKEY && ((HotkeyCommand)baseCommand).getHotkeyType() == HotkeyType.SELECT)) && paramBaseCommand.getFrame().getAmount() - baseCommand.getFrame().getAmount() <= 8)
      if (paramBaseCommand.getType() == CommandType.HOTKEY && baseCommand.getType() == CommandType.HOTKEY && ((HotkeyCommand)paramBaseCommand).getHotkeyType() == ((HotkeyCommand)baseCommand).getHotkeyType() && ((HotkeyCommand)paramBaseCommand).getGroup() == ((HotkeyCommand)baseCommand).getGroup()) {
        if (paramInt > 1) {
          BaseCommand baseCommand1 = paramArrayOfBaseCommand[paramInt - 2];
          if (baseCommand.getFrame().getAmount() - baseCommand1.getFrame().getAmount() <= 8 && baseCommand1.getType() == CommandType.HOTKEY && ((HotkeyCommand)paramBaseCommand).getHotkeyType() == ((HotkeyCommand)baseCommand1).getHotkeyType() && ((HotkeyCommand)paramBaseCommand).getGroup() == ((HotkeyCommand)baseCommand1).getGroup())
            return false; 
        } 
      } else {
        return false;
      }  
    return ((paramBaseCommand.getType() == CommandType.UNIT_MORPH || paramBaseCommand.getType() == CommandType.BUILDING_MORPH || paramBaseCommand.getType() == CommandType.UPGRADE || paramBaseCommand.getType() == CommandType.TECH || paramBaseCommand.getType() == CommandType.BUILD || paramBaseCommand.getType() == CommandType.CANCEL_BUILD || paramBaseCommand.getType() == CommandType.MERGE_ARCHON || paramBaseCommand.getType() == CommandType.MERGE_DARK_ARCHON || paramBaseCommand.getType() == CommandType.LIFT_OFF) && paramInt > 0 && baseCommand.getType() == paramBaseCommand.getType()) ? false : (!(paramInt > 0 && paramBaseCommand.getType() == CommandType.HOTKEY && ((HotkeyCommand)paramBaseCommand).getHotkeyType() == HotkeyType.ASSIGN && baseCommand.getType() == CommandType.HOTKEY && ((HotkeyCommand)paramBaseCommand).getHotkeyType() == ((HotkeyCommand)baseCommand).getHotkeyType() && ((HotkeyCommand)paramBaseCommand).getGroup() == ((HotkeyCommand)baseCommand).getGroup()));
  }
  
  private static int countSameActions(BaseCommand[] paramArrayOfBaseCommand, int paramInt, BaseCommand paramBaseCommand) {
    int i = paramBaseCommand.getFrame().getAmount() - 25;
    byte b = 0;
    for (int j = paramInt; j >= 0; j--) {
      BaseCommand baseCommand = paramArrayOfBaseCommand[j];
      if (baseCommand.getFrame().getAmount() < i)
        break; 
      if (baseCommand.getType() == paramBaseCommand.getType()) {
        if (++b == 6)
          return b; 
      } else if (baseCommand.getType() == CommandType.SELECT121 || baseCommand.getType() == CommandType.SELECT_ADD || baseCommand.getType() == CommandType.SELECT_REMOVE || (baseCommand.getType() == CommandType.HOTKEY && ((HotkeyCommand)baseCommand).getHotkeyType() == HotkeyType.SELECT)) {
        break;
      } 
    } 
    return b;
  }
}
