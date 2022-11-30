package scrreplayparser.commands;

import scrreplayparser.repcore.Frame;

public class UnanalyzedCommand extends BaseCommand {
  byte[] rawData;
  
  public UnanalyzedCommand(Frame paramFrame, int paramInt, CommandType paramCommandType, byte[] paramArrayOfbyte) {
    super(paramFrame, paramInt, paramCommandType);
    this.rawData = paramArrayOfbyte;
  }
  
  public String getParameterString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("[");
    if (this.rawData != null) {
      for (byte b : this.rawData) {
        stringBuilder.append(String.format("%02x", new Object[] { Integer.valueOf(b & 0xFF) }));
        stringBuilder.append(" ");
      } 
      stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    } 
    stringBuilder.append("]");
    return "rawData:" + stringBuilder.toString();
  }
}
