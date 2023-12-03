package egg.content;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import static mindustry.type.ItemStack.*;

import egg.world.blocks.production.*;
import mindustry.world.draw.*;

public class EggBlocks {
    public static Block

    // crafting
    grainFilter,

    // units
    incubator;

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
    }
}
