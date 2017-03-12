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

package com.shatteredpixel.lovecraftpixeldungeon.typedscroll.gender;


import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;

public class Gender {

	private static String genderHeShe = "it";

	public static String getGenderHeShe() {
		return genderHeShe;
	}

	public static void setGenderHeShe(String gender) {
		Gender.genderHeShe = gender;
	}

	private static String genderHisHer = "it's";

	public static String getGenderHisHer(String him) {
		String gender = genderHisHer;
		if(him.equals("his")){
			gender = "him";
		}
		return gender;
	}

	public static String getCapGender(String gendersmall){
		String gen = gendersmall;
		String capname = "?";
		if(gen == "her"){
			capname = "Her";
		} else if(gen == "his"){
			capname = "his";
		} else if(gen == "he"){
			capname = "He";
		} else if(gen == "she"){
			capname = "She";
		}
		return capname;
	}

	public static void setGenderHisHer(String gender) {
		Gender.genderHisHer = gender;
	}

	public static void makeGender(){
		int rand = Randomer.randomInteger(2);
		if(rand == 0){
			setGenderHeShe("he");
			setGenderHisHer("his");
		} else {
			setGenderHeShe("she");
			setGenderHisHer("her");
		}
	}
}
