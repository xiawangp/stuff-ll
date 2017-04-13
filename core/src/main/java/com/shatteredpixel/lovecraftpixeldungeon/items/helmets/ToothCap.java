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

package com.shatteredpixel.lovecraftpixeldungeon.items.helmets;

import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.ToothFaierie;
import com.shatteredpixel.lovecraftpixeldungeon.items.HelmetSlot;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.HeroSprite;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class ToothCap extends HelmetSlot{

    {
        image = ItemSpriteSheet.TOOTHCAP;
        weight = 2;
        identify();
    }

    final String ACTIVE = "active";
    private static boolean activated = true;

    @Override
    public boolean isUpgradable() {
        return false;
    }

    public static final String AC_ON = "ON";
    public static final String AC_OFF = "OFF";

    @Override
    public boolean doEquip(Hero hero) {
        ((HeroSprite)hero.sprite).changeSkin(new ToothFaierie());
        (hero.sprite).operate(hero.pos);
        activated = true;
        return super.doEquip(hero);
    }

    @Override
    public void doDrop(Hero hero) {
        super.doDrop(hero);
        ((HeroSprite)hero.sprite).updateArmor();
        (hero.sprite).zap(hero.pos);
        activated = false;
    }

    @Override
    protected void onThrow(int cell) {
        super.onThrow(cell);
        ((HeroSprite)curUser.sprite).updateArmor();
        (curUser.sprite).zap(curUser.pos);
        activated = false;
    }

    @Override
    public ArrayList<String> actions( Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        if(this.isEquipped(hero)){
            actions.add(AC_ON);
            actions.add(AC_OFF);
        } else {
            actions.remove(AC_ON);
            actions.remove(AC_OFF);
        }
        return actions;
    }

    @Override
    public boolean doUnequip(Hero hero, boolean collect, boolean single) {
        ((HeroSprite)hero.sprite).updateArmor();
        (hero.sprite).operate(hero.pos);
        activated = false;
        return super.doUnequip(hero, collect, single);
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if(action.equals(AC_ON)){
            ((HeroSprite)hero.sprite).changeSkin(new ToothFaierie());
            (hero.sprite).operate(hero.pos);
            activated = true;
        } else if(action.equals(AC_OFF)){
            ((HeroSprite)hero.sprite).updateArmor();
            (hero.sprite).operate(hero.pos);
            activated = false;
        }
    }

    @Override
    public int price() {
        return 1800;
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put( ACTIVE, activated );
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        activated = bundle.getBoolean(ACTIVE);
        if(activated){
            ((HeroSprite)curUser.sprite).changeSkin(new ToothFaierie());
            (curUser.sprite).operate(curUser.pos);
        } else {
            ((HeroSprite)curUser.sprite).updateArmor();
            (curUser.sprite).operate(curUser.pos);
        }
    }

    public boolean isActive(){
        return activated;
    }
}
