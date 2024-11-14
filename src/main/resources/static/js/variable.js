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

    // 제련
    _BAR : ["T2_ORE", "T3_ORE",
            "T4_ORE", "T4_ORE_LEVEL1@1", "T4_ORE_LEVEL2@2", "T4_ORE_LEVEL3@3", "T4_ORE_LEVEL4@4",
            "T5_ORE", "T5_ORE_LEVEL1@1", "T5_ORE_LEVEL2@2", "T5_ORE_LEVEL3@3", "T5_ORE_LEVEL4@4",
            "T6_ORE", "T6_ORE_LEVEL1@1", "T6_ORE_LEVEL2@2", "T6_ORE_LEVEL3@3", "T6_ORE_LEVEL4@4",
            "T7_ORE", "T7_ORE_LEVEL1@1", "T7_ORE_LEVEL2@2", "T7_ORE_LEVEL3@3", "T7_ORE_LEVEL4@4",
            "T8_ORE", "T8_ORE_LEVEL1@1", "T8_ORE_LEVEL2@2", "T8_ORE_LEVEL3@3", "T8_ORE_LEVEL4@4"],
    _METALBAR : ["T2_METALBAR", "T3_METALBAR", 
                 "T4_METALBAR", "T4_METALBAR_LEVEL1@1", "T4_METALBAR_LEVEL2@2", "T4_METALBAR_LEVEL3@3", "T4_METALBAR_LEVEL4@4",
                 "T5_METALBAR", "T5_METALBAR_LEVEL1@1", "T5_METALBAR_LEVEL2@2", "T5_METALBAR_LEVEL3@3", "T5_METALBAR_LEVEL4@4", 
                 "T6_METALBAR", "T6_METALBAR_LEVEL1@1", "T6_METALBAR_LEVEL2@2", "T6_METALBAR_LEVEL3@3", "T6_METALBAR_LEVEL4@4",
                 "T7_METALBAR", "T7_METALBAR_LEVEL1@1", "T7_METALBAR_LEVEL2@2", "T7_METALBAR_LEVEL3@3", "T7_METALBAR_LEVEL4@4",
                 "T8_METALBAR", "T8_METALBAR_LEVEL1@1", "T8_METALBAR_LEVEL2@2", "T8_METALBAR_LEVEL3@3", "T8_METALBAR_LEVEL4@4"],
    _WOOD : ["T2_WOOD", "T3_WOOD", 
             "T4_WOOD", "T4_WOOD_LEVEL1@1", "T4_WOOD_LEVEL2@2", "T4_WOOD_LEVEL3@3", "T4_WOOD_LEVEL4@4",
             "T5_WOOD", "T5_WOOD_LEVEL1@1", "T5_WOOD_LEVEL2@2", "T5_WOOD_LEVEL3@3", "T5_WOOD_LEVEL4@4", 
             "T6_WOOD", "T6_WOOD_LEVEL1@1", "T6_WOOD_LEVEL2@2", "T6_WOOD_LEVEL3@3", "T6_WOOD_LEVEL4@4",
             "T7_WOOD", "T7_WOOD_LEVEL1@1", "T7_WOOD_LEVEL2@2", "T7_WOOD_LEVEL3@3", "T7_WOOD_LEVEL4@4",
             "T8_WOOD", "T8_WOOD_LEVEL1@1", "T8_WOOD_LEVEL2@2", "T8_WOOD_LEVEL3@3", "T8_WOOD_LEVEL4@4"],
    _PLANKS : ["T2_PLANKS", "T3_PLANKS", 
                "T4_PLANKS", "T4_PLANKS_LEVEL1@1", "T4_PLANKS_LEVEL2@2", "T4_PLANKS_LEVEL3@3", "T4_PLANKS_LEVEL4@4",
                "T5_PLANKS", "T5_PLANKS_LEVEL1@1", "T5_PLANKS_LEVEL2@2", "T5_PLANKS_LEVEL3@3", "T5_PLANKS_LEVEL4@4", 
                "T6_PLANKS", "T6_PLANKS_LEVEL1@1", "T6_PLANKS_LEVEL2@2", "T6_PLANKS_LEVEL3@3", "T6_PLANKS_LEVEL4@4",
                "T7_PLANKS", "T7_PLANKS_LEVEL1@1", "T7_PLANKS_LEVEL2@2", "T7_PLANKS_LEVEL3@3", "T7_PLANKS_LEVEL4@4",
                "T8_PLANKS", "T8_PLANKS_LEVEL1@1", "T8_PLANKS_LEVEL2@2", "T8_PLANKS_LEVEL3@3", "T8_PLANKS_LEVEL4@4"],
    _HIDE : ["T2_HIDE", "T3_HIDE", 
             "T4_HIDE", "T4_HIDE_LEVEL1@1", "T4_HIDE_LEVEL2@2", "T4_HIDE_LEVEL3@3", "T4_HIDE_LEVEL4@4",
             "T5_HIDE", "T5_HIDE_LEVEL1@1", "T5_HIDE_LEVEL2@2", "T5_HIDE_LEVEL3@3", "T5_HIDE_LEVEL4@4", 
             "T6_HIDE", "T6_HIDE_LEVEL1@1", "T6_HIDE_LEVEL2@2", "T6_HIDE_LEVEL3@3", "T6_HIDE_LEVEL4@4",
             "T7_HIDE", "T7_HIDE_LEVEL1@1", "T7_HIDE_LEVEL2@2", "T7_HIDE_LEVEL3@3", "T7_HIDE_LEVEL4@4",
             "T8_HIDE", "T8_HIDE_LEVEL1@1", "T8_HIDE_LEVEL2@2", "T8_HIDE_LEVEL3@3", "T8_HIDE_LEVEL4@4"],
    _LEATHER : ["T2_LEATHER", "T3_LEATHER", 
                "T4_LEATHER", "T4_LEATHER_LEVEL1@1", "T4_LEATHER_LEVEL2@2", "T4_LEATHER_LEVEL3@3", "T4_LEATHER_LEVEL4@4",
                "T5_LEATHER", "T5_LEATHER_LEVEL1@1", "T5_LEATHER_LEVEL2@2", "T5_LEATHER_LEVEL3@3", "T5_LEATHER_LEVEL4@4", 
                "T6_LEATHER", "T6_LEATHER_LEVEL1@1", "T6_LEATHER_LEVEL2@2", "T6_LEATHER_LEVEL3@3", "T6_LEATHER_LEVEL4@4",
                "T7_LEATHER", "T7_LEATHER_LEVEL1@1", "T7_LEATHER_LEVEL2@2", "T7_LEATHER_LEVEL3@3", "T7_LEATHER_LEVEL4@4",
                "T8_LEATHER", "T8_LEATHER_LEVEL1@1", "T8_LEATHER_LEVEL2@2", "T8_LEATHER_LEVEL3@3", "T8_LEATHER_LEVEL4@4"],
    _FIBER : ["T2_FIBER", "T3_FIBER", 
              "T4_FIBER", "T4_FIBER_LEVEL1@1", "T4_FIBER_LEVEL2@2", "T4_FIBER_LEVEL3@3", "T4_FIBER_LEVEL4@4",
              "T5_FIBER", "T5_FIBER_LEVEL1@1", "T5_FIBER_LEVEL2@2", "T5_FIBER_LEVEL3@3", "T5_FIBER_LEVEL4@4", 
              "T6_FIBER", "T6_FIBER_LEVEL1@1", "T6_FIBER_LEVEL2@2", "T6_FIBER_LEVEL3@3", "T6_FIBER_LEVEL4@4",
              "T7_FIBER", "T7_FIBER_LEVEL1@1", "T7_FIBER_LEVEL2@2", "T7_FIBER_LEVEL3@3", "T7_FIBER_LEVEL4@4",
              "T8_FIBER", "T8_FIBER_LEVEL1@1", "T8_FIBER_LEVEL2@2", "T8_FIBER_LEVEL3@3", "T8_FIBER_LEVEL4@4"],
    _CLOTH : ["T2_CLOTH", "T3_CLOTH", 
              "T4_CLOTH", "T4_CLOTH_LEVEL1@1", "T4_CLOTH_LEVEL2@2", "T4_CLOTH_LEVEL3@3", "T4_CLOTH_LEVEL4@4",
              "T5_CLOTH", "T5_CLOTH_LEVEL1@1", "T5_CLOTH_LEVEL2@2", "T5_CLOTH_LEVEL3@3", "T5_CLOTH_LEVEL4@4", 
              "T6_CLOTH", "T6_CLOTH_LEVEL1@1", "T6_CLOTH_LEVEL2@2", "T6_CLOTH_LEVEL3@3", "T6_CLOTH_LEVEL4@4",
              "T7_CLOTH", "T7_CLOTH_LEVEL1@1", "T7_CLOTH_LEVEL2@2", "T7_CLOTH_LEVEL3@3", "T7_CLOTH_LEVEL4@4",
              "T8_CLOTH", "T8_CLOTH_LEVEL1@1", "T8_CLOTH_LEVEL2@2", "T8_CLOTH_LEVEL3@3", "T8_CLOTH_LEVEL4@4"],

        // 요리
    _FISH : ["T3_FISH_FRESHWATER_SWAMP_RARE", "T5_FISH_FRESHWATER_SWAMP_RARE", "T7_FISH_FRESHWATER_SWAMP_RARE", 
                "T3_FISH_SALTWATER_ALL_RARE", "T5_FISH_SALTWATER_ALL_RARE", "T7_FISH_SALTWATER_ALL_RARE",
                "T3_FISH_FRESHWATER_MOUNTAIN_RARE", "T5_FISH_FRESHWATER_MOUNTAIN_RARE", "T7_FISH_FRESHWATER_MOUNTAIN_RARE",
                "T3_FISH_FRESHWATER_STEPPE_RARE", "T5_FISH_FRESHWATER_STEPPE_RARE", "T7_FISH_FRESHWATER_STEPPE_RARE",
                "T3_FISH_FRESHWATER_AVALON_RARE", "T5_FISH_FRESHWATER_AVALON_RARE", "T7_FISH_FRESHWATER_AVALON_RARE",
                "T3_FISH_FRESHWATER_FOREST_RARE", "T5_FISH_FRESHWATER_FOREST_RARE", "T7_FISH_FRESHWATER_FOREST_RARE",
                "T3_FISH_FRESHWATER_HIGHLANDS_RARE", "T5_FISH_FRESHWATER_HIGHLANDS_RARE", "T7_FISH_FRESHWATER_HIGHLANDS_RARE"],
    _FISHSAUCE : ["T1_FISHSAUCE_LEVEL1", "T1_FISHSAUCE_LEVEL2", "T1_FISHSAUCE_LEVEL3"],
    _CROP : ["T1_CARROT", "T2_BEAN", "T3_WHEAT", "T4_TURNIP", "T5_CABBAGE", "T6_POTATO", "T7_CORN", "T8_PUMPKIN"],
    _HERB : ["T2_AGARIC", "T3_COMFREY", "T4_BURDOCK", "T5_TEASEL", "T6_FOXGLOVE", "T7_MULLEIN", "T8_YARROW"],

};

// 제련 페이지
// 기본 포커스 비용 티어별(2T ~ 8T)
const focusTableRefining = [18,
        31,
        54, 94, 164, 287, 503,
        94,	164, 287, 503, 880,
        164, 287, 503, 880, 1539,
        287, 503, 880, 1539 , 2694,
        503, 880, 1539, 2694, 4714
    ];
    // 제련 전 품목들의 티어별 아이템 가치
    const beforeRefiningValue = [4,
             2,
             4, 12, 28, 60, 124,
             5, 10, 21, 42, 85,
             8, 16, 32, 64, 128,
             12, 25, 51, 102, 204,
             25, 51, 102, 204, 409
            ];
    // 제련 후 품목들의 티어별 아이템 가치
    const afterRefiningValue = [4,
            8,
            16, 32, 64, 128, 256,
            32, 64, 128, 256, 512,
            64, 128, 256, 512, 1024,
            128, 256, 512, 1024, 2048,
            256, 512, 1024, 2048, 4096
            ];


// 요리 계산 페이지 (해당 음식의 재료들)
// 일반, 아발로니안 음식들 티어별 필요한 피쉬소스 : 10, 30, 90
// 아발로니안 음식들 티어별 필요한 아발로니안 에너지는 피쉬소스양과 동일.
// 피쉬가 들어가는 음식들 티어별 필요한 피쉬소스 : 3, 9, 27
const cookTree = {
// 수프
T1_MEAL_SOUP : {
        names : ["T1_CARROT"],
        nums : [16, 10]
},
T3_MEAL_SOUP : {
        names : ["T3_WHEAT"],
        nums : [48, 30]
},
T5_MEAL_SOUP : {
        names : ["T5_CABBAGE"],
        nums : [144, 90]
},
// 조개 수프
T1_MEAL_SOUP_FISH : {
        names : ["T3_FISH_FRESHWATER_SWAMP_RARE", "T1_CARROT"],
        nums : [1, 2, 3]
},
T3_MEAL_SOUP_FISH : {
        names : ["T5_FISH_FRESHWATER_SWAMP_RARE", "T3_WHEAT", "T3_COMFREY", "T3_MEAT"],
        nums : [1, 2, 2, 2, 9]
},
T5_MEAL_SOUP_FISH : {
        names : ["T7_FISH_FRESHWATER_SWAMP_RARE", "T5_CABBAGE", "T5_TEASEL", "T5_MEAT"],
        nums : [1, 6, 6, 6, 27]
},
// 샐러드
T2_MEAL_SALAD : {
        names : ["T2_BEAN", "T1_CARROT"],
        nums : [8, 8, 10]
},
T4_MEAL_SALAD : {
        names : ["T4_TURNIP", "T3_WHEAT"],
        nums : [24, 24, 30]
},
T6_MEAL_SALAD : {
        names : ["T6_POTATO", "T5_CABBAGE"],
        nums : [72, 72, 90]
},
// 크라켄 샐러드
T2_MEAL_SALAD_FISH : {
        names : ["T3_FISH_SALTWATER_ALL_RARE", "T2_BEAN", "T2_AGARIC"],
        nums : [1, 1, 1, 3]
},
T4_MEAL_SALAD_FISH : {
        names : ["T5_FISH_SALTWATER_ALL_RARE", "T4_TURNIP", "T4_BURDOCK", "T4_MEAT"],
        nums : [1, 2, 2, 2, 9]
},
T6_MEAL_SALAD_FISH : {
        names : ["T7_FISH_SALTWATER_ALL_RARE", "T6_POTATO", "T6_FOXGLOVE", "T6_MEAT"],
        nums : [1, 6, 6, 6, 27]
},
// 파이
T3_MEAL_PIE : {
        names : ["T3_WHEAT", "T3_FLOUR", "T3_MEAT"],
        nums : [2, 4, 8, 10]
},
T5_MEAL_PIE : {
        names : ["T5_CABBAGE", "T3_FLOUR", "T5_MEAT", "T4_MILK"],
        nums : [6, 12, 24, 6, 30]
},
T7_MEAL_PIE : {
        names : ["T7_CORN", "T3_FLOUR", "T7_MEAT", "T6_MILK"],
        nums : [18, 36, 72, 18, 90]
},
// 명사수 파이
T3_MEAL_PIE_FISH : {
        names : ["T3_FISH_FRESHWATER_MOUNTAIN_RARE", "T3_FLOUR", "T3_EGG"],
        nums : [1, 1, 1, 3]
},
T5_MEAL_PIE_FISH : {
        names : ["T5_FISH_FRESHWATER_MOUNTAIN_RARE", "T5_CABBAGE", "T5_TEASEL", "T5_EGG"],
        nums : [1, 2, 2, 2, 9]
},
T7_MEAL_PIE_FISH : {
        names : ["T7_FISH_FRESHWATER_MOUNTAIN_RARE", "T7_CORN", "T7_MULLEIN", "T7_MEAT"],
        nums : [1, 6, 6, 6, 27]
},
// 오믈렛
T3_MEAL_OMELETTE : {
        names : ["T3_WHEAT", "T3_MEAT", "T3_EGG"],
        nums : [4, 8, 2, 10]
},
T5_MEAL_OMELETTE : {
        names : ["T5_CABBAGE", "T5_MEAT", "T5_EGG"],
        nums : [12, 24, 6, 30]
},
T7_MEAL_OMELETTE : {
        names : ["T7_CORN", "T7_MEAT", "T5_EGG"],
        nums : [36, 72, 18, 90]
},
// 게 오믈렛
T3_MEAL_OMELETTE_FISH : {
        names : ["T3_FISH_FRESHWATER_STEPPE_RARE", "T3_COMFREY", "T3_EGG"],
        nums : [1, 1, 1, 3]
},
T5_MEAL_OMELETTE_FISH : {
        names : ["T5_FISH_FRESHWATER_STEPPE_RARE", "T5_CABBAGE", "T5_TEASEL", "T5_EGG"],
        nums : [1, 2, 2, 2, 9]
},
T7_MEAL_OMELETTE_FISH : {
        names : ["T7_FISH_FRESHWATER_STEPPE_RARE", "T7_CORN", "T7_MULLEIN", "T7_MEAT"],
        nums : [1, 6, 6, 6, 27]
},
// 아발로니안 오믈렛
T3_MEAL_OMELETTE_AVALON : {
        names : ["T4_MILK", "T3_MEAT", "T3_EGG", "QUESTITEM_TOKEN_AVALON"],
        nums : [4, 8, 2, 10, 10]
},
T5_MEAL_OMELETTE_AVALON : {
        names : ["T6_MILK", "T5_MEAT", "T5_EGG", "QUESTITEM_TOKEN_AVALON"],
        nums : [12, 24, 6, 30, 30]
},
T7_MEAL_OMELETTE_AVALON : {
        names : ["T8_MILK", "T5_MEAT", "T5_EGG", "QUESTITEM_TOKEN_AVALON"],
        nums : [36, 72, 18, 90, 90]
},
// 구운 고기
T3_MEAL_ROAST : {
        names : ["T3_MEAT", "T2_BEAN", "T4_MILK"],
        nums : [8, 4, 4, 10]
},
T5_MEAL_ROAST : {
        names : ["T5_MEAT", "T5_CABBAGE", "T6_MILK"],
        nums : [24, 12, 12, 30]
},
T7_MEAL_ROAST : {
        names : ["T7_MEAT", "T7_CORN", "T8_MILK"],
        nums : [72, 36, 36, 90]
},
// 스내퍼 구운고기
T3_MEAL_ROAST_FISH : {
        names : ["T3_FISH_FRESHWATER_AVALON_RARE", "T3_COMFREY", "T4_MILK"],
        nums : [1, 1, 1, 3]
},
T5_MEAL_ROAST_FISH : {
        names : ["T5_FISH_FRESHWATER_AVALON_RARE", "T5_CABBAGE", "T5_TEASEL", "T6_MILK"],
        nums : [1, 2, 2, 2, 9]
},
T7_MEAL_ROAST_FISH : {
        names : ["T7_FISH_FRESHWATER_AVALON_RARE", "T7_CORN", "T7_MULLEIN", "T8_MILK"],
        nums : [1, 6, 6, 6, 27]
},
// 스튜
T4_MEAL_STEW : {
        names : ["T4_TURNIP", "T4_BREAD", "T4_MEAT"],
        nums : [4, 4, 8, 10]
},
T6_MEAL_STEW : {
        names : ["T5_CABBAGE", "T4_BREAD", "T6_MEAT"],
        nums : [12, 12, 24, 30]
},
T8_MEAL_STEW : {
        names : ["T7_CORN", "T4_BREAD", "T8_MEAT"],
        nums : [36, 36, 72, 90]
},
// 장어 스튜
T4_MEAL_STEW_FISH : {
        names : ["T3_FISH_FRESHWATER_FOREST_RARE", "T4_TURNIP", "T4_BURDOCK"],
        nums : [1, 1, 1, 3]
},
T6_MEAL_STEW_FISH : {
        names : ["T5_FISH_FRESHWATER_FOREST_RARE", "T6_POTATO", "T6_FOXGLOVE", "T6_MILK"],
        nums : [1, 2, 2, 2, 9]
},
T8_MEAL_STEW_FISH : {
        names : ["T7_FISH_FRESHWATER_FOREST_RARE", "T8_PUMPKIN", "T8_YARROW", "T8_MILK"],
        nums : [1, 6, 6, 6, 27]
},
// 아발로니안 스튜
T4_MEAL_STEW_AVALON : {
        names : ["T1_CARROT", "T4_TURNIP", "T4_MEAT", "QUESTITEM_TOKEN_AVALON"],
        nums : [4, 4, 8, 10, 10]
},
T6_MEAL_STEW_AVALON : {
        names : ["T5_CABBAGE", "T6_POTATO", "T6_MEAT", "QUESTITEM_TOKEN_AVALON"],
        nums : [12, 12, 24, 30, 30]
},
T8_MEAL_STEW_AVALON : {
        names : ["T7_CORN", "T8_PUMPKIN", "T8_MEAT", "QUESTITEM_TOKEN_AVALON"],
        nums : [36, 36, 72, 90, 90]
},
// 샌드위치
T4_MEAL_SANDWICH : {
        names : ["T4_BREAD", "T4_MEAT", "T4_BUTTER"],
        nums : [4, 8, 2, 10]
},
T6_MEAL_SANDWICH : {
        names : ["T4_BREAD", "T6_MEAT", "T6_BUTTER"],
        nums : [12, 24, 6, 30]
},
T8_MEAL_SANDWICH : {
        names : ["T4_BREAD", "T8_MEAT", "T8_BUTTER"],
        nums : [36, 72, 18, 90]
},
// 떠돌이 샌드위치
T4_MEAL_SANDWICH_FISH : {
        names : ["T3_FISH_FRESHWATER_HIGHLANDS_RARE", "T4_TURNIP", "T4_BUTTER"],
        nums : [1, 1, 1, 3]
},
T6_MEAL_SANDWICH_FISH : {
        names : ["T5_FISH_FRESHWATER_HIGHLANDS_RARE", "T6_POTATO", "T6_FOXGLOVE", "T6_BUTTER"],
        nums : [1, 2, 2, 2, 9]
},
T8_MEAL_SANDWICH_FISH : {
        names : ["T7_FISH_FRESHWATER_HIGHLANDS_RARE", "T8_PUMPKIN", "T8_YARROW", "T8_BUTTER"],
        nums : [1, 6, 6, 6, 27]
},
// 아발로니안 샌드위치
T4_MEAL_SANDWICH_AVALON : {
        names : ["T4_BREAD", "T4_MEAT", "T4_BUTTER", "QUESTITEM_TOKEN_AVALON"],
        nums : [4, 8, 2, 10, 10]
},
T6_MEAL_SANDWICH_AVALON : {
        names : ["T4_BREAD", "T6_MEAT", "T6_BUTTER", "QUESTITEM_TOKEN_AVALON"],
        nums : [12, 24, 6, 30]
},
T8_MEAL_SANDWICH_AVALON : {
        names : ["T4_BREAD", "T8_MEAT", "T8_BUTTER", "QUESTITEM_TOKEN_AVALON"],
        nums : [36, 72, 18, 90, 90]
},
FISHSAUCE : ["T1_FISHSAUCE_LEVEL1", "T1_FISHSAUCE_LEVEL2", "T1_FISHSAUCE_LEVEL3"],        
};



