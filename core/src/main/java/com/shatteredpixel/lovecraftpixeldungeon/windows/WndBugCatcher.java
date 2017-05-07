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

import android.content.Intent;
import android.net.Uri;

import com.shatteredpixel.lovecraftpixeldungeon.LovecraftPixelDungeon;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.lovecraftpixeldungeon.ui.Icons;
import com.shatteredpixel.lovecraftpixeldungeon.ui.RenderedTextMultiline;
import com.watabou.input.Touchscreen;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.TouchArea;

public class WndBugCatcher extends WndTabbed {

	private static final int WIDTH    = LovecraftPixelDungeon.landscape() ? Camera.main.width/3 : Camera.main.width/2;
	private static final int HEIGHT   = Camera.main.height/2;

	public WndBugCatcher() {
		super();
		resize(WIDTH, HEIGHT);
		String title = Messages.get(this, "title");

		int width = WIDTH;

		String info = Messages.get(this, "txt")+"\n" +
				"_-_ whiplasher69\n" +
				"_-_ 931451545\n" +
				"_-_ hmdzl001\n" +
				"_-_ Ropuszka\n" +
				"_-_ Buue2\n" +
				"_-_ SariusDev\n" +
				"_-_ trick1234\n" +
				"_-_ supersonic1654";

		IconTitle titlebar = new IconTitle();
		titlebar.icon( Icons.BUG.get());
		titlebar.label( title );
		titlebar.setRect( 0, 0, width, 0 );
		add( titlebar );

		RenderedTextMultiline txtInfo = PixelScene.renderMultiline( info, 6 );
		txtInfo.maxWidth(width);
		txtInfo.setPos(titlebar.left(), titlebar.bottom() + 2);
		add( txtInfo );

		RenderedTextMultiline bugLink = PixelScene.renderMultiline( Messages.get(this, "report"), 8 );
		bugLink.maxWidth(width);
		bugLink.setPos(titlebar.left(), txtInfo.bottom());
		bugLink.hardlight(0x25B625);
		add(bugLink);

		TouchArea hotArea = new TouchArea( bugLink.left(), bugLink.top(), bugLink.width(), bugLink.height() ) {
			@Override
			protected void onClick( Touchscreen.Touch touch ) {
				Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "https://www.reddit.com/user/TypedScroll/" ) );
				Game.instance.startActivity( intent );
			}
		};
		add( hotArea );

		resize( width, (int)(txtInfo.top() + txtInfo.height() + bugLink.height()) );
	}
}


