package scrreplayparser.commands;

import scrreplayparser.repcore.Frame;

public class LatencyCommand extends BaseCommand {
  Latency latency;
  
  public LatencyCommand(Frame paramFrame, int paramInt, CommandType paramCommandType, Latency paramLatency) {
    super(paramFrame, paramInt, paramCommandType);
    this.latency = paramLatency;
  }
  
  public String getParameterString() {
    return "Latency:" + this.latency;
  }
}
