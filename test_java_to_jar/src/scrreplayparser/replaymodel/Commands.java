package scrreplayparser.replaymodel;

import java.util.ArrayList;
import java.util.List;
import scrreplayparser.commands.BaseCommand;
import scrreplayparser.commands.ParseErrorCommand;

public class Commands {
  private List<BaseCommand> cmds = new ArrayList<>();
  
  private List<ParseErrorCommand> parseErrCmds = new ArrayList<>();
  
  public List<BaseCommand> getCmds() {
    return this.cmds;
  }
  
  public void setCmds(List<BaseCommand> paramList) {
    this.cmds = paramList;
  }
  
  public List<ParseErrorCommand> getParseErrCmds() {
    return this.parseErrCmds;
  }
  
  public void setParseErrCmds(List<ParseErrorCommand> paramList) {
    this.parseErrCmds = paramList;
  }
  
  public String toString() {
    return "Commands\n\tcmds: " + this.cmds + "\n\tparseErrCmds: " + this.parseErrCmds + "";
  }
}
