package egg.world.blocks.production;

import static mindustry.type.ItemStack.*;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import egg.content.EggItems;
import mindustry.Vars;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.units.UnitBlock;
import mindustry.world.draw.DrawBlock;
import mindustry.world.draw.DrawGlowRegion;

public class Incubator extends UnitBlock {
    public DrawIncubator drawer = new DrawIncubator();

    public Incubator(String name) {
        super(name);
        consumeItem(EggItems.egg);
        requirements(Category.units, with(EggItems.egg, 1));
        itemCapacity = 1;
    }

    @Override
    public void load() {
        super.load();

        drawer.load(this);
    }

    public class IncubatorBuild extends UnitBuild {
        public int currentPlan = 0;
        public float eggFadeIn, heat;

        @Override
        public void draw() {
            drawer.draw(this);
        }

        @Override
        public float warmup() {
            return power.status;
        }

        @Override
        public void updateTile(){
            eggFadeIn = Mathf.approachDelta(eggFadeIn, items.total(), 0.2f);
            heat = Mathf.approachDelta(heat, warmup(), 0.02f);
        }
    }

    public class DrawIncubator extends DrawBlock {
        public float glowScale = 5f, glowIntensity = 0.2f;
        public TextureRegion base, top, eggTexture;
        public DrawGlowRegion glow = new DrawGlowRegion(){{
            color = Color.valueOf("fc8d68");
            glowScale = 5f;
        }};

        public void draw(IncubatorBuild build) {
            glow.alpha = build.heat;

            Draw.rect(base, build.x, build.y);
            if (build.items.has(EggItems.egg)) {
                float intensity = 1 - (Mathf.absin(build.totalProgress(), glowScale, glow.alpha) * glowIntensity + 1f - glowIntensity) * build.warmup() * glow.alpha;
                Draw.color(new Color(intensity, intensity, intensity, build.eggFadeIn));
                Draw.rect(eggTexture, build.x, build.y, Vars.itemSize, Vars.itemSize);
                Draw.reset();
            }
            Draw.rect(top, build.x, build.y);
            glow.draw(build);
        }

        @Override
        public void load(Block block) {
            base = Core.atlas.find(block.name);
            top = Core.atlas.find(block.name + "-top");
            eggTexture = EggItems.egg.uiIcon;
            glow.load(block);
        }
    }
}
