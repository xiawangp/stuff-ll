/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2016 Evan Debenham
 *
 * Lovecraft Pixel Dungeon
 * Copyright (C) 2016 Leon Horn
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
package com.shatteredpixel.lovecraftpixeldungeon.scenes;

import android.content.Intent;
import android.net.Uri;
import android.opengl.GLES20;

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.LovecraftPixelDungeon;
import com.shatteredpixel.lovecraftpixeldungeon.effects.BannerSprites;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Fireball;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.ui.Archs;
import com.shatteredpixel.lovecraftpixeldungeon.ui.ChangesButton;
import com.shatteredpixel.lovecraftpixeldungeon.ui.ExitButton;
import com.shatteredpixel.lovecraftpixeldungeon.ui.Icons;
import com.shatteredpixel.lovecraftpixeldungeon.ui.LanguageButton;
import com.shatteredpixel.lovecraftpixeldungeon.ui.PrefsButton;
import com.shatteredpixel.lovecraftpixeldungeon.ui.RedButton;
import com.shatteredpixel.lovecraftpixeldungeon.windows.WndBugCatcher;
import com.shatteredpixel.lovecraftpixeldungeon.windows.WndHardNotification;
import com.shatteredpixel.lovecraftpixeldungeon.windows.WndTextInput;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Button;

import javax.microedition.khronos.opengles.GL10;

public class TitleScene extends PixelScene {

	public static String yourname;
	
	@Override
	public void create() {
		
		super.create();

		Music.INSTANCE.play( Assets.THEME, true );
		Music.INSTANCE.volume( LovecraftPixelDungeon.musicVol() / 10f );

		uiCamera.visible = false;
		
		int w = Camera.main.width;
		int h = Camera.main.height;
		
		Archs archs = new Archs();
		archs.setSize( w, h );
		add( archs );
		
		Image title = BannerSprites.get( BannerSprites.Type.PIXEL_DUNGEON );
		add( title );

		float topRegion = Math.max(95f, h*0.45f);

		title.x = (w - title.width()) / 2f;
		if (LovecraftPixelDungeon.landscape())
			title.y = (topRegion - title.height()) / 2f;
		else
			title.y = 16 + (topRegion - title.height() - 16) / 2f;

		align(title);

		placeTorch(title.x + 22, title.y + 46);
		placeTorch(title.x + title.width - 22, title.y + 46);

		Image signs = new Image( BannerSprites.get( BannerSprites.Type.PIXEL_DUNGEON_SIGNS ) ) {
			private float time = 0;
			@Override
			public void update() {
				super.update();
				am = (float)Math.sin( -(time += Game.elapsed) );
			}
			@Override
			public void draw() {
				GLES20.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE );
				super.draw();
				GLES20.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			}
		};
		signs.x = title.x + (title.width() - signs.width())/2f;
		signs.y = title.y;
		add( signs );
		
		DashboardItem btnBadges = new DashboardItem( Messages.get(this, "badges"), 3 ) {
			@Override
			protected void onClick() {
				LovecraftPixelDungeon.switchNoFade( BadgesScene.class );
			}
		};
		add(btnBadges);
		
		DashboardItem btnAbout = new DashboardItem( Messages.get(this, "about"), 1 ) {
			@Override
			protected void onClick() {
				LovecraftPixelDungeon.switchNoFade( AboutScene.class );
			}
		};
		add( btnAbout );
		
		DashboardItem btnPlay = new DashboardItem( Messages.get(this, "play"), 0 ) {
			@Override
			protected void onClick() {
				LovecraftPixelDungeon.switchNoFade( StartScene.class );
			}
		};
		add( btnPlay );
		
		DashboardItem btnRankings = new DashboardItem( Messages.get(this, "rankings"), 2 ) {
			@Override
			protected void onClick() {
				LovecraftPixelDungeon.switchNoFade( RankingsScene.class );
			}
		};
		add( btnRankings );

		if (LovecraftPixelDungeon.landscape()) {
			btnRankings     .setPos( w / 2 - btnRankings.width(), topRegion );
			btnBadges       .setPos( w / 2, topRegion );
			btnPlay         .setPos( btnRankings.left() - btnPlay.width(), topRegion );
			btnAbout        .setPos( btnBadges.right(), topRegion );
		} else {
			btnPlay.setPos( w / 2 - btnPlay.width(), topRegion );
			btnRankings.setPos( w / 2, btnPlay.top() );
			btnBadges.setPos( w / 2 - btnBadges.width(), btnPlay.top() + DashboardItem.SIZE );
			btnAbout.setPos( w / 2, btnBadges.top() );
		}

		BitmapText version = new BitmapText( "v " + Game.version + "", pixelFont);
		version.measure();
		version.hardlight( 0xCCCCCC );
		version.x = w - version.width();
		version.y = h - version.height();
		add( version );

		DonateButton donateButton = new DonateButton();
		donateButton.setPos(w / 2 - (donateButton.width() / 2), h - donateButton.height() * 2);
		add(donateButton);

		Button changes = new ChangesButton();
		changes.setPos( w-changes.width(), h - version.height() - changes.height());
		add( changes );
		
		PrefsButton btnPrefs = new PrefsButton();
		btnPrefs.setPos( 0, 0 );
		add( btnPrefs );

		LanguageButton btnLang = new LanguageButton();
		btnLang.setPos(16, 1);
		add( btnLang );

		ExitButton btnExit = new ExitButton();
		btnExit.setPos( w - btnExit.width(), 0 );
		add( btnExit );

		BugCatcher bug = new BugCatcher();
		bug.setPos(1, h - 25);
		add(bug);

		if (Dungeon.hero.playername == null) {
			yourname = Messages.get(this, "playernameplaceholder");
		} else if(Dungeon.hero.playername == "?"){
			yourname = Messages.get(this, "playernamerandomname");
		} else {
			yourname = Dungeon.hero.playername;
		}


		RedButton heronamebtn = new RedButton(yourname) {
			@Override
			protected void onClick() {
				placeWndtTxtInput(yourname);
			}
		};
		heronamebtn.textColor(0xFBE363);
		heronamebtn.setRect(0, h - 15, heronamebtn.reqWidth(), 15);
		add(heronamebtn);

		fadeIn();

		if(Dungeon.hero.playername == null){
			placeWndtTxtInput(yourname);

			WndHardNotification pleasehelp = new WndHardNotification(Icons.TYPEDSCROLL.get(), Messages.get(this, "pleasehelp"), Messages.get(this, "pleasehelptxt"), Messages.get(this, "pleasehelprecognized"), 1);
			add(pleasehelp);
		}
	}
	
	private void placeTorch( float x, float y ) {
		Fireball fb = new Fireball();
		fb.setPos( x, y );
		add( fb );
	}

	private void placeWndtTxtInput(final String yourname2){
		WndTextInput wndTextInput = new WndTextInput(Messages.get(this, "playernametitle"), yourname2, 20, false, Messages.get(this, "playernameagree"), Messages.get(this, "playernamerandomname")){
			@Override
			protected void onSelect(boolean positive) {
				super.onSelect(positive);
				if(positive){
					yourname = getText();
					Dungeon.hero.playername = yourname;
				} else {
					yourname = "?";
					Dungeon.hero.playername = yourname;
				}
				LovecraftPixelDungeon.switchNoFade(TitleScene.class);
			}
		};
		add(wndTextInput);
	}

	private static class BugCatcher extends Button {

		public static final float SIZE	= 48;

		private Image image;

		public BugCatcher() {
			super();

			width = image.width;
			height = image.height;
		}

		@Override
		protected void onClick() {
			WndBugCatcher bugCatcher = new WndBugCatcher();
			add(bugCatcher);
		}

		@Override
		protected void createChildren() {
			super.createChildren();

			image = Icons.DEBUG.get();
			add( image );
		}

		@Override
		protected void layout() {
			super.layout();

			image.x = x;
			image.y = y;
		}

		@Override
		protected void onTouchDown() {
			image.brightness( 1.5f );
			Sample.INSTANCE.play( Assets.SND_CLICK, 1, 1, 0.8f );
		}

		@Override
		protected void onTouchUp() {
			image.resetColor();
		}
	}

	private static class DonateButton extends Button{

		protected Image image;

		public DonateButton() {
			super();

			width = image.width;
			height = image.height;
		}

		@Override
		protected void createChildren() {
			super.createChildren();

			image = Icons.MONEY.get();
			add( image );
		}

		@Override
		protected void layout() {
			super.layout();

			image.x = x;
			image.y = y;
		}

		@Override
		protected void onTouchDown() {
			image.brightness( 1.5f );
			Sample.INSTANCE.play( Assets.SND_CLICK );
		}

		@Override
		protected void onTouchUp() {
			image.resetColor();
		}

		@Override
		protected void onClick() {
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.me/LeonDAHorn/1"));
			Game.instance.startActivity( browserIntent );
		}

	}
	
	private static class DashboardItem extends Button {
		
		public static final float SIZE	= 48;
		
		private static final int IMAGE_SIZE	= 32;
		
		private Image image;
		private RenderedText label;
		
		public DashboardItem( String text, int index ) {
			super();
			
			image.frame( image.texture.uvRect( index * IMAGE_SIZE, 0, (index + 1) * IMAGE_SIZE, IMAGE_SIZE ) );
			this.label.text( text );
			
			setSize( SIZE, SIZE );
		}
		
		@Override
		protected void createChildren() {
			super.createChildren();
			
			image = new Image( Assets.DASHBOARD );
			add( image );
			
			label = renderText( 9 );
			add( label );
		}
		
		@Override
		protected void layout() {
			super.layout();
			
			image.x = x + (width - image.width()) / 2;
			image.y = y;
			align(image);
			
			label.x = x + (width - label.width()) / 2;
			label.y = image.y + image.height() +2;
			align(label);
		}
		
		@Override
		protected void onTouchDown() {
			image.brightness( 1.5f );
			Sample.INSTANCE.play( Assets.SND_CLICK, 1, 1, 0.8f );
		}
		
		@Override
		protected void onTouchUp() {
			image.resetColor();
		}
	}
}
