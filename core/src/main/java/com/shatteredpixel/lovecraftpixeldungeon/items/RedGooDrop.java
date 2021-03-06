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

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Speck;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class RedGooDrop extends Item {
	
	{
		image = ItemSpriteSheet.GOO_DROP_RED;
		
		stackable = true;
	}

	public final int value = 10;
	
	@Override
	public boolean doPickUp( Hero hero ) {
		
		DewVial vial = hero.belongings.getItem( DewVial.class );
		
		if (hero.HP < hero.HT || vial == null || vial.isFull()) {
			
			int value = this.value + (Dungeon.depth - 1) / 5;
			if (hero.subClass == HeroSubClass.WARDEN) {
				value+=2;
			}
			
			int effect = Math.min( hero.HT - hero.HP, value * quantity );
			if (effect > 0) {
				hero.HP += effect;
				hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
				hero.sprite.showStatus( CharSprite.POSITIVE, Messages.get(Dewdrop.class, "value", effect) );
			} else {
				GLog.i( Messages.get(Dewdrop.class, "already_full") );
				return false;
			}
			
		} else {
			
			vial.collectRedGooDew( this );
			
		}
		
		Sample.INSTANCE.play( Assets.SND_DEWDROP );
		hero.spendAndNext( TIME_TO_PICK_UP );
		
		return true;
	}

	@Override
	//max of one dew in a stack
	public Item quantity(int value) {
		quantity = Math.min( value, 1);
		return this;
	}

}
