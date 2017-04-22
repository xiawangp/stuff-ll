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
package com.shatteredpixel.lovecraftpixeldungeon.scenes;

import com.shatteredpixel.lovecraftpixeldungeon.Chrome;
import com.shatteredpixel.lovecraftpixeldungeon.LovecraftPixelDungeon;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.ui.Archs;
import com.shatteredpixel.lovecraftpixeldungeon.ui.ExitButton;
import com.shatteredpixel.lovecraftpixeldungeon.ui.RenderedTextMultiline;
import com.shatteredpixel.lovecraftpixeldungeon.ui.ScrollPane;
import com.shatteredpixel.lovecraftpixeldungeon.ui.Window;
import com.watabou.noosa.Camera;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.ui.Component;

//TODO: update this class with relevant info as new versions come out.
public class ChangesScene extends PixelScene {

	private static final String TXT_Update =
					"_Initial Update I.I.I_ \n\n " +
					"_-_ Player is nameable \n " +
					"_-_ new Items: \n " +
					"\t\t _-_ Mushrooms \n " +
					"\t\t _-_ Ores & Stick \n " +
					"\t\tnew Ring of Mining \n " +
					"_-_ new Enchantments \n " +
					"\t\t _-_ Confuse \n " +
					"\t\t _-_ Corrode \n " +
					"\t\t _-_ Corrupting \n " +
					"\t\t _-_ Maddening \n " +
					"\t\t _-_ Dew Withdrawing \n " +
					"\t\t _-_ Infectious \n " +
					"\t\t _-_ Gas \n " +
					"\t\t _-_ Lulling \n " +
					"\t\t _-_ Mirroring \n " +
					"\t\t _-_ Rooting \n " +
					"_-_ new Mobs \n " +
					"\t\t _-_ Mi Go Larva \n " +
					"\t\t _-_ Young Mi Go \n " +
					"\t\t _-_ Mi Go \n " +
					"\t\t _-_ Mi Go Nurse \n " +
					"\t\t _-_ Mi Go Queen \n " +
					"\t\t _-_ Snake God Yig \n " +
					"\t\t _-_ Kagebunshin \n " +
					"\t\t _-_ Parasit \n " +
					"_-_ Shop on Depth 1 \n " +
					"_-_ Start with _1200G_ pocket money \n " +
					"_-_ new Rooms \n " +
					"\t\t _-_ Level Boss Rooms \n " +
					"\t\t _-_ Altar Rooms \n " +
					"_-_ Crafting System \n " +
					"_-_ Enchanting System \n " +
					"_-_ Mental Health \n " +
					"\t\t _-_ You dont die but go crazy and inflict self harm \n " +
					"_-_ new Buffs \n " +
					"\t\t _-_ Infection \n " +
					"_-_ Dewvial can be used to upgrade \n\n\n" +

					"_Plant Update I.I.II_ \n\n " +
					"_-_ Name Generator is reworked: \n" +
					"_-_ Dewvial new fill up graphics \n" +
					"_-_ Gold has new Sprite according to amount\n" +
					"_-_ Ankh and blessed Ankh have different sprites\n" +
					"\t\t _-_ Roman, Elf, Goblin and Fantasy Names \n" +
					"_-_ new Mobs:\n" +
					"\t\t _-_ Living Plants\n" +
					"\t\t _-_ Shoggoths\n" +
					"\t\t _-_ Elements\n" +
					"\t\t _-_ Mi Go King\n" +
					"\t\t _-_ Mi Go Warrior\n" +
					"\t\t _-_ Alb\n" +
					"\t\t _-_ Facegugger\n" +
					"\t\t _-_ Ithaqua\n" +
					"\t\t _-_ Kek\n" +
					"_-_ new Items:\n" +
					"\t\t _-_ Ancient Pot\n" +
					"\t\t _-_ Waterbag\n" +
					"\t\t _-_ GooElement\n" +
					"\t\t _-_ Mi Go Egg\n" +
					"\t\t _-_ Statue of Pepe\n" +
					"\t\t _-_ Potion of Waterwave\n" +
					"\t\t _-_ Scroll of Equivalentexchange\n" +
					"\t\t _-_ Scroll of Sunlight\n" +
					"\t\t _-_ Scroll of Summoning\n" +
					"\t\t _-_ Scroll of Thunderstorm\n" +
					"_-_ new Blobs:\n" +
					"\t\t _-_ Icewind\n" +
					"\t\t _-_ Corruption Gas\n" +
					"\t\t _-_ Teleport Gas\n" +
					"_-_ Level 2 added \n\n\n" +

					"_Update I.I.III_ \n\n " +
					"_-_ New _Inventoryslot_ for hats \n" +
					"\t\t _-_ MiGo Cap \n" +
					"\t\t _-_ Toothfarie Cap \n" +
					"\t\t _-_ Shoggoth Cap \n" +
					"_-_ New Mobs:\n" +
					"\t\t _-_ Elements \n" +
					"\t\t _-_ Shoggoths \n" +
					"\t\t _-_ Shoggoth Birthchamber \n" +
					"\t\t _-_ Toothfaries \n" +
					"\t\t _-_ Mongolian Sandworms \n" +
					"_-_ New Items:\n" +
					"\t\t _-_ Baby Thooth \n" +
					"\t\t _-_ Hard Slime \n" +
					"\t\t _-_ MiGo Scales \n" +
					"_-_ Loading Quotes \n" +
					"_-_ Level 5 Boss Fight added\n\n\n";

	@Override
	public void create() {
		super.create();

		int w = Camera.main.width;
		int h = Camera.main.height;

		RenderedText title = PixelScene.renderText( Messages.get(this, "title"), 9 );
		title.hardlight(Window.TITLE_COLOR);
		title.x = (w - title.width()) / 2 ;
		title.y = 4;
		align(title);
		add(title);

		ExitButton btnExit = new ExitButton();
		btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
		add( btnExit );

		RenderedTextMultiline text = renderMultiline(TXT_Update, 6 );

		NinePatch panel = Chrome.get(Chrome.Type.TOAST);

		int pw = 135 + panel.marginLeft() + panel.marginRight() - 2;
		int ph = h - 16;

		panel.size( pw, ph );
		panel.x = (w - pw) / 2f;
		panel.y = title.y + title.height();
		align( panel );
		add( panel );

		ScrollPane list = new ScrollPane( new Component() );
		add( list );

		Component content = list.content();
		content.clear();

		text.maxWidth((int) panel.innerWidth());

		content.add(text);

		content.setSize( panel.innerWidth(), (int)Math.ceil(text.height()) );

		list.setRect(
				panel.x + panel.marginLeft(),
				panel.y + panel.marginTop() - 1,
				panel.innerWidth(),
				panel.innerHeight() + 2);
		list.scrollTo(0, 0);

		Archs archs = new Archs();
		archs.setSize( Camera.main.width, Camera.main.height );
		addToBack( archs );

		fadeIn();
	}

	@Override
	protected void onBackPressed() {
		LovecraftPixelDungeon.switchNoFade(TitleScene.class);
	}
}


