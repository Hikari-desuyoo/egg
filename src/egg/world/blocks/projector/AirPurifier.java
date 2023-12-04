package egg.world.blocks.projector;

import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import mindustry.Vars;
import mindustry.entities.Units;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import mindustry.logic.Ranged;
import mindustry.world.Block;
import mindustry.world.draw.DrawBlock;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawRegion;

public class AirPurifier extends Block {
    public float radius = 50f;
    public int sides = 8;
    public float shieldRotation = 0f;
    public DrawBlock drawer = new DrawMulti(
            new DrawDefault(),
            new DrawRegion("-rotator", 5, true),
            new DrawRegion("-top"));

    public AirPurifier(String name) {
        super(name);
        update = true;
        solid = true;
        // group = BlockGroup.projectors;
        hasPower = true;
        hasItems = true;
        // envEnabled |= Env.space;
        // ambientSound = Sounds.shield;
        // ambientSoundVolume = 0.08f;
    }

    @Override
    public void setBars() {
        super.setBars();
        removeBar("shield");
        super.load();
    }

    @Override
    public void load() {
        super.load();
        drawer.load(this);
    }

    public class AirPurifierBuild extends Building implements Ranged {
        public float buildup, radscl, warmup;

        @Override
        public float range() {
            return realRadius() * radscl;
        }

        public float realRadius() {
            return radius * radscl;
        }

        public void draw() {
            super.draw();
            drawer.draw(this);

            if (buildup > 0f) {
                Draw.alpha(buildup * 0.2f);
                Draw.z(Layer.blockAdditive);
                Draw.blend(Blending.additive);
                Draw.blend();
                Draw.z(Layer.block);
                Draw.reset();
            }

            drawShield();
        }

        @Override
        public void updateTile() {
            warmup = Mathf.lerpDelta(warmup, efficiency, 0.1f);
            radscl = Mathf.lerpDelta(radscl, warmup, 0.05f);
        }

        public void drawShield() {
            float radius = realRadius();

            if (radius > 0.001f) {
                Draw.color(Color.white);

                if (Vars.renderer.animateShields) {
                    Draw.z(Layer.shields);
                    Fill.circle(x, y, radius);
                } else {
                    Draw.z(Layer.shields);
                    Lines.stroke(1.5f);
                    Draw.alpha(0.09f);
                    Fill.circle(x, y, radius);
                    Draw.alpha(1f);
                    Lines.circle(x, y, radius);
                    Draw.reset();
                }
            }

            Draw.reset();
        }
    }
}
