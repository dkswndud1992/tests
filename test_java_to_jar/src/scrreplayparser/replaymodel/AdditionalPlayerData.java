package scrreplayparser.replaymodel;

import java.util.List;
import java.util.stream.Collectors;
import scrreplayparser.commands.BaseCommand;
import scrreplayparser.repcore.Frame;
import scrreplayparser.repcore.MapPoint;

public class AdditionalPlayerData {
  private Replay parentReplay;
  
  private Player parentPlayer;
  
  private List<BaseCommand> playerCommands;
  
  private double averageApm;
  
  private MapPoint startLocation;
  
  private int startLocationAsClock;
  
  private Frame getLastCommandFrame() {
    return ((BaseCommand)this.playerCommands.get(this.playerCommands.size() - 1)).getFrame();
  }
  
  private int getCommandCount() {
    return this.playerCommands.size();
  }
  
  public double getAverageApm() {
    return this.averageApm;
  }
  
  public MapPoint getStartLocation() {
    return this.startLocation;
  }
  
  public void setStartLocation(MapPoint paramMapPoint) {
    this.startLocation = paramMapPoint;
  }
  
  public int getStartDirection() {
    return this.startLocationAsClock;
  }
  
  public void setStartDirection(int paramInt) {
    this.startLocationAsClock = paramInt;
  }
  
  public AdditionalPlayerData(Player paramPlayer, Replay paramReplay) {
    this.parentPlayer = paramPlayer;
    this.parentReplay = paramReplay;
    computeData();
  }
  
  private void computeData() {
    List<BaseCommand> list = this.parentReplay.getCommands().getCmds();
    this.playerCommands = (List<BaseCommand>)list.stream().filter(paramBaseCommand -> (paramBaseCommand.getPlayerID() == this.parentPlayer.getId())).collect(Collectors.toList());
    for (StartLocation startLocation : this.parentReplay.mapData.getStartLocations()) {
      if (startLocation.getSlotID() == this.parentPlayer.getSlotID())
        this.startLocation = startLocation.getPoint(); 
    } 
    double d1 = (this.parentReplay.header.getMapWidth() / 2 * 32);
    double d2 = (this.parentReplay.header.getMapHeight() / 2 * 32);
    this.startLocationAsClock = angleToClock(Math.atan2(d2 - this.startLocation.y, this.startLocation.x - d1));
    if (!this.playerCommands.isEmpty()) {
      long l = getLastCommandFrame().getDuration().toMillis();
      this.averageApm = getCommandCount() / l / 1000.0D / 60.0D;
    } else {
      this.averageApm = 0.0D;
    } 
  }
  
  public static int angleToClock(double paramDouble) {
    double d = paramDouble * Math.PI / 180.0D;
    d *= -1.0D;
    int i = (int)(d / 0.5235987755982988D);
    i = Math.floorMod(i, 12);
    i += 3;
    i = Math.floorMod(i, 12);
    return (i == 0) ? 12 : i;
  }
}
