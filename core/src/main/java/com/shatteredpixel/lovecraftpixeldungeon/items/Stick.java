package com.shatteredpixel.lovecraftpixeldungeon.items;

import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;

public class Stick extends Item {

    {
        image = ItemSpriteSheet.STICK;
        stackable = true;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }
}
