package com.shatteredpixel.lovecraftpixeldungeon.actors.mobs;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Levitation;
import com.shatteredpixel.lovecraftpixeldungeon.effects.MiGoTounge;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Pushing;
import com.shatteredpixel.lovecraftpixeldungeon.items.StatueOfPepe;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.enchantments.Unstable;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee.Whip;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.YigSprite;
import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.HashSet;

public class Kek extends Mob{

    {
        spriteClass = YigSprite.class;

        HP = HT = (Dungeon.hero.MH/2)+Dungeon.depth;
        defenseSkill = (Dungeon.hero.STR/2) + Dungeon.depth;

        EXP = 10*Dungeon.depth;
        baseSpeed = 3f;
        loot = new StatueOfPepe();
        lootChance = 10f;

        properties.add(Property.MINIBOSS);
    }

    protected Weapon weapon;

    public Kek() {
        super();

        weapon = new Whip().enchant(new Unstable());
        weapon.identify();

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
    protected boolean act() {
        Buff.affect(enemy, Levitation.class, Levitation.DURATION);
        if (state == HUNTING &&
                paralysed <= 0 &&
                enemy != null &&
                enemy.invisible == 0 &&
                Level.fieldOfView[enemy.pos] &&
                Dungeon.level.distance( pos, enemy.pos ) < 5 &&
                !Dungeon.level.adjacent( pos, enemy.pos ) &&
                Random.Int(3) == 0 &&
                chain(enemy.pos)) {
            return false;
        } else {
            return super.act();
        }
    }
    private boolean chain(int target){
        if (enemy.properties().contains(Property.IMMOVABLE) || Randomer.randomBoolean())
            return false;

        Ballistica chain = new Ballistica(pos, target, Ballistica.PROJECTILE);

        if (chain.collisionPos != enemy.pos || chain.path.size() < 2 || Level.pit[chain.path.get(1)])
            return false;
        else {
            int newPos = -1;
            for (int i : chain.subPath(1, chain.dist)){
                if (!Level.solid[i] && Actor.findChar(i) == null){
                    newPos = i;
                    break;
                }
            }

            if (newPos == -1){
                return false;
            } else {
                final int newPosFinal = newPos;
                sprite.parent.add(new MiGoTounge(sprite.center(), enemy.sprite.center(), new Callback() {
                    public void call() {
                        Actor.addDelayed(new Pushing(enemy, enemy.pos, newPosFinal, new Callback(){
                            public void call() {
                                enemy.pos = newPosFinal;
                                Dungeon.level.press(newPosFinal, enemy);
                                Cripple.prolong(enemy, Cripple.class, 4f);
                                if (enemy == Dungeon.hero) {
                                    Dungeon.hero.interrupt();
                                    Dungeon.observe();
                                    GameScene.updateFog();
                                }
                            }
                        }), -1);
                        next();
                    }
                }));
            }
        }
        return true;
    }


    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, Dungeon.depth + weapon.defenseFactor(null));
    }

    @Override
    public int defenseProc( Char enemy, int damage ) {
        //TODO: SPAWN FROGS
        return super.defenseProc(enemy, damage);
    }

    @Override
    public void die( Object cause ) {
        super.die( cause );
        GLog.n(Messages.get(this, "die"));
    }

    private static final HashSet<Class<?>> IMMUNITIES = new HashSet<>();
    static {

    }

    @Override
    public HashSet<Class<?>> immunities() {
        return IMMUNITIES;
    }

}
