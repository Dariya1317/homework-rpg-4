package com.narxoz.rpg;

import com.narxoz.rpg.battle.RaidEngine;
import com.narxoz.rpg.battle.RaidResult;
import com.narxoz.rpg.bridge.AreaSkill;
import com.narxoz.rpg.bridge.EffectImplementor;
import com.narxoz.rpg.bridge.FireEffect;
import com.narxoz.rpg.bridge.IceEffect;
import com.narxoz.rpg.bridge.PhysicalEffect;
import com.narxoz.rpg.bridge.ShadowEffect;
import com.narxoz.rpg.bridge.SingleTargetSkill;
import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.EnemyUnit;
import com.narxoz.rpg.composite.HeroUnit;
import com.narxoz.rpg.composite.PartyComposite;
import com.narxoz.rpg.composite.RaidGroup;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Homework 4 Demo: Bridge + Composite ===\n");    
        
        HeroUnit arthas = new HeroUnit("Arthas", 140, 30);
        HeroUnit jaina = new HeroUnit("Jaina", 90, 40);
        HeroUnit illidan = new HeroUnit("Illidan", 160, 35);
        
        EnemyUnit goblin = new EnemyUnit("Goblin", 70, 20);
        EnemyUnit orc = new EnemyUnit("Orc", 120, 25);
        EnemyUnit troll = new EnemyUnit("Troll", 150, 35);
        EnemyUnit ogre = new EnemyUnit("Ogre", 200, 40);

        PartyComposite alliance = new PartyComposite("Alliance Heroes");
        alliance.add(arthas);
        alliance.add(jaina);

        PartyComposite horde = new PartyComposite("Horde Defenders");
        horde.add(orc);
        horde.add(troll);

        RaidGroup enemyRaid = new RaidGroup("Enemy Raid Force");
        enemyRaid.add(horde);              
        enemyRaid.add(goblin);              
        enemyRaid.add(ogre);                

        PartyComposite illidanGroup = new PartyComposite("Illidan's Squad");
        illidanGroup.add(illidan);
        
        RaidGroup bossRaid = new RaidGroup("Boss Raid");
        bossRaid.add(illidanGroup);          
        bossRaid.add(new EnemyUnit("Felhound", 80, 15));

        System.out.println("\n--- Team Structures ---");
        System.out.println("Alliance:");
        alliance.printTree("");
        
        System.out.println("\nEnemy Raid (nested structure):");
        enemyRaid.printTree("");
        
        System.out.println("\nBoss Raid (nested structure):");
        bossRaid.printTree("");

        EffectImplementor physical = new PhysicalEffect();
        EffectImplementor fire = new FireEffect();
        EffectImplementor ice = new IceEffect();
        EffectImplementor shadow = new ShadowEffect();

        System.out.println("\n--- Same Skill with Different Effects ---");
        
        Skill slash = new SingleTargetSkill("Slash", 25, physical);
        Skill fireSlash = new SingleTargetSkill("Slash", 25, fire);
        Skill iceSlash = new SingleTargetSkill("Slash", 25, ice);
        Skill shadowSlash = new SingleTargetSkill("Slash", 25, shadow);

        System.out.println(slash.getSkillName() + " + " + slash.getEffectName() + " = " + slash.resolvedDamage() + " damage");
        System.out.println(fireSlash.getSkillName() + " + " + fireSlash.getEffectName() +  " = " + fireSlash.resolvedDamage() + " damage");
        System.out.println(iceSlash.getSkillName() + " + " + iceSlash.getEffectName() + " = " + iceSlash.resolvedDamage() + " damage");
        System.out.println(shadowSlash.getSkillName() + " + " + shadowSlash.getEffectName() + " = " + shadowSlash.resolvedDamage() + " damage");

        System.out.println("\n--- Different Skills with Same Effect ---");
        
        Skill physicalSlash = new SingleTargetSkill("Slash", 25, physical);
        Skill physicalStorm = new AreaSkill("Storm", 15, physical);
        Skill physicalBlast = new AreaSkill("Blast", 30, physical);

        System.out.println(physicalSlash.getSkillName() + " (" + physicalSlash.getEffectName() + "): " + physicalSlash.resolvedDamage());
        System.out.println(physicalStorm.getSkillName() + " (" + physicalStorm.getEffectName() + "): " + physicalStorm.resolvedDamage());
        System.out.println(physicalBlast.getSkillName() + " (" + physicalBlast.getEffectName() + "): " + physicalBlast.resolvedDamage());

        System.out.println("\n--- AreaSkill on Composite Target ---");
        AreaSkill fireStorm = new AreaSkill("Fire Storm", 20, fire);
        
        System.out.println("Before Fire Storm:");
        horde.printTree("  ");
        
        System.out.println("\nCasting Fire Storm on Horde Defenders:");
        fireStorm.cast(horde);
        
        System.out.println("\nAfter Fire Storm:");
        horde.printTree("  ");

        arthas = new HeroUnit("Arthas", 140, 30);
        jaina = new HeroUnit("Jaina", 90, 40);
        goblin = new EnemyUnit("Goblin", 70, 20);
        orc = new EnemyUnit("Orc", 120, 25);
        
        alliance = new PartyComposite("Alliance Heroes");
        alliance.add(arthas);
        alliance.add(jaina);
        
        PartyComposite enemyParty = new PartyComposite("Enemy Party");
        enemyParty.add(goblin);
        enemyParty.add(orc);

        Skill allianceSkill = new SingleTargetSkill("Holy Strike", 30, fire);
        Skill enemySkill = new AreaSkill("Dark Slash", 25, shadow);

        System.out.println("\n--- Battle: Alliance vs Enemies ---");
        System.out.println(" ");
        RaidEngine engine = RaidEngine.getInstance();
        engine.setRandomSeed(42L); 
        
        RaidResult result = engine.runRaid(alliance, enemyParty, allianceSkill, enemySkill);

        System.out.println("\n--- Raid Result ---");
        System.out.println("Winner: " + result.getWinner());
        System.out.println("Rounds: " + result.getRounds());
        System.out.println("\nBattle Log:");
        for (String line : result.getLog()) {
            System.out.println(line);
        }
        System.out.println();
        System.out.println("===== FINAL STATE =====");

        System.out.println("Heroes tree after battle:");
        alliance.printTree("");

        System.out.println();
        System.out.println("Monsters tree after battle:");
        enemyParty.printTree("");
    }
}
