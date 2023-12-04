package egg;

import egg.content.EggBlocks;
import egg.content.EggItems;
import egg.content.EggStatusEffects;
import egg.content.EggUnitTypes;
import mindustry.mod.*;

public class EggMod extends Mod {

    public EggMod() {
    }

    @Override
    public void loadContent() {
        EggStatusEffects.load();
        EggUnitTypes.load();
        EggItems.load();
        EggBlocks.load();
    }

}
