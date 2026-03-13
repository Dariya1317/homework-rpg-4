package com.narxoz.rpg.bridge;

import com.narxoz.rpg.composite.CombatNode;

public class SingleTargetSkill extends Skill {
    public SingleTargetSkill(String skillName, int basePower, EffectImplementor effect) {
        super(skillName, basePower, effect);
    }

    @Override
    public void cast(CombatNode target) {
        // TODO: Single-target Bridge action
        // 1) Resolve final damage through effect implementor
        // 2) Apply to target node
        if (target == null) {
            System.out.println(getSkillName() + ": no target");
            return;
        }
        
        if (!target.isAlive()) {
            System.out.println(getSkillName() + ": target " + target.getName() + " is already dead");
            return;
        }
        
        int damage = resolvedDamage();
        System.out.println(getSkillName() + " (" + getEffectName() + ") deals " + damage + " damage to " + target.getName());
        
        target.takeDamage(damage);
    }
}
