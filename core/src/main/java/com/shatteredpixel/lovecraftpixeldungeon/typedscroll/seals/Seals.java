package com.shatteredpixel.lovecraftpixeldungeon.typedscroll.seals;

import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.gods.Gods;
import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;

public class Seals {

	public static String randomSeal(){
		String name = "sigyll of "+ Gods.getYourGodsEnemyName();
		switch(Randomer.randomInteger(9)) {
			case 0:
				name = "the elder sign";
				break;
			case 1:
				name = "the black seal";
				break;
			case 2:
				name = "yellow sign";
				break;
			case 3:
				name = "the r'lyeh seal";
				break;
			case 4:
				name = "zkauba's seal";
				break;
			case 5:
				name = "of the silver key gates";
				break;
			case 6:
				name = "the dreamlands seal";
				break;
			case 7:
				name = "the seven geases";
				break;
		}
		return name;
	}
}
