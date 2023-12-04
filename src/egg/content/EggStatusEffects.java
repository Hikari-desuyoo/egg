package egg.content;

import mindustry.content.Fx;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;

public class EggStatusEffects {
    public static StatusEffect intoxicated;

    public static void load() {
        intoxicated = new StatusEffect("intoxicated") {
            {
                color = Pal.spore;
                effect = Fx.sapped;
                damage = 0.01f;
            }
        };
    }
}
