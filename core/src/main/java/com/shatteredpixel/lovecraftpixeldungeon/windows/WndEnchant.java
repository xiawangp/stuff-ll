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
import com.shatteredpixel.lovecraftpixeldungeon.items.DewVial;
import com.shatteredpixel.lovecraftpixeldungeon.items.Gold;
import com.shatteredpixel.lovecraftpixeldungeon.items.Item;
import com.shatteredpixel.lovecraftpixeldungeon.items.armor.Armor;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.lovecraftpixeldungeon.ui.ItemSlot;
import com.shatteredpixel.lovecraftpixeldungeon.ui.RedButton;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.audio.Sample;

public class WndEnchant extends WndTabbed {

	protected static final int COLS_P    = 3;
	protected static final int COLS_L    = 3;

	protected static final int SLOT_SIZE	= 28;
	protected static final int SLOT_MARGIN	= 1;

	protected static final int TITLE_HEIGHT	= 12;

	private Listener listener;
	private String title;

	private int nCols;
	private int nRows;

	protected int count;

	public WndEnchant() {

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

		int x1 = 30;
		int y1 = TITLE_HEIGHT + 5;

		int x2 = 30;
		int y2 = TITLE_HEIGHT + 5+28+5;

		final ItemButton itemButton1 = new ItemButton( new Item());
		final ItemButton itemButton2 = new ItemButton(null);
		if(Dungeon.hero.belongings.getItem(DewVial.class) != null && Dungeon.hero.belongings.getItem(DewVial.class).isFull()){
			itemButton2.item(Dungeon.hero.belongings.getItem(DewVial.class));
			add(itemButton1.setPos(x1, y1));
			add(itemButton2.setPos(x2, y2));
		} else {
			itemButton2.item(new Item());
			add(itemButton1.setPos(x1, y1));
			add(itemButton2.setPos(x2, y2));
		}

		RedButton btnCraft = new RedButton( Messages.get(this, "buttonenchant") ) {
			@Override
			protected void onClick() {
				if(itemButton1.item instanceof Weapon){
					((Weapon) itemButton1.item).enchant();
				} else if(itemButton1.item instanceof Armor){
					((Armor) itemButton1.item).inscribe();
				}
				if(itemButton2.item instanceof DewVial){
					((DewVial) itemButton2.item).empty();
				}
				hide();
			}
		};
		btnCraft.setRect( 0, slotsHeight+TITLE_HEIGHT+3, slotsWidth, 20 );
		if(Dungeon.hero.belongings.getItem(DewVial.class) != null && Dungeon.hero.belongings.getItem(DewVial.class).isFull()){
			add( btnCraft );
		}

		resize( slotsWidth, slotsHeight + TITLE_HEIGHT +23 );

		layoutTabs();
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
		
		public ItemButton( Item item) {
			
			super( item );

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

		protected WndBag.Mode mode = WndBag.Mode.ENCHANTABLE;
		
		@Override
		protected void onClick() {
			if(!(item instanceof DewVial)){
				GameScene.selectItem(itemSelector, mode, Messages.get(WndEnchant.class, "inventorytitle"));
			}
		}

		protected WndBag.Listener itemSelector = new WndBag.Listener() {
			@Override
			public void onSelect(Item item) {
				if(item != null){
					item(item);
				}
			}
		};
	}
	
	public interface Listener {
		void onSelect(Item item);
	}
}
