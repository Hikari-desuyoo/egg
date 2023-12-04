package egg.content;

import java.util.ArrayList;
import java.util.Arrays;

import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Position;
import arc.math.geom.Vec2;
import arc.util.Log;
import arc.util.Time;
import egg.world.blocks.projector.AirPurifier.AirPurifierBuild;
import mindustry.Vars;
import mindustry.ai.types.GroundAI;
import mindustry.ai.types.SuicideAI;
import mindustry.content.Fx;
import mindustry.entities.units.UnitController;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.logic.LExecutor.Var;
import mindustry.type.UnitType;
import mindustry.world.Tile;

public class EggUnitTypes {
    public static UnitType chicken;

    public static void load() {
        chicken = new ChickenUnitType("chicken") {
            {
                speed = 1f;
                aiController = SuicideAI::new;
                itemCapacity = 1;
                hitSize = 8f;
                mechStepParticles = true;
                health = 10;
                constructor = MechUnit::create;
                drawCell = false;
                deathExplosionEffect = Fx.dropItem;
                deathSound = Sounds.buttonClick;
            }
        };
    }

    // public static class ChickenUnit extends MechUnit implements Boundedc,
    // Builderc, Drawc, ElevationMovec, Entityc, Flyingc, Healthc, Hitboxc, Itemsc,
    // Mechc, Minerc, Physicsc, Posc, Rotc, Shieldc, Statusc, Syncc, Teamc, Unitc,
    // Velc, Weaponsc {
    // public float lastEgg;

    // ChickenUnit() {
    // super();
    // lastEgg = Time.delta;
    // }

    // public void hatchEgg() {
    // addItem(EggItems.egg);
    // lastEgg = Time.delta;
    // }

    // public int classId() {
    // return chickenId;
    // }

    // public void update() {
    // if (shouldHatchEgg()) {
    // hatchEgg();
    // }
    // }

    // public boolean shouldHatchEgg() {
    // return stack.amount == 0 && Time.delta - lastEgg > 10 && Mathf.chance(0.01d);
    // }
    // }

    public static class ChickenUnitType extends UnitType {
        public int turningSens = 90;

        public ChickenUnitType(String name) {
            super(name);
            baseRegion = new TextureRegion();
        }

        public void updateMovement(Unit unit) {
            float desperate = 1;
            if (unit.hasEffect(EggStatusEffects.intoxicated)){desperate = 4;}

            Building solid = Vars.indexer.findTile(unit.team, unit.x, unit.y, 5, b -> b.block.solid);

            double distance = 100;
            if (solid != null) {
                float deltaX = solid.x - unit.x;
                float deltaY = solid.y - unit.y;
                distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            }

            if (Mathf.chance(0.01 * desperate) && distance > 2) {
                Vec2 vec = new Vec2(3, 3);
                vec.setAngle(unit.rotation());
                unit.movePref(vec);
            }

            if (Mathf.chance(0.1 * desperate)) {
                int directionX, directionY;

                if (solid == null) {
                    directionX = ((int) unit.x % 2 == 0 ? -1 : 1);
                    directionY = ((int) unit.y % 2 == 0 ? -1 : 1);
                } else {
                    directionX = ((int) solid.x > unit.x ? -1 : 1);
                    directionY = ((int) solid.y > unit.y ? -1 : 1);
                }

                unit.lookAt(
                        unit.x + directionX * 10,
                        unit.y + directionY * 10);
            }
        }

        public void update(Unit unit) {
            Building build = Vars.indexer.findTile(
                    unit.team, unit.x, unit.y, EggBlocks.airPurifier.radius,
                    b -> {
                        if (!(b instanceof AirPurifierBuild)) {
                            return false;
                        }
                        float deltaX = b.x - unit.x;
                        float deltaY = b.y - unit.y;
                        AirPurifierBuild airB = (AirPurifierBuild) b;
                        return airB.realRadius() >= Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                    });

            if (build == null) {
                unit.apply(EggStatusEffects.intoxicated);
            } else {
                unit.unapply(EggStatusEffects.intoxicated);
            }

            if(Mathf.chance(0.001)){
                unit.addItem(EggItems.egg);
            }

            updateMovement(unit);
        }

        public void draw(Unit unit) {
            baseRegion.width = baseRegion.height = 0;
            super.draw(unit);
        }

        public <T extends Unit & Legsc> void drawLegs(T unit) {
        }
    }
}
