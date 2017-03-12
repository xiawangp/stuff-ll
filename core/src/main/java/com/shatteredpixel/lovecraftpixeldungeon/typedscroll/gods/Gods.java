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

package com.shatteredpixel.lovecraftpixeldungeon.typedscroll.gods;

import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;

public class Gods {

	private static String yourgodsName = "Nyarlathotep";

	public static String getYourgodsName() {
		return yourgodsName;
	}

	public static void setYourgodsName(String yourgodsName) {
		Gods.yourgodsName = yourgodsName;
	}

	public static String makeYourGodsName(){
		String name = yourgodsName;
		switch(Randomer.randomInteger(20)){
			case 0:
				name = "Nyarlathotep";
				break;
			case 1:
				name = "Azathoth";
				break;
			case 2:
				name = "Yog-Sothoth";
				break;
			case 3:
				name = "Cthulhu";
				break;
			case 4:
				name = "Hastur";
				break;
			case 5:
				name = "Dagon";
				break;
			case 6:
				name = "Bokrug";
				break;
			case 7:
				name = "Ithaqua";
				break;
			case 8:
				name = "Nug";
				break;
			case 9:
				name = "Yeb";
				break;
			case 10:
				name = "Shudde M'ell";
				break;
			case 11:
				name = "Nodens";
				break;
			case 12:
				name = "Nyog'Sothep";
				break;
			case 13:
				name = "Shub-Niggurath";
				break;
			case 14:
				name = "Ubbo-Sathla";
				break;
			case 15:
				name = "Ulthar";
				break;
			case 16:
				name = "Karakal";
				break;
			case 17:
				name = "Zo-Kalar";
				break;
			case 18:
				name = "Alala";
				break;
			case 19:
				name = "Amon-Gorloth";
				break;
		}
		yourgodsName = name;
		return name;
	}

	public static String getYourGodsEnemyName(){
		String name = "Cthaat";
		switch(getYourgodsName()){
			case "Nyarlathotep":
				name = "Nodens";
				break;
			case "Azathoth":
				name = "Yog-Sothoth";
				break;
			case "Yog-Sothoth":
				name = "Azathoth";
				break;
			case "Cthulhu":
				name = "Hastur";
				break;
			case "Hastur":
				name = "Cthulhu";
				break;
			case "Dagon":
				name = "Bokrug";
				break;
			case "Bokrug":
				name = "Dagon";
				break;
			case "Ithaqua":
				name = "Karakal";
				break;
			case "Nug":
				name = "Yeb";
				break;
			case "Yeb":
				name = "Nug";
				break;
			case "Shudde M'ell":
				name = "Zo-Kalar";
				break;
			case "Nodens":
				name = "Nyarlathotep";
				break;
			case "Noyg'Sothep":
				name = "Alala";
				break;
			case "Shub-Niggurath":
				name = "Ulthar";
				break;
			case "Ubbo-Sathla":
				name = "Amon-Gorloth";
				break;
			case "Ulthar":
				name = "Shub-Niggurath";
				break;
			case "Karakal":
				name = "Ithaqua";
				break;
			case "Zo-Kalar":
				name = "Shudde M'ell";
				break;
			case "Alala":
				name = "Nyog'Sothep";
				break;
			case "Amon-Gorloth":
				name = "Ubbo-Sathla";
				break;
		}
		return name;
	}

	public static String randomGod(){
		String name = "Yegg-Ha";
		switch(Randomer.randomInteger(20)){
			case 0:
				name = "Nyarlathotep";
				break;
			case 1:
				name = "Azathoth";
				break;
			case 2:
				name = "YogDzewa-Sothoth";
				break;
			case 3:
				name = "Cthulhu";
				break;
			case 4:
				name = "Hastur";
				break;
			case 5:
				name = "Dagon";
				break;
			case 6:
				name = "Bokrug";
				break;
			case 7:
				name = "Ithaqua";
				break;
			case 8:
				name = "Nug";
				break;
			case 9:
				name = "Yeb";
				break;
			case 10:
				name = "Shudde M'ell";
				break;
			case 11:
				name = "Nodens";
				break;
			case 12:
				name = "Nyog'Sothep";
				break;
			case 13:
				name = "Shub-Niggurath";
				break;
			case 14:
				name = "Ubbo-Sathla";
				break;
			case 15:
				name = "Ulthar";
				break;
			case 16:
				name = "Karakal";
				break;
			case 17:
				name = "Zo-Kalar";
				break;
			case 18:
				name = "Alala";
				break;
			case 19:
				name = "Amon-Gorloth";
				break;
		}
		if(name == getYourgodsName()){
			name = "Yegg-Ha";
		}
		return name;
	}

	public static String randomBeast(){
		String name = "Tsur'lhn";
		switch(Randomer.randomInteger(20)){
			case 0:
				name = "Turua";
				break;
			case 1:
				name = "the green god";
				break;
			case 2:
				name = "Uitzilcapac";
				break;
			case 3:
				name = "Vibur";
				break;
			case 4:
				name = "Vile-Oct";
				break;
			case 5:
				name = "the king in yellow";
				break;
			case 6:
				name = "Voltiyig";
				break;
			case 7:
				name = "Abholos";
				break;
			case 8:
				name = "Ammutseba";
				break;
			case 9:
				name = "Atlach-Nacha";
				break;
			case 10:
				name = "Aylith";
				break;
			case 11:
				name = "B'gnu-Thun";
				break;
			case 12:
				name = "Bugg-Shash";
				break;
			case 13:
				name = "Chaugnar Faugn";
				break;
			case 14:
				name = "Cthylla";
				break;
			case 15:
				name = "Dhumin";
				break;
			case 16:
				name = "Dygra";
				break;
			case 17:
				name = "Dythalla";
				break;
			case 18:
				name = "Dzéwà";
				break;
			case 19:
				name = "god of the labyrinth";
				break;
		}
		if(name == getYourgodsName()){
			name = "Yegg-Ha";
		}
		return name;
	}
}
