package com.shatteredpixel.lovecraftpixeldungeon.items.shrooms;

import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;

import java.util.ArrayList;

public class OrangeShroom extends Mushrooms{
    {
        image = ItemSpriteSheet.SHROOM_ORANGE;
        energy = Hunger.HUNGRY;
        hornValue = 1;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);

        if (action.equals(AC_EAT)) {

            fire(hero);

            GLog.w(Messages.get(this, "burn"));
        }
    }

    public void fire(Hero hero){
        ArrayList<Char> affected = new ArrayList<>();

        for(int i = hero.visibleEnemies(); i > 0; i--){
            affected.add(hero.visibleEnemy(i));
        }

        for(int a = affected.size(); a > 0; a--) {
            Char target = affected.get(a - 1);
            GameScene.add(Blob.seed(target.pos, 2, Fire.class));
            Buff.affect( target, Light.class, Light.DURATION );
        }

        GameScene.add(Blob.seed(hero.pos, 2, Fire.class));
        Buff.affect( hero, Light.class, Light.DURATION );
        Buff.affect( hero, Bless.class, Light.DURATION );

        hero.decreaseMentalHealth(affected.size());
    }

    @Override
    public int price() {
        return 100 * quantity;
    }
}
