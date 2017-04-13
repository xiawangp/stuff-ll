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

import com.shatteredpixel.lovecraftpixeldungeon.items.Item;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.lovecraftpixeldungeon.ui.HealthBar;
import com.shatteredpixel.lovecraftpixeldungeon.ui.RenderedTextMultiline;
import com.shatteredpixel.lovecraftpixeldungeon.ui.Window;
import com.watabou.noosa.Image;
import com.watabou.noosa.ui.Component;

public class IconTitle extends Component {

	private static final float FONT_SIZE = 9;

	private static final float GAP = 2;

	protected Image imIcon;
	protected RenderedTextMultiline tfLabel;
	protected HealthBar health;

	private float healthLvl = Float.NaN;

	public IconTitle() {
		super();
	}

	public IconTitle( Item item ) {
		ItemSprite icon = new ItemSprite();
		icon( icon );
		label( Messages.titleCase( item.toString() ) );
		icon.view( item );
	}

	public IconTitle( Image icon, String label ) {
		super();

		icon( icon );
		label( label );
	}

	@Override
	protected void createChildren() {
		imIcon = new Image();
		add( imIcon );

		tfLabel = PixelScene.renderMultiline( (int)FONT_SIZE );
		tfLabel.hardlight( Window.TITLE_COLOR );
		add( tfLabel );

		health = new HealthBar();
		add( health );
	}

	@Override
	protected void layout() {

		health.visible = !Float.isNaN( healthLvl );

		imIcon.x = x + (Math.max(0, 8 - imIcon.width()/2));
		imIcon.y = y + (Math.max(0, 8 - imIcon.height()/2));

		int imWidth = (int)Math.max(imIcon.width(), 16);
		int imHeight = (int)Math.max(imIcon.height(), 16);

		tfLabel.maxWidth((int)(width - (imWidth + GAP)));
		tfLabel.setPos(x + imWidth + GAP, imHeight > tfLabel.height() ?
						y +(imHeight - tfLabel.height()) / 2 :
						y);
		PixelScene.align(tfLabel);

		if (health.visible) {
			health.setRect( tfLabel.left(), tfLabel.bottom(), tfLabel.maxWidth(), 0 );
			height = Math.max( imHeight, health.bottom() );
		} else {
			height = Math.max( imHeight, tfLabel.height() );
		}
	}

	public void icon( Image icon ) {
		remove( imIcon );
		add( imIcon = icon );
	}

	public void label( String label ) {
		tfLabel.text( label );
	}

	public void label( String label, int color ) {
		tfLabel.text( label );
		tfLabel.hardlight( color );
	}

	public void color( int color ) {
		tfLabel.hardlight( color );
	}

	public void health( float value ) {
		health.level( healthLvl = value );
		layout();
	}
}
