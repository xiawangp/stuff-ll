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
import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.elements.AirElement;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.elements.EarthElement;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.elements.Element;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.elements.FireElemental;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Pushing;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Splash;
import com.shatteredpixel.lovecraftpixeldungeon.items.potions.PotionOfFrost;
import com.shatteredpixel.lovecraftpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.lovecraftpixeldungeon.items.potions.PotionOfMindVision;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.shatteredpixel.lovecraftpixeldungeon.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Ancientpot extends Item {

	private static ArrayList<Element> elements = new ArrayList<>();
	static {
		elements.add(new AirElement());
		elements.add(new FireElemental());
		elements.add(new EarthElement());
	}

	private int elementId;
	
	public static final String AC_SHATTER	= "SHATTER";
	
	{
		image = ItemSpriteSheet.HONEYPOT;

		defaultAction = AC_THROW;
		usesTargeting = true;

		stackable = true;
		weight = 7;
	}

	public void setElements(int id){
		this.elementId = id;
	}
	
	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_SHATTER );
		return actions;
	}
	
	@Override
	public void execute( final Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals( AC_SHATTER )) {
			
			hero.sprite.zap( hero.pos );
			
			detach( hero.belongings.backpack );

			shatter( hero, hero.pos ).collect();

			hero.next();

		}
	}
	
	@Override
	protected void onThrow( int cell ) {
		if (Level.pit[cell]) {
			super.onThrow( cell );
		} else {
			Dungeon.level.drop(shatter( null, cell ), cell);
		}
	}
	
	public Item shatter( Char owner, int pos ) {

		GLog.p(elementId+"");
		GLog.p(elements.get(0).name+"");
		GLog.p(elements.get(1).name+"");
		GLog.p(elements.get(2).name+"");
		
		if (Dungeon.visible[pos]) {
			Sample.INSTANCE.play( Assets.SND_SHATTER );
			Splash.at( pos, 0xffd500, 5 );
		}
		
		int newPos = pos;
		if (Actor.findChar( pos ) != null) {
			ArrayList<Integer> candidates = new ArrayList<Integer>();
			boolean[] passable = Level.passable;
			
			for (int n : PathFinder.NEIGHBOURS4) {
				int c = pos + n;
				if (passable[c] && Actor.findChar( c ) == null) {
					candidates.add( c );
				}
			}
	
			newPos = candidates.size() > 0 ? Random.element( candidates ) : -1;
		}
		
		if (newPos != -1) {
			Element element;
			if(elements.get(elementId) == null){
				element = Random.element(elements);
			} else {
				element = elements.get(elementId);
			}
			element.spawn( Dungeon.depth );
			element.setPotInfo( pos, owner );
			element.HP = element.HT;
			element.pos = newPos;
			
			GameScene.add( element );
			Actor.addDelayed( new Pushing( element, pos, newPos ), -1f );

			element.sprite.alpha( 0 );
			element.sprite.parent.add( new AlphaTweener( element.sprite, 1, 0.15f ) );
			
			Sample.INSTANCE.play( Assets.SND_MISS );
			return new ShatteredPot().setBee( element );
		} else {
			return this;
		}
	}

	private static final String POTIONUSED = "mybee";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put( POTIONUSED, elementId );
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		elementId = bundle.getInt( POTIONUSED );
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}
	
	@Override
	public int price() {
		return 30 * quantity;
	}

	//The bee's broken 'home', all this item does is let its bee know where it is, and who owns it (if anyone).
	public static class ShatteredPot extends Item {

		public static final String AC_FILL	= "FILL";

		{
			image = ItemSpriteSheet.SHATTPOT;
			stackable = false;
		}

		private int myBee;
		private int beeDepth;

		public Item setBee(Char bee){
			myBee = bee.id();
			beeDepth = Dungeon.depth;
			return this;
		}

		@Override
		public ArrayList<String> actions(Hero hero) {
			ArrayList<String> actions = super.actions(hero);
			actions.add( AC_FILL );
			return actions;
		}

		@Override
		public void execute(Hero hero, String action) {
			super.execute(hero, action);

			if(action.equals(AC_FILL)){
				GameScene.selectItem( itemSelector, WndBag.Mode.ANCIENTPOT, Messages.get(this, "fill") );
			}
		}

		@Override
		public boolean doPickUp(Hero hero) {
			if ( super.doPickUp(hero) ){
				setHolder( hero );
				return true;
			}else
				return false;
		}

		@Override
		public void doDrop(Hero hero) {
			super.doDrop(hero);
			updateBee( hero.pos, null );
		}

		@Override
		protected void onThrow(int cell) {
			super.onThrow(cell);
			updateBee( cell, null );
		}

		public void setHolder(Char holder){
			updateBee(holder.pos, holder );
		}

		public void goAway(){
			updateBee( -1, null);
		}

		private void updateBee( int cell, Char holder){
			//important, as ids are not unique between depths.
			if (Dungeon.depth != beeDepth)
				return;

			AirElement bee = (AirElement)Actor.findById( myBee );
			if (bee != null)
				bee.setPotInfo( cell, holder );
		}

		@Override
		public boolean isUpgradable() {
			return false;
		}

		@Override
		public boolean isIdentified() {
			return true;
		}

		private static final String MYBEE = "mybee";
		private static final String BEEDEPTH = "beedepth";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put( MYBEE, myBee );
			bundle.put( BEEDEPTH, beeDepth );
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			myBee = bundle.getInt( MYBEE );
			beeDepth = bundle.getInt( BEEDEPTH );
		}

		private final WndBag.Listener itemSelector = new WndBag.Listener() {
			@Override
			public void onSelect( Item item ) {
				if (item != null) {
					item.detach(curUser.belongings.backpack);
					ShatteredPot.this.detach(curUser.belongings.backpack);
					curUser.sprite.operate(curUser.pos);
					int id = 1;
					if(item instanceof PotionOfLiquidFlame){
						id = 1;
					} else if(item instanceof PotionOfFrost){
						id = 2;
					} else if(item instanceof PotionOfMindVision){
						id = 3;
					}
					Ancientpot ancientpot = new Ancientpot();
					ancientpot.setElements(id);
					ancientpot.collect();
				}
			}
		};
	}
}
