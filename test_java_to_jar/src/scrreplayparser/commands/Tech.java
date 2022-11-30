package scrreplayparser.commands;

import java.util.HashMap;
import java.util.Map;

public enum Tech {
  STIM_PACKS((byte)0, "Stim Packs"),
  LOCKDOWN((byte)1, "Lockdown"),
  EMP_SHOCKWAVE((byte)2, "EMP Shockwave"),
  SPIDER_MINES((byte)3, "Spider Mines"),
  SCANNER_SWEEP((byte)4, "Scanner Sweep"),
  TANK_SIEGE_MODE((byte)5, "Tank Siege Mode"),
  DEFENSIVE_MATRIX((byte)6, "Defensive Matrix"),
  IRRADIATE((byte)7, "Irradiate"),
  YAMATO_GUN((byte)8, "Yamato Gun"),
  CLOAKING_FIELD((byte)9, "Cloaking Field"),
  PERSONNEL_CLOAKING((byte)10, "Personnel Cloaking"),
  BURROWING((byte)11, "Burrowing"),
  INFESTATION((byte)12, "Infestation"),
  SPAWN_BROODLINGS((byte)13, "Spawn Broodlings"),
  DARK_SWARM((byte)14, "Dark Swarm"),
  PLAGUE((byte)15, "Plague"),
  CONSUME((byte)16, "Consume"),
  ENSNARE((byte)17, "Ensnare"),
  PARASITE((byte)18, "Parasite"),
  PSIONIC_STORM((byte)19, "Psionic Storm"),
  HALLUCINATION((byte)20, "Hallucination"),
  RECALL((byte)21, "Recall"),
  STASIS_FIELD((byte)22, "Stasis Field"),
  ARCHON_WARP((byte)23, "Archon Warp"),
  RESTORATION((byte)24, "Restoration"),
  DISRUPTION_WEB((byte)25, "Disruption Web"),
  UNUSED_26((byte)26, "Unused 26"),
  MIND_CONTROL((byte)27, "Mind Control"),
  DARK_ARCHON_MELD((byte)28, "Dark Archon Meld"),
  FEEDBACK((byte)29, "Feedback"),
  OPTICAL_FLARE((byte)30, "Optical Flare"),
  MAELSTROM((byte)31, "Maelstrom"),
  LURKER_ASPECT((byte)32, "Lurker Aspect"),
  UNUSED_33((byte)33, "Unused 33"),
  HEALING((byte)34, "Healing"),
  UNKNOWN((byte)-103, "Unknown");
  
  int id;
  
  String name;
  
  private static final Map<Integer, Tech> lookupByByte;
  
  Tech(byte paramByte, String paramString1) {
    this.id = paramByte;
    this.name = paramString1;
  }
  
  public String getName() {
    return this.name;
  }
  
  public static Tech getByID(int paramInt) {
    Tech tech1 = lookupByByte.get(Integer.valueOf(paramInt));
    if (tech1 != null)
      return tech1; 
    Tech tech2 = UNKNOWN;
    tech2.id = paramInt;
    return tech2;
  }
  
  static {
    lookupByByte = new HashMap<>();
    for (Tech tech : values())
      lookupByByte.put(Integer.valueOf(tech.id), tech); 
    lookupByByte.remove(Integer.valueOf(153));
  }
}
