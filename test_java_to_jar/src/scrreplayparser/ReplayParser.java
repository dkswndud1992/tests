package scrreplayparser;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import scrreplayparser.commands.BaseCommand;
import scrreplayparser.commands.BuildCommand;
import scrreplayparser.commands.BuildingMorphCommand;
import scrreplayparser.commands.CancelTrainCommand;
import scrreplayparser.commands.ChatCommand;
import scrreplayparser.commands.CommandType;
import scrreplayparser.commands.GameSpeedCommand;
import scrreplayparser.commands.HotkeyCommand;
import scrreplayparser.commands.HotkeyType;
import scrreplayparser.commands.Latency;
import scrreplayparser.commands.LatencyCommand;
import scrreplayparser.commands.LeaveGameCommand;
import scrreplayparser.commands.LeaveReason;
import scrreplayparser.commands.LiftOffCommand;
import scrreplayparser.commands.MinimapPingCommand;
import scrreplayparser.commands.Order;
import scrreplayparser.commands.ParseErrorCommand;
import scrreplayparser.commands.QueueableCommand;
import scrreplayparser.commands.RightClickCommand;
import scrreplayparser.commands.SelectCommand;
import scrreplayparser.commands.TargetedOrderCommand;
import scrreplayparser.commands.Tech;
import scrreplayparser.commands.TechCommand;
import scrreplayparser.commands.TrainCommand;
import scrreplayparser.commands.UnanalyzedCommand;
import scrreplayparser.commands.Unit;
import scrreplayparser.commands.UnitTag;
import scrreplayparser.commands.Upgrade;
import scrreplayparser.commands.UpgradeCommand;
import scrreplayparser.decoder.Decoder;
import scrreplayparser.decoder.RepFormat;
import scrreplayparser.repcore.Frame;
import scrreplayparser.repcore.GameEngine;
import scrreplayparser.repcore.GameSpeed;
import scrreplayparser.repcore.GameType;
import scrreplayparser.repcore.MapPoint;
import scrreplayparser.repcore.PlayerColor;
import scrreplayparser.repcore.PlayerRace;
import scrreplayparser.repcore.PlayerType;
import scrreplayparser.repcore.TileSet;
import scrreplayparser.replaymodel.Commands;
import scrreplayparser.replaymodel.MapData;
import scrreplayparser.replaymodel.Player;
import scrreplayparser.replaymodel.Replay;
import scrreplayparser.replaymodel.ReplayHeader;
import scrreplayparser.replaymodel.ReplaySection;
import scrreplayparser.replaymodel.StartLocation;
import scrreplayparser.util.ByteArrayReader;

public class ReplayParser {
  public static final String VERSION = "v1.5";
  
  String fileName;
  
  public ReplayParser(String paramString) {
    this.fileName = paramString;
  }
  
  public Replay parseFile(boolean paramBoolean1, boolean paramBoolean2) {
    Decoder decoder = Decoder.createDecoderFromFile(this.fileName);
    if (decoder.getRepformat() == RepFormat.UNKNOWN || decoder.getRepformat() == RepFormat.LEGACY)
      return null; 
    try {
      Replay replay = parse(decoder, paramBoolean1, paramBoolean2);
      if (paramBoolean1 && paramBoolean2)
        replay.computeAdditionalReplayData(); 
      return replay;
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return null;
  }
  
  private Replay parse(Decoder paramDecoder, boolean paramBoolean1, boolean paramBoolean2) {
    ReplaySection replaySection;
    Replay replay = new Replay();
    if (paramBoolean2) {
      replaySection = ReplaySection.MAPDATA;
    } else if (paramBoolean1) {
      replaySection = ReplaySection.COMMANDS;
    } else {
      replaySection = ReplaySection.HEADER;
    } 
    for (ReplaySection replaySection1 : ReplaySection.values()) {
      paramDecoder.NewSection();
      int i = 0;
      if (replaySection1.getSize() == 0) {
        try {
          byte[] arrayOfByte = paramDecoder.decodeSection(4);
          ByteBuffer byteBuffer = ByteBuffer.wrap(arrayOfByte);
          byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
          i = byteBuffer.getInt();
        } catch (IOException iOException) {
          iOException.printStackTrace();
        } 
      } else {
        i = replaySection1.getSize();
      } 
      System.out.println("i:"+i);
      try {
        byte[] arrayOfByte = paramDecoder.decodeSection(i);
        ArrayList<Byte> arrayList = new ArrayList<Byte>();
        for (byte b : arrayOfByte)
          arrayList.add(Byte.valueOf(b)); 
        	System.out.println("replaySection1.ordinal():"+replaySection1.ordinal());
        switch (replaySection1.ordinal()+1) {
          case 1:
            // parseReplayID(arrayList);
            break;
          case 2:
            replay.setHeader(parseHeader(arrayList));
            break;
          case 3:
            if (paramBoolean1)
              replay.setCommands(parseCommands(arrayList)); 
            break;
          case 4:
            if (paramBoolean2)
              replay.setMapData(parseMapData(arrayList, replay.getHeader())); 
            break;
        } 
        if (replaySection1 == replaySection)
          break; 
      } catch (IOException iOException) {
        iOException.printStackTrace();
      } 
    } 
    return replay;
  }
  
  public void parseReplayID(List<Byte> paramList)  {
	try{
		switch (new String(getSubArrayFromList(paramList, 0, paramList.size()))) {
	      case "reRS":
	      case "seRS":
	        return;
	    } 
	}catch(Exception e){
		e.printStackTrace();
	}
  }
  
  public byte[] getSubArrayFromList(List<Byte> paramList, int paramInt1, int paramInt2) {
	  System.out.println("paramList:"+paramList);
	  System.out.println("paramInt1:"+paramInt1);
	  System.out.println("paramInt2:"+paramInt2);
	  
    List<Byte> list = paramList.subList(paramInt1, paramInt2);
    System.out.println("list:"+list);
    System.out.println("list.size():"+list.size());
    byte[] arrayOfByte = new byte[list.size()];
    for (int b = 0; b < arrayOfByte.length; b++) {
    	System.out.println(b+":"+ (list.get(b)).byteValue());
      arrayOfByte[b] = (list.get(b)).byteValue(); 
    }
    return arrayOfByte;
  }
  
  public String trimTrailingZeroChars(String paramString) {
    for (byte b = 0; b < paramString.length(); b++) {
      if (paramString.charAt(b) == '\000')
        return paramString.substring(0, b); 
    } 
    return paramString;
  }
  
  public ReplayHeader parseHeader(List<Byte> paramList) {
    ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
    ReplayHeader replayHeader = new ReplayHeader();
    replayHeader.setEngine(GameEngine.getByID(((Byte)paramList.get(0)).byteValue()));
    replayHeader.setFrames(new Frame(ByteBuffer.wrap(getSubArrayFromList(paramList, 1, paramList.size())).order(byteOrder).getInt()));
    replayHeader.setStartTime(Instant.ofEpochSecond(ByteBuffer.wrap(getSubArrayFromList(paramList, 8, paramList.size())).order(byteOrder).getInt(), 0L));
    replayHeader.setTitle(trimTrailingZeroChars(new String(getSubArrayFromList(paramList, 24, 52))));
    replayHeader.setMapWidth(ByteBuffer.wrap(getSubArrayFromList(paramList, 52, paramList.size())).order(byteOrder).getShort());
    replayHeader.setMapHeight(ByteBuffer.wrap(getSubArrayFromList(paramList, 54, paramList.size())).order(byteOrder).getShort());
    replayHeader.setAvailableSlotsCount(((Byte)paramList.get(57)).byteValue());
    replayHeader.setSpeed(GameSpeed.getByID(((Byte)paramList.get(58)).byteValue()));
    replayHeader.setType(GameType.getByID(ByteBuffer.wrap(getSubArrayFromList(paramList, 60, paramList.size())).order(byteOrder).getShort()));
    replayHeader.setSubType(ByteBuffer.wrap(getSubArrayFromList(paramList, 62, paramList.size())).order(byteOrder).getShort());
    replayHeader.setHost(trimTrailingZeroChars(new String(getSubArrayFromList(paramList, 72, 96))));
    replayHeader.setMap(trimTrailingZeroChars(new String(getSubArrayFromList(paramList, 97, 123))));
    replayHeader.setPIDPlayers(new HashMap<>(12));
    replayHeader.setSlots(new Player[12]);
    List<Byte> list = paramList.subList(161, 593);
    for (byte b = 0; b < (replayHeader.getSlots()).length; b++) {
      Player player = new Player();
      replayHeader.getSlots()[b] = player;
      List<Byte> list1 = list.subList(b * 36, b * 36 + 36);
      player.setSlotID(ByteBuffer.wrap(getSubArrayFromList(list1, 0, list1.size())).order(byteOrder).getShort());
      player.setId(((Byte)list1.get(4)).byteValue());
      player.setType(PlayerType.getByID(((Byte)list1.get(8)).byteValue()));
      player.setRace(PlayerRace.getByID(((Byte)list1.get(9)).byteValue()));
      player.setTeam(((Byte)list1.get(10)).byteValue());
      player.setName(trimTrailingZeroChars(new String(getSubArrayFromList(list1, 11, 36))));
      if (b < 8)
        player.setColor(PlayerColor.getByID(ByteBuffer.wrap(getSubArrayFromList(paramList, 593 + b * 4, paramList.size())).order(byteOrder).getInt())); 
      if (!player.getName().isEmpty()) {
        replayHeader.getPIDPlayers().put(Integer.valueOf(player.getId()), player);
        replayHeader.getOrigPlayers().add(player);
      } 
    } 
    replayHeader.setPlayers(new ArrayList<Player>(replayHeader.getOrigPlayers()));
    Collections.sort(replayHeader.getPlayers(), Comparator.comparing(Player::getTeam));
    return replayHeader;
  }
  
  public Commands parseCommands(List<Byte> paramList) {
    Commands commands = new Commands();
    ByteArrayReader byteArrayReader = new ByteArrayReader(paramList);
    int i = paramList.size();
    while (byteArrayReader.getPos() < i) {
      int j = byteArrayReader.getInteger();
      int k = byteArrayReader.getNextByteAsInt();
      int m = byteArrayReader.getPos() + k;
      while (byteArrayReader.getPos() < m) {
        UnanalyzedCommand unanalyzedCommand1 = null;
        int i1;
        UnitTag[] arrayOfUnitTag1;
        int i2;
        int i3;
        int i4;
        UnitTag[] arrayOfUnitTag2;
        int i5;
        BaseCommand baseCommand;
        ParseErrorCommand parseErrorCommand;
        boolean bool = true;
        int n = byteArrayReader.getNextByteAsInt();
        CommandType commandType = CommandType.getByID(byteArrayReader.getNextByteAsInt());
        switch (commandType.ordinal()) {
          case 1:
            new RightClickCommand(new Frame(j), n, commandType, new MapPoint(byteArrayReader.getShort(), byteArrayReader.getShort()), new UnitTag(byteArrayReader.getShort()), Unit.getByID(byteArrayReader.getShort()), (byteArrayReader.getNextByteAsInt() != 0));
            break;
          case 2:
          case 3:
          case 4:
            i1 = byteArrayReader.getNextByteAsInt();
            arrayOfUnitTag1 = new UnitTag[i1];
            for (i2 = 0; i2 < arrayOfUnitTag1.length; i2++)
              arrayOfUnitTag1[i2] = new UnitTag(byteArrayReader.getShort()); 
            new SelectCommand(new Frame(j), n, commandType, arrayOfUnitTag1);
            break;
          case 5:
            new HotkeyCommand(new Frame(j), n, commandType, HotkeyType.getByID(byteArrayReader.getNextByteAsInt()), byteArrayReader.getNextByteAsInt());
            break;
          case 6:
          case 7:
            new TrainCommand(new Frame(j), n, commandType, Unit.getByID(byteArrayReader.getShort()));
            break;
          case 8:
            new TargetedOrderCommand(new Frame(j), n, commandType, new MapPoint(byteArrayReader.getShort(), byteArrayReader.getShort()), new UnitTag(byteArrayReader.getShort()), Unit.getByID(byteArrayReader.getShort()), Order.getByID(byteArrayReader.getNextByteAsInt()), (byteArrayReader.getNextByteAsInt() != 0));
            break;
          case 9:
            new BuildCommand(new Frame(j), n, commandType, Order.getByID(byteArrayReader.getNextByteAsInt()), new MapPoint(byteArrayReader.getShort(), byteArrayReader.getShort()), Unit.getByID(byteArrayReader.getShort()));
            break;
          case 10:
          case 11:
          case 12:
          case 13:
          case 14:
          case 15:
          case 16:
          case 17:
          case 18:
          case 19:
            new QueueableCommand(new Frame(j), n, commandType, (byteArrayReader.getNextByteAsInt() != 0));
            break;
          case 20:
            new LeaveGameCommand(new Frame(j), n, commandType, LeaveReason.getByID(byteArrayReader.getNextByteAsInt()));
            break;
          case 21:
            new MinimapPingCommand(new Frame(j), n, commandType, new MapPoint(byteArrayReader.getShort(), byteArrayReader.getShort()));
            break;
          case 22:
            i2 = byteArrayReader.getNextByteAsInt();
            if (i2 > 127)
              i2 -= 128; 
            if (n > 127)
              n -= 128; 
            new ChatCommand(new Frame(j), n, commandType, i2, trimTrailingZeroChars(new String(byteArrayReader.getBytes(80))));
            break;
          case 23:
            new UnanalyzedCommand(new Frame(j), n, commandType, byteArrayReader.getBytes(2));
            break;
          case 24:
            new UnanalyzedCommand(new Frame(j), n, commandType, byteArrayReader.getBytes(4));
            break;
          case 25:
            new GameSpeedCommand(new Frame(j), n, commandType, GameSpeed.getByID(byteArrayReader.getNextByteAsInt()));
            break;
          case 26:
            new CancelTrainCommand(new Frame(j), n, commandType, new UnitTag(byteArrayReader.getShort()));
            break;
          case 27:
            new UnanalyzedCommand(new Frame(j), n, commandType, byteArrayReader.getBytes(2));
            break;
          case 28:
            new LiftOffCommand(new Frame(j), n, commandType, new MapPoint(byteArrayReader.getShort(), byteArrayReader.getShort()));
            break;
          case 29:
            new TechCommand(new Frame(j), n, commandType, Tech.getByID(byteArrayReader.getNextByteAsInt()));
            break;
          case 30:
            new UpgradeCommand(new Frame(j), n, commandType, Upgrade.getByID(byteArrayReader.getNextByteAsInt()));
            break;
          case 31:
            new BuildingMorphCommand(new Frame(j), n, commandType, Unit.getByID(byteArrayReader.getShort()));
            break;
          case 32:
            new LatencyCommand(new Frame(j), n, commandType, Latency.getByID(byteArrayReader.getNextByteAsInt()));
            break;
          case 33:
            new UnanalyzedCommand(new Frame(j), n, commandType, byteArrayReader.getBytes(4));
            break;
          case 34:
          case 35:
            i3 = byteArrayReader.getInteger();
            byteArrayReader.incrementPos(i3);
            break;
          case 36:
          case 37:
          case 38:
          case 39:
          case 40:
          case 41:
          case 42:
          case 43:
          case 44:
          case 45:
          case 46:
          case 47:
          case 48:
          case 49:
          case 50:
          case 51:
          case 52:
          case 53:
          case 54:
          case 55:
          case 56:
          case 57:
            break;
          case 58:
            new UnanalyzedCommand(new Frame(j), n, commandType, byteArrayReader.getBytes(6));
            break;
          case 59:
            new UnanalyzedCommand(new Frame(j), n, commandType, byteArrayReader.getBytes(1));
            break;
          case 60:
            new UnanalyzedCommand(new Frame(j), n, commandType, byteArrayReader.getBytes(1));
            break;
          case 61:
            new UnanalyzedCommand(new Frame(j), n, commandType, byteArrayReader.getBytes(1));
            break;
          case 62:
            new UnanalyzedCommand(new Frame(j), n, commandType, byteArrayReader.getBytes(5));
            break;
          case 63:
            new UnanalyzedCommand(new Frame(j), n, commandType, byteArrayReader.getBytes(7));
            break;
          case 64:
            new UnanalyzedCommand(new Frame(j), n, commandType, byteArrayReader.getBytes(17));
            break;
          case 65:
            new UnanalyzedCommand(new Frame(j), n, commandType, byteArrayReader.getBytes(2));
            break;
          case 66:
            new UnanalyzedCommand(new Frame(j), n, commandType, byteArrayReader.getBytes(1));
            break;
          case 67:
            new UnanalyzedCommand(new Frame(j), n, commandType, byteArrayReader.getBytes(1));
            break;
          case 68:
            new UnanalyzedCommand(new Frame(j), n, commandType, byteArrayReader.getBytes(2));
            break;
          case 69:
            new UnanalyzedCommand(new Frame(j), n, commandType, byteArrayReader.getBytes(2));
            break;
          case 70:
            new UnanalyzedCommand(new Frame(j), n, commandType, byteArrayReader.getBytes(12));
            break;
          case 71:
            new UnanalyzedCommand(new Frame(j), n, commandType, byteArrayReader.getBytes(9));
            break;
          case 72:
            new UnanalyzedCommand(new Frame(j), n, commandType, byteArrayReader.getBytes(11));
            break;
          case 73:
            new UnanalyzedCommand(new Frame(j), n, commandType, byteArrayReader.getBytes(12));
            break;
          case 74:
            new UnanalyzedCommand(new Frame(j), n, commandType, byteArrayReader.getBytes(4));
            break;
          case 75:
            i4 = byteArrayReader.getNextByteAsInt();
            arrayOfUnitTag2 = new UnitTag[i4];
            for (i5 = 0; i5 < arrayOfUnitTag2.length; i5++)
              arrayOfUnitTag2[i5] = new UnitTag(byteArrayReader.getInteger()); 
            new SelectCommand(new Frame(j), n, commandType, arrayOfUnitTag2);
            break;
          case 76:
          case 77:
            i5 = byteArrayReader.getNextByteAsInt();
            unanalyzedCommand1 = new UnanalyzedCommand(new Frame(j), n, commandType, byteArrayReader.getBytes(i5 * 4));
            break;
          default:
            System.out.format("skipping typeID: %#x, frame: %d, playerID: %d, remaining bytes: %d %s\n", new Object[] { Integer.valueOf(commandType.getId()), Integer.valueOf(j), Integer.valueOf(n), Integer.valueOf(m - byteArrayReader.getPos()), byteArrayReader.getByteData().subList(byteArrayReader.getPos(), m) });
            baseCommand = null;
            if (commands.getCmds().size() > 0)
              baseCommand = commands.getCmds().get(commands.getCmds().size() - 1); 
            parseErrorCommand = new ParseErrorCommand(new Frame(j), n, commandType, baseCommand);
            commands.getParseErrCmds().add(parseErrorCommand);
            byteArrayReader.setPos(m);
            bool = false;
            break;
        } 
        if (bool) {
          if (unanalyzedCommand1 == null) {
            commands.getCmds().add(new UnanalyzedCommand(new Frame(j), n, commandType, null));
            continue;
          } 
          commands.getCmds().add(unanalyzedCommand1);
        } 
      } 
      byteArrayReader.setPos(m);
    } 
    return commands;
  }
  
  public MapData parseMapData(List<Byte> paramList, ReplayHeader paramReplayHeader) {
    MapData mapData = new MapData();
    ByteArrayReader byteArrayReader = new ByteArrayReader(paramList);
    int i = paramList.size();
    while (byteArrayReader.getPos() < i) {
      short s1;
      short s2;
      int m;
      int n;
      String str = byteArrayReader.getString(4);
      int j = byteArrayReader.getInteger();
      int k = byteArrayReader.getPos() + j;
      switch (str) {
        case "VER ":
          mapData.setVersion(byteArrayReader.getShort());
          break;
        case "ERA ":
          mapData.setTileset(TileSet.getByID((short)(byteArrayReader.getShort() & 0x7)));
          break;
        case "DIM ":
          s1 = byteArrayReader.getShort();
          s2 = byteArrayReader.getShort();
          if (s1 <= 256 && s2 <= 256) {
            if (s1 > paramReplayHeader.getMapWidth())
              paramReplayHeader.setMapWidth(s1); 
            if (s2 > paramReplayHeader.getMapHeight())
              paramReplayHeader.setMapHeight(s2); 
          } 
          break;
        case "MTXM":
          m = j / 2;
          if (mapData.getTiles() == null)
            mapData.setTiles(new ArrayList<Short>(m)); 
          for (n = 0; n < m; n++)
            mapData.getTiles().add(n, Short.valueOf(byteArrayReader.getShort())); 
          break;
        case "UNIT":
          while (byteArrayReader.getPos() < k) {
            n = byteArrayReader.getPos() + 36;
            byteArrayReader.incrementPos(4);
            short s3 = byteArrayReader.getShort();
            short s4 = byteArrayReader.getShort();
            short s5 = byteArrayReader.getShort();
            byteArrayReader.incrementPos(2);
            byteArrayReader.incrementPos(2);
            byteArrayReader.incrementPos(2);
            int i1 = byteArrayReader.getNextByteAsInt();
            switch (Unit.getByID(s5).ordinal()) {
              case 1:
              case 2:
              case 3:
                mapData.getMineralFields().add(new MapPoint(s3, s4));
                break;
              case 4:
                mapData.getGeysers().add(new MapPoint(s3, s4));
                break;
              case 5:
                mapData.getStartLocations().add(new StartLocation(new MapPoint(s3, s4), i1));
                break;
            } 
            byteArrayReader.setPos(n);
          } 
          break;
      } 
      byteArrayReader.setPos(k);
    } 
    return mapData;
  }
}
