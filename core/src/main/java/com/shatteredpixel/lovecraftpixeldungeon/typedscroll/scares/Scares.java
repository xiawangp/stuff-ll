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

package com.shatteredpixel.lovecraftpixeldungeon.typedscroll.scares;

import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;

public class Scares {

	public static String randomScareMessage(Mob mob){
		String scare = "";
		int rand = Randomer.randomInteger(3);
		if(mob.name == "mi-go" || mob.name == "young mi-go"){
			switch(rand){
				case 0:
					scare = mob.name + " is oozing with parasites!";
					break;
				case 1:
					scare = mob.name + " is infested with some type of cordyceps!";
					break;
				case 2:
					scare = mob.name + " starts shrieking in sharp, piercing sounds! ";
					break;
				default:
					scare = mob.name + " scares the shit out of you!";
					break;
			}
		}
		if(mob.name == "poor thief"){
			switch(rand){
				case 0:
					scare = mob.name + " is oozing with parasites!";
					break;
				case 1:
					scare = mob.name + " is infested with some type of cordyceps!";
					break;
				case 2:
					scare = mob.name + " shows of some wiggling warts! ";
					break;
				default:
					scare = mob.name + " scares the shit out of you!";
					break;
			}
		}
		if(mob.name == "acidic rhan-tegoth" || mob.name == "rhan-tegoth"){
			switch(rand){
				case 0:
					scare = mob.name + " has grown a new eye!";
					break;
				case 1:
					scare = mob.name + " is infested with some type of cordyceps!";
					break;
				case 2:
					scare = mob.name + " is screaming in the voice of your dead mother!";
					break;
				default:
					scare = mob.name + " scares the shit out of you!";
					break;
			}
		}
		if(mob.name == "mad bandit" || mob.name == "mad thief"){
			switch(rand){
				case 0:
					scare = mob.name + " shows of some wiggling warts!";
					break;
				case 1:
					scare = mob.name + " is infested with some type of cordyceps!";
					break;
				case 2:
					scare = mob.name + " has a slithering worm in his nostrils!";
					break;
				default:
					scare = mob.name + " scares the shit out of you!";
					break;
			}
		}
		if(mob.name == "spawn of Mormo"){
			switch(rand){
				case 0:
					scare = mob.name + "'s face is changing rapidly!";
					break;
				case 1:
					scare = mob.name + " is infested with some type of cordyceps!";
					break;
				case 2:
					scare = mob.name + " is moving way to fast! "+mob.name+ " is everywhere!";
					break;
				default:
					scare = mob.name + " scares the shit out of you!";
					break;
			}
		}
		if(mob.name == "spawn of Zstylzhemghi"){
			switch(rand){
				case 0:
					scare = mob.name + " is growing gluey sticks out of its body!";
					break;
				case 1:
					scare = mob.name + " is infested with some type of cordyceps!";
					break;
				case 2:
					scare = mob.name + " is moving way to fast! "+mob.name+ " is everywhere!";
					break;
				default:
					scare = mob.name + " scares the shit out of you!";
					break;
			}
		}
		if(mob.name.contains("occultist")){
			switch(rand){
				case 0:
					scare = mob.name + " shows off some malformed body parts";
					break;
				case 1:
					scare = mob.name + " is infested with some type of cordyceps!";
					break;
				case 2:
					scare = mob.name + " has alien worms naturally inhabiting its skin!";
					break;
				default:
					scare = mob.name + " scares the shit out of you!";
					break;
			}
		}
		if(mob.name == "spawn of Janai'ngo"){
			switch(rand){
				case 0:
					scare = mob.name + " is powered by farts! Gyo! Gyo!";
					break;
				case 1:
					scare = mob.name + " is infested with some type of cordyceps!";
					break;
				case 2:
					scare = mob.name + " is constantly singing in strange clicking sounds!";
					break;
				default:
					scare = mob.name + " scares the shit out of you!";
					break;
			}
		}
		if(mob.name.contains("Eihort")){
			switch(rand){
				case 0:
					scare = mob.name + " is constantly shifting its soft body!";
					break;
				case 1:
					scare = mob.name + " faces of long dead freinds are mirroring in its black eyes!";
					break;
				case 2:
					scare = mob.name + " is eating its onw eggs!";
					break;
				default:
					scare = mob.name + " scares the shit out of you!";
					break;
			}
		}
		if(mob.name.contains("Cthugha")){
			switch(rand){
				case 0:
					scare = mob.name + " is living fire! It's living dammit!";
					break;
				case 1:
					scare = mob.name + " morphs it's flames into your facial features";
					break;
				case 2:
					scare = mob.name + "'s flames are whispering...";
					break;
				default:
					scare = mob.name + " scares the shit out of you!";
					break;
			}
		}
		if(mob.name == "spawn of Ubbo-Sathla"){
			switch(rand){
				case 0:
					scare = mob.name + "'s mouth seems to lead into a star filled void!";
					break;
				case 1:
					scare = mob.name + " has organic impossible features!";
					break;
				case 2:
					scare = mob.name + " has fine hair on its body waving in tact.";
					break;
				default:
					scare = mob.name + " scares the shit out of you!";
					break;
			}
		}
		if(mob.name == "the green god"){
			switch(rand){
				case 0:
					scare = mob.name + " gives you visions of the end of the universe!";
					break;
				case 1:
					scare = mob.name + " is sprouting plants all over its body!";
					break;
				case 2:
					scare = mob.name + " looks like goat! Iä! Iä!";
					break;
				default:
					scare = mob.name + " scares the shit out of you!";
					break;
			}
		}
		if(mob.name == "the king in yellow"){
			switch(rand){
				case 0:
					scare = mob.name + " tells you about the philadelphia-project!";
					break;
				case 1:
					scare = mob.name + " shows you a short glimpse of its true form!";
					break;
				case 2:
					scare = mob.name + " is speaking in your head!";
					break;
				default:
					scare = mob.name + " scares the shit out of you!";
					break;
			}
		}
		if(mob.name == "golem"){
			switch(rand){
				case 0:
					scare = mob.name + " shows you it's stolen rotten organs!";
					break;
				case 1:
					scare = mob.name + " grows some more limbs!";
					break;
				case 2:
					scare = mob.name + "'s eye is looking directly at you!";
					break;
				default:
					scare = mob.name + " scares the shit out of you!";
					break;
			}
		}
		if(mob.name == "Shoggoth"){
			switch(rand){
				case 0:
					scare = "You can see its organs transform through its 'skin'.";
					break;
				case 1:
					scare = mob.name + " looks like living jelly!";
					break;
				case 2:
					scare = mob.name + " grows a mouth to tell you it wants to eat you!";
					break;
				default:
					scare = mob.name + " scares the shit out of you!";
					break;
			}
		}
		if(mob.name == "Turua"){
			switch(rand){
				case 0:
					scare = "The horror! The horror!";
					break;
				case 1:
					scare = "You cant make out "+mob.name+"'s eyes...";
					break;
				case 2:
					scare = mob.name + " screams earth shattering high-pitched!";
					break;
				default:
					scare = mob.name + " scares the shit out of you!";
					break;
			}
		}
		if(mob.name == "infested prisoner"){
			switch(rand){
				case 0:
					scare = "You see the spawns of Eihort eat its way out of the "+mob.name+"!";
					break;
				case 1:
					scare = mob.name + " looses a limb!";
					break;
				case 2:
					scare = mob.name + " whispers: 'Help me! Kill me!'";
					break;
				default:
					scare = mob.name + " scares the shit out of you!";
					break;
			}
		}
		if(mob.name == "Ithaqua"){
			switch(rand){
				case 0:
					scare = "Its warm, isn't it?!";
					break;
				case 1:
					scare = mob.name + " gives you visions of man eating man!";
					break;
				case 2:
					scare = mob.name + " seems to move in time. No really!";
					break;
				default:
					scare = mob.name + " scares the shit out of you!";
					break;
			}
		}
		if(mob.name == "gate to Nyaghoggua"){
			switch(rand){
				case 0:
					scare = "Its alive! Its alive!";
					break;
				case 1:
					scare = mob.name + " opens it mouth and shows you hyperspace!";
					break;
				case 2:
					scare = mob.name + " has the whole universe in its mouth!";
					break;
				default:
					scare = mob.name + " scares the shit out of you!";
					break;
			}
		}
		if(mob.name == "offspring of Vthyarilops"){
			switch(rand){
				case 0:
					scare = mob.name + " are blind and yet they see every movement of yours!";
					break;
				case 1:
					scare = mob.name + " has two mouths and two testicles!";
					break;
				case 2:
					scare = mob.name + " is flopping around like crazy!";
					break;
				default:
					scare = mob.name + " scares the shit out of you!";
					break;
			}
		}
		return scare;
	}
}
