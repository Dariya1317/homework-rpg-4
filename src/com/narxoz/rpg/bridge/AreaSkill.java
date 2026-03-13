package com.narxoz.rpg.bridge;

import com.narxoz.rpg.composite.CombatNode;
import java.util.List;

public class AreaSkill extends Skill {
    public AreaSkill(String skillName, int basePower, EffectImplementor effect) {
        super(skillName, basePower, effect);
    }

    @Override
    public void cast(CombatNode target) {
        if (target == null) {
            System.out.println(getSkillName() + ": no target");
            return;
        }
        
        int damage = resolvedDamage();
        List<CombatNode> children = target.getChildren();
        if (children.isEmpty()) {
            if (target.isAlive()) {
                System.out.println(getSkillName() + " (" + getEffectName() + ") deals " + damage + " AOE damage to " + target.getName());
                target.takeDamage(damage);
            }
            return;
        }
        System.out.println(getSkillName() + " (" + getEffectName() + ") deals " + damage + " AOE damage to all members of " + target.getName());
        
        boolean hitAny = false;
        for (CombatNode child : children) {
            if (child.isAlive()) {
                child.takeDamage(damage);
                hitAny = true;
            }
        }
        
        if (!hitAny) {
            System.out.println(" no alive targets in " + target.getName());
        }
    }
}
