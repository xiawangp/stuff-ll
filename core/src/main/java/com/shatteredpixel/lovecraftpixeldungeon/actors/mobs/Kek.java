package com.shatteredpixel.lovecraftpixeldungeon.actors.mobs;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.items.Generator;
import com.shatteredpixel.lovecraftpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.YigSprite;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

import java.util.HashSet;

public class Kek extends Mob{

    {
        spriteClass = YigSprite.class;

        HP = HT = (Dungeon.hero.MH/2)+Dungeon.depth;
        defenseSkill = (Dungeon.hero.STR/2) + Dungeon.depth;

        EXP = 10*Dungeon.depth;
        baseSpeed = 2f;
        loot = Generator.random(Generator.Category.WEP_T5).upgrade(Dungeon.hero.MH/Dungeon.hero.STR);
        lootChance = 0.3f*Dungeon.depth;

        properties.add(Property.MINIBOSS);
    }

    protected Weapon weapon;

    public Kek() {
        super();

        do {
            weapon = (Weapon)Generator.random( Generator.Category.WEAPON );
        } while (!(weapon instanceof MeleeWeapon) || weapon.cursed);

        weapon.identify();
        weapon.enchant( Weapon.Enchantment.random() );

        HP = HT = 15 + Dungeon.depth * 5;
        defenseSkill = 4 + Dungeon.depth;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( weapon.min(), weapon.max() );
    }

    @Override
    public int attackSkill( Char target ) {
        return (int)((Dungeon.hero.STR + Dungeon.depth) * weapon.ACC);
    }

    @Override
    protected float attackDelay() {
        return weapon.DLY;
    }

    @Override
    protected boolean canAttack(Char enemy) {
        return Dungeon.level.distance( pos, enemy.pos ) <= weapon.RCH;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, Dungeon.depth + weapon.defenseFactor(null));
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        if (Random.Int(4) == 0) {
            if(enemy == Dungeon.hero){
                try{
                    Hero hero = Dungeon.hero;
                    KindOfWeapon weapon = hero.belongings.weapon;

                    hero.belongings.weapon = null;
                    Dungeon.quickslot.clearItem(weapon);
                    weapon.updateQuickslot();
                    Dungeon.level.drop(weapon, hero.pos).sprite.drop();
                    GLog.n(Messages.get(this, "disarm"));
                } catch(Exception e) {

                }
            } else {
                enemy.damage(enemy.damageRoll(), this);
            }
        }
        return damage;
    }

    @Override
    public int defenseProc( Char enemy, int damage ) {
        return super.defenseProc(enemy, damage);
    }

    @Override
    public void die( Object cause ) {
        super.die( cause );
    }

    private static final HashSet<Class<?>> IMMUNITIES = new HashSet<>();
    static {

    }

    @Override
    public HashSet<Class<?>> immunities() {
        return IMMUNITIES;
    }

}
