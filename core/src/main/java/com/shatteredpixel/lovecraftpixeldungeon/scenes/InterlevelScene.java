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
package com.shatteredpixel.lovecraftpixeldungeon.scenes;

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.LovecraftPixelDungeon;
import com.shatteredpixel.lovecraftpixeldungeon.Statistics;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.items.Generator;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.levels.RegularLevel;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.ui.GameLog;
import com.shatteredpixel.lovecraftpixeldungeon.windows.WndError;
import com.shatteredpixel.lovecraftpixeldungeon.windows.WndStory;
import com.watabou.input.Touchscreen;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.BitmapTextMultiline;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.TouchArea;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class InterlevelScene extends PixelScene {

	private static final float TIME_TO_FADE = 0.3f;
	
	public enum Mode {
		DESCEND, ASCEND, CONTINUE, RESURRECT, RETURN, FALL, RESET, NONE
	}
	public static Mode mode;
	
	public static int returnDepth;
	public static int returnPos;
	
	public static boolean noStory = false;

	public static boolean fallIntoPit;
	
	private enum Phase {
		FADE_IN, STATIC, FADE_OUT
	}

	private final String[] TIPS = {
			Messages.get(this, "leviticus"),
			Messages.get(this, "exodus"),
			Messages.get(this, "slenderman1"),
			Messages.get(this, "slenderman2"),
			Messages.get(this, "nyarlathotep"),
			Messages.get(this, "nietzsche"),
			Messages.get(this, "poe1"),
			Messages.get(this, "poe2"),
			Messages.get(this, "chinesecurse"),
			Messages.get(this, "wikipediacurse"),
			Messages.get(this, "chinesesaying"),
			Messages.get(this, "peterkurten"),
			Messages.get(this, "johnwayne"),
			Messages.get(this, "jigsaw"),
			Messages.get(this, "bloch"),
			Messages.get(this, "3deadkings"),
			Messages.get(this, "oppenheimer"),
			Messages.get(this, "vegetius"),
			Messages.get(this, "tannhauser"),
			Messages.get(this, "gita"),
			Messages.get(this, "alice1"),
			Messages.get(this, "alice2"),
			Messages.get(this, "irulan"),
			Messages.get(this, "henley"),
			Messages.get(this, "henley2"),
			Messages.get(this, "gameofthrones"),
	};

	private Phase phase;
	private float timeLeft;
	
	private RenderedText message;
	
	private Thread thread;
	private Exception error = null;
	private float waitingTime;

	private ArrayList<BitmapText> tipBox;

	@Override
	public void create() {
		super.create();

		String text = Messages.get(Mode.class, mode.name());
		
		message = PixelScene.renderText( text, 9 );
		message.x = (Camera.main.width - message.width()) / 2;
		message.y = (Camera.main.height - message.height()) / 6;
		align(message);
		add( message );

		tipBox = new ArrayList<>();

		BitmapTextMultiline tip = PixelScene.createMultiline(TIPS[Random.Int(TIPS.length)], 6);
		tip.maxWidth = Camera.main.width * 9 / 10;
		tip.measure();

		for (BitmapText line : tip.new LineSplitter().split()) {
			line.measure();
			line.x = PixelScene.align(Camera.main.width / 2 - line.width() / 2);
			line.y = PixelScene.align(Camera.main.height * 3 / 4 - tip.height() * 3 / 4 + tipBox.size() * line.height());
			tipBox.add(line);
			add(line);
		}
		
		phase = Phase.FADE_IN;
		timeLeft = TIME_TO_FADE;

		thread = new Thread() {
			@Override
			public void run() {
				
				try {
					
					Generator.reset();

					switch (mode) {
					case DESCEND:
						descend();
						break;
					case ASCEND:
						ascend();
						break;
					case CONTINUE:
						restore();
						break;
					case RESURRECT:
						resurrect();
						break;
					case RETURN:
						returnTo();
						break;
					case FALL:
						fall();
						break;
					case RESET:
						reset();
						break;
					}
					
					if ((Dungeon.depth % 5) == 0) {
						Sample.INSTANCE.load( Assets.SND_BOSS );
					}
					
				} catch (Exception e) {
					
					error = e;

				}

				add(new TouchArea(Camera.main.x, Camera.main.y, Camera.main.width, Camera.main.height){
					@Override
					protected void onClick(Touchscreen.Touch touch) {
						super.onClick(touch);
						if (phase == Phase.STATIC && error == null) {
							phase = Phase.FADE_OUT;
							timeLeft = TIME_TO_FADE;
						}
					}
				});
			}
		};
		thread.start();
		waitingTime = 0f;
	}
	
	@Override
	public void update() {
		super.update();

		waitingTime += Game.elapsed;
		
		float p = timeLeft / TIME_TO_FADE;
		
		switch (phase) {
		
		case FADE_IN:
			message.alpha( 1 - p );
			if ((timeLeft -= Game.elapsed) <= 0) {
				if (!thread.isAlive() && error == null) {
					phase = Phase.FADE_OUT;
					timeLeft = TIME_TO_FADE;
				} else {
					phase = Phase.STATIC;
				}
			}
			break;
			
		case FADE_OUT:
			message.alpha( p );

			if (mode == Mode.CONTINUE || (mode == Mode.DESCEND && Dungeon.depth == 1)) {
				Music.INSTANCE.volume( p * (LovecraftPixelDungeon.musicVol()/10f));
			}
			if ((timeLeft -= Game.elapsed) <= 0) {
				Game.switchScene( GameScene.class );
			}
			break;
			
		case STATIC:
			if (error != null) {
				String errorMsg;
				if (error instanceof FileNotFoundException) errorMsg = Messages.get(this, "file_not_found");
				else if (error instanceof IOException) errorMsg = Messages.get(this, "io_error");

				else throw new RuntimeException("fatal error occured while moving between floors", error);

				add( new WndError( errorMsg ) {
					public void onBackPressed() {
						super.onBackPressed();
						Game.switchScene( StartScene.class );
					}
				} );
				error = null;
			} else if ((int)waitingTime == 10){
				waitingTime = 60f;
				LovecraftPixelDungeon.reportException(
						new RuntimeException("waited more than 10 seconds on levelgen. Seed:" + Dungeon.seed + " depth:" + Dungeon.depth)
				);
			}
			break;
		}
	}

	private void descend() throws IOException {

		Actor.fixTime();
		if (Dungeon.hero == null) {
			Dungeon.init();
			if (noStory) {
				Dungeon.chapters.add( WndStory.ID_SEWERS );
				noStory = false;
			}
			GameLog.wipe();
		} else {
			Dungeon.saveAll();
		}

		Level level;
		if (Dungeon.depth >= Statistics.deepestFloor) {
			level = Dungeon.newLevel();
		} else {
			Dungeon.depth++;
			level = Dungeon.loadLevel( Dungeon.hero.heroClass );
		}
		Dungeon.switchLevel( level, level.entrance );
	}
	
	private void fall() throws IOException {

		Actor.fixTime();
		Dungeon.saveAll();

		Level level;
		if (Dungeon.depth >= Statistics.deepestFloor) {
			level = Dungeon.newLevel();
		} else {
			Dungeon.depth++;
			level = Dungeon.loadLevel( Dungeon.hero.heroClass );
		}
		Dungeon.switchLevel( level, fallIntoPit ? level.pitCell() : level.randomRespawnCell() );
	}
	
	private void ascend() throws IOException {
		Actor.fixTime();

		Dungeon.saveAll();
		Dungeon.depth--;
		Level level = Dungeon.loadLevel( Dungeon.hero.heroClass );
		Dungeon.switchLevel( level, level.exit );
	}
	
	private void returnTo() throws IOException {
		
		Actor.fixTime();

		Dungeon.saveAll();
		Dungeon.depth = returnDepth;
		Level level = Dungeon.loadLevel( Dungeon.hero.heroClass );
		Dungeon.switchLevel( level, returnPos );
	}
	
	private void restore() throws IOException {
		
		Actor.fixTime();

		GameLog.wipe();

		Dungeon.loadGame( StartScene.curClass );
		if (Dungeon.depth == -1) {
			Dungeon.depth = Statistics.deepestFloor;
			Dungeon.switchLevel( Dungeon.loadLevel( StartScene.curClass ), -1 );
		} else {
			Level level = Dungeon.loadLevel( StartScene.curClass );
			Dungeon.switchLevel( level, Dungeon.hero.pos );
		}
	}
	
	private void resurrect() throws IOException {
		
		Actor.fixTime();
		
		if (Dungeon.level.locked) {
			Dungeon.hero.resurrect( Dungeon.depth );
			Dungeon.depth--;
			Level level = Dungeon.newLevel();
			Dungeon.switchLevel( level, level.entrance );
		} else {
			Dungeon.hero.resurrect( -1 );
			Dungeon.resetLevel();
		}
	}

	private void reset() throws IOException {

		Actor.fixTime();

		Dungeon.depth--;
		Level level = Dungeon.newLevel();
		//FIXME this only partially addresses issues regarding weak floors.
		RegularLevel.weakFloorCreated = false;
		Dungeon.switchLevel( level, level.entrance );
	}

	@Override
	protected void onBackPressed() {

	}
}
