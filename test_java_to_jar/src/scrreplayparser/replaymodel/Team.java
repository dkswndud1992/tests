package scrreplayparser.replaymodel;

import java.util.List;

public class Team {
  private int id;
  
  private List<Player> players;
  
  public int getId() {
    return this.id;
  }
  
  public void setId(int paramInt) {
    this.id = paramInt;
  }
  
  public List<Player> getPlayers() {
    return this.players;
  }
  
  public int getSize() {
    return this.players.size();
  }
  
  public String toString() {
    return "Team\n\tid: " + this.id + "\n\tplayers: " + this.players;
  }
}
