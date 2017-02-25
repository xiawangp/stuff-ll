package com.shatteredpixel.lovecraftpixeldungeon.typedscroll.people;

import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;

public class People {

	public static String randomPerson(){
		String name = "Abdul Alhazred";
		switch(Randomer.randomInteger(18)) {
			case 0:
				name = "Abdul Alhazred";
				break;
			case 1:
				name = "Bran Mak Morn";
				break;
			case 2:
				name = "Remigius";
				break;
			case 3:
				name = "Gauthier de Metz";
				break;
			case 4:
				name = "Heiriarchus";
				break;
			case 5:
				name = "Otto Dostman";
				break;
			case 6:
				name = "Dr. Margaret Alice Murray";
				break;
			case 7:
				name = "Friedrich von Junzt";
				break;
			case 9:
				name = "Barzai the Wise";
				break;
			case 10:
				name = "Geber";
				break;
			case 11:
				name = "Enoch Bowen";
				break;
			case 12:
				name = "Atal";
				break;
			case 13:
				name = "Titus Crow";
				break;
			case 14:
				name = "Gespard Du Nord";
				break;
			case 15:
				name = "E-poh";
				break;
			case 16:
				name = "Eldin the Wanderer";
				break;
			case 17:
				name = "Edward Elric";
				break;
		}
		return name;
	}
}
