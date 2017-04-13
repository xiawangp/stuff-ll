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
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Freezing;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.IceWind;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Frost;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.enchantments.Blazing;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.enchantments.Chilling;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Spear;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.IthaquaSprite;
import com.shatteredpixel.lovecraftpixeldungeon.ui.BossHealthBar;
import com.shatteredpixel.lovecraftpixeldungeon.ui.MiniBossHealthBar;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.HashSet;

public class Ithaqua extends Mob{

    {
        spriteClass = IthaquaSprite.class;

        HP = HT = (Dungeon.hero.MH/2)+Dungeon.depth + 30;
        defenseSkill = (Dungeon.hero.STR/2) + Dungeon.depth;

        EXP = 10*Dungeon.depth;

        loot = new Spear().enchant(new Chilling()).upgrade(Dungeon.hero.STR/2);
        lootChance = 0.3f*Dungeon.depth;

        properties.add(Property.MINIBOSS);
    }

    protected Weapon weapon;

    public Ithaqua() {
        super();

        do {
            weapon = new Spear();
        } while (!(weapon instanceof MeleeWeapon) || weapon.cursed);

        weapon.identify();
        weapon.enchant( new Chilling() );

        HP = HT = 15 + Dungeon.depth * 5;
        defenseSkill = 4 + Dungeon.depth;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( weapon.min(), weapon.max() );
    }

    @Override
    public int attackSkill( Char target ) {
        return (int)((Dungeon.hero.STR + Dungeon.depth) * weapon.ACC);
    }

    @Override
    protected float attackDelay() {
        return weapon.DLY;
    }

    @Override
    protected boolean canAttack(Char enemy) {
        return Dungeon.level.distance( pos, enemy.pos ) <= weapon.RCH;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, Dungeon.depth + weapon.defenseFactor(null));
    }

    @Override
    public void notice() {
        super.notice();
        MiniBossHealthBar.assignBoss(this);
        if (HP <= HT/2) BossHealthBar.bleed(true);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        MiniBossHealthBar.assignBoss(this);
        if (HP <= HT/2) BossHealthBar.bleed(true);
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        if (Random.Int(4) == 0) {
            if(enemy == Dungeon.hero){
                try{
                    Hero hero = Dungeon.hero;
                    KindOfWeapon weapon = hero.belongings.weapon;

                    hero.belongings.weapon = null;
                    Dungeon.quickslot.clearItem(weapon);
                    weapon.updateQuickslot();
                    Dungeon.level.drop(weapon, hero.pos).sprite.drop();
                    GLog.n(Messages.get(this, "disarm"));
                } catch(Exception e) {

                }
            } else {
                enemy.damage(enemy.damageRoll(), this);
            }
        }
        return damage;
    }

    @Override
    public int defenseProc( Char enemy, int damage ) {
        if(enemy != Dungeon.hero){
            enemy.damage(Dungeon.hero.attackSkill(enemy), this);
        }
        if(Random.Int(20) == 10){
            GameScene.add(Blob.seed(this.pos, 500, IceWind.class));
        }
        return super.defenseProc(enemy, damage);
    }

    @Override
    public void die( Object cause ) {
        super.die( cause );
    }

    private static final HashSet<Class<?>> IMMUNITIES = new HashSet<>();
    static {
        IMMUNITIES.add(Frost.class);
        IMMUNITIES.add(Freezing.class);
        IMMUNITIES.add(Chilling.class);
    }

    private static final HashSet<Class<?>> WEAKNESSES = new HashSet<>();
    static {
        WEAKNESSES.add(Fire.class);
        WEAKNESSES.add(Blazing.class);
    }

    @Override
    public HashSet<Class<?>> weaknesses() {
        return WEAKNESSES;
    }

    @Override
    public HashSet<Class<?>> immunities() {
        return IMMUNITIES;
    }

}
