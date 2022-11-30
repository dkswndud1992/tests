package scrreplayparser.commands;

import java.util.HashMap;
import java.util.Map;

public enum Upgrade {
  TERRAN_INFANTRY_ARMOR((byte)0, "Terran Infantry Armor"),
  TERRAN_VEHICLE_PLATING((byte)1, "Terran Vehicle Plating"),
  TERRAN_SHIP_PLATING((byte)2, "Terran Ship Plating"),
  ZERG_CARAPACE((byte)3, "Zerg Carapace"),
  ZERG_FLYER_CARAPACE((byte)4, "Zerg Flyer Carapace"),
  PROTOSS_GROUND_ARMOR((byte)5, "Protoss Ground Armor"),
  PROTOSS_AIR_ARMOR((byte)6, "Protoss Air Armor"),
  TERRAN_INFANTRY_WEAPONS((byte)7, "Terran Infantry Weapons"),
  TERRAN_VEHICLE_WEAPONS((byte)8, "Terran Vehicle Weapons"),
  TERRAN_SHIP_WEAPONS((byte)9, "Terran Ship Weapons"),
  ZERG_MELEE_ATTACKS((byte)10, "Zerg Melee Attacks"),
  ZERG_MISSILE_ATTACKS((byte)11, "Zerg Missile Attacks"),
  ZERG_FLYER_ATTACKS((byte)12, "Zerg Flyer Attacks"),
  PROTOSS_GROUND_WEAPONS((byte)13, "Protoss Ground Weapons"),
  PROTOSS_AIR_WEAPONS((byte)14, "Protoss Air Weapons"),
  PROTOSS_PLASMA_SHIELDS((byte)15, "Protoss Plasma Shields"),
  U238_SHELLS_MARINE_RANGE((byte)16, "U-238 Shells (Marine Range)"),
  ION_THRUSTERS_VULTURE_SPEED((byte)17, "Ion Thrusters (Vulture Speed)"),
  TITAN_REACTOR_SCIENCE_VESSEL_ENERGY((byte)19, "Titan Reactor (Science Vessel Energy)"),
  OCULAR_IMPLANTS_GHOST_SIGHT((byte)20, "Ocular Implants (Ghost Sight)"),
  MOEBIUS_REACTOR_GHOST_ENERGY((byte)21, "Moebius Reactor (Ghost Energy)"),
  APOLLO_REACTOR_WRAITH_ENERGY((byte)22, "Apollo Reactor (Wraith Energy)"),
  COLOSSUS_REACTOR_BATTLE_CRUISER_ENERGY((byte)23, "Colossus Reactor (Battle Cruiser Energy)"),
  VENTRAL_SACS_OVERLORD_TRANSPORT((byte)24, "Ventral Sacs (Overlord Transport)"),
  ANTENNAE_OVERLORD_SIGHT((byte)25, "Antennae (Overlord Sight)"),
  PNEUMATIZED_CARAPACE_OVERLORD_SPEED((byte)26, "Pneumatized Carapace (Overlord Speed)"),
  METABOLIC_BOOST_ZERGLING_SPEED((byte)27, "Metabolic Boost (Zergling Speed)"),
  ADRENAL_GLANDS_ZERGLING_ATTACK((byte)28, "Adrenal Glands (Zergling Attack)"),
  MUSCULAR_AUGMENTS_HYDRALISK_SPEED((byte)29, "Muscular Augments (Hydralisk Speed)"),
  GROOVED_SPINES_HYDRALISK_RANGE((byte)30, "Grooved Spines (Hydralisk Range)"),
  GAMETE_MEIOSIS_QUEEN_ENERGY((byte)31, "Gamete Meiosis (Queen Energy)"),
  DEFILER_ENERGY((byte)32, "Defiler Energy"),
  SINGULARITY_CHARGE_DRAGOON_RANGE((byte)33, "Singularity Charge (Dragoon Range)"),
  LEG_ENHANCEMENT_ZEALOT_SPEED((byte)34, "Leg Enhancement (Zealot Speed)"),
  SCARAB_DAMAGE((byte)35, "Scarab Damage"),
  REAVER_CAPACITY((byte)36, "Reaver Capacity"),
  GRAVITIC_DRIVE_SHUTTLE_SPEED((byte)37, "Gravitic Drive (Shuttle Speed)"),
  SENSOR_ARRAY_OBSERVER_SIGHT((byte)38, "Sensor Array (Observer Sight)"),
  GRAVITIC_BOOSTER_OBSERVER_SPEED((byte)39, "Gravitic Booster (Observer Speed)"),
  KHAYDARIN_AMULET_TEMPLAR_ENERGY((byte)40, "Khaydarin Amulet (Templar Energy)"),
  APIAL_SENSORS_SCOUT_SIGHT((byte)41, "Apial Sensors (Scout Sight)"),
  GRAVITIC_THRUSTERS_SCOUT_SPEED((byte)42, "Gravitic Thrusters (Scout Speed)"),
  CARRIER_CAPACITY((byte)43, "Carrier Capacity"),
  KHAYDARIN_CORE_ARBITER_ENERGY((byte)44, "Khaydarin Core (Arbiter Energy)"),
  ARGUS_JEWEL_CORSAIR_ENERGY((byte)47, "Argus Jewel (Corsair Energy)"),
  ARGUS_TALISMAN_DARK_ARCHON_ENERGY((byte)49, "Argus Talisman (Dark Archon Energy)"),
  CADUCEUS_REACTOR_MEDIC_ENERGY((byte)51, "Caduceus Reactor (Medic Energy)"),
  CHITINOUS_PLATING_ULTRALISK_ARMOR((byte)52, "Chitinous Plating (Ultralisk Armor)"),
  ANABOLIC_SYNTHESIS_ULTRALISK_SPEED((byte)53, "Anabolic Synthesis (Ultralisk Speed)"),
  CHARON_BOOSTERS_GOLIATH_RANGE((byte)54, "Charon Boosters (Goliath Range)"),
  UNKNOWN((byte)-103, "Unknown");
  
  int id;
  
  String name;
  
  private static final Map<Integer, Upgrade> lookupByByte;
  
  Upgrade(byte paramByte, String paramString1) {
    this.id = paramByte;
    this.name = paramString1;
  }
  
  public String getName() {
    return this.name;
  }
  
  public static Upgrade getByID(int paramInt) {
    Upgrade upgrade1 = lookupByByte.get(Integer.valueOf(paramInt));
    if (upgrade1 != null)
      return upgrade1; 
    Upgrade upgrade2 = UNKNOWN;
    upgrade2.id = paramInt;
    return upgrade2;
  }
  
  static {
    lookupByByte = new HashMap<>();
    for (Upgrade upgrade : values())
      lookupByByte.put(Integer.valueOf(upgrade.id), upgrade); 
    lookupByByte.remove(Integer.valueOf(153));
  }
}
