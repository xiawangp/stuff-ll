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
package com.shatteredpixel.lovecraftpixeldungeon.windows;

import com.shatteredpixel.lovecraftpixeldungeon.LovecraftPixelDungeon;
import com.shatteredpixel.lovecraftpixeldungeon.items.Heap;
import com.shatteredpixel.lovecraftpixeldungeon.items.Item;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.lovecraftpixeldungeon.ui.ItemSlot;
import com.shatteredpixel.lovecraftpixeldungeon.ui.RenderedTextMultiline;
import com.shatteredpixel.lovecraftpixeldungeon.ui.Window;

public class WndInfoItem extends Window {
	
	private static final float GAP	= 2;
	
	private static final int WIDTH_P = 120;
	private static final int WIDTH_L = 144;
	
	public WndInfoItem( Heap heap ) {
		
		super();
		
		if (heap.type == Heap.Type.HEAP || heap.type == Heap.Type.FOR_SALE) {
			
			Item item = heap.peek();
			
			int color = TITLE_COLOR;
			if (item.levelKnown && item.level() > 0) {
				color = ItemSlot.UPGRADED;
			} else if (item.levelKnown && item.level() < 0) {
				color = ItemSlot.DEGRADED;
			}
			fillFields( item.image(), item.glowing(), color, item.toString(), item.info() );
			
		} else {

			fillFields( heap.image(), heap.glowing(), TITLE_COLOR, heap.toString(), heap.info() );

		}
	}
	
	public WndInfoItem( Item item ) {
		
		super();
		
		int color = TITLE_COLOR;
		if (item.levelKnown && item.level() > 0) {
			color = ItemSlot.UPGRADED;
		} else if (item.levelKnown && item.level() < 0) {
			color = ItemSlot.DEGRADED;
		}
		
		fillFields( item.image(), item.glowing(), color, item.toString(), item.info() );
	}
	
	private void fillFields( int image, ItemSprite.Glowing glowing, int titleColor, String title, String info ) {

		int width = LovecraftPixelDungeon.landscape() ? WIDTH_L : WIDTH_P;

		IconTitle titlebar = new IconTitle();
		titlebar.icon( new ItemSprite( image, glowing ) );
		titlebar.label( Messages.titleCase( title ), titleColor );
		titlebar.setRect( 0, 0, width, 0 );
		add( titlebar );
		
		RenderedTextMultiline txtInfo = PixelScene.renderMultiline( info, 6 );
		txtInfo.maxWidth(width);
		txtInfo.setPos(titlebar.left(), titlebar.bottom() + GAP);
		add( txtInfo );
		
		resize( width, (int)(txtInfo.top() + txtInfo.height()) );
	}
}
