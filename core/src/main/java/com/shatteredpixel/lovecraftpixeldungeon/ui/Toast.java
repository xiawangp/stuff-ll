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
package com.shatteredpixel.lovecraftpixeldungeon.ui;

import com.shatteredpixel.lovecraftpixeldungeon.Chrome;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.PixelScene;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.ui.Component;

public class Toast extends Component {

	private static final float MARGIN_HOR	= 2;
	private static final float MARGIN_VER	= 2;

	protected NinePatch bg;
	protected SimpleButton close;
	protected RenderedTextMultiline text;

	public Toast( String text ) {
		super();
		text( text );

		width = this.text.width() + close.width() + bg.marginHor() + MARGIN_HOR * 3;
		height = Math.max( this.text.height(), close.height() ) + bg.marginVer() + MARGIN_VER * 2;
	}

	@Override
	protected void createChildren() {
		super.createChildren();

		bg = Chrome.get( Chrome.Type.TOAST_TR );
		add( bg );

		close = new SimpleButton( Icons.get( Icons.CLOSE ) ) {
			protected void onClick() {
				onClose();
			};
		};
		add( close );

		text = PixelScene.renderMultiline(8);
		add( text );
	}
	
	@Override
	protected void layout() {
		super.layout();
		
		bg.x = x;
		bg.y = y;
		bg.size( width, height );
		
		close.setPos(
			bg.x + bg.width() - bg.marginHor() / 2f - MARGIN_HOR - close.width(),
			y + (height - close.height()) / 2f );
		PixelScene.align(close);

		text.setPos(close.left() - MARGIN_HOR - text.width(), y + (height - text.height()) / 2);
		PixelScene.align(text);
	}

	public void text( String txt ) {
		text.text( txt );
	}
	
	protected void onClose() {};
}
