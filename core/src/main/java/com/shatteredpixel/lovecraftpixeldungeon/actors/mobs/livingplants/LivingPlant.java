/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2016 Evan Debenham
 *
 * Lovercaft Pixel Dungeon
 * Copyright (C) 2016-2017 Leon Horn
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This Program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without eben the implied warranty of
 * GNU General Public License for more details.
 *
 * You should have have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses>
 */

package com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.livingplants;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.watabou.utils.Random;

import java.util.HashSet;

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
    protected Char chooseEnemy() {
        HashSet<Char> enemies = new HashSet<>();
        for (Mob mob : Dungeon.level.mobs){
            if (mob != this && Level.fieldOfView[mob.pos] && mob.hostile){
                enemies.add(mob);
            }
        }
        if (enemies.size() > 0){
            return Random.element(enemies);
        }
        return Dungeon.hero;
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
