/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2016 Evan Debenham
 *
 * Lovecraft Pixel Dungeon
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

package com.shatteredpixel.lovecraftpixeldungeon.actors.mobs;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Levitation;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Ooze;
import com.shatteredpixel.lovecraftpixeldungeon.effects.MiGoTounge;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Pushing;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.KekSprite;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class Kek extends Mob{

    {
        spriteClass = KekSprite.class;

        HP = HT = (Dungeon.hero.MH/2)+Dungeon.depth+50;
        defenseSkill = (Dungeon.hero.STR/2) + Dungeon.depth + 10;

        EXP = 10*Dungeon.depth;
        baseSpeed = 3f;

        properties.add(Property.MINIBOSS);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(4, 12);
    }

    @Override
    protected boolean act() {
        Dungeon.level.updateFieldOfView( this, Level.fieldOfView );

        if (state == HUNTING &&
                paralysed <= 0 &&
                enemy != null &&
                enemy.invisible == 0 &&
                Level.fieldOfView[enemy.pos] &&
                Dungeon.level.distance( pos, enemy.pos ) < 5 &&
                !Dungeon.level.adjacent( pos, enemy.pos ) &&
                Random.Int(3) == 0 &&

                chain(enemy.pos)) {

            return false;

        } else {
            return super.act();
        }
    }

    private boolean chain(int target){
        if (enemy.properties().contains(Property.IMMOVABLE))
            return false;

        Ballistica chain = new Ballistica(pos, target, Ballistica.PROJECTILE);

        int newPos = -1;
        for (int i : chain.subPath(1, chain.dist)){
            if (!Level.solid[i] && Actor.findChar(i) == null){
                newPos = i;
                break;
            }
        }

        if (newPos == -1){
            return false;
        } else {
            final int newPosFinal = newPos;
            Buff.affect(enemy, Levitation.class, Levitation.DURATION);
            Buff.affect(enemy, Ooze.class);
            sprite.parent.add(new MiGoTounge(sprite.center(), enemy.sprite.center(), new Callback() {
                public void call() {
                    Actor.addDelayed(new Pushing(enemy, enemy.pos, newPosFinal, new Callback(){
                        public void call() {
                            enemy.pos = newPosFinal;
                            Dungeon.level.press(newPosFinal, enemy);
                            Cripple.prolong(enemy, Cripple.class, 4f);
                            if (enemy == Dungeon.hero) {
                                Dungeon.hero.interrupt();
                                Dungeon.observe();
                                GameScene.updateFog();
                            }
                        }
                    }), -1);
                    next();
                }
            }));
        }
        return true;
    }

    @Override
    public int attackSkill( Char target ) {
        return 20;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 8);
    }

    @Override
    public int defenseProc( Char enemy, int damage ) {
        //TODO: SPAWN FROGS
        return super.defenseProc(enemy, damage);
    }

    @Override
    public void die( Object cause ) {
        super.die( cause );
        GLog.n(Messages.get(this, "die"));
    }

}
