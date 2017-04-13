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

package com.shatteredpixel.lovecraftpixeldungeon.typedscroll.playername;

import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;

public class Playername {

	public static String DEADPLAYER_1 = "Bob";
	public static String DEADPLAYER_2 = "Harry";
	public static String DEADPLAYER_3 = "Jules Verne";
	public static String DEADPLAYER_4 = "Stephen HighpriestKlArkashTon";
	public static String DEADPLAYER_5 = "August Darleth";
	public static String DEADPLAYER_6 = "Watabou";
	public static String DEADPLAYER_7 = "TypedScroll";
	public static String DEADPLAYER_8 = "Flora";
	public static String DEADPLAYER_9 = "Leon";
	public static String DEADPLAYER_10 = "Damien";
	public static String DEADPLAYER_11 = "Sarah";
	public static String DEADPLAYER_12 = "Frank Miller";
	public static String DEADPLAYER_13 = "Steve Jobs";
	public static String DEADPLAYER_14 = "Lonly Android";
	public static String DEADPLAYER_15 = "Andy Samberg";
	public static String DEADPLAYER_16 = "Billy";
	public static String DEADPLAYER_17 = "Joe";
	public static String DEADPLAYER_18 = "Randy";
	public static String DEADPLAYER_19 = "Mo";
	public static String DEADPLAYER_20 = "Fat Maxiumus";

	private static String playerName;

	public static String deadPlayers(int number){
		String name = "John Lennon";
		switch(number){
			case 1:
				name = DEADPLAYER_1;
				break;
			case 2:
				name = DEADPLAYER_2;
				break;
			case 3:
				name = DEADPLAYER_3;
				break;
			case 4:
				name = DEADPLAYER_4;
				break;
			case 5:
				name = DEADPLAYER_5;
				break;
			case 6:
				name = DEADPLAYER_6;
				break;
			case 7:
				name = DEADPLAYER_7;
				break;
			case 8:
				name = DEADPLAYER_8;
				break;
			case 9:
				name = DEADPLAYER_9;
				break;
			case 10:
				name = DEADPLAYER_10;
				break;
			case 11:
				name = DEADPLAYER_11;
				break;
			case 12:
				name = DEADPLAYER_12;
				break;
			case 13:
				name = DEADPLAYER_13;
				break;
			case 14:
				name = DEADPLAYER_14;
				break;
			case 15:
				name = DEADPLAYER_15;
				break;
			case 16:
				name = DEADPLAYER_16;
				break;
			case 17:
				name = DEADPLAYER_17;
				break;
			case 18:
				name = DEADPLAYER_18;
				break;
			case 19:
				name = DEADPLAYER_19;
				break;
			case 20:
				name = DEADPLAYER_20;
				break;
		}
		return name;
	};

	public static void generateDEADPLAYERNames(){
		DEADPLAYER_1 = makeDeadPlayerName();
		DEADPLAYER_2 = makeDeadPlayerName();
		DEADPLAYER_3 = makeDeadPlayerName();
		DEADPLAYER_4 = makeDeadPlayerName();
		DEADPLAYER_5 = makeDeadPlayerName();
		DEADPLAYER_6 = makeDeadPlayerName();
		DEADPLAYER_7 = makeDeadPlayerName();
		DEADPLAYER_8 = makeDeadPlayerName();
		DEADPLAYER_9 = makeDeadPlayerName();
		DEADPLAYER_10 = makeDeadPlayerName();
		DEADPLAYER_11 = makeDeadPlayerName();
		DEADPLAYER_12 = makeDeadPlayerName();
		DEADPLAYER_13 = makeDeadPlayerName();
		DEADPLAYER_14 = makeDeadPlayerName();
		DEADPLAYER_15 = makeDeadPlayerName();
		DEADPLAYER_16 = makeDeadPlayerName();
		DEADPLAYER_17 = makeDeadPlayerName();
		DEADPLAYER_18 = makeDeadPlayerName();
		DEADPLAYER_19 = makeDeadPlayerName();
		DEADPLAYER_20 = makeDeadPlayerName();
	};

	public static void makePlayerName(){
		String name = "H. P. Lovecraft";
		String start_sillabylls[] = {
				"a", "ab", "ac", "ad", "ae", "af", "ag", "ah", "ai", "aj", "ak", "al", "am", "an",
				"ao", "ap", "aq", "ar", "as", "at", "au", "av", "aw", "ax", "ay", "az", "ba", "b",
				"be", "bf", "bh", "bi", "bj", "bl", "bo", "br", "bs", "bt", "bu", "by", "bz", "c",
				"ce", "ch", "ci", "cj", "cl", "co", "cr", "cu", "cy", "ca", "d", "de", "dg", "dh",
				"di", "dj", "dl", "do", "dr", "ds", "du", "dz", "da", "e", "ea", "eb", "ec", "ed",
				"ef", "eg", "eh", "ei", "ej", "ek", "el", "em", "en", "eo", "ep", "eq", "er", "es",
				"et", "eu", "ev", "ew", "ex", "ey", "ez", "f", "fa", "fc", "fe", "fi", "fj", "fl",
				"fo", "fp", "fq", "fr", "fs", "ft", "fu", "fy", "g", "ga", "ge", "gh", "gi", "gj",
				"gk", "gl", "gn", "go", "gr", "gs", "gt", "gu", "gv", "gw", "gy", "gz", "h", "he",
				"ha", "hi", "hj", "hl", "hn", "ho", "ht", "hu", "hy", "hz", "i", "ia", "ib", "ic",
				"id", "ie", "if", "ig", "ih", "ij", "ik", "il", "im", "in", "io", "ip", "iq", "ir",
				"is", "it", "iu", "iv", "iw", "ix", "iy", "iz", "j", "ja", "je", "ji", "jn", "jo",
				"jr", "js", "jt", "ju", "jy", "jz", "k", "ka", "ke", "kg", "kh", "ki", "kj", "kl",
				"kn", "ko", "kr", "ks", "kt", "ku", "kv", "kw", "kx", "ky", "kz", "l", "la", "le",
				"lh", "li", "lj", "ln", "lo", "lu", "ly", "lz", "m", "ma", "mc", "me", "mi", "mj",
				"mo", "mu", "my", "n", "na", "ne", "ng", "nh", "ni", "nj", "no", "nu", "ny", "o",
				"oa", "ob", "oc", "oe", "of", "og", "oh", "oi", "oj", "ok", "ol", "om", "on", "op",
				"oq", "or", "os", "ot", "ou", "ov", "ow", "ox", "oy", "oz", "p", "pa", "pe", "ph",
				"pi", "pj", "pl", "pn", "po", "pr", "ps", "pu", "pv", "pw", "py", "q", "qa", "qe",
				"qh", "qi", "qj", "qo", "qu", "qy", "r", "ra", "re", "rh", "ri", "rj", "rl", "ro",
				"ru", "rx", "ry", "s", "sa", "sc", "se", "sg", "sh", "si", "sj", "sk", "sl", "sm",
				"sn", "so", "sp", "sq", "sr", "ss", "st", "su", "sv", "sw", "sx", "sy", "t", "te",
				"th", "ti", "tj", "to", "tr", "ts", "tu", "ty", "tz", "u", "Au", "uc", "ud", "ue",
				"uf", "ug", "uh", "ui", "uj", "uk", "ul", "um", "un", "uo", "up", "uq", "ur", "us",
				"ut", "uv", "uw", "ux", "uy", "uz", "v", "ve", "vi", "vj", "vo", "vu", "vy", "w",
				"we", "wh", "wi", "wo", "wu", "wy", "x", "xe", "xi", "xo", "xu", "xy", "y", "yb",
				"yc", "yd", "ye", "yf", "yg", "yh", "yi", "yj", "yk", "yl", "ym", "yn", "yo", "yp",
				"yq", "yr", "ys", "yt", "yu", "yv", "yw", "yx", "yz", "z", "ze", "zh", "zi", "zo",
				"zs", "zu", "zw", "zy",
		};
		name = "";
		name = name+start_sillabylls[Randomer.randomInteger(396)];
		String end = name.substring(1, name.length());
		switch (name.charAt(0)){
			case 'a':
				name = "A"+end;
				break;
			case 'b':
				name = "B"+end;
				break;
			case 'c':
				name = "C"+end;
				break;
			case 'd':
				name = "D"+end;
				break;
			case 'e':
				name = "E"+end;
				break;
			case 'f':
				name = "F"+end;
				break;
			case 'g':
				name = "G"+end;
				break;
			case 'h':
				name = "H"+end;
				break;
			case 'i':
				name = "I"+end;
				break;
			case 'j':
				name = "J"+end;
				break;
			case 'k':
				name = "K"+end;
				break;
			case 'l':
				name = "L"+end;
				break;
			case 'm':
				name = "M"+end;
				break;
			case 'n':
				name = "N"+end;
				break;
			case 'o':
				name = "O"+end;
				break;
			case 'p':
				name = "P"+end;
				break;
			case 'q':
				name = "Q"+end;
				break;
			case 'r':
				name = "R"+end;
				break;
			case 's':
				name = "S"+end;
				break;
			case 't':
				name = "T"+end;
				break;
			case 'u':
				name = "U"+end;
				break;
			case 'v':
				name = "V"+end;
				break;
			case 'w':
				name = "W"+end;
				break;
			case 'x':
				name = "X"+end;
				break;
			case 'y':
				name = "Y"+end;
				break;
			case 'z':
				name = "Z"+end;
				break;
		}
		for(int i = 4; i > 0; i--){
			int randomer = Randomer.randomInteger(10);
			if(randomer == 2 && !name.endsWith("'") && !name.endsWith("-")){
				name = name+"'";
			} else if(randomer == 5 && !name.endsWith("'") && !name.endsWith("-")){
				name = name+"-";
			} else {
				name = name+start_sillabylls[Randomer.randomInteger(396)];
			}
		}
		if(name.endsWith("-") || name.endsWith("'")){
			int lenght = name.length()-1;
			name = name.substring(0, lenght);
		}
		String postfix[] = {
			"don", "-chan", "-kun", " senpai", "gon", "sky", "man", "",
				"ra", "kov", "er", "ya",
		};
		name = name+postfix[Randomer.randomInteger(12)];
		setPlayerName(name);
	}

	public static String makeDeadPlayerName(){
		String name = "H. P. Lovecraft";
		String start_sillabylls[] = {
				"a", "ab", "ac", "ad", "ae", "af", "ag", "ah", "ai", "aj", "ak", "al", "am", "an",
				"ao", "ap", "aq", "ar", "as", "at", "au", "av", "aw", "ax", "ay", "az", "ba", "b",
				"be", "bf", "bh", "bi", "bj", "bl", "bo", "br", "bs", "bt", "bu", "by", "bz", "c",
				"ce", "ch", "ci", "cj", "cl", "co", "cr", "cu", "cy", "ca", "d", "de", "dg", "dh",
				"di", "dj", "dl", "do", "dr", "ds", "du", "dz", "da", "e", "ea", "eb", "ec", "ed",
				"ef", "eg", "eh", "ei", "ej", "ek", "el", "em", "en", "eo", "ep", "eq", "er", "es",
				"et", "eu", "ev", "ew", "ex", "ey", "ez", "f", "fa", "fc", "fe", "fi", "fj", "fl",
				"fo", "fp", "fq", "fr", "fs", "ft", "fu", "fy", "g", "ga", "ge", "gh", "gi", "gj",
				"gk", "gl", "gn", "go", "gr", "gs", "gt", "gu", "gv", "gw", "gy", "gz", "h", "he",
				"ha", "hi", "hj", "hl", "hn", "ho", "ht", "hu", "hy", "hz", "i", "ia", "ib", "ic",
				"id", "ie", "if", "ig", "ih", "ij", "ik", "il", "im", "in", "io", "ip", "iq", "ir",
				"is", "it", "iu", "iv", "iw", "ix", "iy", "iz", "j", "ja", "je", "ji", "jn", "jo",
				"jr", "js", "jt", "ju", "jy", "jz", "k", "ka", "ke", "kg", "kh", "ki", "kj", "kl",
				"kn", "ko", "kr", "ks", "kt", "ku", "kv", "kw", "kx", "ky", "kz", "l", "la", "le",
				"lh", "li", "lj", "ln", "lo", "lu", "ly", "lz", "m", "ma", "mc", "me", "mi", "mj",
				"mo", "mu", "my", "n", "na", "ne", "ng", "nh", "ni", "nj", "no", "nu", "ny", "o",
				"oa", "ob", "oc", "oe", "of", "og", "oh", "oi", "oj", "ok", "ol", "om", "on", "op",
				"oq", "or", "os", "ot", "ou", "ov", "ow", "ox", "oy", "oz", "p", "pa", "pe", "ph",
				"pi", "pj", "pl", "pn", "po", "pr", "ps", "pu", "pv", "pw", "py", "q", "qa", "qe",
				"qh", "qi", "qj", "qo", "qu", "qy", "r", "ra", "re", "rh", "ri", "rj", "rl", "ro",
				"ru", "rx", "ry", "s", "sa", "sc", "se", "sg", "sh", "si", "sj", "sk", "sl", "sm",
				"sn", "so", "sp", "sq", "sr", "ss", "st", "su", "sv", "sw", "sx", "sy", "t", "te",
				"th", "ti", "tj", "to", "tr", "ts", "tu", "ty", "tz", "u", "Au", "uc", "ud", "ue",
				"uf", "ug", "uh", "ui", "uj", "uk", "ul", "um", "un", "uo", "up", "uq", "ur", "us",
				"ut", "uv", "uw", "ux", "uy", "uz", "v", "ve", "vi", "vj", "vo", "vu", "vy", "w",
				"we", "wh", "wi", "wo", "wu", "wy", "x", "xe", "xi", "xo", "xu", "xy", "y", "yb",
				"yc", "yd", "ye", "yf", "yg", "yh", "yi", "yj", "yk", "yl", "ym", "yn", "yo", "yp",
				"yq", "yr", "ys", "yt", "yu", "yv", "yw", "yx", "yz", "z", "ze", "zh", "zi", "zo",
				"zs", "zu", "zw", "zy",
		};
		name = "";
		name = name+start_sillabylls[Randomer.randomInteger(396)];
		String end = name.substring(1, name.length());
		switch (name.charAt(0)){
			case 'a':
				name = "A"+end;
				break;
			case 'b':
				name = "B"+end;
				break;
			case 'c':
				name = "C"+end;
				break;
			case 'd':
				name = "D"+end;
				break;
			case 'e':
				name = "E"+end;
				break;
			case 'f':
				name = "F"+end;
				break;
			case 'g':
				name = "G"+end;
				break;
			case 'h':
				name = "H"+end;
				break;
			case 'i':
				name = "I"+end;
				break;
			case 'j':
				name = "J"+end;
				break;
			case 'k':
				name = "K"+end;
				break;
			case 'l':
				name = "L"+end;
				break;
			case 'm':
				name = "M"+end;
				break;
			case 'n':
				name = "N"+end;
				break;
			case 'o':
				name = "O"+end;
				break;
			case 'p':
				name = "P"+end;
				break;
			case 'q':
				name = "Q"+end;
				break;
			case 'r':
				name = "R"+end;
				break;
			case 's':
				name = "S"+end;
				break;
			case 't':
				name = "T"+end;
				break;
			case 'u':
				name = "U"+end;
				break;
			case 'v':
				name = "V"+end;
				break;
			case 'w':
				name = "W"+end;
				break;
			case 'x':
				name = "X"+end;
				break;
			case 'y':
				name = "Y"+end;
				break;
			case 'z':
				name = "Z"+end;
				break;
		}
		for(int i = 4; i > 0; i--){
			int randomer = Randomer.randomInteger(10);
			if(randomer == 2 && !name.endsWith("'") && !name.endsWith("-")){
				name = name+"'";
			} else if(randomer == 5 && !name.endsWith("'") && !name.endsWith("-")){
				name = name+"-";
			} else {
				name = name+start_sillabylls[Randomer.randomInteger(396)];
			}
		}
		if(name.endsWith("-") || name.endsWith("'")){
			int lenght = name.length()-1;
			name = name.substring(0, lenght);
		}
		String postfix[] = {
				"don", "-chan", "-kun", " senpai", "gon", "sky", "man", "",
				"ra", "kov", "er", "ya",
		};
		name = name+postfix[Randomer.randomInteger(12)];
		return(name);
	}

	public static String getPlayerName() {
		String name = playerName;
		if(name == null){
			name = makeDeadPlayerName();
		}
		return name;
	}

	public static void setPlayerName(String playerName) {
		Playername.playerName = playerName;
		if(Playername.playerName == null){
			Playername.playerName = makeDeadPlayerName();
		} else {
			Playername.playerName = makeDeadPlayerName();
		}
	}
}
