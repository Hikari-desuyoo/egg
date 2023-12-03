package egg.content;

import mindustry.gen.EntityMapping;
import mindustry.type.UnitType;

public class EggUnitTypes {
    public static UnitType chicken;

    static {
        EntityMapping.nameMap.put("egg-chicken", EntityMapping.idMap[4]);
    }

    public static void load() {
        chicken = new UnitType("chicken") {
            {
                speed = 1f;
                hitSize = 8f;
                health = 10;
            }
        };
    }
}
