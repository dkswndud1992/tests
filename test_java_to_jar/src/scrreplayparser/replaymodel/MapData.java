package scrreplayparser.replaymodel;

import java.util.ArrayList;
import java.util.List;
import scrreplayparser.repcore.MapPoint;
import scrreplayparser.repcore.TileSet;

public class MapData {
  private short version;
  
  TileSet tileset;
  
  List<Short> tiles;
  
  List<MapPoint> mineralFields = new ArrayList<>();
  
  List<MapPoint> geysers = new ArrayList<>();
  
  List<StartLocation> startLocations = new ArrayList<>();
  
  public short getVersion() {
    return this.version;
  }
  
  public void setVersion(short paramShort) {
    this.version = paramShort;
  }
  
  public TileSet getTileset() {
    return this.tileset;
  }
  
  public void setTileset(TileSet paramTileSet) {
    this.tileset = paramTileSet;
  }
  
  public List<Short> getTiles() {
    return this.tiles;
  }
  
  public void setTiles(List<Short> paramList) {
    this.tiles = paramList;
  }
  
  public List<MapPoint> getMineralFields() {
    return this.mineralFields;
  }
  
  public void setMineralFields(List<MapPoint> paramList) {
    this.mineralFields = paramList;
  }
  
  public List<MapPoint> getGeysers() {
    return this.geysers;
  }
  
  public void setGeysers(List<MapPoint> paramList) {
    this.geysers = paramList;
  }
  
  public List<StartLocation> getStartLocations() {
    return this.startLocations;
  }
  
  public void setStartLocations(List<StartLocation> paramList) {
    this.startLocations = paramList;
  }
  
  public String toString() {
    return "MapData:\n\tversion: " + this.version + "\n\ttileset: " + this.tileset + "";
  }
}
