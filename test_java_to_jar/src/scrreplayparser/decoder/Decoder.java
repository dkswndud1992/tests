package scrreplayparser.decoder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class Decoder {
  byte[] replayData;
  
  ByteArrayInputStream inputStream;
  
  RepFormat repformat;
  
  int sectionsCounter;
  
  byte[] int32Buf = new byte[4];
  
  byte[] buf = new byte[8192];
  
  public RepFormat getRepformat() {
    return this.repformat;
  }
  
  public static Decoder createDecoderFromFile(String paramString) {
    File file = new File(paramString);
    if (file.isDirectory())
      return null; 
    try {
      return new Decoder(Files.readAllBytes(file.toPath()));
    } catch (IOException iOException) {
      iOException.printStackTrace();
      return null;
    } 
  }
  
  public Decoder(byte[] paramArrayOfbyte) {
    this.replayData = paramArrayOfbyte;
    this.inputStream = new ByteArrayInputStream(paramArrayOfbyte);
    detectRepFormat();
  }
  
  private void detectRepFormat() {
    byte[] arrayOfByte = Arrays.copyOf(this.replayData, 30);
    if (arrayOfByte.length < 30) {
      this.repformat = RepFormat.UNKNOWN;
    } else if (arrayOfByte[12] == 115) {
      this.repformat = RepFormat.MODERN_121;
    } else if (arrayOfByte[28] != 120) {
      this.repformat = RepFormat.LEGACY;
    } else {
      this.repformat = RepFormat.MODERN;
    } 
    System.out.println("arrayOfByte.length:"+arrayOfByte.length);
    System.out.println("arrayOfByte[12]:"+arrayOfByte[12]);
    System.out.println("arrayOfByte[28]:"+arrayOfByte[28]);
  }
  
  public byte[] decodeSection(int paramInt) throws IOException {
	System.out.println("this.repformat.ordinal():"+this.repformat.ordinal());
    switch (this.repformat.ordinal()) {
      case 1:
      case 2:
        return decodeSectionModern(paramInt);
      case 3:
        // return decodeSectionLegacy(paramInt);
    	  return decodeSectionModern(paramInt);
    } 
    return null;
  }
  
  private byte[] decodeSectionModern(int paramInt) throws IOException {
    int i = sectionHeader(paramInt);
    byte[] arrayOfByte = new byte[paramInt];
    int j = 0;
    for (byte b = 0; b < i; b++) {
      int k = readInt32();
      if (this.buf.length < k)
        this.buf = new byte[k]; 
      byte[] arrayOfByte1 = Arrays.copyOf(this.buf, k);
      this.inputStream.read(arrayOfByte1);
      System.arraycopy(arrayOfByte1, 0, this.buf, 0, k);
      if (k > 4 && arrayOfByte1[0] == 120) {
        try {
          Inflater inflater = new Inflater();
          inflater.setInput(arrayOfByte1, 0, arrayOfByte1.length);
          byte[] arrayOfByte2 = new byte[Math.min(8192, paramInt)];
          int m = inflater.inflate(arrayOfByte2);
          inflater.end();
          System.arraycopy(arrayOfByte2, 0, arrayOfByte, j, m);
          j += m;
        } catch (DataFormatException dataFormatException) {}
      } else {
        System.arraycopy(arrayOfByte1, 0, arrayOfByte, j, arrayOfByte1.length);
        j += arrayOfByte1.length;
      } 
    } 
    return arrayOfByte;
  }
  
  private byte[] decodeSectionLegacy(int paramInt) {
    return null;
  }
  
  public int readInt32() {
    try {
      this.inputStream.read(this.int32Buf);
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
    ByteBuffer byteBuffer = ByteBuffer.wrap(this.int32Buf);
    byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    return byteBuffer.getInt();
  }
  
  public void NewSection() {
    this.sectionsCounter++;
    if (this.repformat == RepFormat.MODERN_121 && this.sectionsCounter == 2)
      readInt32(); 
  }
  
  public int sectionHeader(int paramInt) {
    if (paramInt == 0)
      return 0; 
    readInt32();
    return readInt32();
  }
  
  public void close() {
    try {
      this.inputStream.close();
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
  }
}
