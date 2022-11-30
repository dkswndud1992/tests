package scrreplayparser.commands;

import java.util.HashMap;
import java.util.Map;

public enum CommandType {
  KEEP_ALIVE((byte)5, "KeepAlive"),
  SAVE_GAME((byte)6, "SaveGame"),
  LOAD_GAME((byte)7, "LoadGame"),
  RESTART_GAME((byte)8, "RestartGame"),
  SELECT((byte)9, "Select"),
  SELECT_ADD((byte)10, "SelectAdd"),
  SELECT_REMOVE((byte)11, "SelectRemove"),
  BUILD((byte)12, "Build"),
  VISION((byte)13, "Vision"),
  ALLIANCE((byte)14, "Alliance"),
  GAME_SPEED((byte)15, "GameSpeed"),
  PAUSE((byte)16, "Pause"),
  RESUME((byte)17, "Resume"),
  CHEAT((byte)18, "Cheat"),
  HOTKEY((byte)19, "Hotkey"),
  RIGHT_CLICK((byte)20, "RightClick"),
  TARGETED_ORDER((byte)21, "TargetedOrder"),
  CANCEL_BUILD((byte)24, "CancelBuild"),
  CANCEL_MORPH((byte)25, "CancelMorph"),
  STOP((byte)26, "Stop"),
  CARRIER_STOP((byte)27, "CarrierStop"),
  REAVER_STOP((byte)28, "ReaverStop"),
  ORDER_NOTHING((byte)29, "OrderNothing"),
  RETURN_CARGO((byte)30, "ReturnCargo"),
  TRAIN((byte)31, "Train"),
  CANCEL_TRAIN((byte)32, "CancelTrain"),
  CLOACK((byte)33, "Cloack"),
  DECLOACK((byte)34, "Decloack"),
  UNIT_MORPH((byte)35, "UnitMorph"),
  UNSIEGE((byte)37, "Unsiege"),
  SIEGE((byte)38, "Siege"),
  TRAIN_FIGHTER((byte)39, "TrainFighter"),
  UNLOAD_ALL((byte)40, "UnloadAll"),
  UNLOAD((byte)41, "Unload"),
  MERGE_ARCHON((byte)42, "MergeArchon"),
  HOLD_POSITION((byte)43, "HoldPosition"),
  BURROW((byte)44, "Burrow"),
  UNBURROW((byte)45, "Unburrow"),
  CANCEL_NUKE((byte)46, "CancelNuke"),
  LIFT_OFF((byte)47, "LiftOff"),
  TECH((byte)48, "Tech"),
  CANCEL_TECH((byte)49, "CancelTech"),
  UPGRADE((byte)50, "Upgrade"),
  CANCEL_UPGRADE((byte)51, "CancelUpgrade"),
  CANCEL_ADDON((byte)52, "CancelAddon"),
  BUILDING_MORPH((byte)53, "BuildingMorph"),
  STIM((byte)54, "Stim"),
  SYNC((byte)55, "Sync"),
  VOICE_ENABLE((byte)56, "VoiceEnable"),
  VOICE_DISABLE((byte)57, "VoiceDisable"),
  VOICE_SQUELCH((byte)58, "VoiceSquelch"),
  VOICE_UNSQUELCH((byte)59, "VoiceUnsquelch"),
  START_GAME((byte)60, "StartGame"),
  DOWNLOAD_PERCENTAGE((byte)61, "DownloadPercentage"),
  CHANGE_GAME_SLOT((byte)62, "ChangeGameSlot"),
  NEW_NET_PLAYER((byte)63, "NewNetPlayer"),
  JOINED_GAME((byte)64, "JoinedGame"),
  CHANGE_RACE((byte)65, "ChangeRace"),
  TEAM_GAME_TEAM((byte)66, "TeamGameTeam"),
  UMS_TEAM((byte)67, "UMSTeam"),
  MELEE_TEAM((byte)68, "MeleeTeam"),
  SWAP_PLAYERS((byte)69, "SwapPlayers"),
  SAVED_DATA((byte)72, "SavedData"),
  BRIEFING_START((byte)84, "BriefingStart"),
  LATENCY((byte)85, "Latency"),
  REPLAY_SPEED((byte)86, "ReplaySpeed"),
  LEAVE_GAME((byte)87, "LeaveGame"),
  MINIMAP_PING((byte)88, "MinimapPing"),
  MERGE_DARK_ARCHON((byte)90, "MergeDarkArchon"),
  MAKE_GAME_PUBLIC((byte)91, "MakeGamePublic"),
  CHAT((byte)92, "Chat"),
  MOVE121((byte)96, "Move121"),
  Type_0X61((byte)97, "AttackMove_0x61"),
  Type_0X62((byte)98, "0x62"),
  SELECT121((byte)99, "Select121"),
  Type_0X64((byte)100, "0x64"),
  Type_0X65((byte)101, "0x65"),
  UNKNOWN((byte)-103, "Unknown");
  
  int id;
  
  String name;
  
  private static final Map<Integer, CommandType> lookupByByte;
  
  CommandType(byte paramByte, String paramString1) {
    this.id = paramByte;
    this.name = paramString1;
  }
  
  public int getId() {
    return this.id;
  }
  
  public String getName() {
    return this.name;
  }
  
  public static CommandType getByID(int paramInt) {
    CommandType commandType1 = lookupByByte.get(Integer.valueOf(paramInt));
    if (commandType1 != null)
      return commandType1; 
    CommandType commandType2 = UNKNOWN;
    commandType2.id = paramInt;
    return commandType2;
  }
  
  static {
    lookupByByte = new HashMap<>();
    for (CommandType commandType : values())
      lookupByByte.put(Integer.valueOf(commandType.id), commandType); 
    lookupByByte.remove(Integer.valueOf(153));
  }
}
