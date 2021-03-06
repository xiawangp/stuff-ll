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

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This class is released under GNU general public license
 *
 * Description: This class generates random names from syllables, and provides programmer a
 * simple way to set a group of rules for generator to avoid unpronounceable and bizarre names.
 *
 * SYLLABLE FILE REQUIREMENTS/FORMAT:
 * 1) all syllables are separated by line break.
 * 2) Syllable should not contain or start with whitespace, as this character is ignored and only first part of the syllable is read.
 * 3) + and - characters are used to set rules, and using them in other way, may result in unpredictable results.
 * 4) Empty lines are ignored.
 *
 * SYLLABLE CLASSIFICATION:
 * Name is usually composed from 3 different class of syllables, which include prefix, middle part and suffix.
 * To declare syllable as a prefix in the file, insert "-" as a first character of the line.
 * To declare syllable as a suffix in the file, insert "+" as a first character of the line.
 * everything else is read as a middle part.
 *
 * NUMBER OF SYLLABLES:
 * Names may have any positive number of syllables. In case of 2 syllables, name will be composed from prefix and suffix.
 * In case of 1 syllable, name will be chosen from amongst the prefixes.
 * In case of 3 and more syllables, name will begin with prefix, is filled with middle parts and ended with suffix.
 *
 * ASSIGNING RULES:
 * I included a way to set 4 kind of rules for every syllable. To add rules to the syllables, write them right after the
 * syllable and SEPARATE WITH WHITESPACE. (example: "aad +v -c"). The order of rules is not important.
 *
 * RULES:
 * 1) +v means that next syllable must definitely start with a Vowel.
 * 2) +c means that next syllable must definitely start with a consonant.
 * 3) -v means that this syllable can only be added to another syllable, that ends with a Vowel.
 * 4) -c means that this syllable can only be added to another syllable, that ends with a consonant.
 * So, our example: "aad +v -c" means that "aad" can only be after consonant and next syllable must start with Vowel.
 * Beware of creating logical mistakes, like providing only syllables ending with consonants, but expecting only Vowels, which will be detected
 * and RuntimeException will be thrown.
 *
 * TO START:
 * Create a new NameGenerator object, provide the syllable file, and create names using compose() method.
 *
 *
 */
public class NameGenerator {
    ArrayList<String> pre = new ArrayList<String>();
    ArrayList<String> mid = new ArrayList<String>();
    ArrayList<String> sur = new ArrayList<String>();

    final private static char[] Vowels = {'a', 'e', 'i', 'o', 'u', 'ä', 'ö', 'õ', 'ü', 'y'};
    final private static char[] consonants = {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p',    'q', 'r', 's', 't', 'v', 'w', 'x', 'y'};

    private String fileName;


    public NameGenerator(String filename, Context context) throws IOException{
        fileName = filename;
        refresh(context);
    }

    public void changeFile(String filename, Context context) throws IOException{
        if(fileName == null) throw new IOException("File name cannot be null");
        fileName = filename;
        refresh(context);
    }

    /**
     * Refresh names from file. No need to call that method, if you are not changing the file during the operation of program, as this method
     * is called every time file name is changed or new NameGenerator object created.
     * @throws IOException
     */
    public void refresh(Context context) throws IOException{

        InputStream fIn;
        InputStreamReader isr;
        BufferedReader bufRead;
        String line;

        fIn = context.getAssets().open(fileName, Context.MODE_WORLD_READABLE);
        isr = new InputStreamReader(fIn);
        bufRead = new BufferedReader(isr);
        line="";

        while(line != null){
            line = bufRead.readLine();
            if(line != null && !line.equals("")){
                if(line.charAt(0) == '-'){
                    pre.add(line.substring(1).toLowerCase());
                }
                else if(line.charAt(0) == '+'){
                    sur.add(line.substring(1).toLowerCase());
                }
                else{
                    mid.add(line.toLowerCase());
                }
            }
        }
        bufRead.close();
        isr.close();
        fIn.close();
    }

    private String upper(String s){
        return s.substring(0,1).toUpperCase().concat(s.substring(1));
    }

    private boolean containsConsFirst(ArrayList<String> array){
        for(String s: array){
            if(consonantFirst(s)) return true;
        }
        return false;
    }

    private boolean containsVocFirst(ArrayList<String> array){
        for(String s: array){
            if(VowelFirst(s)) return true;
        }
        return false;
    }

    private boolean allowCons(ArrayList<String> array){
        for(String s: array){
            if(hatesPreviousVowels(s) || hatesPreviousConsonants(s) == false) return true;
        }
        return false;
    }

    private boolean allowVocs(ArrayList<String> array){
        for(String s: array){
            if(hatesPreviousConsonants(s) || hatesPreviousVowels(s) == false) return true;
        }
        return false;
    }

    private boolean expectsVowel(String s){
        if(s.substring(1).contains("+v")) return true;
        else return false;
    }
    private boolean expectsConsonant(String s){
        if(s.substring(1).contains("+c")) return true;
        else return false;
    }
    private boolean hatesPreviousVowels(String s){
        if(s.substring(1).contains("-c")) return true;
        else return false;
    }
    private boolean hatesPreviousConsonants(String s){
        if(s.substring(1).contains("-v")) return true;
        else return false;
    }

    private String pureSyl(String s){
        s = s.trim();
        if(s.charAt(0) == '+' || s.charAt(0) == '-') s = s.substring(1);
        return s.split(" ")[0];
    }

    private boolean VowelFirst(String s){
        return (String.copyValueOf(Vowels).contains(String.valueOf(s.charAt(0)).toLowerCase()));
    }

    private boolean consonantFirst(String s){
        return (String.copyValueOf(consonants).contains(String.valueOf(s.charAt(0)).toLowerCase()));
    }

    private boolean VowelLast(String s){
        return (String.copyValueOf(Vowels).contains(String.valueOf(s.charAt(s.length()-1)).toLowerCase()));
    }

    private boolean consonantLast(String s){
        return (String.copyValueOf(consonants).contains(String.valueOf(s.charAt(s.length()-1)).toLowerCase()));
    }


    /**
     * Compose a new name.
     * @param syls The number of syllables used in name.
     * @return Returns composed name as a String
     * @throws RuntimeException when logical mistakes are detected inside chosen file, and program is unable to complete the name.
     */
    public String compose(int syls){
        if(syls > 2 && mid.size() == 0) throw new RuntimeException("You are trying to create a name with more than 3 parts, which requires middle parts, " +
                "which you have none in the file "+fileName+". You should add some. Every word, which doesn't have + or - for a prefix is counted as a middle part.");
        if(pre.size() == 0) throw new RuntimeException("You have no prefixes to start creating a name. add some and use \"-\" prefix, to identify it as a prefix for a name. (example: -asd)");
        if(sur.size() == 0) throw new RuntimeException("You have no suffixes to end a name. add some and use \"+\" prefix, to identify it as a suffix for a name. (example: +asd)");
        if(syls < 1) throw new RuntimeException("compose(int syls) can't have less than 1 syllable");
        int expecting = 0; // 1 for Vowel, 2 for consonant
        int last = 0; // 1 for Vowel, 2 for consonant
        String name;
        int a = (int)(Math.random() * pre.size());

        if(VowelLast(pureSyl(pre.get(a)))) last = 1;
        else last = 2;

        if(syls > 2){
            if(expectsVowel(pre.get(a))){
                expecting = 1;
                if(containsVocFirst(mid) == false) throw new RuntimeException("Expecting \"middle\" part starting with Vowel, " +
                        "but there is none. You should add one, or remove requirement for one.. ");
            }
            if(expectsConsonant(pre.get(a))){
                expecting = 2;
                if(containsConsFirst(mid) == false) throw new RuntimeException("Expecting \"middle\" part starting with consonant, " +
                        "but there is none. You should add one, or remove requirement for one.. ");
            }
        }
        else{
            if(expectsVowel(pre.get(a))){
                expecting = 1;
                if(containsVocFirst(sur) == false) throw new RuntimeException("Expecting \"suffix\" part starting with Vowel, " +
                        "but there is none. You should add one, or remove requirement for one.. ");
            }
            if(expectsConsonant(pre.get(a))){
                expecting = 2;
                if(containsConsFirst(sur) == false) throw new RuntimeException("Expecting \"suffix\" part starting with consonant, " +
                        "but there is none. You should add one, or remove requirement for one.. ");
            }
        }
        if(VowelLast(pureSyl(pre.get(a))) && allowVocs(mid) == false) throw new RuntimeException("Expecting \"middle\" part that allows last character of prefix to be a Vowel, " +
                "but there is none. You should add one, or remove requirements that cannot be fulfilled.. the prefix used, was : \""+pre.get(a)+"\", which" +
                "means there should be a part available, that has \"-v\" requirement or no requirements for previous syllables at all.");

        if(consonantLast(pureSyl(pre.get(a))) && allowCons(mid) == false) throw new RuntimeException("Expecting \"middle\" part that allows last character of prefix to be a consonant, " +
                "but there is none. You should add one, or remove requirements that cannot be fulfilled.. the prefix used, was : \""+pre.get(a)+"\", which" +
                "means there should be a part available, that has \"-c\" requirement or no requirements for previous syllables at all.");

        int b[] = new int[syls];
        for(int i = 0; i<b.length-2; i++){

            do{
                b[i] = (int)(Math.random() * mid.size());
            }
            while(expecting == 1 && VowelFirst(pureSyl(mid.get(b[i]))) == false || expecting == 2 && consonantFirst(pureSyl(mid.get(b[i]))) == false
                    || last == 1 && hatesPreviousVowels(mid.get(b[i])) || last == 2 && hatesPreviousConsonants(mid.get(b[i])));

            expecting = 0;
            if(expectsVowel(mid.get(b[i]))){
                expecting = 1;
                if(i < b.length-3 && containsVocFirst(mid) == false) throw new RuntimeException("Expecting \"middle\" part starting with Vowel, " +
                        "but there is none. You should add one, or remove requirement for one.. ");
                if(i == b.length-3 && containsVocFirst(sur) == false) throw new RuntimeException("Expecting \"suffix\" part starting with Vowel, " +
                        "but there is none. You should add one, or remove requirement for one.. ");
            }
            if(expectsConsonant(mid.get(b[i]))){
                expecting = 2;
                if(i < b.length-3 && containsConsFirst(mid) == false) throw new RuntimeException("Expecting \"middle\" part starting with consonant, " +
                        "but there is none. You should add one, or remove requirement for one.. ");
                if(i == b.length-3 && containsConsFirst(sur) == false) throw new RuntimeException("Expecting \"suffix\" part starting with consonant, " +
                        "but there is none. You should add one, or remove requirement for one.. ");
            }
            if(VowelLast(pureSyl(mid.get(b[i]))) && allowVocs(mid) == false && syls > 3) throw new RuntimeException("Expecting \"middle\" part that allows last character of last syllable to be a Vowel, " +
                    "but there is none. You should add one, or remove requirements that cannot be fulfilled.. the part used, was : \""+mid.get(b[i])+"\", which " +
                    "means there should be a part available, that has \"-v\" requirement or no requirements for previous syllables at all.");

            if(consonantLast(pureSyl(mid.get(b[i]))) && allowCons(mid) == false && syls > 3) throw new RuntimeException("Expecting \"middle\" part that allows last character of last syllable to be a consonant, " +
                    "but there is none. You should add one, or remove requirements that cannot be fulfilled.. the part used, was : \""+mid.get(b[i])+"\", which " +
                    "means there should be a part available, that has \"-c\" requirement or no requirements for previous syllables at all.");
            if(i == b.length-3){
                if(VowelLast(pureSyl(mid.get(b[i]))) && allowVocs(sur) == false) throw new RuntimeException("Expecting \"suffix\" part that allows last character of last syllable to be a Vowel, " +
                        "but there is none. You should add one, or remove requirements that cannot be fulfilled.. the part used, was : \""+mid.get(b[i])+"\", which " +
                        "means there should be a suffix available, that has \"-v\" requirement or no requirements for previous syllables at all.");

                if(consonantLast(pureSyl(mid.get(b[i]))) && allowCons(sur) == false) throw new RuntimeException("Expecting \"suffix\" part that allows last character of last syllable to be a consonant, " +
                        "but there is none. You should add one, or remove requirements that cannot be fulfilled.. the part used, was : \""+mid.get(b[i])+"\", which " +
                        "means there should be a suffix available, that has \"-c\" requirement or no requirements for previous syllables at all.");
            }
            if(VowelLast(pureSyl(mid.get(b[i])))) last = 1;
            else last = 2;
        }

        int c;
        do{
            c = (int)(Math.random() * sur.size());
        }
        while(expecting == 1 && VowelFirst(pureSyl(sur.get(c))) == false || expecting == 2 && consonantFirst(pureSyl(sur.get(c))) == false
                || last == 1 && hatesPreviousVowels(sur.get(c)) || last == 2 && hatesPreviousConsonants(sur.get(c)));

        name = upper(pureSyl(pre.get(a).toLowerCase()));
        for(int i = 0; i<b.length-2; i++){
            name = name.concat(pureSyl(mid.get(b[i]).toLowerCase()));
        }
        if(syls > 1)
            name = name.concat(pureSyl(sur.get(c).toLowerCase()));
        return name;
    }
}
