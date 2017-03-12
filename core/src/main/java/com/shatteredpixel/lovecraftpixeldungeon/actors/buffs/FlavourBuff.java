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
package com.shatteredpixel.lovecraftpixeldungeon.actors.buffs;

//buff whose only internal logic is to wait and detach after a time.
public class FlavourBuff extends Buff {
	
	@Override
	public boolean act() {
		detach();
		return true;
	}

	//flavour buffs can all just rely on cooldown()
	protected String dispTurns() {
		//add one turn as buffs act last, we want them to end at 1 visually, even if they end at 0 internally.
		return dispTurns(cooldown()+1f);
	}
}
