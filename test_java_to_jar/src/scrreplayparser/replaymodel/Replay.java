package scrreplayparser.replaymodel;

public class Replay {
  ReplayHeader header;
  
  Commands commands;
  
  MapData mapData;
  
  AdditionalReplayData additionalReplayData;
  
  public ReplayHeader getHeader() {
    return this.header;
  }
  
  public void setHeader(ReplayHeader paramReplayHeader) {
    this.header = paramReplayHeader;
  }
  
  public Commands getCommands() {
    return this.commands;
  }
  
  public void setCommands(Commands paramCommands) {
    this.commands = paramCommands;
  }
  
  public MapData getMapData() {
    return this.mapData;
  }
  
  public void setMapData(MapData paramMapData) {
    this.mapData = paramMapData;
  }
  
  public AdditionalReplayData getAdditionalReplayData() {
    return this.additionalReplayData;
  }
  
  public void computeAdditionalReplayData() {
    this.additionalReplayData = new AdditionalReplayData(this);
    for (Player player : this.header.getPlayers())
      player.computeAdditionalPlayerData(this); 
  }
}
