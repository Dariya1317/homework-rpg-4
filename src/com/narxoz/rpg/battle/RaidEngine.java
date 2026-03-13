package com.narxoz.rpg.battle;

import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;
import java.util.Random;

public class RaidEngine {
    private static RaidEngine instance;
    private Random random = new Random(1L);

    private RaidEngine() {
    }

    public static RaidEngine getInstance() {
        if (instance == null) {
            instance = new RaidEngine();
        }
        return instance;
    }

    public RaidEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public RaidResult runRaid(CombatNode teamA, CombatNode teamB, Skill skillA, Skill skillB) {
        if (teamA == null || teamB == null) {
            throw new IllegalArgumentException("Teams cannot be null");
        }

        RaidResult result = new RaidResult();
        result.addLine("=== Raid Battle Start ===");
        result.addLine("Team A: " + teamA.getName());
        result.addLine("Team B: " + teamB.getName());
        result.addLine("Skill A: " + skillA.getSkillName() + " [" + skillA.getEffectName() + "]");
        result.addLine("Skill B: " + skillB.getSkillName() + " [" + skillB.getEffectName() + "]");
        result.addLine("--------------------------");

        int round = 0;
        while (teamA.isAlive() && teamB.isAlive()) {
            round++;
            result.addLine("--- Round " + round + " ---");
            if (teamA.isAlive()) {
                result.addLine(teamA.getName() + " uses " + skillA.getSkillName());
                skillA.cast(teamB);
            }
            if (teamB.isAlive()) {
                result.addLine(teamB.getName() + " uses " + skillB.getSkillName());
                skillB.cast(teamA);
            }

            result.addLine("Status: " + teamA.getName() + " HP=" + teamA.getHealth() + " | " + teamB.getName() + " HP=" + teamB.getHealth());
        }
        result.setRounds(round);
        if (teamA.isAlive()) {
            result.setWinner(teamA.getName());
            result.addLine("=== VICTORY: " + teamA.getName() + " wins! ===");
        } else {
            result.setWinner(teamB.getName());
            result.addLine("=== VICTORY: " + teamB.getName() + " wins! ===");
        }

        return result;
    }
}


