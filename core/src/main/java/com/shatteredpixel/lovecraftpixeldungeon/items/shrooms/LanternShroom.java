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

package com.shatteredpixel.lovecraftpixeldungeon.items.shrooms;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Lightning;
import com.shatteredpixel.lovecraftpixeldungeon.effects.particles.SparkParticle;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.levels.traps.LightningTrap;
import com.shatteredpixel.lovecraftpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.lovecraftpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.lovecraftpixeldungeon.utils.BArray;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class LanternShroom extends Mushrooms{

    {
        image = ItemSpriteSheet.SHROOM_LANTERN;
        energy = Hunger.HUNGRY;
        hornValue = 1;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);

        if (action.equals(AC_EAT)) {

            int upgrade = Random.Int(hero.STR/2, hero.STR);

            lightning(hero, upgrade);

            hero.decreaseMentalHealth(upgrade);

            GLog.h(Messages.get(this, "zap"));
        }
    }

    private void lightning(Hero hero, int upgrade) {

        ArrayList<Char> affected = new ArrayList<>();

        for(int i = hero.visibleEnemies(); i > 0; i--){
            affected.add(hero.visibleEnemy(i));
        }

        for(int a = affected.size(); a > 0; a--){

            ArrayList<Lightning.Arc> arcs = new ArrayList<>();

            Char target = affected.get(a-1);

            hero.spend( 1f );

            if (hero.hit( hero ,target, true )) {
                int dmg = upgrade*2;
                if (Level.water[target.pos] && !target.flying) {
                    dmg *= 2.5f;
                }
                target.damage( dmg, LightningTrap.LIGHTNING );

                target.sprite.centerEmitter().burst( SparkParticle.FACTORY, 3 );
                target.sprite.flash();
            } else {
            target.sprite.showStatus( CharSprite.NEUTRAL,  target.defenseVerb() );
            }

            int dist;
            if (Level.water[target.pos] && !target.flying)
                dist = 2;
            else
                dist = 1;

            PathFinder.buildDistanceMap( target.pos, BArray.not( Level.solid, null ), dist );
            for (int i = 0; i < PathFinder.distance.length; i++) {
                if (PathFinder.distance[i] < Integer.MAX_VALUE){
                    Char n = Actor.findChar( i );
                    if (n == Dungeon.hero && PathFinder.distance[i] > 1)
                        //the hero is only zapped if they are adjacent
                        continue;
                    else if (n != null && !affected.contains( n )) {
                        arcs.add(new Lightning.Arc(target.sprite.center(), n.sprite.center()));
                    }
                }
            }

            int cell = new Ballistica(hero.pos, target.pos, Ballistica.MAGIC_BOLT).collisionPos;

            Char ch = Actor.findChar( cell );
            if (ch != null) {
                arcs.add( new Lightning.Arc(curUser.sprite.center(), ch.sprite.center()));
            } else {
                arcs.add( new Lightning.Arc(curUser.sprite.center(), DungeonTilemap.raisedTileCenterToWorld(new Ballistica(hero.pos, target.pos, Ballistica.MAGIC_BOLT).collisionPos)));
                CellEmitter.center( cell ).burst( SparkParticle.FACTORY, 3 );
            }

            //don't want to wait for the effect before processing damage.
            hero.sprite.parent.addToFront( new Lightning( arcs, null ) );
        }

    }

    private static final ItemSprite.Glowing GLOW = new ItemSprite.Glowing( 0xB2FBF9 );

    @Override
    public ItemSprite.Glowing glowing() {
        return GLOW;
    }

    @Override
    public int price() {
        return 100 * quantity;
    }
}
