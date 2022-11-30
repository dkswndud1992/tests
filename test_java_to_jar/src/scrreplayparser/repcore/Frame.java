package scrreplayparser.repcore;

import java.time.Duration;

public class Frame {
  private int amount;
  
  public int getAmount() {
    return this.amount;
  }
  
  public void setAmount(int paramInt) {
    this.amount = paramInt;
  }
  
  public Frame(int paramInt) {
    this.amount = paramInt;
  }
  
  public double getSeconds() {
    return (getMilliseconds() / 1000);
  }
  
  public int getMilliseconds() {
    return this.amount * 42;
  }
  
  public Duration getDuration() {
    return Duration.ofMillis(getMilliseconds());
  }
  
  public String toString() {
    return "Frame\n\tamount: " + this.amount;
  }
}
