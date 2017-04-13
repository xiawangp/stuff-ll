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

package com.shatteredpixel.lovecraftpixeldungeon.items;

import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.GasesImmunity;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.MoonFury;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Ooze;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Speed;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Kek;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Pushing;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class StatueOfPepe extends Item {

    private static final String AC_POST	= "POST";
    private static final String AC_SUMMON= "SUMMON";
    private static boolean kekspawnused = false;

    {
        image = ItemSpriteSheet.ARTIFACT_STATUEPEPE1;
        stackable = false;
        identify();
        weight = 20;
        defaultAction = AC_POST;
        unique = true;
    }

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_POST );
        actions.add( AC_SUMMON );
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if(action.equals(AC_POST)){
            if(!curUser.isStarving() && curUser.MH == curUser.MMH && curUser.HP == curUser.HT){
                ArrayList<Integer> ints = new ArrayList<>();
                for(int i = 9; i > 0; i--){
                    ints.add(Random.Int(0, 9));
                }
                String intsstring = ">";
                for(int y = 0; y < ints.size(); y++){
                    intsstring = intsstring + ints.get(y).toString();
                }
                int score = 1;
                if(ints.get(8) == ints.get(7)){
                    score *=2;
                    if(ints.get(7) == ints.get(6)){
                        score *=2;
                        if(ints.get(6) == ints.get(5)){
                            score *=2;
                            if(ints.get(5) == ints.get(4)){
                                score *=2;
                                if(ints.get(4) == ints.get(3)){
                                    score *=2;
                                    if(ints.get(3) == ints.get(2)){
                                        score *=2;
                                        if(ints.get(2) == ints.get(1)){
                                            score *=2;
                                            if(ints.get(1) == ints.get(0)){
                                                score *=2;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Buff.affect(curUser, Bless.class, score*5);
                    Buff.affect(curUser, Speed.class, score*5);
                    Buff.affect(curUser, GasesImmunity.class, score*5);
                    Buff.affect(curUser, Invisibility.class, score*5);
                    Buff.affect(curUser, MoonFury.class, score*5);
                    curUser.earnExp(score*2);
                    GLog.p(Messages.get(this, "pos", intsstring));
                } else {
                    curUser.MH = curUser.MH/2;
                    Buff.affect(curUser, Ooze.class);
                    Buff.affect(curUser, Bleeding.class);
                    Buff.affect(curUser, Slow.class, 30f);
                    GLog.n(Messages.get(this, "neg", intsstring));
                }
            } else {
                GLog.w(Messages.get(this, "cantpost"));
            }

        }
        if(action.equals(AC_SUMMON)){
            if(!kekspawnused){
                ArrayList<Integer> spawnPoints = new ArrayList<>();
                for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
                    int p = curUser.pos + PathFinder.NEIGHBOURS8[i];
                    if (Actor.findChar( p ) == null && (Level.passable[p] || Level.avoid[p])) {
                        spawnPoints.add( p );
                    }
                }
                if (spawnPoints.size() > 0) {
                    Kek kek = new Kek();
                    kek.pos = Random.element( spawnPoints );
                    Buff.affect(kek, Corruption.class);
                    GameScene.add( kek );
                    Actor.addDelayed( new Pushing( kek, curUser.pos, kek.pos ), -1 );
                    kekspawnused = true;
                    GLog.p(Messages.get(this, "summoned"));
                } else {
                    GLog.w(Messages.get(this, "cantspawn2"));
                }
            } else {
                GLog.w(Messages.get(this, "cantspawn1"));
            }
        }
    }

    @Override
    public int price() {
        return 77777;
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put("kekspawnused", kekspawnused);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        kekspawnused = bundle.getBoolean("kekspawnused");
    }

}
