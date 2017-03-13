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
package com.shatteredpixel.lovecraftpixeldungeon.actors.mobs;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Pushing;
import com.shatteredpixel.lovecraftpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.enchantments.Unstable;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.RunicBlade;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.MiGoKingSprite;
import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;
import com.shatteredpixel.lovecraftpixeldungeon.ui.BossHealthBar;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class MiGoKing extends Mob {

	{
		spriteClass = MiGoKingSprite.class;

		HP = HT = Dungeon.hero.MH + Dungeon.hero.lvl+50;
		defenseSkill = Dungeon.hero.STR + Dungeon.depth + (Dungeon.hero.lvl / 2);

		EXP = 10 * Dungeon.depth;

		maxLvl = 5;
		properties.add(Property.IMMOVABLE);

		loot = MiGoSword.class;
		lootChance = 0.8f;

		baseSpeed = 2f;
	}

	protected Weapon weapon;

	public MiGoKing() {
		super();

		do {
			weapon = new RunicBlade().enchant(new Unstable());
		} while (!(weapon instanceof MeleeWeapon) || weapon.cursed);

		weapon.identify();
		weapon.enchant(new Unstable());

		HP = HT = 15 + Dungeon.depth * 5;
		defenseSkill = 4 + Dungeon.depth;
	}

	@Override
	public void notice() {
		super.notice();
		BossHealthBar.assignBoss(this);
		if (HP <= HT/2) BossHealthBar.bleed(true);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		BossHealthBar.assignBoss(this);
		if (HP <= HT/2) BossHealthBar.bleed(true);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(weapon.min(), weapon.max()) / 2;
	}

	@Override
	public int attackSkill(Char target) {
		return (int) ((Dungeon.hero.STR + Dungeon.depth) * weapon.ACC / 2);
	}

	@Override
	protected float attackDelay() {
		return weapon.DLY;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, Dungeon.depth + weapon.defenseFactor(null));
	}

	@Override
	public int attackProc(Char enemy, int damage) {

		mentalMinus(3, 7, enemy);

		if (Random.Int(4) == 0) {
			if (enemy == Dungeon.hero) {
				try {
					Hero hero = Dungeon.hero;
					KindOfWeapon weapon = hero.belongings.weapon;

					hero.belongings.weapon = null;
					Dungeon.quickslot.clearItem(weapon);
					weapon.updateQuickslot();
					Dungeon.level.drop(weapon, hero.pos).sprite.drop();
				} catch (Exception e) {

				}
			} else {
				enemy.damage(enemy.damageRoll(), this);
			}
		}

		return damage;
	}

	@Override
	public int defenseProc(Char enemy, int damage) {

		if (Randomer.randomBoolean()) {
			ArrayList<Integer> spawnPoints = new ArrayList<>();
			for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
				int p = pos + PathFinder.NEIGHBOURS8[i];
				if (Actor.findChar(p) == null && (Level.passable[p] || Level.avoid[p])) {
					spawnPoints.add(p);
				}
			}
			if (spawnPoints.size() > 0) {
				if (Randomer.randomBoolean()) {
					MiGoSword sword = new MiGoSword();
					sword.pos = Random.element(spawnPoints);

					GameScene.add(sword);
					Actor.addDelayed(new Pushing(sword, pos, sword.pos), -1);
				} else {
					MiGoShield shield = new MiGoShield();
					shield.pos = Random.element(spawnPoints);

					GameScene.add(shield);
					Actor.addDelayed(new Pushing(shield, pos, shield.pos), -1);
				}
			}
		}

		return super.defenseProc(enemy, damage);
	}
}