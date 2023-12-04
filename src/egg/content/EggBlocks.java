package egg.content;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;

import static mindustry.type.ItemStack.*;

import egg.world.blocks.production.*;
import egg.world.blocks.projector.AirPurifier;
import mindustry.world.draw.*;
import mindustry.world.meta.BuildVisibility;

public class EggBlocks {
    public static Block grainFilter, incubator;
    public static AirPurifier airPurifier;

    public static void load() {
        grainFilter = new SurpriseCrafter("grain-filter") {
            {
                requirements(Category.crafting, with(Items.copper, 30, Items.lead, 25));
                craftEffect = Fx.drillSteam;
                hasPower = true;
                craftTime = 160f;
                outputItem = new ItemStack(Items.scrap, 1);
                surpriseItems = with(EggItems.egg, 1);
                chance = 0.01f;

                drawer = new DrawMulti(
                        new DrawDefault(),
                        new DrawRegion("-rotator", 10, true),
                        new DrawRegion("-top"));

                consumeItems(with(Items.sand, 10));
                consumePower(5);
            }
        };

        incubator = new Incubator("incubator") {
            {
                consumePower(1.2f);
            }
        };

        airPurifier = new AirPurifier("air-purifier") {
            {
                requirements(Category.units, with(Items.copper, 30, Items.lead, 25));
                size = 2;
                buildVisibility = BuildVisibility.shown;
                solid = true;
                radius = 50f;
                consumePower(5);
            }
        };
    }
}
