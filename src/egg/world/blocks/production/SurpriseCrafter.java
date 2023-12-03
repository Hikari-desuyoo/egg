package egg.world.blocks.production;

import arc.math.Mathf;
import mindustry.type.ItemStack;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatValues;

public class SurpriseCrafter extends GenericCrafter {
    public ItemStack[] surpriseItems;
    public Float chance;

    public SurpriseCrafter(String name) {
        super(name);
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.output, StatValues.string("(?)"));
    }

    public class SurpriseCrafterBuild extends GenericCrafterBuild {
        @Override
        public void craft(){
            if(Mathf.chance(chance)){
                for(var surprise : surpriseItems){
                    for(int i = 0; i < surprise.amount; i++){
                        offload(surprise.item);
                    }
                }
            }
            super.craft();
        }
    }
}
