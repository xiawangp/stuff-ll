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
package com.shatteredpixel.lovecraftpixeldungeon.ui;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.PixelScene;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Image;

public class DangerIndicator extends Tag {
	
	public static final int COLOR	= 0xFF4C4C;
	
	private BitmapText number;
	private Image icon;
	
	private int enemyIndex = 0;
	
	private int lastNumber = -1;
	
	public DangerIndicator() {
		super( 0xFF4C4C );
		
		setSize( 24, 16 );
		
		visible = false;
	}
	
	@Override
	protected void createChildren() {
		super.createChildren();
		
		number = new BitmapText( PixelScene.pixelFont);
		add( number );
		
		icon = Icons.SKULL.get();
		add( icon );
	}
	
	@Override
	protected void layout() {
		super.layout();
		
		icon.x = right() - 10;
		icon.y = y + (height - icon.height) / 2;
		
		placeNumber();
	}
	
	private void placeNumber() {
		number.x = right() - 11 - number.width();
		number.y = y + (height - number.baseLine()) / 2f;
		PixelScene.align(number);
	}
	
	@Override
	public void update() {
		
		if (Dungeon.hero.isAlive()) {
			int v =  Dungeon.hero.visibleEnemies();
			if (v != lastNumber) {
				lastNumber = v;
				if (visible = lastNumber > 0) {
					number.text( Integer.toString( lastNumber ) );
					number.measure();
					placeNumber();

					flash();
				}
			}
		} else {
			visible = false;
		}
		
		super.update();
	}
	
	@Override
	protected void onClick() {
		if (Dungeon.hero.visibleEnemies() > 0) {

			Mob target = Dungeon.hero.visibleEnemy(enemyIndex++);

			HealthIndicator.instance.target(target == HealthIndicator.instance.target() ? null : target);

			if (Dungeon.hero.curAction == null) {
				Camera.main.target = null;
				Camera.main.focusOn(target.sprite);
			}
		}
	}
}
