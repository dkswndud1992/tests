package scrreplayparser.replaymodel;

import scrreplayparser.repcore.PlayerColor;
import scrreplayparser.repcore.PlayerRace;
import scrreplayparser.repcore.PlayerType;

public class Player {
  private int slotID;
  
  private int id;
  
  private PlayerType type;
  
  private PlayerRace race;
  
  private byte team;
  
  private String name;
  
  private PlayerColor color;
  
  AdditionalPlayerData additionalPlayerData;
  
  public int getSlotID() {
    return this.slotID;
  }
  
  public void setSlotID(int paramInt) {
    this.slotID = paramInt;
  }
  
  public int getId() {
    return this.id;
  }
  
  public void setId(int paramInt) {
    this.id = paramInt;
  }
  
  public PlayerType getType() {
    return this.type;
  }
  
  public void setType(PlayerType paramPlayerType) {
    this.type = paramPlayerType;
  }
  
  public PlayerRace getRace() {
    return this.race;
  }
  
  public void setRace(PlayerRace paramPlayerRace) {
    this.race = paramPlayerRace;
  }
  
  public byte getTeam() {
    return this.team;
  }
  
  public void setTeam(byte paramByte) {
    this.team = paramByte;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String paramString) {
    this.name = paramString;
  }
  
  public PlayerColor getColor() {
    return this.color;
  }
  
  public void setColor(PlayerColor paramPlayerColor) {
    this.color = paramPlayerColor;
  }
  
  public AdditionalPlayerData getAdditionalPlayerData() {
    return this.additionalPlayerData;
  }
  
  public void setAdditionalPlayerData(AdditionalPlayerData paramAdditionalPlayerData) {
    this.additionalPlayerData = paramAdditionalPlayerData;
  }
  
  public String toString() {
    return "Player\n\tslotID: " + this.slotID + "\n\tid: " + this.id + "\n\ttype: " + this.type + "\n\trace: " + this.race + "\n\tteam: " + this.team + "\n\tname: " + this.name + "\n\tcolor: " + this.color;
  }
  
  public void computeAdditionalPlayerData(Replay paramReplay) {
    this.additionalPlayerData = new AdditionalPlayerData(this, paramReplay);
  }
}
