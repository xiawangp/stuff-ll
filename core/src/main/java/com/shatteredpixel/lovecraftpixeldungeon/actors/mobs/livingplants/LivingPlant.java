package com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.livingplants;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.watabou.utils.Random;

public class LivingPlant extends Mob {

    {
        HP = HT = Dungeon.hero.STR;
        defenseSkill = Dungeon.depth+3;
        baseSpeed = 3f;
        EXP = 1;
        HUNTING = new Hunting();
    }

    private class Hunting extends Mob.Hunting{
        @Override
        public boolean act(boolean enemyInFOV, boolean justAlerted) {
            if (enemy != null)
                enemyInFOV = true;
            return super.act(enemyInFOV, justAlerted);
        }
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        mentalMinus(1, 8, enemy);
        return super.attackProc(enemy, damage);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 1, 3 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 4+Dungeon.depth;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 1);
    }

}
