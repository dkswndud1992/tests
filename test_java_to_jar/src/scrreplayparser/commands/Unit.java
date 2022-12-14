package scrreplayparser.commands;

import java.util.HashMap;
import java.util.Map;

public enum Unit {
  MARINE((short)0, "Marine"),
  GHOST((short)1, "Ghost"),
  VULTURE((short)2, "Vulture"),
  GOLIATH((short)3, "Goliath"),
  GOLIATH_TURRET((short)4, "Goliath Turret"),
  SIEGE_TANK_TANK_MODE((short)5, "Siege Tank (Tank Mode)"),
  SIEGE_TANK_TURRET_TANK_MODE((short)6, "Siege Tank Turret (Tank Mode)"),
  SCV((short)7, "SCV"),
  WRAITH((short)8, "Wraith"),
  SCIENCE_VESSEL((short)9, "Science Vessel"),
  GUI_MOTANG_FIREBAT((short)10, "Gui Motang (Firebat)"),
  DROPSHIP((short)11, "Dropship"),
  BATTLECRUISER((short)12, "Battlecruiser"),
  SPIDER_MINE((short)13, "Spider Mine"),
  NUCLEAR_MISSILE((short)14, "Nuclear Missile"),
  TERRAN_CIVILIAN((short)15, "Terran Civilian"),
  SARAH_KERRIGAN_GHOST((short)16, "Sarah Kerrigan (Ghost)"),
  ALAN_SCHEZAR_GOLIATH((short)17, "Alan Schezar (Goliath)"),
  ALAN_SCHEZAR_TURRET((short)18, "Alan Schezar Turret"),
  JIM_RAYNOR_VULTURE((short)19, "Jim Raynor (Vulture)"),
  JIM_RAYNOR_MARINE((short)20, "Jim Raynor (Marine)"),
  TOM_KAZANSKY_WRAITH((short)21, "Tom Kazansky (Wraith)"),
  MAGELLAN_SCIENCE_VESSEL((short)22, "Magellan (Science Vessel)"),
  EDMUND_DUKE_TANK_MODE((short)23, "Edmund Duke (Tank Mode)"),
  EDMUND_DUKE_TURRET_TANK_MODE((short)24, "Edmund Duke Turret (Tank Mode)"),
  EDMUND_DUKE_SIEGE_MODE((short)25, "Edmund Duke (Siege Mode)"),
  EDMUND_DUKE_TURRET_SIEGE_MODE((short)26, "Edmund Duke Turret (Siege Mode)"),
  ARCTURUS_MENGSK_BATTLECRUISER((short)27, "Arcturus Mengsk (Battlecruiser)"),
  HYPERION_BATTLECRUISER((short)28, "Hyperion (Battlecruiser)"),
  NORAD_II_BATTLECRUISER((short)29, "Norad II (Battlecruiser)"),
  TERRAN_SIEGE_TANK_SIEGE_MODE((short)30, "Terran Siege Tank (Siege Mode)"),
  SIEGE_TANK_TURRET_SIEGE_MODE((short)31, "Siege Tank Turret (Siege Mode)"),
  FIREBAT((short)32, "Firebat"),
  SCANNER_SWEEP((short)33, "Scanner Sweep"),
  MEDIC((short)34, "Medic"),
  LARVA((short)35, "Larva"),
  EGG((short)36, "Egg"),
  ZERGLING((short)37, "Zergling"),
  HYDRALISK((short)38, "Hydralisk"),
  ULTRALISK((short)39, "Ultralisk"),
  DRONE((short)41, "Drone"),
  OVERLORD((short)42, "Overlord"),
  MUTALISK((short)43, "Mutalisk"),
  GUARDIAN((short)44, "Guardian"),
  QUEEN((short)45, "Queen"),
  DEFILER((short)46, "Defiler"),
  SCOURGE((short)47, "Scourge"),
  TORRASQUE_ULTRALISK((short)48, "Torrasque (Ultralisk)"),
  MATRIARCH_QUEEN((short)49, "Matriarch (Queen)"),
  INFESTED_TERRAN((short)50, "Infested Terran"),
  INFESTED_KERRIGAN_INFESTED_TERRAN((short)51, "Infested Kerrigan (Infested Terran)"),
  UNCLEAN_ONE_DEFILER((short)52, "Unclean One (Defiler)"),
  HUNTER_KILLER_HYDRALISK((short)53, "Hunter Killer (Hydralisk)"),
  DEVOURING_ONE_ZERGLING((short)54, "Devouring One (Zergling)"),
  KUKULZA_MUTALISK((short)55, "Kukulza (Mutalisk)"),
  KUKULZA_GUARDIAN((short)56, "Kukulza (Guardian)"),
  YGGDRASILL_OVERLORD((short)57, "Yggdrasill (Overlord)"),
  VALKYRIE((short)58, "Valkyrie"),
  MUTALISK_COCOON((short)59, "Mutalisk Cocoon"),
  CORSAIR((short)60, "Corsair"),
  DARK_TEMPLAR((short)61, "Dark Templar"),
  DEVOURER((short)62, "Devourer"),
  DARK_ARCHON((short)63, "Dark Archon"),
  PROBE((short)64, "Probe"),
  ZEALOT((short)65, "Zealot"),
  DRAGOON((short)66, "Dragoon"),
  HIGH_TEMPLAR((short)67, "High Templar"),
  ARCHON((short)68, "Archon"),
  SHUTTLE((short)69, "Shuttle"),
  SCOUT((short)70, "Scout"),
  ARBITER((short)71, "Arbiter"),
  CARRIER((short)72, "Carrier"),
  INTERCEPTOR((short)73, "Interceptor"),
  PROTOSS_DARK_TEMPLAR_HERO((short)74, "Protoss Dark Templar (Hero)"),
  ZERATUL_DARK_TEMPLAR((short)75, "Zeratul (Dark Templar)"),
  TASSADAR_ZERATUL_ARCHON((short)76, "Tassadar/Zeratul (Archon)"),
  FENIX_ZEALOT((short)77, "Fenix (Zealot)"),
  FENIX_DRAGOON((short)78, "Fenix (Dragoon)"),
  TASSADAR_TEMPLAR((short)79, "Tassadar (Templar)"),
  MOJO_SCOUT((short)80, "Mojo (Scout)"),
  WARBRINGER_REAVER((short)81, "Warbringer (Reaver)"),
  GANTRITHOR_CARRIER((short)82, "Gantrithor (Carrier)"),
  REAVER((short)83, "Reaver"),
  OBSERVER((short)84, "Observer"),
  SCARAB((short)85, "Scarab"),
  DANIMOTH_ARBITER((short)86, "Danimoth (Arbiter)"),
  ALDARIS_TEMPLAR((short)87, "Aldaris (Templar)"),
  ARTANIS_SCOUT((short)88, "Artanis (Scout)"),
  RHYNADON_BADLANDS_CRITTER((short)89, "Rhynadon (Badlands Critter)"),
  BENGALAAS_JUNGLE_CRITTER((short)90, "Bengalaas (Jungle Critter)"),
  CARGO_SHIP_UNUSED((short)91, "Cargo Ship (Unused)"),
  MERCENARY_GUNSHIP_UNUSED((short)92, "Mercenary Gunship (Unused)"),
  SCANTID_DESERT_CRITTER((short)93, "Scantid (Desert Critter)"),
  KAKARU_TWILIGHT_CRITTER((short)94, "Kakaru (Twilight Critter)"),
  RAGNASAUR_ASHWORLD_CRITTER((short)95, "Ragnasaur (Ashworld Critter)"),
  URSADON_ICE_WORLD_CRITTER((short)96, "Ursadon (Ice World Critter)"),
  LURKER_EGG((short)97, "Lurker Egg"),
  RASZAGAL_CORSAIR((short)98, "Raszagal (Corsair)"),
  SAMIR_DURAN_GHOST((short)99, "Samir Duran (Ghost)"),
  ALEXEI_STUKOV_GHOST((short)100, "Alexei Stukov (Ghost)"),
  MAP_REVEALER((short)101, "Map Revealer"),
  GERARD_DU_GALLE_BATTLE_CRUISER((short)102, "Gerard DuGalle (BattleCruiser)"),
  LURKER((short)103, "Lurker"),
  INFESTED_DURAN_INFESTED_TERRAN((short)104, "Infested Duran (Infested Terran)"),
  DISRUPTION_WEB((short)105, "Disruption Web"),
  COMMAND_CENTER((short)106, "Command Center"),
  COM_SAT((short)107, "ComSat"),
  NUCLEAR_SILO((short)108, "Nuclear Silo"),
  SUPPLY_DEPOT((short)109, "Supply Depot"),
  REFINERY((short)110, "Refinery"),
  BARRACKS((short)111, "Barracks"),
  ACADEMY((short)112, "Academy"),
  FACTORY((short)113, "Factory"),
  STARPORT((short)114, "Starport"),
  CONTROL_TOWER((short)115, "Control Tower"),
  SCIENCE_FACILITY((short)116, "Science Facility"),
  COVERT_OPS((short)117, "Covert Ops"),
  PHYSICS_LAB((short)118, "Physics Lab"),
  MACHINE_SHOP((short)120, "Machine Shop"),
  REPAIR_BAY_UNUSED((short)121, "Repair Bay (Unused)"),
  ENGINEERING_BAY((short)122, "Engineering Bay"),
  ARMORY((short)123, "Armory"),
  MISSILE_TURRET((short)124, "Missile Turret"),
  BUNKER((short)125, "Bunker"),
  NORAD_II_CRASHED((short)126, "Norad II (Crashed)"),
  ION_CANNON((short)127, "Ion Cannon"),
  URAJ_CRYSTAL((short)128, "Uraj Crystal"),
  KHALIS_CRYSTAL((short)129, "Khalis Crystal"),
  INFESTED_CC((short)130, "Infested CC"),
  HATCHERY((short)131, "Hatchery"),
  LAIR((short)132, "Lair"),
  HIVE((short)133, "Hive"),
  NYDUS_CANAL((short)134, "Nydus Canal"),
  HYDRALISK_DEN((short)135, "Hydralisk Den"),
  DEFILER_MOUND((short)136, "Defiler Mound"),
  GREATER_SPIRE((short)137, "Greater Spire"),
  QUEENS_NEST((short)138, "Queens Nest"),
  EVOLUTION_CHAMBER((short)139, "Evolution Chamber"),
  ULTRALISK_CAVERN((short)140, "Ultralisk Cavern"),
  SPIRE((short)141, "Spire"),
  SPAWNING_POOL((short)142, "Spawning Pool"),
  CREEP_COLONY((short)143, "Creep Colony"),
  SPORE_COLONY((short)144, "Spore Colony"),
  UNUSED_ZERG_BUILDING_1((short)145, "Unused Zerg Building1"),
  SUNKEN_COLONY((short)146, "Sunken Colony"),
  ZERG_OVERMIND_WITH_SHELL((short)147, "Zerg Overmind (With Shell)"),
  OVERMIND((short)148, "Overmind"),
  EXTRACTOR((short)149, "Extractor"),
  MATURE_CHRYSALIS((short)150, "Mature Chrysalis"),
  CEREBRATE((short)151, "Cerebrate"),
  CEREBRATE_DAGGOTH((short)152, "Cerebrate Daggoth"),
  UNUSED_ZERG_BUILDING_2((short)153, "Unused Zerg Building2"),
  NEXUS((short)154, "Nexus"),
  ROBOTICS_FACILITY((short)155, "Robotics Facility"),
  PYLON((short)156, "Pylon"),
  ASSIMILATOR((short)157, "Assimilator"),
  UNUSED_PROTOSS_BUILDING_1((short)158, "Unused Protoss Building1"),
  OBSERVATORY((short)159, "Observatory"),
  GATEWAY((short)160, "Gateway"),
  UNUSED_PROTOSS_BUILDING_2((short)161, "Unused Protoss Building2"),
  PHOTON_CANNON((short)162, "Photon Cannon"),
  CITADEL_OF_ADUN((short)163, "Citadel of Adun"),
  CYBERNETICS_CORE((short)164, "Cybernetics Core"),
  TEMPLAR_ARCHIVES((short)165, "Templar Archives"),
  FORGE((short)166, "Forge"),
  STARGATE((short)167, "Stargate"),
  STASIS_CELL_PRISON((short)168, "Stasis Cell/Prison"),
  FLEET_BEACON((short)169, "Fleet Beacon"),
  ARBITER_TRIBUNAL((short)170, "Arbiter Tribunal"),
  ROBOTICS_SUPPORT_BAY((short)171, "Robotics Support Bay"),
  SHIELD_BATTERY((short)172, "Shield Battery"),
  KHAYDARIN_CRYSTAL_FORMATION((short)173, "Khaydarin Crystal Formation"),
  PROTOSS_TEMPLE((short)174, "Protoss Temple"),
  XEL_NAGA_TEMPLE((short)175, "Xel'Naga Temple"),
  MINERAL_FIELD_TYPE_1((short)176, "Mineral Field (Type 1)"),
  MINERAL_FIELD_TYPE_2((short)177, "Mineral Field (Type 2)"),
  MINERAL_FIELD_TYPE_3((short)178, "Mineral Field (Type 3)"),
  CAVE_UNUSED((short)179, "Cave (Unused)"),
  CAVE_IN_UNUSED((short)180, "Cave-in (Unused)"),
  CANTINA_UNUSED((short)181, "Cantina (Unused)"),
  MINING_PLATFORM_UNUSED((short)182, "Mining Platform (Unused)"),
  INDEPENDENT_COMMAND_CENTER_UNUSED((short)183, "Independent Command Center (Unused)"),
  INDEPENDENT_STARPORT_UNUSED((short)184, "Independent Starport (Unused)"),
  INDEPENDENT_JUMP_GATE_UNUSED((short)185, "Independent Jump Gate (Unused)"),
  RUINS_UNUSED((short)186, "Ruins (Unused)"),
  KHAYDARIN_CRYSTAL_FORMATION_UNUSED((short)187, "Khaydarin Crystal Formation (Unused)"),
  VESPENE_GEYSER((short)188, "Vespene Geyser"),
  WARP_GATE((short)189, "Warp Gate"),
  PSI_DISRUPTER((short)190, "Psi Disrupter"),
  ZERG_MARKER((short)191, "Zerg Marker"),
  TERRAN_MARKER((short)192, "Terran Marker"),
  PROTOSS_MARKER((short)193, "Protoss Marker"),
  ZERG_BEACON((short)194, "Zerg Beacon"),
  TERRAN_BEACON((short)195, "Terran Beacon"),
  PROTOSS_BEACON((short)196, "Protoss Beacon"),
  ZERG_FLAG_BEACON((short)197, "Zerg Flag Beacon"),
  TERRAN_FLAG_BEACON((short)198, "Terran Flag Beacon"),
  PROTOSS_FLAG_BEACON((short)199, "Protoss Flag Beacon"),
  POWER_GENERATOR((short)200, "Power Generator"),
  OVERMIND_COCOON((short)201, "Overmind Cocoon"),
  DARK_SWARM((short)202, "Dark Swarm"),
  FLOOR_MISSILE_TRAP((short)203, "Floor Missile Trap"),
  FLOOR_HATCH_UNUSED((short)204, "Floor Hatch (Unused)"),
  LEFT_UPPER_LEVEL_DOOR((short)205, "Left Upper Level Door"),
  RIGHT_UPPER_LEVEL_DOOR((short)206, "Right Upper Level Door"),
  LEFT_PIT_DOOR((short)207, "Left Pit Door"),
  RIGHT_PIT_DOOR((short)208, "Right Pit Door"),
  FLOOR_GUN_TRAP((short)209, "Floor Gun Trap"),
  LEFT_WALL_MISSILE_TRAP((short)210, "Left Wall Missile Trap"),
  LEFT_WALL_FLAME_TRAP((short)211, "Left Wall Flame Trap"),
  RIGHT_WALL_MISSILE_TRAP((short)212, "Right Wall Missile Trap"),
  RIGHT_WALL_FLAME_TRAP((short)213, "Right Wall Flame Trap"),
  START_LOCATION((short)214, "Start Location"),
  FLAG((short)215, "Flag"),
  YOUNG_CHRYSALIS((short)216, "Young Chrysalis"),
  PSI_EMITTER((short)217, "Psi Emitter"),
  DATA_DISC((short)218, "Data Disc"),
  KHAYDARIN_CRYSTAL((short)219, "Khaydarin Crystal"),
  MINERAL_CLUSTER_TYPE_1((short)220, "Mineral Cluster Type 1"),
  MINERAL_CLUSTER_TYPE_2((short)221, "Mineral Cluster Type 2"),
  PROTOSS_VESPENE_GAS_ORB_TYPE_1((short)222, "Protoss Vespene Gas Orb Type 1"),
  PROTOSS_VESPENE_GAS_ORB_TYPE_2((short)223, "Protoss Vespene Gas Orb Type 2"),
  ZERG_VESPENE_GAS_SAC_TYPE_1((short)224, "Zerg Vespene Gas Sac Type 1"),
  ZERG_VESPENE_GAS_SAC_TYPE_2((short)225, "Zerg Vespene Gas Sac Type 2"),
  TERRAN_VESPENE_GAS_TANK_TYPE_1((short)226, "Terran Vespene Gas Tank Type 1"),
  TERRAN_VESPENE_GAS_TANK_TYPE_2((short)227, "Terran Vespene Gas Tank Type 2"),
  UNKNOWN((short)153, "Unknown");
  
  short id;
  
  String name;
  
  private static final Map<Short, Unit> lookupByByte;
  
  Unit(short paramShort, String paramString1) {
    this.id = paramShort;
    this.name = paramString1;
  }
  
  public String getName() {
    return this.name;
  }
  
  public static Unit getByID(short paramShort) {
    Unit unit1 = lookupByByte.get(Short.valueOf(paramShort));
    if (unit1 != null)
      return unit1; 
    Unit unit2 = UNKNOWN;
    unit2.id = paramShort;
    return unit2;
  }
  
  static {
    lookupByByte = new HashMap<>();
    for (Unit unit : values())
      lookupByByte.put(Short.valueOf(unit.id), unit); 
    lookupByByte.remove(Short.valueOf((short)153));
  }
}
