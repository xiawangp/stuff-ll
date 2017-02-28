/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2016 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.shatteredpixel.lovecraftpixeldungeon.windows;

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.items.Ankh;
import com.shatteredpixel.lovecraftpixeldungeon.items.Bomb;
import com.shatteredpixel.lovecraftpixeldungeon.items.Generator;
import com.shatteredpixel.lovecraftpixeldungeon.items.Gold;
import com.shatteredpixel.lovecraftpixeldungeon.items.Honeypot;
import com.shatteredpixel.lovecraftpixeldungeon.items.Item;
import com.shatteredpixel.lovecraftpixeldungeon.items.Stick;
import com.shatteredpixel.lovecraftpixeldungeon.items.ores.Adamantium;
import com.shatteredpixel.lovecraftpixeldungeon.items.ores.Cobalt;
import com.shatteredpixel.lovecraftpixeldungeon.items.ores.Copper;
import com.shatteredpixel.lovecraftpixeldungeon.items.ores.DarkGold;
import com.shatteredpixel.lovecraftpixeldungeon.items.ores.Iron;
import com.shatteredpixel.lovecraftpixeldungeon.items.ores.Uranium;
import com.shatteredpixel.lovecraftpixeldungeon.items.quest.Pickaxe;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Dagger;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Dirk;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Glaive;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Greataxe;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Greatshield;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Greatsword;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.HandAxe;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Knuckles;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Longsword;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Mace;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Quarterstaff;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.RoundShield;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.RunicBlade;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Sai;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Scimitar;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Spear;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.WarHammer;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.WornShortsword;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.missiles.Boomerang;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.missiles.Tamahawk;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.lovecraftpixeldungeon.ui.ItemSlot;
import com.shatteredpixel.lovecraftpixeldungeon.ui.QuickSlotButton;
import com.shatteredpixel.lovecraftpixeldungeon.ui.RedButton;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

public class WndCraft extends WndTabbed {

	protected static final int COLS_P    = 3;
	protected static final int COLS_L    = 3;

	protected static final int SLOT_SIZE	= 28;
	protected static final int SLOT_MARGIN	= 1;

	protected static final int TITLE_HEIGHT	= 12;

	private static ArrayList<Item> items = new ArrayList<Item>();

	private static ArrayList<Character> itemsChar = new ArrayList<Character>();

	private Listener listener;
	private String title;

	private int nCols;
	private int nRows;

	protected int count;
	protected int col;
	protected int row;

	public WndCraft() {

		super();

		this.title = Messages.get(this, "wndtitle");

		nCols = COLS_L;
		nRows = COLS_P;

		int slotsWidth = SLOT_SIZE * nCols + SLOT_MARGIN * (nCols - 1);
		int slotsHeight = SLOT_SIZE * nRows + SLOT_MARGIN * (nRows - 1);

		RenderedText txtTitle = PixelScene.renderText( title != null ? title : Messages.get(this, "wndtitle"), 9 );
		txtTitle.hardlight( TITLE_COLOR );
		txtTitle.x = (int)(slotsWidth - txtTitle.width()) / 2;
		txtTitle.y = (int)(TITLE_HEIGHT - txtTitle.height()) / 2;
		add( txtTitle );

		items.clear();
		itemsChar.clear();

		for(int i = 9; i > 0; i--){
			placeItem(i);
		}

		RedButton btnCraft = new RedButton( Messages.get(this, "buttoncraft") ) {
			@Override
			protected void onClick() {
				craft();
				hide();
			}
		};
		btnCraft.setRect( 0, slotsHeight+TITLE_HEIGHT+3, slotsWidth, 20 );
		add( btnCraft );

		resize( slotsWidth, slotsHeight + TITLE_HEIGHT +23 );

		layoutTabs();
	}

	public void craft(){
		String craftcode = "";
		for(int i = itemsChar.size(); i > 0; i--){
			craftcode = craftcode + itemsChar.get(i-1);
		}
		itemsChar.clear();
		//Copper = O, Iron = I, Gold = D, Adamantium = A, Uranium = U, Cobalt = C, Stick = S, nothing = n
		if(craftcode.contains("IInnSnnSn")){
			items.clear();
			Dungeon.level.drop(new HandAxe(), Dungeon.hero.pos);
		} else if(craftcode.contains("CSDSnnDnn")){
			items.clear();
			Dungeon.level.drop(new Boomerang(), Dungeon.hero.pos);
		} else if(craftcode.contains("OOOnSnnSn")){
			items.clear();
			Dungeon.level.drop(new Pickaxe(), Dungeon.hero.pos);
		}  else if(craftcode.contains("nISnnSnnS")){
			items.clear();
			Dungeon.level.drop(new Tamahawk().quantity(2), Dungeon.hero.pos);
		} else if(craftcode.contains("nInSnSnOn")){
			items.clear();
			Dungeon.level.drop(new Knuckles(), Dungeon.hero.pos);
		} else if(craftcode.contains("nUnnUnnSn")){
			items.clear();
			Dungeon.level.drop(new RunicBlade(), Dungeon.hero.pos);
		} else if(craftcode.contains("nCnnCnnSn")){
			items.clear();
			Dungeon.level.drop(new Longsword(), Dungeon.hero.pos);
		} else if(craftcode.contains("nDnnSnnSn")){
			items.clear();
			Dungeon.level.drop(new Spear(), Dungeon.hero.pos);
		} else if(craftcode.contains("nAnnSnnSn")){
			items.clear();
			Dungeon.level.drop(new Glaive(), Dungeon.hero.pos);
		} else if(craftcode.contains("DIDDIDnSn")){
			items.clear();
			Dungeon.level.drop(new WarHammer(), Dungeon.hero.pos);
		} else if(craftcode.contains("CCnCSnnSn")){
			items.clear();
			Dungeon.level.drop(new Greataxe(), Dungeon.hero.pos);
		} else if(craftcode.contains("DADACADAD")){
			items.clear();
			Dungeon.level.drop(new Greatshield(), Dungeon.hero.pos);
		} else if(craftcode.contains("nAnnAnnSn")){
			items.clear();
			Dungeon.level.drop(new Greatsword(), Dungeon.hero.pos);
		} else if(craftcode.contains("nInnSnnSn")){
			items.clear();
			Dungeon.level.drop(new Mace(), Dungeon.hero.pos);
		} else if(craftcode.contains("nnnnOnnSn")){
			items.clear();
			Dungeon.level.drop(new Dirk(), Dungeon.hero.pos);
		} else if(craftcode.contains("nnnnInnSn")){
			items.clear();
			Dungeon.level.drop(new Dagger(), Dungeon.hero.pos);
		} else if(craftcode.contains("nInnInnSn")){
			items.clear();
			Dungeon.level.drop(new WornShortsword(), Dungeon.hero.pos);
		} else if(craftcode.contains("nOnnSnnOn")){
			items.clear();
			Dungeon.level.drop(new Quarterstaff(), Dungeon.hero.pos);
		} else if(craftcode.contains("nnnInISnS")){
			items.clear();
			Dungeon.level.drop(new Sai(), Dungeon.hero.pos);
		} else if(craftcode.contains("nIInInnSn")){
			items.clear();
			Dungeon.level.drop(new Scimitar(), Dungeon.hero.pos);
		} else if(craftcode.contains("nOnIOnOn")){
			items.clear();
			Dungeon.level.drop(new RoundShield(), Dungeon.hero.pos);
		} else if(craftcode.contains("nDnDUDnDn")){
			items.clear();
			Dungeon.level.drop(new Ankh(), Dungeon.hero.pos);
		} else if(craftcode.contains("nInIIInIn")){
			items.clear();
			Dungeon.level.drop(new Bomb().quantity(2), Dungeon.hero.pos);
		} else if(craftcode.contains("nUnnSnnUn")){
			items.clear();
			Dungeon.level.drop(Generator.random(Generator.Category.WAND), Dungeon.hero.pos);
		} else if(craftcode.contains("nCnnCnnCn")){
			items.clear();
			Dungeon.level.drop(new MagesStaff(), Dungeon.hero.pos);
		} else if(craftcode.contains("OnOOnOOOO")){
			items.clear();
			Dungeon.level.drop(new Honeypot.ShatteredPot(), Dungeon.hero.pos);
		} else if(craftcode.contains("nnnnDnnnn")){
			items.clear();
			Dungeon.level.drop(new Gold().quantity(10), Dungeon.hero.pos);
		}
	}

	@Override
	public void hide() {
		for(int i = items.size(); i > 0; i--){
			if(items.get(i-1) != null){
				Dungeon.level.drop(items.get(i-1), Dungeon.hero.pos);
			}
		}
		items.clear();
		super.hide();
	}

	protected void placeItem(Integer id) {
		
		int x = col * (SLOT_SIZE + SLOT_MARGIN);
		int y = TITLE_HEIGHT + row * (SLOT_SIZE + SLOT_MARGIN);
		
		add( new ItemButton( new Item(), id).setPos( x, y ) );
		items.add(null);
		itemsChar.add('n');
		
		if (++col >= nCols) {
			col = 0;
			row++;
		}
		
		count++;
	}
	
	@Override
	public void onMenuPressed() {
		if (listener == null) {
			hide();
		}
	}
	
	@Override
	public void onBackPressed() {
		if (listener != null) {
			listener.onSelect( null );
		}
		super.onBackPressed();
	}
	
	@Override
	protected int tabHeight() {
		return 20;
	}
	
	private class ItemButton extends ItemSlot {
		
		private static final int NORMAL		= 0x9953564D;
		private Item item;
		private ColorBlock bg;
		private Integer id;
		
		public ItemButton(Item item, Integer id ) {
			
			super( item );

			this.id = id-1;

			this.item = item;
			if (item instanceof Gold) {
				bg.visible = false;
			}
			
			width = height = SLOT_SIZE;

		}
		
		@Override
		protected void createChildren() {
			bg = new ColorBlock( SLOT_SIZE, SLOT_SIZE, NORMAL );
			add( bg );
			
			super.createChildren();
		}
		
		@Override
		protected void layout() {
			bg.x = x;
			bg.y = y;
			
			super.layout();
		}
		
		@Override
		public void item( Item item ) {
			this.item = item;
			super.item( item );
		}
		
		@Override
		protected void onTouchDown() {
			bg.brightness( 1.5f );
			Sample.INSTANCE.play( Assets.SND_CLICK, 0.7f, 0.7f, 1.2f );
		};
		
		protected void onTouchUp() {
			bg.brightness( 1.0f );
		};

		protected WndBag.Mode mode = WndBag.Mode.CRAFTING;
		
		@Override
		protected void onClick() {
			GameScene.selectItem(itemSelector, mode, Messages.get(WndCraft.class, "inventorytitle"));
		}

		protected WndBag.Listener itemSelector = new WndBag.Listener() {

			@Override
			public void onSelect(Item item) {
				if (item != null) {

					item.detach(Dungeon.hero.belongings.backpack);

					if(items.get(id) != null){
						Dungeon.level.drop(items.get(id), Dungeon.hero.pos);
					}

					itemsChar.set(id, item.toString().charAt(0));

					if(item instanceof Cobalt){
						item(new Cobalt());
						items.set(id, new Cobalt());
					} else if(item instanceof Uranium){
						item(new Uranium());
						items.set(id, new Uranium());
					} else if(item instanceof Adamantium){
						item(new Adamantium());
						items.set(id, new Adamantium());
					} else if(item instanceof DarkGold){
						item(new DarkGold());
						items.set(id, new DarkGold());
					} else if(item instanceof Iron){
						item(new Iron());
						items.set(id, new Iron());
					} else if(item instanceof Copper){
						item(new Copper());
						items.set(id, new Copper());
					} else if(item instanceof Stick){
						item(new Stick());
						items.set(id, new Stick());
					}


					QuickSlotButton.refresh();
				}
			}
		};
	}
	
	public interface Listener {
		void onSelect(Item item);
	}
}
