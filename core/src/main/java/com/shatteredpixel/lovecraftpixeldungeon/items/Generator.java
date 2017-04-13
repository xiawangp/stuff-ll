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
package com.shatteredpixel.lovecraftpixeldungeon.items;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.LovecraftPixelDungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.npcs.Ghost;
import com.shatteredpixel.lovecraftpixeldungeon.items.armor.Armor;
import com.shatteredpixel.lovecraftpixeldungeon.items.armor.ClothArmor;
import com.shatteredpixel.lovecraftpixeldungeon.items.armor.LeatherArmor;
import com.shatteredpixel.lovecraftpixeldungeon.items.armor.MailArmor;
import com.shatteredpixel.lovecraftpixeldungeon.items.armor.PlateArmor;
import com.shatteredpixel.lovecraftpixeldungeon.items.armor.ScaleArmor;
import com.shatteredpixel.lovecraftpixeldungeon.items.artifacts.AlchemistsToolkit;
import com.shatteredpixel.lovecraftpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.lovecraftpixeldungeon.items.artifacts.CapeOfThorns;
import com.shatteredpixel.lovecraftpixeldungeon.items.artifacts.ChaliceOfBlood;
import com.shatteredpixel.lovecraftpixeldungeon.items.artifacts.CloakOfShadows;
import com.shatteredpixel.lovecraftpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.lovecraftpixeldungeon.items.artifacts.EtherealChains;
import com.shatteredpixel.lovecraftpixeldungeon.items.artifacts.HornOfPlenty;
import com.shatteredpixel.lovecraftpixeldungeon.items.artifacts.LloydsBeacon;
import com.shatteredpixel.lovecraftpixeldungeon.items.artifacts.MasterThievesArmband;
import com.shatteredpixel.lovecraftpixeldungeon.items.artifacts.SandalsOfNature;
import com.shatteredpixel.lovecraftpixeldungeon.items.artifacts.TalismanOfForesight;
import com.shatteredpixel.lovecraftpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.shatteredpixel.lovecraftpixeldungeon.items.artifacts.UnstableSpellbook;
import com.shatteredpixel.lovecraftpixeldungeon.items.bags.Bag;
import com.shatteredpixel.lovecraftpixeldungeon.items.food.Food;
import com.shatteredpixel.lovecraftpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.lovecraftpixeldungeon.items.food.Pasty;
import com.shatteredpixel.lovecraftpixeldungeon.items.potions.Potion;
import com.shatteredpixel.lovecraftpixeldungeon.items.potions.PotionOfExperience;
import com.shatteredpixel.lovecraftpixeldungeon.items.potions.PotionOfFrost;
import com.shatteredpixel.lovecraftpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.lovecraftpixeldungeon.items.potions.PotionOfInvisibility;
import com.shatteredpixel.lovecraftpixeldungeon.items.potions.PotionOfLevitation;
import com.shatteredpixel.lovecraftpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.lovecraftpixeldungeon.items.potions.PotionOfMight;
import com.shatteredpixel.lovecraftpixeldungeon.items.potions.PotionOfMindVision;
import com.shatteredpixel.lovecraftpixeldungeon.items.potions.PotionOfParalyticGas;
import com.shatteredpixel.lovecraftpixeldungeon.items.potions.PotionOfPurity;
import com.shatteredpixel.lovecraftpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.lovecraftpixeldungeon.items.potions.PotionOfToxicGas;
import com.shatteredpixel.lovecraftpixeldungeon.items.rings.Ring;
import com.shatteredpixel.lovecraftpixeldungeon.items.rings.RingOfAccuracy;
import com.shatteredpixel.lovecraftpixeldungeon.items.rings.RingOfElements;
import com.shatteredpixel.lovecraftpixeldungeon.items.rings.RingOfEvasion;
import com.shatteredpixel.lovecraftpixeldungeon.items.rings.RingOfForce;
import com.shatteredpixel.lovecraftpixeldungeon.items.rings.RingOfFuror;
import com.shatteredpixel.lovecraftpixeldungeon.items.rings.RingOfHaste;
import com.shatteredpixel.lovecraftpixeldungeon.items.rings.RingOfMagic;
import com.shatteredpixel.lovecraftpixeldungeon.items.rings.RingOfMight;
import com.shatteredpixel.lovecraftpixeldungeon.items.rings.RingOfMining;
import com.shatteredpixel.lovecraftpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.lovecraftpixeldungeon.items.rings.RingOfTenacity;
import com.shatteredpixel.lovecraftpixeldungeon.items.rings.RingOfWealth;
import com.shatteredpixel.lovecraftpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfEquivalentExchange;
import com.shatteredpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfLullaby;
import com.shatteredpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfMagicalInfusion;
import com.shatteredpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.shatteredpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import com.shatteredpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfRage;
import com.shatteredpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.shatteredpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfSummoning;
import com.shatteredpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfSunlight;
import com.shatteredpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfTerror;
import com.shatteredpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfThunderstorm;
import com.shatteredpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.lovecraftpixeldungeon.items.shrooms.BlueShroom;
import com.shatteredpixel.lovecraftpixeldungeon.items.shrooms.BlueWhiteShroom;
import com.shatteredpixel.lovecraftpixeldungeon.items.shrooms.EggShroom;
import com.shatteredpixel.lovecraftpixeldungeon.items.shrooms.FlyAgaricShroom;
import com.shatteredpixel.lovecraftpixeldungeon.items.shrooms.GreenRedShroom;
import com.shatteredpixel.lovecraftpixeldungeon.items.shrooms.GreenShroom;
import com.shatteredpixel.lovecraftpixeldungeon.items.shrooms.LanternShroom;
import com.shatteredpixel.lovecraftpixeldungeon.items.shrooms.Mushrooms;
import com.shatteredpixel.lovecraftpixeldungeon.items.shrooms.OrangeShroom;
import com.shatteredpixel.lovecraftpixeldungeon.items.shrooms.PurpleShroom;
import com.shatteredpixel.lovecraftpixeldungeon.items.shrooms.RedShroom;
import com.shatteredpixel.lovecraftpixeldungeon.items.wands.Wand;
import com.shatteredpixel.lovecraftpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.lovecraftpixeldungeon.items.wands.WandOfCorruption;
import com.shatteredpixel.lovecraftpixeldungeon.items.wands.WandOfDisintegration;
import com.shatteredpixel.lovecraftpixeldungeon.items.wands.WandOfFireblast;
import com.shatteredpixel.lovecraftpixeldungeon.items.wands.WandOfFrost;
import com.shatteredpixel.lovecraftpixeldungeon.items.wands.WandOfLightning;
import com.shatteredpixel.lovecraftpixeldungeon.items.wands.WandOfMagicMissile;
import com.shatteredpixel.lovecraftpixeldungeon.items.wands.WandOfPrismaticLight;
import com.shatteredpixel.lovecraftpixeldungeon.items.wands.WandOfRegrowth;
import com.shatteredpixel.lovecraftpixeldungeon.items.wands.WandOfTransfusion;
import com.shatteredpixel.lovecraftpixeldungeon.items.wands.WandOfVenom;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.AssassinsBlade;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.BattleAxe;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Dagger;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Dirk;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Flail;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Glaive;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Greataxe;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Greatshield;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Greatsword;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.HandAxe;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Knuckles;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Longsword;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Mace;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.NewShortsword;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Quarterstaff;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.RoundShield;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.RunicBlade;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Sai;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Scimitar;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Spear;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Sword;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.WarHammer;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Whip;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.WornShortsword;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.missiles.Boomerang;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.missiles.CurareDart;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.missiles.Dart;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.missiles.IncendiaryDart;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.missiles.Javelin;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.missiles.Shuriken;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.missiles.Tamahawk;
import com.shatteredpixel.lovecraftpixeldungeon.plants.BlandfruitBush;
import com.shatteredpixel.lovecraftpixeldungeon.plants.Blindweed;
import com.shatteredpixel.lovecraftpixeldungeon.plants.Dreamfoil;
import com.shatteredpixel.lovecraftpixeldungeon.plants.Earthroot;
import com.shatteredpixel.lovecraftpixeldungeon.plants.Fadeleaf;
import com.shatteredpixel.lovecraftpixeldungeon.plants.Firebloom;
import com.shatteredpixel.lovecraftpixeldungeon.plants.Icecap;
import com.shatteredpixel.lovecraftpixeldungeon.plants.Plant;
import com.shatteredpixel.lovecraftpixeldungeon.plants.Rotberry;
import com.shatteredpixel.lovecraftpixeldungeon.plants.Sorrowmoss;
import com.shatteredpixel.lovecraftpixeldungeon.plants.Starflower;
import com.shatteredpixel.lovecraftpixeldungeon.plants.Stormvine;
import com.shatteredpixel.lovecraftpixeldungeon.plants.Sungrass;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Generator {

	public static enum Category {
		WEAPON	( 100,	Weapon.class ),
		WEP_T1	( 0, 	Weapon.class),
		WEP_T2	( 0,	Weapon.class),
		WEP_T3	( 0, 	Weapon.class),
		WEP_T4	( 0, 	Weapon.class),
		WEP_T5	( 0, 	Weapon.class),
		ARMOR	( 60,	Armor.class ),
		POTION	( 500,	Potion.class ),
		SCROLL	( 400,	Scroll.class ),
		WAND	( 40,	Wand.class ),
		RING	( 15,	Ring.class ),
		ARTIFACT( 15,   Artifact.class),
		SEED	( 50,	Plant.Seed.class ),
		FOOD	( 0,	Food.class ),
		SHROOMS	( 0,	Mushrooms.class ),
		GOLD	( 500,	Gold.class );
		
		public Class<?>[] classes;
		public float[] probs;
		
		public float prob;
		public Class<? extends Item> superClass;
		
		private Category( float prob, Class<? extends Item> superClass ) {
			this.prob = prob;
			this.superClass = superClass;
		}
		
		public static int order( Item item ) {
			for (int i=0; i < values().length; i++) {
				if (values()[i].superClass.isInstance( item )) {
					return i;
				}
			}
			
			return item instanceof Bag ? Integer.MAX_VALUE : Integer.MAX_VALUE - 1;
		}
	}

	private static final float[][] floorSetTierProbs = new float[][] {
			{0, 70, 20,  8,  2},
			{0, 25, 50, 20,  5},
			{0, 10, 40, 40, 10},
			{0,  5, 20, 50, 25},
			{0,  2,  8, 20, 70}
	};
	
	private static HashMap<Category,Float> categoryProbs = new HashMap<Generator.Category, Float>();

	private static final float[] INITIAL_ARTIFACT_PROBS = new float[]{ 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1};
	
	static {
		
		Category.GOLD.classes = new Class<?>[]{
			Gold.class };
		Category.GOLD.probs = new float[]{ 1 };
		
		Category.SCROLL.classes = new Class<?>[]{
			ScrollOfIdentify.class,
			ScrollOfTeleportation.class,
			ScrollOfRemoveCurse.class,
			ScrollOfUpgrade.class,
			ScrollOfRecharging.class,
			ScrollOfMagicMapping.class,
			ScrollOfRage.class,
			ScrollOfTerror.class,
			ScrollOfLullaby.class,
			ScrollOfMagicalInfusion.class,
			ScrollOfPsionicBlast.class,
			ScrollOfMirrorImage.class,
				ScrollOfSunlight.class,
				ScrollOfThunderstorm.class,
				ScrollOfEquivalentExchange.class,
				ScrollOfSummoning.class};
		Category.SCROLL.probs = new float[]{ 30, 10, 20, 0, 15, 15, 12, 8, 8, 0, 4, 10, 7, 8, 4, 4};
		
		Category.POTION.classes = new Class<?>[]{
			PotionOfHealing.class,
			PotionOfExperience.class,
			PotionOfToxicGas.class,
			PotionOfParalyticGas.class,
			PotionOfLiquidFlame.class,
			PotionOfLevitation.class,
			PotionOfStrength.class,
			PotionOfMindVision.class,
			PotionOfPurity.class,
			PotionOfInvisibility.class,
			PotionOfMight.class,
			PotionOfFrost.class };
		Category.POTION.probs = new float[]{ 45, 4, 15, 10, 15, 10, 0, 20, 12, 10, 0, 10 };

		//TODO: add last ones when implemented
		Category.WAND.classes = new Class<?>[]{
			WandOfMagicMissile.class,
			WandOfLightning.class,
			WandOfDisintegration.class,
			WandOfFireblast.class,
			WandOfVenom.class,
			WandOfBlastWave.class,
			//WandOfLivingEarth.class,
			WandOfFrost.class,
			WandOfPrismaticLight.class,
			//WandOfWarding.class,
			WandOfTransfusion.class,
			WandOfCorruption.class,
			WandOfRegrowth.class };
		Category.WAND.probs = new float[]{ 5, 4, 4, 4, 4, 3, /*3,*/ 3, 3, /*3,*/ 3, 3, 3 };

		//see generator.randomWeapon
		Category.WEAPON.classes = new Class<?>[]{};
		Category.WEAPON.probs = new float[]{};

		Category.WEP_T1.classes = new Class<?>[]{
			WornShortsword.class,
			Knuckles.class,
			Dagger.class,
			MagesStaff.class,
			Boomerang.class,
			Dart.class
		};
		Category.WEP_T1.probs = new float[]{ 1, 1, 1, 0, 0, 1 };

		Category.WEP_T2.classes = new Class<?>[]{
			NewShortsword.class,
			HandAxe.class,
			Spear.class,
			Quarterstaff.class,
			Dirk.class,
			IncendiaryDart.class
		};
		Category.WEP_T2.probs = new float[]{ 6, 5, 5, 4, 4, 6 };

		Category.WEP_T3.classes = new Class<?>[]{
			Sword.class,
			Mace.class,
			Scimitar.class,
			RoundShield.class,
			Sai.class,
			Whip.class,
			Shuriken.class,
			CurareDart.class
		};
		Category.WEP_T3.probs = new float[]{ 6, 5, 5, 4, 4, 4, 6, 6 };

		Category.WEP_T4.classes = new Class<?>[]{
			Longsword.class,
			BattleAxe.class,
			Flail.class,
			RunicBlade.class,
			AssassinsBlade.class,
			Javelin.class
		};
		Category.WEP_T4.probs = new float[]{ 6, 5, 5, 4, 4, 6 };

		Category.WEP_T5.classes = new Class<?>[]{
			Greatsword.class,
			WarHammer.class,
			Glaive.class,
			Greataxe.class,
			Greatshield.class,
			Tamahawk.class
		};
		Category.WEP_T5.probs = new float[]{ 6, 5, 5, 4, 4, 6 };

		//see Generator.randomArmor
		Category.ARMOR.classes = new Class<?>[]{
			ClothArmor.class,
			LeatherArmor.class,
			MailArmor.class,
			ScaleArmor.class,
			PlateArmor.class };
		Category.ARMOR.probs = new float[]{ 0, 0, 0, 0, 0 };
		
		Category.FOOD.classes = new Class<?>[]{
			Food.class,
			Pasty.class,
			MysteryMeat.class };
		Category.FOOD.probs = new float[]{ 4, 1, 0 };

		Category.SHROOMS.classes = new Class<?>[]{
				BlueShroom.class,
				BlueWhiteShroom.class,
				EggShroom.class,
				FlyAgaricShroom.class,
				GreenRedShroom.class,
				GreenShroom.class,
				LanternShroom.class,
				OrangeShroom.class,
				PurpleShroom.class,
				RedShroom.class };
		Category.SHROOMS.probs = new float[]{ 5, 4, 3, 2, 2, 5, 3, 3, 2, 1 };

			
		Category.RING.classes = new Class<?>[]{
			RingOfAccuracy.class,
			RingOfEvasion.class,
			RingOfElements.class,
			RingOfForce.class,
			RingOfFuror.class,
			RingOfHaste.class,
			RingOfMagic.class, //currently removed from drop tables, pending rework
			RingOfMight.class,
			RingOfSharpshooting.class,
			RingOfTenacity.class,
			RingOfWealth.class,
				RingOfMining.class};
		Category.RING.probs = new float[]{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 };

		Category.ARTIFACT.classes = new Class<?>[]{
			CapeOfThorns.class,
			ChaliceOfBlood.class,
			CloakOfShadows.class,
			HornOfPlenty.class,
			MasterThievesArmband.class,
			SandalsOfNature.class,
			TalismanOfForesight.class,
			TimekeepersHourglass.class,
			UnstableSpellbook.class,
			AlchemistsToolkit.class, //currently removed from drop tables, pending rework.
			DriedRose.class, //starts with no chance of spawning, chance is set directly after beating ghost quest.
			LloydsBeacon.class,
			EtherealChains.class
			};
		Category.ARTIFACT.probs = INITIAL_ARTIFACT_PROBS.clone();
		
		Category.SEED.classes = new Class<?>[]{
			Firebloom.Seed.class,
			Icecap.Seed.class,
			Sorrowmoss.Seed.class,
			Blindweed.Seed.class,
			Sungrass.Seed.class,
			Earthroot.Seed.class,
			Fadeleaf.Seed.class,
			Rotberry.Seed.class,
			BlandfruitBush.Seed.class,
			Dreamfoil.Seed.class,
			Stormvine.Seed.class,
			Starflower.Seed.class};
		Category.SEED.probs = new float[]{ 12, 12, 12, 12, 12, 12, 12, 0, 4, 12, 12, 3 };
	}
	
	public static void reset() {
		for (Category cat : Category.values()) {
			categoryProbs.put( cat, cat.prob );
		}
	}
	
	public static Item random() {
		return random( Random.chances( categoryProbs ) );
	}
	
	public static Item random( Category cat ) {
		try {
			
			categoryProbs.put( cat, categoryProbs.get( cat ) / 2 );
			
			switch (cat) {
			case ARMOR:
				return randomArmor();
			case WEAPON:
				return randomWeapon();
			case ARTIFACT:
				Item item = randomArtifact();
				//if we're out of artifacts, return a ring instead.
				return item != null ? item : random(Category.RING);
			default:
				return ((Item)cat.classes[Random.chances( cat.probs )].newInstance()).random();
			}
			
		} catch (Exception e) {

			LovecraftPixelDungeon.reportException(e);
			return null;
			
		}
	}
	
	public static Item random( Class<? extends Item> cl ) {
		try {
			
			return ((Item)cl.newInstance()).random();
			
		} catch (Exception e) {

			LovecraftPixelDungeon.reportException(e);
			return null;
			
		}
	}

	public static Armor randomArmor(){
		return randomArmor(Dungeon.depth / 5);
	}
	
	public static Armor randomArmor(int floorSet) {

		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);

		try {
			Armor a = (Armor)Category.ARMOR.classes[Random.chances(floorSetTierProbs[floorSet])].newInstance();
			a.random();
			return a;
		} catch (Exception e) {
			LovecraftPixelDungeon.reportException(e);
			return null;
		}
	}

	public static final Category[] wepTiers = new Category[]{
			Category.WEP_T1,
			Category.WEP_T2,
			Category.WEP_T3,
			Category.WEP_T4,
			Category.WEP_T5
	};

	public static Weapon randomWeapon(){
		return randomWeapon(Dungeon.depth / 5);
	}
	
	public static Weapon randomWeapon(int floorSet) {

		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);

		try {
			Category c = wepTiers[Random.chances(floorSetTierProbs[floorSet])];
			Weapon w = (Weapon)c.classes[Random.chances(c.probs)].newInstance();
			w.random();
			return w;
		} catch (Exception e) {
			LovecraftPixelDungeon.reportException(e);
			return null;
		}
	}

	//enforces uniqueness of artifacts throughout a run.
	public static Artifact randomArtifact() {

		try {
			Category cat = Category.ARTIFACT;
			int i = Random.chances( cat.probs );

			//if no artifacts are left, return null
			if (i == -1){
				return null;
			}

			Artifact artifact = (Artifact)cat.classes[i].newInstance();

			//remove the chance of spawning this artifact.
			cat.probs[i] = 0;
			spawnedArtifacts.add(cat.classes[i].getSimpleName());

			artifact.random();

			return artifact;

		} catch (Exception e) {
			LovecraftPixelDungeon.reportException(e);
			return null;
		}
	}

	public static boolean removeArtifact(Artifact artifact) {
		if (spawnedArtifacts.contains(artifact.getClass().getSimpleName()))
			return false;

		Category cat = Category.ARTIFACT;
		for (int i = 0; i < cat.classes.length; i++)
			if (cat.classes[i].equals(artifact.getClass())) {
				if (cat.probs[i] == 1){
					cat.probs[i] = 0;
					spawnedArtifacts.add(artifact.getClass().getSimpleName());
					return true;
				} else
					return false;
			}

		return false;
	}

	//resets artifact probabilities, for new dungeons
	public static void initArtifacts() {
		Category.ARTIFACT.probs = INITIAL_ARTIFACT_PROBS.clone();

		//checks for dried rose quest completion, adds the rose in accordingly.
		if (Ghost.Quest.completed()) Category.ARTIFACT.probs[10] = 1;

		spawnedArtifacts = new ArrayList<String>();
	}

	private static ArrayList<String> spawnedArtifacts = new ArrayList<String>();

	private static final String ARTIFACTS = "artifacts";

	//used to store information on which artifacts have been spawned.
	public static void storeInBundle(Bundle bundle) {
		bundle.put( ARTIFACTS, spawnedArtifacts.toArray(new String[spawnedArtifacts.size()]));
	}

	public static void restoreFromBundle(Bundle bundle) {
		initArtifacts();

		if (bundle.contains(ARTIFACTS)) {
			Collections.addAll(spawnedArtifacts, bundle.getStringArray(ARTIFACTS));
			Category cat = Category.ARTIFACT;

			for (String artifact : spawnedArtifacts)
				for (int i = 0; i < cat.classes.length; i++)
					if (cat.classes[i].getSimpleName().equals(artifact))
						cat.probs[i] = 0;
		}
	}
}
