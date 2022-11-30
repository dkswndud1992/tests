package scrreplayparser.replaymodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import scrreplayparser.commands.BaseCommand;
import scrreplayparser.commands.ChatCommand;
import scrreplayparser.commands.LeaveGameCommand;

public class AdditionalReplayData {
  private Replay parentReplay;
  
  private List<LeaveGameCommand> leaveGameCommands = new ArrayList<>();
  
  private List<ChatCommand> chatCommands = new ArrayList<>();
  
  private byte winnerTeam;
  
  public List<LeaveGameCommand> getLeaveGameCommands() {
    return this.leaveGameCommands;
  }
  
  public List<ChatCommand> getChatCommands() {
    return this.chatCommands;
  }
  
  public byte getWinnerTeam() {
    return this.winnerTeam;
  }
  
  public void setWinnerTeam(byte paramByte) {
    this.winnerTeam = paramByte;
  }
  
  public AdditionalReplayData(Replay paramReplay) {
    this.parentReplay = paramReplay;
    computeData();
  }
  
  private void computeData() {
    HashMap<Object, Object> hashMap = new HashMap<>();
    for (Player player : this.parentReplay.header.getPlayers()) {
      byte b = hashMap.containsKey(Byte.valueOf(player.getTeam())) ? (byte)((Integer)hashMap.get(Byte.valueOf(player.getTeam()))).intValue() : 0;
      hashMap.put(Byte.valueOf(player.getTeam()), Integer.valueOf(b + 1));
    } 
    if (this.parentReplay.commands != null) {
      List<BaseCommand> list = this.parentReplay.commands.getCmds();
      for (BaseCommand baseCommand : list) {
        Player player;
        switch (baseCommand.getType().ordinal()) {
          case 1:
            player = (Player)this.parentReplay.header.getPIDPlayers().get(Integer.valueOf(baseCommand.getPlayerID()));
            if (player != null) {
              byte b1 = player.getTeam();
              getLeaveGameCommands().add((LeaveGameCommand)baseCommand);
              byte b2 = hashMap.containsKey(Byte.valueOf(b1)) ? (byte)((Integer)hashMap.get(Byte.valueOf(b1))).intValue() : 0;
              hashMap.put(Byte.valueOf(b1), Integer.valueOf(b2 - 1));
            } 
          case 2:
            getChatCommands().add((ChatCommand)baseCommand);
        } 
      } 
      byte b = 0;
      int i = -1;
      for (Map.Entry<Object, Object> entry : hashMap.entrySet()) {
        byte b1 = ((Byte)entry.getKey()).byteValue();
        int j = ((Integer)entry.getValue()).intValue();
        if (j > i) {
          b = b1;
          i = j;
        } 
      } 
      if (i > 0) {
        byte b1 = 0;
        for (Object obj : hashMap.values()) {
          Integer integer = (Integer)obj;
          if (integer.intValue() == i)
            b1++; 
        } 
        if (b1 == 1)
          setWinnerTeam(b); 
      } 
    } 
  }
  
  public String toString() {
    return "AdditionalReplayData:\n\tleaveGameCommands: " + this.leaveGameCommands + "\n\tchatCommands: " + this.chatCommands + "\n\twinnerTeam: " + this.winnerTeam + "";
  }
}
