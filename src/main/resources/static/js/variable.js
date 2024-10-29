const plateCate = ["SET1", "SET2", "SET3", "ROYAL", "UNDEAD", "HELL", "KEEPER", "AVALON", "FEY"];
const leatherCate = ["SET1", "SET2", "SET3", "ROYAL", "MORGANA", "HELL", "UNDEAD", "AVALON", "FEY"];
const clothCate = ["SET1", "SET2", "SET3", "ROYAL", "KEEPER", "HELL", "MORGANA", "AVALON", "FEY"];
const capeCate = ["CAPE", "CAPEITEM_FW_CAERLEON", "CAPEITEM_FW_MARTLOCK", "CAPEITEM_FW_FORTSTERLING", "CAPEITEM_FW_BRIDGEWATCH", "CAPEITEM_FW_LYMHURST", "CAPEITEM_FW_THETFORD", "CAPEITEM_HERETIC", "CAPEITEM_UNDEAD", "CAPEITEM_KEEPER", "CAPEITEM_MORGANA", "CAPEITEM_DEMON"];
const bagCate = ["BAG", "BAG_INSIGHT"];
const weaponWarriorCate = ["MAIN_SWORD", "MAIN_AXE", "MAIN_MACE", "MAIN_HAMMER", "2H_KNUCKLES_SET1", "2H_CROSSBOW", "OFF_SHIELD"];
const weaponHunterCate = ["2H_BOW", "MAIN_SPEAR", "MAIN_NATURESTAFF", "MAIN_DAGGER", "2H_QUARTERSTAFF", "2H_SHAPESHIFTER_SET1", "OFF_TORCH"];
const weaponMageCate = ["MAIN_FIRESTAFF", "MAIN_HOLYSTAFF", "MAIN_ARCANESTAFF", "MAIN_FROSTSTAFF", "MAIN_CURSEDSTAFF", "OFF_BOOK"];
const foodCate = ["T5_MEAL_SOUP", "T5_MEAL_SOUP_FISH", "T6_MEAL_SALAD", "T6_MEAL_SALAD_FISH", "T7_MEAL_PIE", "T7_MEAL_PIE_FISH", "T7_MEAL_OMELETTE", "T7_MEAL_OMELETTE_FISH"
, "T7_MEAL_OMELETTE_AVALON", "T7_MEAL_ROAST", "T7_MEAL_ROAST_FISH", "T8_MEAL_STEW", "T8_MEAL_STEW_FISH", "T8_MEAL_STEW_AVALON", "T8_MEAL_SANDWICH", "T8_MEAL_SANDWICH_FISH",
"T8_MEAL_SANDWICH_AVALON"];
const potionCate = ["T6_POTION_HEAL", "T6_POTION_ENERGY", "T7_POTION_REVIVE", "T7_POTION_SLOWFIELD", "T7_POTION_STONESKIN", "T8_POTION_COOLDOWN", "T8_POTION_CLEANSE",
"T7_POTION_MOB_RESET", "T7_POTION_CLEANSE2", "T7_POTION_ACID", "T8_POTION_BERSERK", "T8_POTION_LAVA", "T8_POTION_GATHER", "T8_POTION_TORNADO"];


const itemTree = {
    _MAIN_SWORD : ["MAIN_SWORD", "2H_CLAYMORE", "2H_DUALSWORD", "MAIN_SCIMITAR_MORGANA", "2H_CLEAVER_HELL", "2H_DUALSCIMITAR_UNDEAD", "2H_CLAYMORE_AVALON", "MAIN_SWORD_CRYSTAL"],
    _MAIN_AXE :   ["MAIN_AXE", "2H_AXE", "2H_HALBERD", "2H_HALBERD_MORGANA", "2H_SCYTHE_HELL", "2H_DUALAXE_KEEPER", "2H_AXE_AVALON", "2H_SCYTHE_CRYSTAL"],
    _MAIN_MACE : ["MAIN_MACE", "2H_MACE", "2H_FLAIL", "MAIN_ROCKMACE_KEEPER", "MAIN_MACE_HELL", "2H_MACE_MORGANA", "2H_DUALMACE_AVALON", "MAIN_MACE_CRYSTAL"],
    _MAIN_HAMMER : ["MAIN_HAMMER", "2H_POLEHAMMER", "2H_HAMMER", "2H_HAMMER_UNDEAD", "2H_DUALHAMMER_HELL", "2H_RAM_KEEPER", "2H_HAMMER_AVALON"],
    _2H_KNUCKLES_SET1 : ["2H_KNUCKLES_SET1", "2H_KNUCKLES_SET2", "2H_KNUCKLES_SET3", "2H_KNUCKLES_KEEPER", "2H_KNUCKLES_HELL", "2H_KNUCKLES_MORGANA", "2H_KNUCKLES_AVALON"],
    _2H_CROSSBOW : ["2H_CROSSBOW", "2H_CROSSBOWLARGE", "MAIN_1HCROSSBOW", "2H_REPEATINGCROSSBOW_UNDEAD", "2H_DUALCROSSBOW_HELL", "2H_CROSSBOWLARGE_MORGANA", "2H_CROSSBOW_CANNON_AVALON"],
    _OFF_SHIELD : ["OFF_SHIELD", "OFF_TOWERSHIELD_UNDEAD", "OFF_SHIELD_HELL", "OFF_SPIKEDSHIELD_MORGANA", "OFF_SHIELD_AVALON"],
    _2H_BOW : ["2H_BOW", "2H_WARBOW", "2H_LONGBOW", "2H_LONGBOW_UNDEAD", "2H_BOW_HELL", "2H_BOW_KEEPER", "2H_BOW_AVALON"],
    _MAIN_SPEAR : ["MAIN_SPEAR", "2H_SPEAR", "2H_GLAIVE", "MAIN_SPEAR_KEEPER", "2H_HARPOON_HELL", "2H_TRIDENT_UNDEAD", "MAIN_SPEAR_LANCE_AVALON", "2H_GLAIVE_CRYSTAL"],
    _MAIN_NATURESTAFF : ["MAIN_NATURESTAFF", "2H_NATURESTAFF", "2H_WILDSTAFF", "MAIN_NATURESTAFF_KEEPER", "2H_NATURESTAFF_HELL", "2H_NATURESTAFF_KEEPER", "MAIN_NATURESTAFF_AVALON"],
    _MAIN_DAGGER : ["MAIN_DAGGER", "2H_DAGGERPAIR", "2H_CLAWPAIR", "MAIN_RAPIER_MORGANA", "MAIN_DAGGER_HELL", "2H_DUALSICKLE_UNDEAD", "2H_DAGGER_KATAR_AVALON", "2H_DAGGERPAIR_CRYSTAL"],
    _2H_QUARTERSTAFF : ["2H_QUARTERSTAFF", "2H_IRONCLADEDSTAFF", "2H_DOUBLEBLADEDSTAFF", "2H_COMBATSTAFF_MORGANA", "2H_TWINSCYTHE_HELL", "2H_ROCKSTAFF_KEEPER", "2H_QUARTERSTAFF_AVALON", "2H_DOUBLEBLADEDSTAFF_CRYSTAL"],
    _2H_SHAPESHIFTER_SET1 : ["2H_SHAPESHIFTER_SET1", "2H_SHAPESHIFTER_SET2", "2H_SHAPESHIFTER_SET3", "2H_SHAPESHIFTER_MORGANA", "2H_SHAPESHIFTER_HELL", "2H_SHAPESHIFTER_KEEPER", "2H_SHAPESHIFTER_AVALON"],
    _OFF_TORCH : ["OFF_TORCH", "OFF_HORN_KEEPER", "OFF_JESTERCANE_HELL", "OFF_LAMP_UNDEAD", "OFF_TALISMAN_AVALON"],
    _MAIN_FIRESTAFF : ["MAIN_FIRESTAFF", "2H_FIRESTAFF", "2H_INFERNOSTAFF", "MAIN_FIRESTAFF_KEEPER", "2H_FIRESTAFF_HELL", "2H_INFERNOSTAFF_MORGANA", "2H_FIRE_RINGPAIR_AVALON"],
    _MAIN_HOLYSTAFF : ["MAIN_HOLYSTAFF", "2H_HOLYSTAFF", "2H_DIVINESTAFF", "MAIN_HOLYSTAFF_MORGANA", "2H_HOLYSTAFF_HELL", "2H_HOLYSTAFF_UNDEAD", "MAIN_HOLYSTAFF_AVALON", "2H_HOLYSTAFF_CRYSTAL"],
    _MAIN_ARCANESTAFF : ["MAIN_ARCANESTAFF", "2H_ARCANESTAFF", "2H_ENIGMATICSTAFF", "MAIN_ARCANESTAFF_UNDEAD", "2H_ARCANESTAFF_HELL", "2H_ENIGMATICORB_MORGANA", "2H_ARCANE_RINGPAIR_AVALON", "2H_ARCANESTAFF_CRYSTAL"],
    _MAIN_FROSTSTAFF : ["MAIN_FROSTSTAFF", "2H_FROSTSTAFF", "2H_GLACIALSTAFF", "MAIN_FROSTSTAFF_KEEPER", "2H_ICEGAUNTLETS_HELL", "2H_ICECRYSTAL_UNDEAD", "MAIN_FROSTSTAFF_AVALON", "2H_FROSTSTAFF_CRYSTAL"],
    _MAIN_CURSEDSTAFF : ["MAIN_CURSEDSTAFF", "2H_CURSEDSTAFF", "2H_DEMONICSTAFF", "MAIN_CURSEDSTAFF_UNDEAD", "2H_SKULLORB_HELL", "2H_CURSEDSTAFF_MORGANA", "MAIN_CURSEDSTAFF_AVALON"],
    _OFF_BOOK : ["OFF_BOOK", "OFF_ORB_MORGANA", "OFF_DEMONSKULL_HELL", "OFF_TOTEM_KEEPER", "OFF_CENSER_AVALON"],
    _T5_MEAL_SOUP : ["T1_MEAL_SOUP", "T3_MEAL_SOUP", "T5_MEAL_SOUP"],
    _T5_MEAL_SOUP_FISH : ["T1_MEAL_SOUP_FISH", "T3_MEAL_SOUP_FISH", "T5_MEAL_SOUP_FISH"],
    _T6_MEAL_SALAD : ["T2_MEAL_SALAD", "T4_MEAL_SALAD", "T6_MEAL_SALAD"],
    _T6_MEAL_SALAD_FISH : ["T2_MEAL_SALAD_FISH", "T4_MEAL_SALAD_FISH", "T6_MEAL_SALAD_FISH"],
    _T7_MEAL_PIE : ["T3_MEAL_PIE", "T5_MEAL_PIE", "T7_MEAL_PIE"],
    _T7_MEAL_PIE_FISH : ["T3_MEAL_PIE_FISH", "T5_MEAL_PIE_FISH", "T7_MEAL_PIE_FISH"],
    _T7_MEAL_OMELETTE : ["T3_MEAL_OMELETTE", "T5_MEAL_OMELETTE", "T7_MEAL_OMELETTE"],
    _T7_MEAL_OMELETTE_FISH : ["T3_MEAL_OMELETTE_FISH", "T5_MEAL_OMELETTE_FISH", "T7_MEAL_OMELETTE_FISH"],
    _T7_MEAL_OMELETTE_AVALON : ["T3_MEAL_OMELETTE_AVALON", "T5_MEAL_OMELETTE_AVALON", "T7_MEAL_OMELETTE_AVALON"],
    _T7_MEAL_ROAST : ["T3_MEAL_ROAST", "T5_MEAL_ROAST", "T7_MEAL_ROAST"],
    _T7_MEAL_ROAST_FISH : ["T3_MEAL_ROAST_FISH", "T5_MEAL_ROAST_FISH", "T7_MEAL_ROAST_FISH"],
    _T8_MEAL_STEW : ["T4_MEAL_STEW", "T6_MEAL_STEW", "T8_MEAL_STEW"],
    _T8_MEAL_STEW_FISH : ["T4_MEAL_STEW_FISH", "T6_MEAL_STEW_FISH", "T8_MEAL_STEW_FISH"],
    _T8_MEAL_STEW_AVALON : ["T4_MEAL_STEW_AVALON", "T6_MEAL_STEW_AVALON", "T8_MEAL_STEW_AVALON"],
    _T8_MEAL_SANDWICH : ["T4_MEAL_SANDWICH", "T6_MEAL_SANDWICH", "T8_MEAL_SANDWICH"],
    _T8_MEAL_SANDWICH_FISH : ["T4_MEAL_SANDWICH_FISH", "T6_MEAL_SANDWICH_FISH", "T8_MEAL_SANDWICH_FISH"],
    _T8_MEAL_SANDWICH_AVALON : ["T4_MEAL_SANDWICH_AVALON", "T6_MEAL_SANDWICH_AVALON", "T8_MEAL_SANDWICH_AVALON"],
    _T6_POTION_HEAL : ["T2_POTION_HEAL", "T4_POTION_HEAL", "T6_POTION_HEAL"],
    _T6_POTION_ENERGY : ["T2_POTION_ENERGY", "T4_POTION_ENERGY", "T6_POTION_ENERGY"],
    _T7_POTION_REVIVE : ["T3_POTION_REVIVE", "T5_POTION_REVIVE", "T7_POTION_REVIVE"],
    _T7_POTION_SLOWFIELD : ["T3_POTION_SLOWFIELD", "T5_POTION_SLOWFIELD", "T7_POTION_SLOWFIELD"],
    _T7_POTION_STONESKIN : ["T3_POTION_STONESKIN", "T5_POTION_STONESKIN", "T7_POTION_STONESKIN"],
    _T8_POTION_COOLDOWN : ["T4_POTION_COOLDOWN", "T6_POTION_COOLDOWN", "T8_POTION_COOLDOWN"],
    _T7_POTION_MOB_RESET : ["T3_POTION_MOB_RESET", "T5_POTION_MOB_RESET", "T7_POTION_MOB_RESET"],
    _T7_POTION_CLEANSE2 : ["T3_POTION_CLEANSE2", "T5_POTION_CLEANSE2", "T7_POTION_CLEANSE2"],
    _T7_POTION_ACID : ["T3_POTION_ACID", "T5_POTION_ACID", "T7_POTION_ACID"],
    _T8_POTION_BERSERK : ["T4_POTION_BERSERK", "T6_POTION_BERSERK", "T8_POTION_BERSERK"],
    _T8_POTION_LAVA : ["T4_POTION_LAVA", "T6_POTION_LAVA", "T8_POTION_LAVA"],
    _T8_POTION_GATHER : ["T4_POTION_GATHER", "T6_POTION_GATHER", "T8_POTION_GATHER"],
    _T8_POTION_TORNADO : ["T4_POTION_TORNADO", "T6_POTION_TORNADO", "T8_POTION_TORNADO"],
};



