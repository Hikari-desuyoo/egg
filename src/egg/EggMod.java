package egg;

import egg.content.EggBlocks;
import egg.content.EggItems;
import egg.content.EggUnitTypes;
import mindustry.mod.*;

public class EggMod extends Mod {

    public EggMod() {
    }

    @Override
    public void loadContent() {
        EggUnitTypes.load();
        EggItems.load();
        EggBlocks.load();
    }

}
