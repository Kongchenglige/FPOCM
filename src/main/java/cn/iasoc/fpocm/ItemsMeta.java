package cn.iasoc.fpocm;

import org.bukkit.configuration.MemorySection;

import java.util.HashMap;
import java.util.Map;

// Step 1: Define a Cache Class
public class ItemsMeta {
    private static final Map<String, Object> cache = new HashMap<>();

    public void load(FPOCM plugin) {
        // Assuming getConfig() returns a configuration object you can iterate over
        plugin.getConfig().getKeys(true).forEach(key -> {
            Object value = plugin.getConfig().get(key);
            if (value instanceof MemorySection) {
                return;
            }
            cache.put(key, value);
        });
    }

    public static double get(String key) {
        return (double) cache.get(key);
    }

}

//public class ItemsMetas {
//    private Map<String, Object> cache = new HashMap<>();
//    //define armors & swords meta
//
//    public static final String[] armors = {
//            "HELMET",
//            "CHESTPLATE",
//            "LEGGINGS",
//            "BOOTS"
//    };
//    public static final String[] swords = {
//            "WOODEN_SWORD",
//            "STONE_SWORD",
//            "IRON_SWORD",
//            "GOLDEN_SWORD",
//            "DIAMOND_SWORD",
//            "NETHERITE_SWORD"
//    };
//}
