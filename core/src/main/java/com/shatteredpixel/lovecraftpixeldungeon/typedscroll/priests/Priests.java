package com.shatteredpixel.lovecraftpixeldungeon.typedscroll.priests;

import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;

public class Priests {

	public static String randomPriest(){
		String name = "Monk Lobon The First";
		switch(Randomer.randomInteger(10)){
			case 0:
				name = "Monk Lobon II";
				break;
			case 1:
				name = "Monk Lobon XVI";
				break;
			case 2:
				name = "Priest Tamash The Elder";
				break;
			case 3:
				name = "Priest Tamash The Younger";
				break;
			case 4:
				name = "Highpriest Oukranos";
				break;
			case 5:
				name = "Cultist Ryonis";
				break;
			case 6:
				name = "Juntz";
				break;
			case 7:
				name = "Godpriest Vorvadoss";
				break;
			case 8:
				name = "God Ulthar The Wise";
				break;
			case 9:
				name = "Forgotten One Savty'ya";
				break;
		}
		return name;
	}
}
