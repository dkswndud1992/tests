package scrreplayparser.decoder;

public enum RepFormat {
  UNKNOWN("Unknown"),
  LEGACY("<1.18"),
  MODERN("1.18 - 1.20"),
  MODERN_121("1.21+");
  
  String name;
  
  public String getName() {
    return this.name;
  }
  
  RepFormat(String paramString1) {
    this.name = paramString1;
  }
}
