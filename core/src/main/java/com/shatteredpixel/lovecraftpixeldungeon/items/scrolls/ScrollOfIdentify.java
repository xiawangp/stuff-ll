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

import com.shatteredpixel.lovecraftpixeldungeon.Badges;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Identification;
import com.shatteredpixel.lovecraftpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.lovecraftpixeldungeon.items.Item;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.shatteredpixel.lovecraftpixeldungeon.windows.WndBag;

public class ScrollOfIdentify extends InventoryScroll {

	{
		initials = 0;
		mode = WndBag.Mode.UNIDENTIFED;

		bones = true;
	}
	
	@Override
	protected void onItemSelected( Item item ) {
		
		curUser.sprite.parent.add( new Identification( curUser.sprite.center().offset( 0, -16 ) ) );
		SpellSprite.show(curUser, SpellSprite.SCROLL_IDENTIFY, SpellSprite.COLOUR_RUNE);
		
		item.identify();
		GLog.i( Messages.get(this, "it_is", item) );
		
		Badges.validateItemLevelAquired( item );
	}
	
	@Override
	public int price() {
		return isKnown() ? 30 * quantity : super.price();
	}
}
