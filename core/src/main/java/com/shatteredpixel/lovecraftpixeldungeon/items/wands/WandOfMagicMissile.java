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
package com.shatteredpixel.lovecraftpixeldungeon.items.wands;

import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.lovecraftpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.lovecraftpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;

public class WandOfMagicMissile extends DamageWand {

	{
		image = ItemSpriteSheet.WAND_MAGIC_MISSILE;
	}

	public int min(int lvl){
		return 2+lvl;
	}

	public int max(int lvl){
		return 8+2*lvl;
	}
	
	@Override
	protected void onZap( Ballistica bolt ) {
				
		Char ch = Actor.findChar( bolt.collisionPos );
		if (ch != null) {

			processSoulMark(ch, chargesPerCast());
			ch.damage(damageRoll(), this);

			ch.sprite.burst(0xFFFFFFFF, level() / 2 + 2);

		}
	}

	@Override
	public void onHit(MagesStaff staff, Char attacker, Char defender, int damage) {
		Buff.prolong( attacker, Recharging.class, 1 + staff.level()/2f);
		SpellSprite.show(attacker, SpellSprite.CHARGE);

	}
	
	protected int initialCharges() {
		return 3;
	}

}
