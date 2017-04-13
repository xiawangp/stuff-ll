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
package com.shatteredpixel.lovecraftpixeldungeon.items.scrolls;

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.lovecraftpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class ScrollOfEquivalentExchange extends Scroll {

	{
		initials = 15;

		bones = true;
	}
	
	@Override
	protected void doRead() {

		Sample.INSTANCE.play( Assets.SND_READ );
		Invisibility.dispel();

		if(curUser.belongings.weapon != null && curUser.belongings.armor != null){
			int wup = curUser.belongings.weapon.level();
			int aup = curUser.belongings.armor.level();
			curUser.belongings.weapon.level(aup);
			curUser.belongings.armor.level(wup);
			SpellSprite.show(curUser, SpellSprite.SCROLL_ENCHANT, SpellSprite.COLOUR_RUNE);
			curUser.decreaseMentalHealth(wup);
		} else {
			if(curUser.belongings.misc1 != null && curUser.belongings.misc2 != null){
				int m1 = curUser.belongings.misc1.level();
				int m2 = curUser.belongings.misc2.level();
				curUser.belongings.misc2.level(m1);
				curUser.belongings.misc1.level(m2);
				SpellSprite.show(curUser, SpellSprite.SCROLL_ENCHANT, SpellSprite.COLOUR_RUNE);
				curUser.decreaseMentalHealth(m1);
			} else {
				for(int i = curUser.belongings.backpack.items.size(); i > 0; i--){
					curUser.exp++;
				}
				SpellSprite.show(curUser, SpellSprite.MASTERY, SpellSprite.COLOUR_RUNE);
			}
		}

		GLog.i( Messages.get(this, "read") );
		setKnown();

		readAnimation();
	}
	
	@Override
	public int price() {
		return isKnown() ? 50 * quantity : super.price();
	}
}
