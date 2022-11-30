package scrreplayparser.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

public class ByteArrayReader {
  private List<Byte> byteData;
  
  private int pos = 0;
  
  public List<Byte> getByteData() {
    return this.byteData;
  }
  
  public void setByteData(List<Byte> paramList) {
    this.byteData = paramList;
  }
  
  public int getPos() {
    return this.pos;
  }
  
  public void setPos(int paramInt) {
    this.pos = paramInt;
  }
  
  public void incrementPos(int paramInt) {
    this.pos += paramInt;
  }
  
  public ByteArrayReader(List<Byte> paramList) {
    this.byteData = paramList;
  }
  
  public int getNextByteAsInt() {
    byte b = ((Byte)this.byteData.get(this.pos)).byteValue();
    this.pos++;
    return b & 0xFF;
  }
  
  public short getShort() {
    byte[] arrayOfByte = new byte[2];
    for (byte b = 0; b < arrayOfByte.length; b++)
      arrayOfByte[b] = ((Byte)this.byteData.get(this.pos + b)).byteValue(); 
    ByteBuffer byteBuffer = ByteBuffer.wrap(arrayOfByte);
    byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    this.pos += 2;
    return byteBuffer.getShort();
  }
  
  public int getInteger() {
    byte[] arrayOfByte = new byte[4];
    for (byte b = 0; b < arrayOfByte.length; b++)
      arrayOfByte[b] = ((Byte)this.byteData.get(this.pos + b)).byteValue(); 
    ByteBuffer byteBuffer = ByteBuffer.wrap(arrayOfByte);
    byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    this.pos += 4;
    return byteBuffer.getInt();
  }
  
  public String getString(int paramInt) {
    byte[] arrayOfByte = new byte[paramInt];
    for (byte b = 0; b < arrayOfByte.length; b++)
      arrayOfByte[b] = ((Byte)this.byteData.get(this.pos + b)).byteValue(); 
    this.pos += paramInt;
    return new String(arrayOfByte);
  }
  
  public byte[] getBytes(int paramInt) {
    byte[] arrayOfByte = new byte[paramInt];
    for (byte b = 0; b < arrayOfByte.length; b++)
      arrayOfByte[b] = ((Byte)this.byteData.get(this.pos + b)).byteValue(); 
    this.pos += paramInt;
    return arrayOfByte;
  }
}
