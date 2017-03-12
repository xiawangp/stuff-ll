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
package com.shatteredpixel.lovecraftpixeldungeon.items;

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Speck;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.shatteredpixel.lovecraftpixeldungeon.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class DewVial extends Item {

	private static final int MAX_VOLUME	= 30;

	private static final String AC_DRINK	= "DRINK";

	private static final String AC_BLESS	= "BLESS";

	private static final float TIME_TO_DRINK = 1f;

	private static final String TXT_STATUS	= "%d/%d";

	{
		image = ItemSpriteSheet.VIAL;

		defaultAction = AC_DRINK;

		unique = true;

		weight = 1;
	}

	private int volume = 0;

	private static final String VOLUME	= "volume";

	protected WndBag.Mode mode = WndBag.Mode.UPGRADEABLE;
	protected String inventoryTitle = Messages.get(this, "upgradetitlebless");

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( VOLUME, volume );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		volume	= bundle.getInt( VOLUME );
	}

	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		if (volume > 0) {
			actions.add( AC_DRINK );
		}
		if(isFull()){
			actions.add( AC_BLESS );
		}
		return actions;
	}

	@Override
	public void execute( final Hero hero, String action ) {

		super.execute( hero, action );

		if(action.equals( AC_BLESS )){
			GameScene.selectItem(itemSelector, mode, inventoryTitle);
			this.empty();
			if(isFull()){
				image = ItemSpriteSheet.VIALF3;
			} else if(volume == MAX_VOLUME/2){
				image = ItemSpriteSheet.VIALF2;
			} else if(volume >= 1){
				image = ItemSpriteSheet.VIALF1;
			} else {
				image = ItemSpriteSheet.VIAL;
			}

			updateQuickslot();
			GLog.w( Messages.get(this, "blessed") );
		}

		if (action.equals( AC_DRINK )) {

			if (volume > 0) {

				int value = 1 + (Dungeon.depth - 1) / 5;
				if (hero.heroClass == HeroClass.HUNTRESS) {
					value++;
				}
				value *= volume;
				value = (int)Math.max(volume*volume*.01*hero.HT, value);
				int effect = Math.min( hero.HT - hero.HP, value );
				if (effect > 0) {
					hero.HP += effect;
					hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), volume > 5 ? 2 : 1 );
					hero.sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "value", effect) );
				}

				if(volume >= MAX_VOLUME/3){
					volume = volume - MAX_VOLUME/3;
				} else {
					volume = 0;
				}

				if(volume < 0){
					volume = 0;
				}

				hero.spend( TIME_TO_DRINK );
				hero.busy();

				Sample.INSTANCE.play( Assets.SND_DRINK );
				hero.sprite.operate( hero.pos );

				updateQuickslot();


			} else {
				GLog.w( Messages.get(this, "empty") );
				if(isFull()){
					image = ItemSpriteSheet.VIALF3;
				} else if(volume == MAX_VOLUME/2){
					image = ItemSpriteSheet.VIALF2;
				} else if(volume >= 1){
					image = ItemSpriteSheet.VIALF1;
				} else {
					image = ItemSpriteSheet.VIAL;
				}

				updateQuickslot();
			}

			if(isFull()){
				image = ItemSpriteSheet.VIALF3;
			} else if(volume == MAX_VOLUME/2){
				image = ItemSpriteSheet.VIALF2;
			} else if(volume >= 1){
				image = ItemSpriteSheet.VIALF1;
			} else {
				image = ItemSpriteSheet.VIAL;
			}

			updateQuickslot();

		}
	}

	public void empty() {volume = 0; updateQuickslot();}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	public boolean isFull() {
		return volume >= MAX_VOLUME;
	}

	public void collectNormalDew( Dewdrop dew ) {

		GLog.i( Messages.get(this, "collected") );
		volume += dew.quantity*dew.value;
		if (volume >= MAX_VOLUME) {
			volume = MAX_VOLUME;
			GLog.p( Messages.get(this, "full") );
		}

		weight = (volume/10) + 1;

		if(isFull()){
			image = ItemSpriteSheet.VIALF3;
		} else if(volume == MAX_VOLUME/2){
			image = ItemSpriteSheet.VIALF2;
		} else if(volume >= 1){
			image = ItemSpriteSheet.VIALF1;
		} else {
			image = ItemSpriteSheet.VIAL;
		}

		updateQuickslot();
	}

	public void collectRedGooDew( RedGooDrop dew ) {

		GLog.i( Messages.get(this, "collected") );
		volume += dew.quantity*dew.value;
		if (volume >= MAX_VOLUME) {
			volume = MAX_VOLUME;
			GLog.p( Messages.get(this, "full") );
		}

		weight = (volume/10) + 1;

		if(isFull()){
			image = ItemSpriteSheet.VIALF3;
		} else if(volume == MAX_VOLUME/2){
			image = ItemSpriteSheet.VIALF2;
		} else if(volume >= 1){
			image = ItemSpriteSheet.VIALF1;
		} else {
			image = ItemSpriteSheet.VIAL;
		}

		updateQuickslot();
	}

	public void collectYellowGooDew( YellowGooDrop dew ) {

		GLog.i( Messages.get(this, "collected") );
		volume += dew.quantity*dew.value;
		if (volume >= MAX_VOLUME) {
			volume = MAX_VOLUME;
			GLog.p( Messages.get(this, "full") );
		}

		weight = (volume/10) + 1;

		if(isFull()){
			image = ItemSpriteSheet.VIALF3;
		} else if(volume == MAX_VOLUME/2){
			image = ItemSpriteSheet.VIALF2;
		} else if(volume >= 1){
			image = ItemSpriteSheet.VIALF1;
		} else {
			image = ItemSpriteSheet.VIAL;
		}

		updateQuickslot();
	}

	public void collectPurpleGooDew( PurpleGooDrop dew ) {

		GLog.i( Messages.get(this, "collected") );
		volume += dew.quantity*dew.value;
		if (volume >= MAX_VOLUME) {
			volume = MAX_VOLUME;
			GLog.p( Messages.get(this, "full") );
		}

		weight = (volume/10) + 1;

		if(isFull()){
			image = ItemSpriteSheet.VIALF3;
		} else if(volume == MAX_VOLUME/2){
			image = ItemSpriteSheet.VIALF2;
		} else if(volume >= 1){
			image = ItemSpriteSheet.VIALF1;
		} else {
			image = ItemSpriteSheet.VIAL;
		}

		updateQuickslot();
	}

	public void collectGreenDew( GreenDewdrop dew ) {

		GLog.i( Messages.get(this, "collected") );
		volume += dew.quantity*3;
		if (volume >= MAX_VOLUME) {
			volume = MAX_VOLUME;
			GLog.p( Messages.get(this, "full") );
		}

		weight = (volume/10) + 1;

		if(isFull()){
			image = ItemSpriteSheet.VIALF3;
		} else if(volume == MAX_VOLUME/2){
			image = ItemSpriteSheet.VIALF2;
		} else if(volume >= 1){
			image = ItemSpriteSheet.VIALF1;
		} else {
			image = ItemSpriteSheet.VIAL;
		}

		updateQuickslot();
	}

	public void fill() {
		volume = MAX_VOLUME;
		if(isFull()){
			image = ItemSpriteSheet.VIALF3;
		} else if(volume == MAX_VOLUME/2){
			image = ItemSpriteSheet.VIALF2;
		} else if(volume >= 1){
			image = ItemSpriteSheet.VIALF1;
		} else {
			image = ItemSpriteSheet.VIAL;
		}
		updateQuickslot();
	}

	@Override
	public String status() {
		return Messages.format( TXT_STATUS, volume, MAX_VOLUME );
	}

	protected WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null) {
				if(item.isIdentified()){
					item.upgrade(Random.Int(1, 2));
				} else {
					item.identify();
					item.upgrade(1);
				}
			}
		}
	};

}
