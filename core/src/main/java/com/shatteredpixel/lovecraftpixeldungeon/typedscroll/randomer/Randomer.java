package com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Randomer {

		public static int randomInteger(int bound){
			Random r = new Random();
			int outcome = r.nextInt(bound);
			return outcome;
		}

		public static float randomFloat(float bound){
			Random r = new Random();
			float outcome = r.nextFloat();
			return outcome;
		}

		public static double randomDouble(double bound){
			Random r = new Random();
			double outcome = r.nextDouble();
			return outcome;
		}

		public static boolean randomBoolean(){
			Random r = new Random();
			boolean outcome = r.nextBoolean();
			return outcome;
		}

		public static int randomMinAndMaxInteger(int min, int max){
			Random r = new Random();
			int maxInt = r.nextInt(max);
			int minInt = min;
			int outcome = 0;
			if(maxInt < minInt){
				outcome = maxInt+minInt;
			} else {
				outcome = maxInt;
			}
			return outcome;
		}

		public static float randomMinAndMaxFloat(float min, float max){
			Random r = new Random();
			float maxInt = r.nextFloat();
			float minInt = min;
			float outcome = 0;
			if(maxInt < minInt){
				outcome = maxInt+minInt;
			} else {
				outcome = maxInt;
			}
			return outcome;
		}

		public static double randomMinAndMaxDouble(double min, double max){
			Random r = new Random();
			double maxInt = r.nextDouble();
			double minInt = min;
			double outcome = 0;
			if(maxInt < minInt){
				outcome = maxInt+minInt;
			} else {
				outcome = maxInt;
			}
			return outcome;
		}

		public static String randomScrambledString(String string) {
			String name;
			String username = "";
			name = string;
			List<Character> chars = new ArrayList<Character>();
			for(int n = name.length(); n > 0; n--){
				chars.add(name.charAt(n-1));
			}
			List<Character> userchars = new ArrayList<Character>();
			for(int u = chars.size(); u > 0; u--){
				userchars.add(chars.get(new Random().nextInt(chars.size())));
			}
			for(int f = userchars.size(); f > 0; f--){
				username = username + userchars.get(f-1);
			}
			return username;
		}

	}
