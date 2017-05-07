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

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.Chrome;
import com.shatteredpixel.lovecraftpixeldungeon.LovecraftPixelDungeon;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.TitleScene;
import com.shatteredpixel.lovecraftpixeldungeon.ui.RedButton;
import com.watabou.noosa.Camera;

public class WndFrameChange extends WndTabbed {

	private static final int WIDTH    = LovecraftPixelDungeon.landscape() ? Camera.main.width/3 : Camera.main.width/2;
	private static final int HEIGHT   = Camera.main.height/2;

	public WndFrameChange() {
		super();
		resize(WIDTH, HEIGHT);
		String title = Messages.get(this, "title");

		int width = WIDTH;

		IconTitle titlebar = new IconTitle();
		titlebar.label( title );
		titlebar.setRect( 0, 0, width, 0 );
		add( titlebar );

		RedButton but1 = new RedButton(Messages.get(this, "frame1")){
			@Override
			protected void onClick() {
				super.onClick();
				LovecraftPixelDungeon.frameColor(0);
				Chrome.assets = Assets.CHROME1;
				LovecraftPixelDungeon.switchNoFade(TitleScene.class);
			}
		};
		but1.textColor(0xA0A695);
		but1.setRect(2, titlebar.bottom()+2, but1.reqWidth(), 15);
		add(but1);
		RedButton but2 = new RedButton(Messages.get(this, "frame2")){
			@Override
			protected void onClick() {
				super.onClick();
				LovecraftPixelDungeon.frameColor(1);
				Chrome.assets = Assets.CHROME2;
				LovecraftPixelDungeon.switchNoFade(TitleScene.class);
			}
		};
		but2.textColor(0x4CCA39);
		but2.setRect(2+but1.reqWidth(), titlebar.bottom()+2, but2.reqWidth(), 15);
		add(but2);
		RedButton but3 = new RedButton(Messages.get(this, "frame3")){
			@Override
			protected void onClick() {
				super.onClick();
				LovecraftPixelDungeon.frameColor(2);
				Chrome.assets = Assets.CHROME3;
				LovecraftPixelDungeon.switchNoFade(TitleScene.class);
			}
		};
		but3.textColor(0x5A4EA5);
		but3.setRect(2+but2.reqWidth()+but1.reqWidth(), titlebar.bottom()+2, but3.reqWidth(), 15);
		add(but3);
		RedButton but4 = new RedButton(Messages.get(this, "frame4")){
			@Override
			protected void onClick() {
				super.onClick();
				LovecraftPixelDungeon.frameColor(3);
				Chrome.assets = Assets.CHROME4;
				LovecraftPixelDungeon.switchNoFade(TitleScene.class);
			}
		};
		but4.textColor(0xA42121);
		but4.setRect(2+but3.reqWidth()+but1.reqWidth()+but2.reqWidth(), titlebar.bottom()+2, but4.reqWidth(), 15);
		add(but4);

		resize( (int)(but3.reqWidth()+but1.reqWidth()+but2.reqWidth()+but4.reqWidth())+4, (int) but1.height()*2+4 );
	}
}


