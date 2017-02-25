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
