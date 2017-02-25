package com.shatteredpixel.lovecraftpixeldungeon.items.shrooms;

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.Badges;
import com.shatteredpixel.lovecraftpixeldungeon.Statistics;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Speck;
import com.shatteredpixel.lovecraftpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.lovecraftpixeldungeon.items.Item;
import com.shatteredpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

public class Mushrooms extends Item{

    private static final float TIME_TO_EAT	= 3f;

    public static final String AC_EAT	= "EAT";

    public float energy = Hunger.HUNGRY;
    public String message = Messages.get(this, "eat_msg");

    public int hornValue = 1;

    {
        stackable = true;
        weight = 1;
        bones = false;
    }

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_EAT );
        return actions;
    }

    @Override
    public void execute( Hero hero, String action ) {

        super.execute( hero, action );

        if (action.equals( AC_EAT )) {

            detach( hero.belongings.backpack );

            (hero.buff( Hunger.class )).satisfy( energy );
            GLog.i( message );

            switch (hero.heroClass) {
                case WARRIOR:
                    if (hero.HP < hero.HT) {
                        hero.HP = Math.min( hero.HP + 5, hero.HT );
                        hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
                    }
                    break;
                case MAGE:
                    //1 charge
                    Buff.affect( hero, Recharging.class, 4f );
                    ScrollOfRecharging.charge( hero );
                    break;
                case ROGUE:
                case HUNTRESS:
                    break;
            }

            hero.sprite.operate( hero.pos );
            hero.busy();
            SpellSprite.show( hero, SpellSprite.FOOD );
            Sample.INSTANCE.play( Assets.SND_EAT );

            hero.spend( TIME_TO_EAT );

            Statistics.foodEaten++;
            Badges.validateFoodEaten();

        }
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }
}
