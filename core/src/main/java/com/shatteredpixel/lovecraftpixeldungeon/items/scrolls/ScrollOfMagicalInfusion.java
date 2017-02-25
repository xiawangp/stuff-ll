/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2016 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.shatteredpixel.lovecraftpixeldungeon.items.scrolls;

import com.shatteredpixel.lovecraftpixeldungeon.Badges;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Enchanting;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Speck;
import com.shatteredpixel.lovecraftpixeldungeon.items.Item;
import com.shatteredpixel.lovecraftpixeldungeon.items.armor.Armor;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.shatteredpixel.lovecraftpixeldungeon.windows.WndBag;

public class ScrollOfMagicalInfusion extends InventoryScroll {
	
	{
		initials = 2;
		mode = WndBag.Mode.ENCHANTABLE;

		bones = true;
	}
	
	@Override
	protected void onItemSelected( Item item ) {

		if (item instanceof Weapon)
			((Weapon)item).upgrade(true);
		else
			((Armor)item).upgrade(true);
		
		GLog.p( Messages.get(this, "infuse", item.name()) );
		
		Badges.validateItemLevelAquired(item);

		curUser.sprite.emitter().start(Speck.factory(Speck.UP), 0.2f, 3);
		Enchanting.show(curUser, item);
	}

	@Override
	public int price() {
		return isKnown() ? 100 * quantity : super.price();
	}
}
