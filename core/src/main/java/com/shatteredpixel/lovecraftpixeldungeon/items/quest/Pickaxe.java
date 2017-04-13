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
package com.shatteredpixel.lovecraftpixeldungeon.items.quest;

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.MiGo;
import com.shatteredpixel.lovecraftpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Speck;
import com.shatteredpixel.lovecraftpixeldungeon.items.ores.Adamantium;
import com.shatteredpixel.lovecraftpixeldungeon.items.ores.Cobalt;
import com.shatteredpixel.lovecraftpixeldungeon.items.ores.DarkGold;
import com.shatteredpixel.lovecraftpixeldungeon.items.ores.Iron;
import com.shatteredpixel.lovecraftpixeldungeon.items.ores.OldCopper;
import com.shatteredpixel.lovecraftpixeldungeon.items.ores.Uranium;
import com.shatteredpixel.lovecraftpixeldungeon.items.rings.RingOfMining;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Terrain;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSprite.Glowing;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;
import com.shatteredpixel.lovecraftpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;

import java.util.ArrayList;

public class Pickaxe extends Weapon {
	
	public static final String AC_MINE	= "MINE";
	
	public static final float TIME_TO_MINE = 2;
	
	private static final Glowing BLOODY = new Glowing( 0x550000 );
	
	{
		image = ItemSpriteSheet.PICKAXE;
		
		unique = true;
		bones = false;

		weight = 10;
		
		defaultAction = AC_MINE;

	}
	
	public boolean bloodStained = false;

	@Override
	public int min(int lvl) {
		return 2;   //tier 2
	}

	@Override
	public int max(int lvl) {
		return 15;  //tier 2
	}

	@Override
	public int STRReq(int lvl) {
		return 14;  //tier 3
	}

	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_MINE );
		return actions;
	}
	
	@Override
	public void execute( final Hero hero, String action ) {

		super.execute( hero, action );
		
		if (action.equals(AC_MINE)) {
			
			if (Dungeon.depth < 11 || Dungeon.depth > 15) {
				GLog.w( Messages.get(this, "no_vein") );
				return;
			}
			
			for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
				
				final int pos = hero.pos + PathFinder.NEIGHBOURS8[i];
				if (Dungeon.level.map[pos] == Terrain.WALL_DECO) {
				
					hero.spend( TIME_TO_MINE );
					hero.busy();
					
					hero.sprite.attack( pos, new Callback() {
						
						@Override
						public void call() {

							CellEmitter.center( pos ).burst( Speck.factory( Speck.STAR ), 7 );
							Sample.INSTANCE.play( Assets.SND_EVOKE );
							
							Level.set( pos, Terrain.WALL );
							GameScene.updateMap( pos );

							int oregenerator = Randomer.randomInteger(60);

							if(oregenerator > 55){
								Cobalt cobalt = new Cobalt();
								if (cobalt.doPickUp( Dungeon.hero )) {
									GLog.i( Messages.get(Dungeon.hero, "you_now_have", cobalt.name()) );
								} else {
									if(Dungeon.hero.belongings.misc1.equals(RingOfMining.class)){
										for(int i = Dungeon.hero.belongings.misc1.level(); i >= 0; i--){
											Dungeon.level.drop( cobalt, hero.pos ).sprite.drop();
										}
									} else if(Dungeon.hero.belongings.misc2.equals(RingOfMining.class)){
										for(int i = Dungeon.hero.belongings.misc2.level(); i >= 0; i--){
											Dungeon.level.drop( cobalt, hero.pos ).sprite.drop();
										}
									} else {
										Dungeon.level.drop( cobalt, hero.pos ).sprite.drop();
									}
								}
							} else if(oregenerator > 50){
								Uranium uranium = new Uranium();
								if (uranium.doPickUp( Dungeon.hero )) {
									GLog.i( Messages.get(Dungeon.hero, "you_now_have", uranium.name()) );
								} else {
									if(Dungeon.hero.belongings.misc1.equals(RingOfMining.class)){
										for(int i = Dungeon.hero.belongings.misc1.level(); i >= 0; i--){
											Dungeon.level.drop( uranium, hero.pos ).sprite.drop();
										}
									} else if(Dungeon.hero.belongings.misc2.equals(RingOfMining.class)){
										for(int i = Dungeon.hero.belongings.misc2.level(); i >= 0; i--){
											Dungeon.level.drop( uranium, hero.pos ).sprite.drop();
										}
									} else {
										Dungeon.level.drop( uranium, hero.pos ).sprite.drop();
									}
								}
							} else if(oregenerator > 45){
								Adamantium adamantium = new Adamantium();
								if (adamantium.doPickUp( Dungeon.hero )) {
									GLog.i( Messages.get(Dungeon.hero, "you_now_have", adamantium.name()) );
								} else {
									if(Dungeon.hero.belongings.misc1.equals(RingOfMining.class)){
										for(int i = Dungeon.hero.belongings.misc1.level(); i >= 0; i--){
											Dungeon.level.drop( adamantium, hero.pos ).sprite.drop();
										}
									} else if(Dungeon.hero.belongings.misc2.equals(RingOfMining.class)){
										for(int i = Dungeon.hero.belongings.misc2.level(); i >= 0; i--){
											Dungeon.level.drop( adamantium, hero.pos ).sprite.drop();
										}
									} else {
										Dungeon.level.drop( adamantium, hero.pos ).sprite.drop();
									}
								}
							} else if(oregenerator > 35){
								DarkGold gold = new DarkGold();
								if (gold.doPickUp( Dungeon.hero )) {
									GLog.i( Messages.get(Dungeon.hero, "you_now_have", gold.name()) );
								} else {
									if(Dungeon.hero.belongings.misc1.equals(RingOfMining.class)){
										for(int i = Dungeon.hero.belongings.misc1.level(); i >= 0; i--){
											Dungeon.level.drop( gold, hero.pos ).sprite.drop();
										}
									} else if(Dungeon.hero.belongings.misc2.equals(RingOfMining.class)){
										for(int i = Dungeon.hero.belongings.misc2.level(); i >= 0; i--){
											Dungeon.level.drop( gold, hero.pos ).sprite.drop();
										}
									} else {
										Dungeon.level.drop( gold, hero.pos ).sprite.drop();
									}
								}
							} else if(oregenerator > 20){
								Iron iron = new Iron();
								if (iron.doPickUp( Dungeon.hero )) {
									GLog.i( Messages.get(Dungeon.hero, "you_now_have", iron.name()) );
								} else {
									if(Dungeon.hero.belongings.misc1.equals(RingOfMining.class)){
										for(int i = Dungeon.hero.belongings.misc1.level(); i >= 0; i--){
											Dungeon.level.drop( iron, hero.pos ).sprite.drop();
										}
									} else if(Dungeon.hero.belongings.misc2.equals(RingOfMining.class)){
										for(int i = Dungeon.hero.belongings.misc2.level(); i >= 0; i--){
											Dungeon.level.drop( iron, hero.pos ).sprite.drop();
										}
									} else {
										Dungeon.level.drop( iron, hero.pos ).sprite.drop();
									}
								}
							} else {
								OldCopper oldCopper = new OldCopper();
								if (oldCopper.doPickUp( Dungeon.hero )) {
									GLog.i( Messages.get(Dungeon.hero, "you_now_have", oldCopper.name()) );
								} else {
									if(Dungeon.hero.belongings.misc1.equals(RingOfMining.class)){
										for(int i = Dungeon.hero.belongings.misc1.level(); i >= 0; i--){
											Dungeon.level.drop(oldCopper, hero.pos ).sprite.drop();
										}
									} else if(Dungeon.hero.belongings.misc2.equals(RingOfMining.class)){
										for(int i = Dungeon.hero.belongings.misc2.level(); i >= 0; i--){
											Dungeon.level.drop(oldCopper, hero.pos ).sprite.drop();
										}
									} else {
										Dungeon.level.drop(oldCopper, hero.pos ).sprite.drop();
									}
								}
							}
							
							Hunger hunger = hero.buff( Hunger.class );
							if (hunger != null && !hunger.isStarving()) {
								hunger.reduceHunger( -Hunger.STARVING / 10 );
								BuffIndicator.refreshHero();
							}
							
							hero.onOperateComplete();
						}
					} );
					
					return;
				}
			}
			
			GLog.w( Messages.get(this, "no_vein") );
			
		}
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
	public int proc( Char attacker, Char defender, int damage ) {
		//TODO: REAL MOB
		if (!bloodStained && defender instanceof MiGo && (defender.HP <= damage)) {
			bloodStained = true;
			updateQuickslot();
		}
		return damage;
	}
	
	private static final String BLOODSTAINED = "bloodStained";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		
		bundle.put( BLOODSTAINED, bloodStained );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		
		bloodStained = bundle.getBoolean( BLOODSTAINED );
	}
	
	@Override
	public Glowing glowing() {
		return bloodStained ? BLOODY : null;
	}

}
