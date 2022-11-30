package scrreplayparser.replaymodel;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import scrreplayparser.repcore.Frame;
import scrreplayparser.repcore.GameEngine;
import scrreplayparser.repcore.GameSpeed;
import scrreplayparser.repcore.GameType;

public class ReplayHeader {
  private GameEngine engine;
  
  private Frame frames;
  
  private Instant startTime;
  
  private String title;
  
  private int mapWidth;
  
  private int mapHeight;
  
  private byte availableSlotsCount;
  
  private GameSpeed speed;
  
  private GameType type;
  
  private int subType;
  
  private String host;
  
  private String map;
  
  private Player[] slots;
  
  private List<Player> origPlayers = new ArrayList<>();
  
  private List<Player> players = new ArrayList<>();
  
  private Map<Integer, Player> PIDPlayers;
  
  public GameEngine getEngine() {
    return this.engine;
  }
  
  public void setEngine(GameEngine paramGameEngine) {
    this.engine = paramGameEngine;
  }
  
  public Frame getFrames() {
    return this.frames;
  }
  
  public void setFrames(Frame paramFrame) {
    this.frames = paramFrame;
  }
  
  public Instant getStartTime() {
    return this.startTime;
  }
  
  public void setStartTime(Instant paramInstant) {
    this.startTime = paramInstant;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String paramString) {
    this.title = paramString;
  }
  
  public int getMapWidth() {
    return this.mapWidth;
  }
  
  public void setMapWidth(int paramInt) {
    this.mapWidth = paramInt;
  }
  
  public int getMapHeight() {
    return this.mapHeight;
  }
  
  public void setMapHeight(int paramInt) {
    this.mapHeight = paramInt;
  }
  
  public byte getAvailableSlotsCount() {
    return this.availableSlotsCount;
  }
  
  public void setAvailableSlotsCount(byte paramByte) {
    this.availableSlotsCount = paramByte;
  }
  
  public GameSpeed getSpeed() {
    return this.speed;
  }
  
  public void setSpeed(GameSpeed paramGameSpeed) {
    this.speed = paramGameSpeed;
  }
  
  public GameType getType() {
    return this.type;
  }
  
  public void setType(GameType paramGameType) {
    this.type = paramGameType;
  }
  
  public int getSubType() {
    return this.subType;
  }
  
  public void setSubType(int paramInt) {
    this.subType = paramInt;
  }
  
  public String getHost() {
    return this.host;
  }
  
  public void setHost(String paramString) {
    this.host = paramString;
  }
  
  public String getMap() {
    return this.map;
  }
  
  public void setMap(String paramString) {
    this.map = paramString;
  }
  
  public Player[] getSlots() {
    return this.slots;
  }
  
  public void setSlots(Player[] paramArrayOfPlayer) {
    this.slots = paramArrayOfPlayer;
  }
  
  public List<Player> getOrigPlayers() {
    return this.origPlayers;
  }
  
  public void setOrigPlayers(List<Player> paramList) {
    this.origPlayers = paramList;
  }
  
  public List<Player> getPlayers() {
    return this.players;
  }
  
  public void setPlayers(List<Player> paramList) {
    this.players = paramList;
  }
  
  public Map<Integer, Player> getPIDPlayers() {
    return this.PIDPlayers;
  }
  
  public void setPIDPlayers(Map<Integer, Player> paramMap) {
    this.PIDPlayers = paramMap;
  }
  
  public String durationToString() {
    Duration duration = this.frames.getDuration();
    return String.format("%smin %ssec", new Object[] { Long.valueOf(duration.toMinutes() % 60L), Long.valueOf(duration.getSeconds() % 60L) });
  }
  
  public String mapSizeToString() {
    return this.mapWidth + "x" + this.mapHeight;
  }
  
  public String matchupToString() {
    StringBuilder stringBuilder = new StringBuilder();
    byte b = ((Player)this.players.get(0)).getTeam();
    for (Player player : this.players) {
      if (b != player.getTeam())
        stringBuilder.append("v"); 
      stringBuilder.append(player.getRace().getShortName());
      b = player.getTeam();
    } 
    return stringBuilder.toString();
  }
  
  public String playerNamesToString() {
    StringBuilder stringBuilder = new StringBuilder();
    byte b = ((Player)this.players.get(0)).getTeam();
    for (Player player : this.players) {
      if (b != player.getTeam())
        stringBuilder.append(" VS "); 
      stringBuilder.append(player.getName());
      b = player.getTeam();
    } 
    return stringBuilder.toString();
  }
  
  public String toString() {
    return "ReplayHeader: \n\tEngine:" + this.engine + "\n\tStartTime: " + this.startTime + "\n\tPlayers: " + playerNamesToString() + "\n\tRaces: " + matchupToString() + "\n\tOrigPlayers: " + this.origPlayers + "\n\tMap=" + this.map + "\n\tMapSize: " + mapSizeToString() + "\n\tDuration: " + durationToString() + "\n\tSpeed: " + this.speed + "\n\tHost: " + this.host + "\n\tTitle: " + this.title + "\n\tAvailableSlotsCount: " + this.availableSlotsCount + "\n\tType: " + this.type + "\n\tSubType: " + this.subType + "";
  }
}
